package wolfPub;

import java.sql.*;

public class Database {
	private static final String jdbcURL = "jdbc:mariadb://localhost/wolfpub"; // Using SERVICE_NAME  publication_t
	private static final String user = "root";
	private static final String password = "";
	
//	private static final String jdbcURL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/spatel49"; // Using SERVICE_NAME
//	private static final String user = "spatel49";
//	private static final String password = "200390990";
	
	public Connection getConnection(boolean isConnected) {
		Connection con = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			
			con = DriverManager.getConnection(jdbcURL, user, password);
			isConnected = true;
		} catch (Throwable oops) {
			// TODO Auto-generated catch block
			isConnected = false;
			oops.printStackTrace();
		}
		return con;
	}
	
	public void closeConnection(Connection con) {
		if(con != null) {
	         try {
	         con.close();
	         } catch(Throwable whatever) {}
	     }
	}
}
