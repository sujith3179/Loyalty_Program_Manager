import java.sql.*;

// Website for Oracle setup at NCSU : http://www.csc.ncsu.edu/techsupport/technotes/oracle.php

public class AdminMethods {
	
	
	public static String addBrand(String newBrandID, String newBrandPassword,
			String newBrandName, String newBrandAddress, String newBrandJoinDate)
	{
		//Insert a brand
		
		try 
		{
	        Connection conn=RewardSystem.conn;
            Statement stmt = null;
	       
	        try
	        {
				stmt = conn.createStatement();
				
				// Construct inserts
				String SQLText = (
						"INSERT INTO USER_TABLE " + 
						"VALUES ('" + newBrandID + "','" + newBrandPassword + "','"
						+ "BRAND" + "')");
				String SQLText2 = (
						"INSERT INTO BRANDS " + 
						"VALUES ('" + newBrandID + "','" + newBrandName + "','"
						+ newBrandAddress + "', TO_DATE('" + newBrandJoinDate + "', 'YYYY-MM-DD'))");
				
				// Execute
				stmt.executeUpdate(SQLText);
				stmt.executeUpdate(SQLText2);
	        }
	        
	        finally 
	        {
	            DatabaseUtilities.close(stmt);
	        }
		}
		catch(Throwable oops) 
		{
            oops.printStackTrace();
            return "Error";
	    }
		return "Success";
	}
	
	public static String addCustomer(String newCustomerID, String newCustomerPassword,
			String newCustomerName, String newCustomerAddress, String newCustomerPhoneNumber)
	{
		
		try 
		{
	        Class.forName("oracle.jdbc.OracleDriver");
	        Connection conn=RewardSystem.conn;
            Statement stmt = null;
	       
	        try
	        {
				stmt = conn.createStatement();
				
				// Construct inserts
				String SQLText = (
						"INSERT INTO USER_TABLE " + 
						"VALUES ('" + newCustomerID + "','" + newCustomerPassword + "','"
						+ "CUSTOMER" + "')");
				String SQLText2 = (
						"INSERT INTO CUSTOMER " + 
						"VALUES ('" + newCustomerID + "','" + newCustomerName + "','"
						+ newCustomerAddress + "','" + newCustomerPhoneNumber + "')");
				
				// Execute
				stmt.executeUpdate(SQLText);
				stmt.executeUpdate(SQLText2);
	        }
	        
	        finally 
	        {
	            DatabaseUtilities.close(stmt);
	        }
		}
		catch(Throwable oops) 
		{
            //oops.printStackTrace();
            return "Error";
	    }
		return "Success";
	}
	
	public static String addActivityType(String activityTypeName, String activityTypeCode)
	{
		try 
		{
	        Class.forName("oracle.jdbc.OracleDriver");
	        Connection conn=RewardSystem.conn;
            Statement stmt = null;
            ResultSet rs = null;
	       
	        try
	        {
				stmt = conn.createStatement();
				
				// Construct insert
				String SQLText = (
						"INSERT INTO ACTIVITY_TYPE(ACTIVITY_TYPE_ID, ACTIVITY_NAME) " + 
						"VALUES ('" + activityTypeCode + "','" + activityTypeName + "')");
				
				// Execute
				stmt.executeUpdate(SQLText);
	        }
	        
	        finally 
	        {
	            DatabaseUtilities.close(rs);
	            DatabaseUtilities.close(stmt);
	        }
		}
		catch(Throwable oops) 
		{
            //oops.printStackTrace();
            return "Error";
	    }
		return "Success";
	}
	
	public static String addRewardType(String rewardTypeName, String rewardTypeCode)
	{
		try 
		{
	        Class.forName("oracle.jdbc.OracleDriver");
	        Connection conn=RewardSystem.conn;
            Statement stmt = null;
            ResultSet rs = null;
	       
	        try
	        {
				stmt = conn.createStatement();
				
				// Construct insert
				String SQLText = (
						"INSERT INTO REWARD_TYPE(REWARD_TYPE_ID, REWARD_NAME) " + 
						"VALUES ('" + rewardTypeCode + "','" + rewardTypeName + "')");
				
				// Execute
				stmt.executeUpdate(SQLText);
	        }
	        
	        finally 
	        {
	            DatabaseUtilities.close(rs);
	            DatabaseUtilities.close(stmt);
	        }
		}
		catch(Throwable oops) 
		{
           // oops.printStackTrace();
            return "Error";
	    }
		return "Success";
	}
	
	public static String showBrandInfo(String brand_id)
	{
		try 
		{
	        Class.forName("oracle.jdbc.OracleDriver");
	        Connection conn=RewardSystem.conn;
            Statement stmt = null;
            ResultSet rs = null;
	       
	        try
	        {
				stmt = conn.createStatement();
				
				// Construct query
				String SQLText = (
						"SELECT B_ID, BRAND_NAME, ADDRESS, JOIN_DATE " + 
						"FROM BRANDS " + 
						"WHERE B_ID = '"+brand_id+"'");
				
				// Execute
				rs = stmt.executeQuery(SQLText);
				
				// Check if empty search. If so, give error.
				if(!rs.isBeforeFirst())
				{
					return "Error";
				}
				
				// Print brand info
				System.out.format("%7s|%15s|%40s|%12s\n", "BID", "Name", "Address", "Join Date");
				while (rs.next()) 
				{
					String bid = rs.getString("B_ID");
					String bname = rs.getString("BRAND_NAME");
					String baddress = rs.getString("ADDRESS");
					String bjoindate = rs.getString("JOIN_DATE");
					System.out.format("%7s|%15s|%40s|%10s\n", bid, bname, baddress, bjoindate);
				}
	        }
	        
	        finally 
	        {
	            DatabaseUtilities.close(rs);
	            DatabaseUtilities.close(stmt);
	        }
		}
		catch(Throwable oops) 
		{
            //oops.printStackTrace();
            return "Error";
	    }
		return "Success";
	}
	
	public static String showCustomerInfo(String customer_id)
	{
		try 
		{
	        Class.forName("oracle.jdbc.OracleDriver");
	        Connection conn=RewardSystem.conn;
            Statement stmt = null;
            ResultSet rs = null;
	       
	        try
	        {
				stmt = conn.createStatement();
				
				// Construct query
				String SQLText = (
						"SELECT C_ID, C_NAME, C_ADDRESS, C_PHONE_NUMBER " + 
						"FROM CUSTOMER " + 
						"WHERE C_ID = '"+customer_id+"'");
				
				// Execute
				rs = stmt.executeQuery(SQLText);
				
				// Check if empty search. If so, give error.
				if(!rs.isBeforeFirst())
				{
					return "Error";
				}
				
				
				System.out.format("%7s|%15s|%40s|%12s\n", "CID", "Name", "Address", "Phone Number");
				while (rs.next()) 
				{
					String bid = rs.getString("C_ID");
					String bname = rs.getString("C_NAME");
					String baddress = rs.getString("C_ADDRESS");
					String bjoindate = rs.getString("C_PHONE_NUMBER");
					System.out.format("%7s|%15s|%40s|%10s\n", bid, bname, baddress, bjoindate);
				}
	        }
	        
	        finally 
	        {
	            DatabaseUtilities.close(rs);
	            DatabaseUtilities.close(stmt);
	        }
		}
		catch(Throwable oops) 
		{
            //oops.printStackTrace();
            return "Error";
	    }
		return "Success";
	}
}