package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnection {
	public ResultSet verifyData(String query) throws ClassNotFoundException, SQLException {													
		//Connection URL Syntax: "jdbc:mysql://ipaddress:portnumber/db_name"		
        String dbUrl = "jdbc:mysql://localhost:3306/drunkindonut"+ "?useUnicode=yes&characterEncoding=UTF-8";		
        

		//Database Username		
		String username = "root";	
        
		//Database Password		
		String password = "";				

		//Query to Execute		
		//String query = "select *  from account;";	
        
 	    //Load mysql jdbc driver		
   	    Class.forName("com.mysql.cj.jdbc.Driver");			
   
   		//Create Connection to DB		
    	Connection con = DriverManager.getConnection(dbUrl,username,password);
  
  		//Create Statement Object		
	   Statement stmt = con.createStatement();					

			// Execute the SQL Query. Store results in ResultSet		
 		ResultSet rs= stmt.executeQuery(query);	
 		return rs;

	}
}
