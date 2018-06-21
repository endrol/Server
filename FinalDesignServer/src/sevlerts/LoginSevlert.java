package sevlerts;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import serviceS.serverListener;
import serviceS.testSocketListener;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.log.LogUtils;

import functionForDataBase.DBUtil;

public class LoginSevlert extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public LoginSevlert() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String code = "";  
        String message = "";
		
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		log(account + " ; " + password);
		
		Connection connect = DBUtil.getConnect();
		
		try {
			Statement statement = (Statement) connect.createStatement();
			String sql = "select account from " + DBUtil.TABLE_PASSWORD 
					+ " where account='" + account + "'"+ 
					"' and password='" + password + "'";
			ResultSet resultSet = statement.executeQuery(sql);//to find the account
			if(resultSet.next()){
				code = "200";
				message = "success";
			}else{
				code = "100";  
                message = "failed to find this account";
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		response.getWriter().append("code: ").append(code).append(";\n message: ")
		.append(message);
		
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String code = "";
        
		String account ;
		String password;
		
		account = request.getParameter("account");
		password = request.getParameter("password");
		
		Connection connect = DBUtil.getConnect();
		
		try {
			Statement statement = (Statement) connect.createStatement();
			String sql = "select account from " + DBUtil.TABLE_PASSWORD 
					+ " where account='" + account + "'"+ 
					" and password='" + password + "'";
			ResultSet resultSet = statement.executeQuery(sql);//find this account
			if(resultSet.next()){
				code = "SUCCESS";
				new serverListener().start();
//				new testSocketListener().start();
			}else{
				code = "FAILED";  
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		log("your account is "+account);
		log("your password is "+password);
		
		response.getWriter().append(code);
		
		System.out.println("hello");
			
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
