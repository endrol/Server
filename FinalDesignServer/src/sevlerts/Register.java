package sevlerts;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import functionForDataBase.DBUtil;

public class Register extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public Register() {
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
		
		String account = request.getParameter("account");  
		String password = request.getParameter("password");
		log("account is "+account+" password is "+password);
		
		String resCode = "";  
        String resMsg = "";  
        String userId = "";
        
        try {
			Connection connect = DBUtil.getConnect(); //connect mysql
			Statement statement = (Statement) connect.createStatement();
			ResultSet resultSet;
			
			String sqlQuery = "select * from " + DBUtil.TABLE_PASSWORD +" where account ='"
					+account+"'";
			resultSet = statement.executeQuery(sqlQuery); 
			if(resultSet.next()){
				resCode = "201";
				resMsg = "find one";
				userId = "";
			}else{
				//register a new account
				String sqlInsertPass = "insert into "+DBUtil.TABLE_PASSWORD + 
						"(account,password) values('"+account+"','"+password+"')"; 
				int row1 = statement.executeUpdate(sqlInsertPass);
				if(row1 == 1){
//					String sqlQueryId = "select userId from " + DBUtil.TABLE_PASSWORD + 
//							" where account='" + account + "'";
//					ResultSet result2 = statement.executeQuery(sqlQueryId);
					resCode = "100";
					resMsg = "insert success";
					log("insert success");
				}else{
					resCode = "200";
					resMsg = "insert failed";  
	                userId = "";
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			 e.printStackTrace(); 
		}
        
        HashMap<String, String> map = new HashMap<String, String>();  
        map.put("resCode", resCode);  
        map.put("resMsg", resMsg);  
        map.put("userId", userId);
        
        response.setContentType("text/html;charset=utf-8"); 
        PrintWriter pw = response.getWriter(); 
        pw.println(map.toString()); // 
        pw.flush();

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
		doGet(request, response);
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
