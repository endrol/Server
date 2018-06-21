package functionForDataBase;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Driver;
import com.mysql.jdbc.log.Log;

public class DBUtil {
	
	//table
	public static final String TABLE_PASSWORD = "user_account_password";
	
	public static Connection getConnect(){
		String url = "jdbc:mysql://localhost:3306/finaldesigntest";
		Connection connector = null;
		try {
			Class.forName("com.mysql.jdbc.Driver"); //java connect to mysql url 
			connector = (Connection) DriverManager.getConnection(url,"EE","ftm963djy");
			System.out.println("correct");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("SQLException: " + e.getMessage());  
            System.out.println("SQLState: " + e.getSQLState());  
            System.out.println("VendorError: " + e.getErrorCode());
		}
		return connector;
		
	}
}
