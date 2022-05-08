import java.sql.*;

public class showQueryMethods 
{
	public static void query1()
	{
		// Perform query 1, "List all customers that are not part of Brand02's program."
		
		try 
		{
	        Connection conn=RewardSystem.conn;
            Statement stmt = null;
            ResultSet rs = null;
	       
	        try
	        {
				stmt = conn.createStatement();
				
				// Construct query string
				String SQLText = ("SELECT *  "
								+ "FROM CUSTOMER C "
								+ "WHERE C.C_ID NOT IN "
								+ "(SELECT W.C_ID "
								+ " FROM WALLET W, SET_UP S "
								+ "WHERE S.B_ID = 'Brand02' "
								+ "AND S.LP_ID = W.LP_ID)");
				
				// Execute
				rs = stmt.executeQuery(SQLText);
				
				// Print brand info
				System.out.format("%7s|%15s|%40s|%12s\n", "CID", "Name", "Address", "Phone Number");
				while (rs.next()) 
				{
					String cid = rs.getString("C_ID");
					String cname = rs.getString("C_NAME");
					String caddress = rs.getString("C_ADDRESS");
					String cphonenumber = rs.getString("C_PHONE_NUMBER");
					System.out.format("%7s|%15s|%40s|%10s\n", cid, cname, caddress, cphonenumber);
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
            oops.printStackTrace();
	    }
	}
	
	public static void query2()
	{
		// Perform query 2, "List customers that have joined a
		// loyalty program but have not participated in any activity
		// in that program (list the customerid and the loyalty program id)."
		
		try 
		{
	        Connection conn=RewardSystem.conn;
            Statement stmt = null;
            ResultSet rs = null;
	       
	        try
	        {
				stmt = conn.createStatement();
				
				// Construct query string
				String SQLText = ("SELECT W.C_ID,W.LP_ID "
								+ "FROM WALLET W "
								+ "WHERE (W.C_ID,W.LP_ID) NOT IN "
								+ "(SELECT AI.C_ID,AI.LP_ID "
								+ " FROM ACTIVITY_INFO AI) ");
				
				// Execute
				rs = stmt.executeQuery(SQLText);
				
				// Print brand info
				System.out.format("%7s|%15s\n", "CID", "LPID");
				while (rs.next()) 
				{
					String cid = rs.getString("C_ID");
					String lpid = rs.getString("LP_ID");
					System.out.format("%7s|%15s\n", cid, lpid);
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
            oops.printStackTrace();
	    }
	}

	public static void query3()
	{
		// Perform query 3, "List the rewards that are part of Brand01 loyalty program."
		
		try 
		{
	        Connection conn=RewardSystem.conn;
            Statement stmt = null;
            ResultSet rs = null;
	       
	        try
	        {
				stmt = conn.createStatement();
				
				// Construct query string
				String SQLText = ("SELECT RT.REWARD_TYPE_ID,RT.REWARD_NAME "
							+ "FROM SET_UP S,HAS_REWARDS HR,REWARD_TYPE RT "
							+ "WHERE S.B_ID='Brand01' AND S.LP_ID=HR.LP_ID AND HR.REWARD_TYPE_ID = RT.REWARD_TYPE_ID ");
				
				// Execute
				rs = stmt.executeQuery(SQLText);
				
				System.out.format("%20s|%20s\n", "Reward Type ID", "Reward Name");
				while (rs.next()) 
				{
					String rewardid = rs.getString("REWARD_TYPE_ID");
					String rewardname = rs.getString("REWARD_NAME");
					System.out.format("%20s|%20s\n", rewardid, rewardname);
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
            oops.printStackTrace();
	    }
	}
	
	public static void query4()
	{
		// Perform query 4, "List all the loyalty programs that
		// include 'refer a friend' as an activity in at least one of
		// their reward rules."
		
		try 
		{
	        Connection conn=RewardSystem.conn;
            Statement stmt = null;
            ResultSet rs = null;
	       
	        try
	        {
				stmt = conn.createStatement();
				
				// Construct query string
				String SQLText = ("SELECT LP.LP_ID "
							+ "FROM LOYALTY_PROGRAM LP,ACTIVITY_TYPE AT,RE_RULES RER "
							+ "WHERE LP.LP_ID=RER.LP_ID AND RER.ACTIVITY_TYPE_ID=AT.ACTIVITY_TYPE_ID AND AT.ACTIVITY_NAME = 'Refer A Friend' ");
				
				// Execute
				rs = stmt.executeQuery(SQLText);
				System.out.format("%7s\n", "LPID");
				while (rs.next()) 
				{
					// Print results to screen here
					String lpid = rs.getString("LP_ID");
					System.out.format("%7s\n", lpid);
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
            oops.printStackTrace();
	    }
	}
	
	public static void query5()
	{
		// Perform query 5, "For Brand01, list for each activity
		// type in their loyalty program, the number instances that
		// have occurred."
		
		try 
		{
	        Connection conn=RewardSystem.conn;
            Statement stmt = null;
            ResultSet rs = null;
	       
	        try
	        {
				stmt = conn.createStatement();
				
				// Construct query string
				String SQLText = ("SELECT TEMP.ACTIVITY_TYPE_ID,COUNT(*) COUNT "
								+ "FROM (SELECT * FROM SET_UP S,ACTIVITY_INFO AI "
								+ "WHERE S.B_ID='Brand01' AND S.LP_ID=AI.LP_ID ) TEMP "
								+ "GROUP BY TEMP.ACTIVITY_TYPE_ID");
				
				// Execute
				rs = stmt.executeQuery(SQLText);
				System.out.format("%12s|%3s\n", "Activity ID","Count");
				while (rs.next()) 
				{
					// Print results to screen here
					String activityid = rs.getString("ACTIVITY_TYPE_ID");
					String count = String.valueOf(rs.getInt("COUNT"));
					System.out.format("%12s|%3s\n", activityid, count);
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
            oops.printStackTrace();
	    }
	}
	
	public static void query6()
	{
		// Perform query 6, "List customers of Brand01 that have redeemed at least twice."
		
		try 
		{
	        Connection conn=RewardSystem.conn;
            Statement stmt = null;
            ResultSet rs = null;
	       
	        try
	        {
				stmt = conn.createStatement();
				
				// Construct query string
				String SQLText = ("SELECT TEMP.C_ID "
								+ "FROM (SELECT RI.C_ID "
								+ "FROM SET_UP S,REWARD_INFO RI "
								+ "WHERE S.B_ID='Brand01' AND S.LP_ID=RI.LP_ID)TEMP "
								+ "GROUP BY TEMP.C_ID "
								+ "HAVING COUNT(*)>1");
				
				// Execute
				rs = stmt.executeQuery(SQLText);
				
				System.out.format("%7s\n", "C_ID");
				while (rs.next()) 
				{
					// Print results to screen here
					String cid = rs.getString("C_ID");
					System.out.format("%7s\n", cid);
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
            oops.printStackTrace();
	    }
	}
	
	public static void query7()
	{
		// Perform query 7, "All brands where total number of points
		// redeemed overall is less than 500 points"
		
		try 
		{
	        Connection conn=RewardSystem.conn;
            Statement stmt = null;
            ResultSet rs = null;
	       
	        try
	        {
				stmt = conn.createStatement();
				
				// Construct query string
				String SQLText = ("SELECT B.B_ID "
								+ "FROM BRANDS B "
								+ "WHERE B.B_ID NOT IN "
								+ "(SELECT TEMP.B_ID "
								+ "FROM(SELECT S.B_ID,RI.POINTS_SPENT "
								+ "FROM SET_UP S,REWARD_INFO RI "
								+ "WHERE S.LP_ID=RI.LP_ID) TEMP "
								+ "GROUP BY TEMP.B_ID "
								+ "HAVING SUM(TEMP.POINTS_SPENT) <= -500)");
				
				// Execute
				rs = stmt.executeQuery(SQLText);
				
				System.out.format("%7s\n", "B_ID");
				while (rs.next()) 
				{
					// Print results to screen here
					String bid = rs.getString("B_ID");
					System.out.format("%7s\n", bid);
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
            oops.printStackTrace();
	    }
	}
	
	public static void query8()
	{
		// Perform query 8, "For Customer C0003, and Brand02, number of activities they have done in the period of
		// 08/1/2021 and 9/30/2021."
		
		try 
		{
	        Connection conn=RewardSystem.conn;
            Statement stmt = null;
            ResultSet rs = null;
	       
	        try
	        {
				stmt = conn.createStatement();
				
				// Construct query string3
				String SQLText = ("SELECT COUNT(*) COUNT "
								+ "FROM(SELECT AI.ACTIVITY_DATE_TIME "
								+ "FROM SET_UP S,ACTIVITY_INFO AI "
								+ "WHERE S.B_ID='Brand02' AND S.LP_ID=AI.LP_ID AND AI.C_ID='C0003') TEMP "
								+ "WHERE TEMP.ACTIVITY_DATE_TIME BETWEEN to_date('01/08/2021 00:00:00', 'dd/mm/yyyy hh24:mi:ss') AND to_date('30/09/2021 23:59:59', 'dd/mm/yyyy hh24:mi:ss')");
				
				// Execute
				rs = stmt.executeQuery(SQLText);
				
				System.out.format("%15s\n", "Activity Count");
				while (rs.next()) 
				{
					// Print results to screen here
					String count = String.valueOf(rs.getInt("COUNT"));
					System.out.format("%15s\n", count);
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
            oops.printStackTrace();
	    }
	}

}
