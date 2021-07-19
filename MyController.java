package servlet3_LoginJoinDB;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//URI(URL) 패턴 지정(경로설정)
@WebServlet ("/")
//@WebServlet (urlPatterns= {"/","/main"})
public class MyController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doProcess(request,response);
		System.out.println("doGet 요청을 받음.");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request,response);
		System.out.println("doPost 요청을 받음.");
	}


	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		super.service(request, response); //살려놓는다.
		                                  //코멘트아웃하면 doGet/doPost요청안함.
		System.out.println("service 요청을 받음.");
	}
	void doProcess(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		System.out.println("doProcess 요청을 받음.");
		request.setCharacterEncoding("utf-8");
		//요청 URL : http://localhost:8082/servlet3_LoginJoinDB/
		//요청 URL : http://localhost:8082/servlet3_LoginJoinDB/MainForm
		//요청 URL : http://localhost:8082/servlet3_LoginJoinDB/JoinForm    UI있음.
		//요청 URL : http://localhost:8082/servlet3_LoginJoinDB/JoinAction  UI없고 제어만
		//요청 URL : http://localhost:8082/servlet3_LoginJoinDB/LoginForm
		
		//요청 URL을 파싱(분석)한다.
		String requestURI = request.getRequestURI();
		// "/servlet3_LoginJoinDB/"
		System.out.println("requestURI:"+requestURI);
		int cmdIndex = requestURI.lastIndexOf("/")+1;
		String command = requestURI.substring(cmdIndex); // "", "MainForm", "JoinForm"
		System.out.println("command:"+command);
		//기본 URI
		//요청 URL : http://localhost:8082/servlet3_LoginJoinDB/
		if(command.equals("")||command.equals("/")) {
			response.sendRedirect("MainForm");
		//메인화면
			//요청 URL : http://localhost:8082/servlet3_LoginJoinDB/MainForm
		}else if(command.equals("MainForm")) {
			//JSP페이지를 전송해준다.
			RequestDispatcher dp = request.getRequestDispatcher("/MainForm.jsp");
			dp.forward(request, response);
		}else if(command.equals("LoginForm")) {
			RequestDispatcher dp = request.getRequestDispatcher("/LoginForm.jsp");
			dp.forward(request, response);
		}else if(command.equals("LoginAction")) {
			request.setCharacterEncoding("utf-8");
			//아이디,이름으로 로그인 처리를 해줌.
			
			int result = 0;//1 : 로그인 성공, 0:아이디가 없음 , 2:이름없음
			
			try {
				result = MemberDao.LoginMember(request);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(result == 1) { //성공
				response.sendRedirect("MainForm");
			}else if(result ==0 ||result==2) { //실패
				response.sendRedirect("LoginForm");
			}
			
			
		}else if(command.equals("LogoutAction")) {
			HttpSession session = request.getSession();
			session.invalidate(); //세션 종료
			response.sendRedirect("MainForm");
		//회원가입 화면	
		}else if(command.equals("JoinForm")) {
			RequestDispatcher dp = request.getRequestDispatcher("/JoinForm.jsp");
			dp.forward(request, response);
		}
		
		//회원가입 액션
		else if(command.equals("JoinAction")) {
			int result = 0;//1 : 회원가입 성공, 2:실패
			
			result = MemberDao.insertMember(request);
			
			if(result == 1) { //성공
				response.sendRedirect("LoginForm");
			}else if(result==2) { //실패
				response.sendRedirect("JoinForm");
			}
		}
	}//end of doProcess

}//end of class
