package servlet3_LoginJoinDB;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
//Database Access Object
public class MemberDao {
	//로그인 처리
	public static int LoginMember( HttpServletRequest request ) throws SQLException {
		Connection conn = null; //DB연결 클래스
		ResultSet rs = null; //검색결과를 담는 클래스
		Statement stmt = null; //쿼리(SQL)을 전송하는 클래스
		String custno = request.getParameter("custno");
		String custname = request.getParameter("custname");
		conn = DBConnection.getConnection();
		//SQL 문자열 
		String query = "SELECT * FROM users_table WHERE custno=" + custno;
		System.out.println( query );
		stmt = conn.createStatement();
		rs = stmt.executeQuery(query);
		int result = 0;  //1 : 로그인 성공, 0 : 아이디없음, 2 : 이름없음
		int check_no = 0;
		String check_name = null;
		//next() 다음값이 있는지 T/F 리턴
		while( rs.next() ) {
			check_no = rs.getInt("custno");
			check_name = rs.getString("custname");
			System.out.println( "check_no:" + check_no );
			System.out.println( "check_name:" + check_name );
			if( check_name.equals( custname ) ) {
				//아이디와 이름 모두 동일
				System.out.println("로그인 성공!");
				//세션에 저장
				HttpSession session = request.getSession();
				session.setAttribute("custno", check_no);
				session.setAttribute("custname", custname);
				result = 1;
			}else {
				//이름만 틀림
				result = 2; //이름 없음.
			}
		}
		return result;
	}
	//회원가입 처리
	public static int insertMember( HttpServletRequest request ) throws UnsupportedEncodingException {
		Connection conn = null; //DB연결 클래스
		ResultSet rs = null; //검색결과를 담는 클래스
		Statement stmt = null; //쿼리(SQL)을 전송하는 클래스
		//String custno = request.getParameter("custno");
		String custname = request.getParameter("custname");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String grade = request.getParameter("grade");
		String city = request.getParameter("city");
		String joindate = request.getParameter("joindate");
		System.out.println("joindate:"+joindate);
		conn = DBConnection.getConnection();
		//SQL 문자열 
		//insert into users_table( custno, custname, phone, address, joindate, grade, city ) 
	    //  values ( 1001, '홍길동', '010-2222-3333', '한양', '20210719', 'A', '01' );
		String query = "INSERT INTO users_table( custno, custname, phone, address, joindate, grade, city )" + 
				" VALUES ( users_table_seq.nextval, "
				+ "'" + custname + "'" + ","
				+ "'" + phone    + "'" + ","
				+ "'" + address + "'" + ","
				+ "'" + joindate + "'" + ","
				+ "'" + grade + "'" + ","
				+ "'" + city + "'" + ")"; //세미콜로 없애면 됨.
		//INSERT INTO users_table( custno, custname, phone, address, joindate, grade, city ) 
		//VALUES ( users_table_seq.nextval, '홍길동','010-1111-2222','강남구','2021-07-19','A','01');
		System.out.println( "query:" + query);
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery( query );
//			while( rs.next() ) {
//				System.out.println( "rs.next()" );
//			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 2; //실패
		}
		return 1; //성공
	}
}