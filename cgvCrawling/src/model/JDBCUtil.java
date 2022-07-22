package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtil {
	// JDBCUTIL
	// 1) C R R U D 에서 쓰임
	// 2) DAO가 증가하면 또 CRUD도 증가됨 -> ...
	// => CTRL+C,CTRL+V
	//		: 코드 재사용 중복코드 최소화 ,모듈화, Util클래스 생성!
	// Utull류 클래스
	// :대부분 static 을 사용하는 이유는 객체가 아닌 로직을 담아놓기위해 사용하기 떄문에
	
	static final String driverName="oracle.jdbc.driver.OracleDriver";
	static final String url="jdbc:oracle:thin:@localhost:1521:xe";
	static final String user="jung";
	static final String password="1234";
    
	
   public static Connection connect() {
	   Connection conn =null;
	   try {
		Class.forName(driverName);
		
		conn = DriverManager.getConnection(url, user, password);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   
	   return conn;
		
	}
	public static void disconnect(PreparedStatement pstmt, Connection conn) {
		try {
			pstmt.close();
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	}
	