import java.sql.*;

public class BrandMethods {
	
	//NOTE: FOR ALL METHODS IN THIS FILE, ASSUME THE FOLLOWING ARE TRUE
		//-The ID of the current brand is accessible as RewardSystem.userID
	
	public static String addLoyaltyProgram(String type, String name, String ID)
	{
		try 
		{
	        Connection conn=RewardSystem.conn;
            Statement stmt = null;
	        ResultSet rs = null;
	        try
	        {
				stmt = conn.createStatement();
				
				// Check if already a loyalty program for this brand
				String SQLText = (
						  "SELECT LP_ID "
						+ "FROM SET_UP "
						+ "WHERE B_ID = '" + RewardSystem.userID + "'");
				rs = stmt.executeQuery(SQLText);
				if (rs.isBeforeFirst())
				{
					rs.next();
					// if before first, it means already a loyalty program
					String savedLPID = rs.getString("LP_ID");
					
					// check if it's a tier program or not
					SQLText = (
							  "SELECT LP_ID "
							+ "FROM TIERS "
							+ "WHERE LP_ID = '" + savedLPID + "'");
					rs = stmt.executeQuery(SQLText);
					if(rs.isBeforeFirst())
					{
						//if is before first, there is a tier definition
						return("Tier");
					}
					else
					{
						// Otherwise, it's presumably regular
						return("Regular");
					}
				}
				else
				{
					// If not, create a loyalty program
					SQLText = (
							"INSERT INTO LOYALTY_PROGRAM " + 
							"VALUES ('" + ID + "','" + name + "','INACTIVE')");
					stmt.executeUpdate(SQLText);
					
					// And add it to set up
					SQLText = (
							"INSERT INTO SET_UP " + 
							"VALUES ('" + RewardSystem.userID + "','" + ID + "')");
					stmt.executeUpdate(SQLText);
					DatabaseUtilities.close(rs);
					return "Success";	
				}			
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
		
	}
	
	public static String setUpTiers(String numberOfTiers, String namesOfTiers, String cutoffsOfTiers, String multipliersOfTiers)
	{
		// Sets up the tiers for the current loyalty program, given the number, name, and cutoffs of
		// the tiers desired. Should insert if no prior tiers have been set up, or update old tiers
		// if previous tiers existed for this loyalty program.
		
		
		try 
		{
	        Connection conn=RewardSystem.conn;
            Statement stmt = null;
	        ResultSet rs = null;
	        try
	        {
				stmt = conn.createStatement();
				
				// Check if any prior tiers have been set up
				
				// first get loyalty program ID
				String SQLText = (
						  "SELECT LP_ID "
						+ "FROM SET_UP "
						+ "WHERE B_ID = '" + RewardSystem.userID + "'");
				rs = stmt.executeQuery(SQLText);
				rs.next();
				String LPID = rs.getString("LP_ID");
				
				// Next, check if any existing tiers
				SQLText = (
						  "SELECT * "
						+ "FROM TIERS "
						+ "WHERE LP_ID = '" + LPID + "'");
				rs = stmt.executeQuery(SQLText);
				if (rs.isBeforeFirst())
				{
					System.out.println("Deleting previous tier definitions");
					// Previous tiers found, need to first delete them
					SQLText = (
							  "DELETE "
							+ "FROM TIERS "
							+ "WHERE LP_ID = '" + LPID + "'");
					rs = stmt.executeQuery(SQLText);
				}
				
				// Now no tiers exist, add in new tiers
				int i = 0;
				String[] tierNames = namesOfTiers.split(" ");
				String[] cutoffs = cutoffsOfTiers.split(" ");
				String[] multipliers = multipliersOfTiers.split(" ");
				Integer intNumberOfTiers = Integer.parseInt(numberOfTiers); 
				
				// Check for proper number of inputs
				if(tierNames.length != intNumberOfTiers | 
				   cutoffs.length != intNumberOfTiers | 
				   multipliers.length != intNumberOfTiers)
				{
					System.out.println("Error: not enough parameters passed for tier definitions.");
					return "Error";
				}
				
				
				while(i < intNumberOfTiers)
				{
					// Insert each tier
					SQLText = (
							  "INSERT INTO "
							+ "TIERS "
							+ "VALUES ('" + LPID + "','" + tierNames[i] + "'," + String.valueOf(i+1) + "," +
							multipliers[i] + "," + cutoffs[i] + ")");
					rs = stmt.executeQuery(SQLText);
					i = i + 1;
				}
				return "Success";
				
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
	}
	
	public static String toggleActivityType(String activityToToggle)
	{
		// Given an activity to toggle (one of "Purchase", "Review", or "Refer"),
		// updates the current brand's loyalty program to either allow that activity
		// if currently unallowed, or disallow it if already allowed.
		
		// To do this, query the 'has_activities' table and make appropriate insert or deletion.
		
		// Should return "Added" if the activity type was added, or "Removed" if the  activity type
		// was removed.
		try 
		{
	        Connection conn=RewardSystem.conn;
            Statement stmt = null;
	        ResultSet rs = null;
	        try
	        {
				stmt = conn.createStatement();
				
				// Check if given activity is already allowed
				
				// first get loyalty program ID
				String SQLText = (
						  "SELECT LP_ID "
						+ "FROM SET_UP "
						+ "WHERE B_ID = '" + RewardSystem.userID + "'");
				rs = stmt.executeQuery(SQLText);
				rs.next();
				String LPID = rs.getString("LP_ID");
				String activityID;
				// Next, get the activity's ID
				if(activityToToggle == "Purchase")
				{
					activityID = "A01";
				}
				else if(activityToToggle == "Review")
				{
					activityID = "A02";
				}
				else if(activityToToggle == "Refer")
				{
					activityID = "A03";
				}
				else
				{
					return "Error";
				}
				
				// Next, check for activity
				SQLText = (
						  "SELECT * "
						+ "FROM HAS_ACTIVITIES "
						+ "WHERE LP_ID = '" + LPID + "' "
						+ "AND ACTIVITY_TYPE_ID = '" + activityID + "'");
				rs = stmt.executeQuery(SQLText);
				if (rs.isBeforeFirst())
				{
					// Activity is already enabled
					return "Already Added";
				}
				else
				{
					// Activity is not enabled, add it
					SQLText = (
							  "INSERT "
							+ "INTO HAS_ACTIVITIES "
							+ "VALUES('" + activityID + "','" + LPID + "')");
					
					stmt.executeUpdate(SQLText);
					return "Added";
				}
				
	        }
	        
	        finally 
	        {
	            DatabaseUtilities.close(stmt);
	            DatabaseUtilities.close(rs);
	        }
		}
		catch(Throwable oops) 
		{
			//oops.printStackTrace();
            return "Error";
	    }
	}
	
	public static String addRewardInstances(String rewardType,String rewardInstanceCount)
	{
		// Given an activity to toggle (one of "Gift Card", "Free Product"),
		// updates the current brand's loyalty program to either allow that reward
		// if currently unallowed, or disallow it if already allowed.
		
		// To do this, query the 'has_rewards' table and make appropriate insert or deletion.
		
		// Should return "Added" if the reward type was added, or "Removed" if the reward type
		// was removed.
		try 
		{
	        Connection conn=RewardSystem.conn;
            Statement stmt = null;
	        ResultSet rs = null;
	        try
	        {
				stmt = conn.createStatement();
				
				// Check if given reward is already allowed
				
				// first get loyalty program ID
				String SQLText = (
						  "SELECT LP_ID "
						+ "FROM SET_UP "
						+ "WHERE B_ID = '" + RewardSystem.userID + "'");
				rs = stmt.executeQuery(SQLText);
				rs.next();
				String LPID = rs.getString("LP_ID");
				String rewardID;
				// Next, get the reward's ID
				if(rewardType == "Gift Card")
				{
					rewardID = "R01";
				}
				else if(rewardType == "Free Product")
				{
					rewardID = "R02";
				}
				else
				{
					return "Error";
				}
				
				// Next, check for reward
				SQLText = (
						  "SELECT * "
						+ "FROM HAS_REWARDS "
						+ "WHERE LP_ID = '" + LPID + "' "
						+ "AND REWARD_TYPE_ID = '" + rewardID + "'");
				rs = stmt.executeQuery(SQLText);
				
				// If reward is not in has_rewards, add it
				if (!rs.isBeforeFirst())
				{
					SQLText = (
							  "INSERT "
							+ "INTO HAS_REWARDS "
							+ "VALUES('" + rewardID + "','" + LPID + "')");
					
					stmt.executeUpdate(SQLText);
				}
				
				// Use RR rule to determine how much this reward should cost
				SQLText = (
						  "SELECT * "
						+ "FROM RR_RULES R1 "
						+ "WHERE R1.LP_ID = '" + LPID + "' "
						+ "AND R1.REWARD_TYPE_ID = '" + rewardID + "' "
						+ "AND R1.VERSION_NO IN "
						+ "(SELECT MAX(VERSION_NO) "
						+ "FROM RR_RULES R2 "
						+ "WHERE R2.LP_ID = '" + LPID + "' "
						+ "AND R2.REWARD_TYPE_ID = '" + rewardID + "')");
				rs = stmt.executeQuery(SQLText);
				
				if (!rs.isBeforeFirst())
				{
					System.out.println("You do not have an RR rule defined for this reward. Cannot create rewards.");
					return "Error";
				}
				
				rs.next();
				int worthPoints = rs.getInt("WORTH_POINTS");
				
				// In either case, call procedure to create new reward instances
				// Perform callable statements to insert reward instances
				CallableStatement cStmt = conn.prepareCall("{call add_reward_instances(?,?,?,?)}");
				cStmt.setString(1, rewardID);
				cStmt.setInt(2, worthPoints);
				cStmt.setString(3, LPID);
				cStmt.setInt(4, Integer.parseInt(rewardInstanceCount));
				cStmt.execute();
				
				return "Success";
				
	        }
	        
	        finally 
	        {
	            DatabaseUtilities.close(stmt);
	            DatabaseUtilities.close(rs);
	        }
		}
		catch(Throwable oops) 
		{
			//oops.printStackTrace();
            return "Error";
	    }
		
	}
	
	public static String addRRRule(String RRRuleCode, String RRRewardCategory, String RRPointsPerReward)
	{
		// Given a specific RRRule code, the category the rule applies to, and the cost per reward in that
		// category, create a new RRRule.
		try 
		{
	        Connection conn=RewardSystem.conn;
            Statement stmt = null;
	        ResultSet rs = null;
	        try
	        {
				stmt = conn.createStatement();
				
				// first get loyalty program ID
				String SQLText = (
						  "SELECT LP_ID "
						+ "FROM SET_UP "
						+ "WHERE B_ID = '" + RewardSystem.userID + "'");
				rs = stmt.executeQuery(SQLText);
				rs.next();
				String LPID = rs.getString("LP_ID");
				
				// Reward category is given
				
				// Check that reward category is enabled
				//No longer a thing, we need to create rule before we instantiate
				
				/*SQLText = (
						  "SELECT * "
						+ "FROM HAS_REWARDS "
						+ "WHERE LP_ID = '" + LPID + "' "
						+ "AND REWARD_TYPE_ID = '" + RRRewardCategory + "'");
				rs = stmt.executeQuery(SQLText);
				if (!rs.isBeforeFirst())
				{
					// If no entries, this reward is not enabled, can't make a rule.
					System.out.println("You have not enabled this reward (or you gave an invalid reward), cannot create rule.");
					return "Error";
				}*/
				
				// Otherwise, we can make this rule.
				
				// Perform insert (details are handled by rule on RRRule, so this
				// works for both updates and creating the first value).
				
				SQLText = (
						  "INSERT "
						+ "INTO RR_RULES "
						+ "VALUES('" + RRRewardCategory + "','" + LPID + "',1," + 
						String.valueOf(RRPointsPerReward) + ",'" + RRRuleCode + "')");
				stmt.executeUpdate(SQLText);
				return "Success";
				
	        }
	        
	        finally 
	        {
	            DatabaseUtilities.close(stmt);
	            DatabaseUtilities.close(rs);
	        }
		}
		catch(Throwable oops) 
		{
			//oops.printStackTrace();
            return "Error";
	    }
	}

	public static String addRERule(String rewardRuleCode, String activityCategory, String pointsPerActivity)
	{
		// Given the code for the RRRule, the activity it applies to, and the points per that activity,
		// make a new RRRule.
		
		try 
		{
	        Connection conn=RewardSystem.conn;
            Statement stmt = null;
	        ResultSet rs = null;
	        try
	        {
				stmt = conn.createStatement();
				
				// first get loyalty program ID
				String SQLText = (
						  "SELECT LP_ID "
						+ "FROM SET_UP "
						+ "WHERE B_ID = '" + RewardSystem.userID + "'");
				rs = stmt.executeQuery(SQLText);
				rs.next();
				String LPID = rs.getString("LP_ID");
				
				// Activity category is given
				
				// Check that activity category is enabled
				SQLText = (
						  "SELECT * "
						+ "FROM HAS_ACTIVITIES "
						+ "WHERE LP_ID = '" + LPID + "' "
						+ "AND ACTIVITY_TYPE_ID = '" + activityCategory + "'");
				rs = stmt.executeQuery(SQLText);
				if (!rs.isBeforeFirst())
				{
					// If no entries, this activity is not enabled, can't make a rule.
					System.out.println("You have not enabled this activity (or you gave an invalid activity), cannot create rule.");
					return "Error";
				}
				
				// Perform insert (details are handled by rule on RERule, so this
				// works for both updates and creating the first value).
				
				SQLText = (
						  "INSERT "
						+ "INTO RE_RULES "
						+ "VALUES('" + activityCategory + "','" + LPID + "',1," + 
						String.valueOf(pointsPerActivity) + ",'" + rewardRuleCode + "')");
				stmt.executeUpdate(SQLText);
				return "Success";
				
	        }
	        
	        finally 
	        {
	            DatabaseUtilities.close(stmt);
	            DatabaseUtilities.close(rs);
	        }
		}
		catch(Throwable oops) 
		{
			//oops.printStackTrace();
            return "Error";
	    }
	}

	public static String validateLoyaltyProgram()
	{
		// Check if a loyalty program is ready to be marked active. Requires at least one RE rule and one RR rule.
		
		try 
		{
	        Connection conn=RewardSystem.conn;
            Statement stmt = null;
	        ResultSet rs = null;
	        try
	        {
				stmt = conn.createStatement();
				
				// first get loyalty program ID
				String SQLText = (
						  "SELECT LP_ID "
						+ "FROM SET_UP "
						+ "WHERE B_ID = '" + RewardSystem.userID + "'");
				rs = stmt.executeQuery(SQLText);
				rs.next();
				String LPID = rs.getString("LP_ID");
				
				// Check for RE Rule
				SQLText = (
						  "SELECT * "
						+ "FROM RE_RULES "
						+ "WHERE LP_ID = '" + LPID + "'");
				rs = stmt.executeQuery(SQLText);
				if (!rs.isBeforeFirst())
				{
					// If no entries, no rule
					System.out.println("You have not created an RE Rule.");
					return "Error";
				}
				
				// Check for RR Rule
				SQLText = (
						  "SELECT * "
						+ "FROM RR_RULES "
						+ "WHERE LP_ID = '" + LPID + "'");
				rs = stmt.executeQuery(SQLText);
				if (!rs.isBeforeFirst())
				{
					// If no entries, no rule
					System.out.println("You have not created an RR Rule.");
					return "Error";
				}
				
				// Both rules found, set status to active
				
				SQLText = (
						  "UPDATE "
						+ "LOYALTY_PROGRAM "
						+ "SET STATUS = 'ACTIVE' "
						+ "WHERE LP_ID = '" + LPID + "'");
				stmt.executeUpdate(SQLText);
				return "Success";
				
	        }
	        
	        finally 
	        {
	            DatabaseUtilities.close(stmt);
	            DatabaseUtilities.close(rs);
	        }
		}
		catch(Throwable oops) 
		{
			//oops.printStackTrace();
            return "Error";
	    }
	}

}