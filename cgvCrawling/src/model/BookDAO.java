package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookDAO {
   Connection conn;
   PreparedStatement pstmt;
   final String sql_insert = "INSERT INTO BOOK VALUES((SELECT NVL(MAX(BID),0)+1 FROM BOOK),?,?)";
   final String sql_selectAll = "SELECT * FROM CGV A, BOOK B WHERE A.CID=B.CID AND MID=? ORDER BY BID ASC";
   final String sql_delete = "DELETE FROM BOOK WHERE MID=? OR BID=?";
   
   public boolean insert(BookVO vo) {
      conn = JDBCUtil.connect();
      try {
         pstmt = conn.prepareStatement(sql_insert);
         pstmt.setInt(1, vo.getCid()); // CGV PK   
         pstmt.setString(2, vo.getMid()); // MEMBER PK
         pstmt.executeUpdate();
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
         return false;
      } finally {
         JDBCUtil.disconnect(pstmt, conn); 
      }
      return true;
   }
   
   public ArrayList<BookVO> selectAll(BookVO vo) {
      ArrayList<BookVO> datas = new ArrayList<BookVO>();
      conn = JDBCUtil.connect();
      try {
         pstmt = conn.prepareStatement(sql_selectAll);
         pstmt.setString(1, vo.getMid()); // 외래키 CID로 이퀄조인 후 MID로 찾아서 해당 회원의 예매정보(영화정보) 반환
         ResultSet rs = pstmt.executeQuery();
         while (rs.next()) {
            BookVO data = new BookVO();
            data.setCid(rs.getInt("CID"));
            data.setBid(rs.getInt("BID"));
            data.setMid(rs.getString("MID"));
            data.setTitle(rs.getString("TITLE"));
            data.setImage(rs.getString("IMAGE"));
            data.setGenre(rs.getString("GENRE"));
            data.setBookCnt(rs.getInt("BOOKCNT"));
            datas.add(data);
         }
         rs.close();
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      return datas;
   }
   
   public boolean delete(BookVO vo) {
      conn = JDBCUtil.connect();
      try {
         pstmt = conn.prepareStatement(sql_delete);
         pstmt.setString(1, vo.getMid());
         pstmt.setInt(2, vo.getBid());
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
   
}
   
   
   
