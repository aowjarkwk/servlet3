package servlet3_LoginJoinDB;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	//정적 함수 지정
	public static Connection getConnection() {
		
		Connection conn = null;
		
		try {
			String db_id = "ezen";
			String db_pw = "1234";
			String url = "jdbc:oracle:thin:@localhost:1521:xe"; //고정 xe:express버전 orcl:일반버전
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			conn = DriverManager.getConnection(url,db_id,db_pw);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
}
