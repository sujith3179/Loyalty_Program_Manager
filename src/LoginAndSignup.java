import java.sql.*;

public class LoginAndSignup {

    static final String jdbcURL 
	= "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl01";
	public static String login(String id, String password)
	{
		try
		{
		Statement stmt = RewardSystem.conn.createStatement();
		ResultSet rs = null;
	       
	        try{		
			rs = stmt.executeQuery("SELECT ID, PASSWORD,TYPE FROM USER_TABLE");
            while (rs.next()) {
            String username = rs.getString("ID");
            String userpassword =  rs.getString("PASSWORD");
            String usertype = rs.getString("TYPE");
            String CUST=new String ("CUSTOMER");
            String BRND=new String ("BRAND");
            String ADMN=new String ("ADMIN");
            //System.out.println("Checking for you inside table");
               if ((id.equals(username)) && (password.equals(userpassword) )) {
            	   //System.out.println( "VALID CREDENTIALS ...");
            	   if(CUST.equals(usertype)){
            		   return "Customer";
            	   }
            	   if( BRND.equals(usertype)){
            		   return "Brand";
            	   }
            	   if( ADMN.equals(usertype)){
            		   return "Admin";
            	   }     
               }
        }
	        }
		finally {
            DatabaseUtilities.close(rs);
            DatabaseUtilities.close(stmt);
        }
	}
		catch(Throwable oops) {
	            //oops.printStackTrace();
	            return "Error";
	        }
		return "Error";	
	
	} 	
	
	public static String brandSignup(String brandIDToSignUp, String brandPasswordToSignUp, String brandNameToSignUp,
    					String brandAddressToSignUp, String brandSignUpDate)
	{
		try {

            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

			conn = RewardSystem.conn;
			stmt = conn.createStatement();
	        try{
	        	
			rs = stmt.executeQuery("SELECT ID FROM USER_TABLE");
            while (rs.next()) {
            
            String username = rs.getString("ID");
            if ((brandIDToSignUp.equals(username))){    	  
          	   return "Error"; 
          	   }
        }
            String BRND=new String ("BRAND");
            stmt.executeUpdate("INSERT INTO USER_TABLE (ID, PASSWORD, TYPE) VALUES ('"+brandIDToSignUp+"','"+brandPasswordToSignUp+"','"+BRND+"')");
            stmt.executeUpdate("INSERT INTO BRANDS (B_ID, BRAND_NAME, ADDRESS,JOIN_DATE) VALUES ('"+brandIDToSignUp+"','"+brandNameToSignUp+
            		"','"+brandAddressToSignUp+"',TO_DATE('"+brandSignUpDate+"', 'YYYY-MM-DD'))");
            return "Success";

	        }
		finally {
            DatabaseUtilities.close(rs);
            DatabaseUtilities.close(stmt);
        }
	}
		catch(Throwable oops) {
	           oops.printStackTrace();
	            return "Error";
	        }
	}
	
	public static String customerSignup(String custIDToSignUp, String custPasswordToSignUp, String custNameToSignUp,
			String custAddressToSignUp, String custPhoneNumber)
	{
		
		try {
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

	        conn = RewardSystem.conn;
			stmt = conn.createStatement();
	       
	        try{
			
			rs = stmt.executeQuery("SELECT ID FROM USER_TABLE");
            while (rs.next()) {
            
            String username = rs.getString("ID");
            if ((custIDToSignUp.equals(username))){      	  
          	   return "Error"; 
          	   }
        }
            String CUST=new String ("CUSTOMER");
            stmt.executeUpdate("INSERT INTO USER_TABLE (ID, PASSWORD, TYPE) VALUES ('"+custIDToSignUp+"','"+custPasswordToSignUp+"','"+CUST+"')");
            stmt.executeUpdate("INSERT INTO CUSTOMER (C_ID, C_NAME, C_ADDRESS,C_PHONE_NUMBER) VALUES ('"+custIDToSignUp+"','"+custNameToSignUp+"','"+custAddressToSignUp+"','"+custPhoneNumber+"')");

	        }
		finally {
            DatabaseUtilities.close(rs);
            DatabaseUtilities.close(stmt);
        }
	}
		catch(Throwable oops) {
	           // oops.printStackTrace();
	            return "Error";
	        }
		return "Success";
	}
}
