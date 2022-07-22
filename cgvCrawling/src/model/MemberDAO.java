package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDAO {
	CgvDAO mDAO;

	Connection conn;
	PreparedStatement pstmt;
	// CRUD
	// 회원가입
	// 로그인
	// 로그아웃
	// 마이페이지에서 내정보 확인
	// 회원정보변경
	// 회원탈퇴
	final String sql_login = "SELECT * FROM MEMBER WHERE MID=?"; // 로그인
	final String sql_user = "INSERT INTO MEMBER VALUES(?,?,?,?)"; // 회원가입
	final String sql_delete = "DELETE FROM MEMBER WHERE MID=? AND MPW=?";// 회원탈퇴
	final String sql_update = "UPDATE MEMBER SET MBOOK = ? WHERE MID=?";// 최근 예매한 영화정보 저장
	final String sql_hasSame = "SELECT COUNT(*) AS CNT FROM MEMBER WHERE MID=?"; // 중복 아이디가 있는지 확인 
	final String sql_selectOne = "SELECT * FROM MEMBER WHERE MID=?";

	public MemberVO login(MemberVO vo) { // 로그인
		conn = JDBCUtil.connect();
		try {
			pstmt = conn.prepareStatement(sql_login);
			pstmt.setString(1, vo.getMid());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				if (rs.getString("mpw").equals(vo.getMpw())) { // mpw 가져와서 vo객체로 받은 mpw와 같은지 확인 > 같다면 실행
					MemberVO data = new MemberVO();
					data.setMid(rs.getString("mid"));
					data.setMname(rs.getString("mname"));
					data.setMpw(rs.getString("mpw"));
					System.out.println("로그: 로그인 성공!");
					return data;
				}
				System.out.println("로그: 비밀번호 불일치로 로그인 실패...");
				return null;
			}
			System.out.println("로그: 회원정보없음 | 로그인 실패...");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public boolean user(MemberVO vo) { // 회원가입 > vo객체로 받은 정보를 set > insert
		conn = JDBCUtil.connect();
		try {
			pstmt = conn.prepareStatement(sql_user);
			pstmt.setString(1, vo.getMid());
			pstmt.setString(2, vo.getMpw());
			pstmt.setString(3, vo.getMname());
			pstmt.setString(4, vo.getMbook());
			int res = pstmt.executeUpdate();
			if (res == 0) {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return true;
	}

	public boolean update(MemberVO vo) { // 최근예매한 영화정보 저장
		conn = JDBCUtil.connect();
		try {
			pstmt = conn.prepareStatement(sql_update); 
			pstmt.setString(1, vo.getMbook());
			pstmt.setString(2, vo.getMid());
			int res = pstmt.executeUpdate();
			if (res == 0) {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return true;
	}

	public boolean delete(MemberVO vo) { // 회원탈퇴
		conn = JDBCUtil.connect();
		try {
			pstmt = conn.prepareStatement(sql_delete);
			pstmt.setString(1, vo.getMid());
			pstmt.setString(2, vo.getMpw());
			int res = pstmt.executeUpdate();
			if (res == 0) {

				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return true;
	}

	public boolean hasSame(MemberVO vo) { // 중복 ID 검사
		conn = JDBCUtil.connect();
		try {
			pstmt = conn.prepareStatement(sql_hasSame);
			pstmt.setString(1, vo.getMid());
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int cnt = rs.getInt("CNT");

			if (cnt >= 1) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true;
		} finally {
			JDBCUtil.disconnect(pstmt, conn);
		}
		return false;

	}

	public MemberVO selectOne(MemberVO vo) {
		conn = JDBCUtil.connect();
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql_selectOne);
			pstmt.setString(1, vo.getMid());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				MemberVO data = new MemberVO();
				data.setMid(rs.getString("MID"));
				data.setMpw(rs.getString("MPW"));
				data.setMname(rs.getString("MNAME"));
				data.setMbook(rs.getString("MBOOK"));
				return data;
			} else {
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JDBCUtil.disconnect(pstmt, conn);
		}
	}

}
