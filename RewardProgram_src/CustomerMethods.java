import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class CustomerMethods {
	
	// brand name of loyalty program | loyalty program id
	private static final String DISPLAY_LPS = "%10s | %10s | %10s";
	// wallet id | points | loyalty program id | wallet level
	private static final String DISPLAY_WALLETS = "%12s | %10s | %10s | %10s";
	// reward instance id | value | expiration | status | is used? | claimed by 
	private static final String DISPLAY_RIS = "%10s | %10s | %10s | %10s";
	
	// purchase code
	private static final String PURCHASE_CODE = "A01";
	// review code
	private static final String REVIEW_CODE = "A02";
	// refer code
	private static final String REFER_CODE = "A03";
	
	
	//NOTE: FOR ALL METHODS IN THIS FILE, ASSUME THE FOLLOWING ARE TRUE
	//-The ID of the current customer is accessible as RewardSystem.userID
	//-The ID of the current loyalty program is accessible as RewardSystem.loyaltyProgramID

	public static String enroll(String loyaltyID) {
		// user joins a particular program and creates a wallet with 0 points.
		Statement stmt = null;
		ResultSet rs = null;
		try {
			if (!isEnrolled(loyaltyID)) {
				stmt = RewardSystem.conn.createStatement();
				
				String values = String.format("('%s',0,'%s',1)", 
						RewardSystem.userID,
						loyaltyID);
				String insert = "INSERT INTO WALLET VALUES" + values;
				if (DatabaseUtilities.executeUpdate(insert) != 1)
					return "Error";
				
				// log join activity
				CallableStatement cstmt = RewardSystem.conn.prepareCall("{call create_initial_wallet_event(?,?,?)}");
				cstmt.setString(1, RewardSystem.userID);
				cstmt.setString(2, loyaltyID);
				//String dateToInsert = (LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).toString();
				System.out.println("Please give the current date and time (in format YYYY-MM-dd HH:mm:ss)");
				System.out.print("Current date and time:");
				String dateToInsert = StateOperator.scanner.nextLine();
				cstmt.setString(3, dateToInsert);
				cstmt.execute();
			}
		} catch (SQLException e) {
			//e.printStackTrace();
			return "Error";
		} finally {
			DatabaseUtilities.close(stmt);
			DatabaseUtilities.close(rs);
		}
		return "Success";
	}
	
	public static boolean isEnrolled(String loyaltyID) {
		// Determine if the current user is enrolled in the program with a given loyalty ID.
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = RewardSystem.conn.createStatement();
			String sql = "SELECT LP_ID " +
 					"FROM WALLET " +
					"WHERE C_ID = " + String.format("'%s'", RewardSystem.userID) +
					" AND LP_ID = " + String.format("'%s'", loyaltyID);
			rs = stmt.executeQuery(sql);
			return rs.next();
		} catch (SQLException e) {
			// e.printStackTrace();
			return false;
		} finally {
			DatabaseUtilities.close(stmt);
			DatabaseUtilities.close(rs);
		}
	}
	
	public static boolean hasPurchase() 
	{
		// Determine if the currently active loyaltyID has purchase activities enabled.
		return hasRewardType(PURCHASE_CODE);
	}
	
	public static boolean hasReview() 
	{
		// Determine if the currently active loyaltyID has review activities enabled.
		return hasRewardType(REVIEW_CODE);
	}
	
	public static boolean hasRefer() 
	{
		// Determine if the currently active loyaltyID has refer activities enabled.
		return hasRewardType(REFER_CODE);
	}
	
	private static boolean hasRewardType(String reId) {
		// Determine if the currently active loyaltyID has 
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = RewardSystem.conn.createStatement();
			// get all loyalty programs and their brand names
			String sql = "SELECT * " +
					"FROM RE_RULES " +
					"WHERE LP_ID = " + String.format("'%s'", RewardSystem.loyaltyProgramID) +
					" AND ACTIVITY_TYPE_ID = " + String.format("'%s'", reId);
			rs = stmt.executeQuery(sql);
			return rs.next();
			
		} catch (SQLException e) {
			// e.printStackTrace();
			return false;
		} finally {
			DatabaseUtilities.close(stmt);
			DatabaseUtilities.close(rs);
		}
	}
	
	public static String showUnjoinedLoyaltyPrograms()
	{
		// Print to screen all loyalty programs that are currently labeled as "Active" and that
		// the current user is not already a member of.
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = RewardSystem.conn.createStatement();
			// get all loyalty programs and their brand names
			String sql = (
					"SELECT B.BRAND_NAME, S.B_ID, S.LP_ID " +
					"FROM SET_UP S, BRANDS B, LOYALTY_PROGRAM L " +
					"WHERE S.B_ID = B.B_ID AND S.LP_ID = L.LP_ID AND L.STATUS = 'ACTIVE'");
			rs = stmt.executeQuery(sql);
			System.out.println(String.format(DISPLAY_LPS, "LP ID", "Brand ID", "Brand"));			
			while (rs.next()) {
				String loyaltyID = rs.getString("LP_ID");
				// only print if user not enrolled in loyalty program
				if (!isEnrolled(loyaltyID)) {
					String out = String.format(DISPLAY_LPS,
							loyaltyID,
							rs.getString("B_ID"),
							rs.getString("BRAND_NAME"));
					System.out.println(out);
				}
			}
			
		} catch (SQLException e) {
			// e.printStackTrace();
			return "Error";
		} finally {
			DatabaseUtilities.close(stmt);
			DatabaseUtilities.close(rs);
		}
		return "Success";
	}
	
	public static String showJoinedLoyaltyPrograms()
	{
		// Print to screen all loyalty programs that the current user is a part of.
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = RewardSystem.conn.createStatement();
			// get all loyalty programs and their brand names
			String sql = "SELECT W.LP_ID, S.B_ID, B.BRAND_NAME " +
					"FROM WALLET W, SET_UP S, BRANDS B " +
					"WHERE W.C_ID = " + String.format("'%s'", RewardSystem.userID) +
					" AND S.LP_ID = W.LP_ID" + 
					" AND S.B_ID = B.B_ID";
			rs = stmt.executeQuery(sql);
			System.out.println(String.format(DISPLAY_LPS, "LP ID", "Brand ID", "Brand"));
			while (rs.next()) {
				String out = String.format(DISPLAY_LPS,
						rs.getString("LP_ID"),
						rs.getString("B_ID"),
						rs.getString("BRAND_NAME"));
				System.out.println(out);
			}
			
		} catch (SQLException e) {
			// e.printStackTrace();
			return "Error";
		} finally {
			DatabaseUtilities.close(stmt);
			DatabaseUtilities.close(rs);
		}
		
		return "Success";
	}
	
	public static boolean isValidID(String ID)
	{	
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = RewardSystem.conn.createStatement();
			String sql = "SELECT C_ID " +
					"FROM CUSTOMER " +
					"WHERE C_ID = " + String.format("'%s'", ID);
			rs = stmt.executeQuery(sql);
			return rs.next();
		} catch (SQLException e) {
			// e.printStackTrace();
			return false;
		} finally {
			DatabaseUtilities.close(stmt);
			DatabaseUtilities.close(rs);
		}
	}
	
	public static void printAllWallets()
	{
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = RewardSystem.conn.createStatement();
			String sql = "SELECT * " +
				"FROM WALLET " +
				"WHERE C_ID = " + String.format("'%s'", RewardSystem.userID);
			rs = stmt.executeQuery(sql);
			System.out.println(String.format(DISPLAY_WALLETS, "Customer ID", "Points", "LP ID", "Wallet Lvl"));
			while (rs.next()) {
				String out = String.format(DISPLAY_WALLETS, 
						rs.getString("C_ID"), 
						rs.getString("POINTS"),
						rs.getString("LP_ID"),
						rs.getString("WALLET_LEVEL"));
				System.out.println(out);
			}
			
		} catch (SQLException e) {
			// fail
		} finally {
			DatabaseUtilities.close(stmt);
			DatabaseUtilities.close(rs);
		}
		
	}
	
	public static void printAllRewards()
	{
		// Print all reward instances created by the current RewardSystem.loyaltyProgramID
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = RewardSystem.conn.createStatement();
			String sql = "SELECT I.REWARD_INSTANCE_ID, I.VALUE, R.WORTH_POINTS AS COST, I.REWARD_TYPE_ID " +
				"FROM REWARD_INSTANCES I, RR_RULES R " +
				"WHERE I.LP_ID = " + String.format("'%s'", RewardSystem.loyaltyProgramID) +
				" AND R.LP_ID = I.LP_ID" +
				" AND R.REWARD_TYPE_ID = I.REWARD_TYPE_ID" +
				" AND CLAIMED_BY IS NULL";
			rs = stmt.executeQuery(sql);
			System.out.println(String.format(DISPLAY_RIS, "RI ID", "Value", "Cost", "RType ID"));
			while (rs.next()) {
				String out = String.format(DISPLAY_RIS, 
						rs.getString("REWARD_INSTANCE_ID"), 
						rs.getString("VALUE"),
						rs.getString("COST"),
						rs.getString("REWARD_TYPE_ID"));
				System.out.println(out);
			}
			
		} catch (SQLException e) {
			//fail
			e.printStackTrace();
		} finally {
			DatabaseUtilities.close(stmt);
			DatabaseUtilities.close(rs);
		}
		
	}
	
	public static String purchase(String amount, String date, String giftCardCode) {
		/**
		 * if gift card given: 
		 * - check claimed by user 
		 * - check hasn't already been used
		 * - if valid: 
		 * 		- don't award points for purchase 
		 * 		- set gift card to used
		 * Constraint: a gift card cannot be used for a purchase if the "value" of the
		 * gift card is less than the purchase amount
		 */
		
		if (giftCardCode.isEmpty())
			return doRewardEarning(PURCHASE_CODE, amount, date, true);
		
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = RewardSystem.conn.createStatement();
			String getGiftCard = "SELECT * " +
					"FROM REWARD_INSTANCES " +
					"WHERE REWARD_INSTANCE_ID = " + giftCardCode +
					" AND CLAIMED_BY = " + String.format("'%s'", RewardSystem.userID) +
					" AND LP_ID = " + String.format("'%s'", RewardSystem.loyaltyProgramID) +
					" AND IS_USED = 0" + 
					" AND VALUE >= " + amount;
			rs = stmt.executeQuery(getGiftCard);
			
			// if gift card is not used, claimed by the customer, 
			// and has enough value to cover purchase... then use it
			if (rs.next()) {
				String useGiftCard = "UPDATE REWARD_INSTANCES " +
						"SET IS_USED = 1 " +
						"WHERE REWARD_INSTANCE_ID = " + giftCardCode;
				DatabaseUtilities.executeUpdate(useGiftCard);
				
				return doRewardEarning(PURCHASE_CODE, amount, date, false);
			} 
		} catch (SQLException e) {
			 //e.printStackTrace();
			return "Error";
		} finally {
			DatabaseUtilities.close(stmt);
			DatabaseUtilities.close(rs);
		}
		
		return "Error";
	}
	
	public static String review(String review, String date) {
		// perform the reward activity of leaving a review for a product
		return doRewardEarning(REVIEW_CODE, review, date, true);
	}

	public static String refer(String referredID, String date) {
		// perform the referral activity for given referral. Assume referred ID exists
		return doRewardEarning(REFER_CODE, referredID, date, true);
	}
	
	/** 
	 * reward earning update:
	 * 	- check number of points for activity from RE_RULES
	 * 	- log activity in ACTIVITY_INFO 
	 * 	- add points to WALLET (accounting for TIER MULTIPLIER)
	 * 	- check if customer moves to next tier (based on total points earned - don't include redeemed points)
	 */
	private static String doRewardEarning(String activityCode, String activityData, String date, boolean canEarn) {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			
			// get number of points for activity
			stmt = RewardSystem.conn.createStatement();
			String selectRE = "SELECT NO_OF_POINTS, VERSION_NO " + 
					"FROM RE_RULES " +
					"WHERE LP_ID = " + String.format("'%s'", RewardSystem.loyaltyProgramID) +
					" AND ACTIVITY_TYPE_ID = " + String.format("'%s'", activityCode) +
					" ORDER BY VERSION_NO DESC";
			rs = stmt.executeQuery(selectRE);
			if (!rs.next())
				return "Error";
			int points = rs.getInt("NO_OF_POINTS");
			if (!canEarn)
				points = 0;
			int version = rs.getInt("VERSION_NO");
			DatabaseUtilities.close(rs);
			
			// log activity info
			String values = String.format("(TO_DATE('%s', 'YYYY-MM-DD HH24:MI:SS') ,%d,'%s','%s','%s','%s', %d)", 
					date,
					points,
					activityCode,
					activityData,
					RewardSystem.userID,
					RewardSystem.loyaltyProgramID,
					version);
			String insert = "INSERT INTO ACTIVITY_INFO VALUES " + values;
			stmt.executeQuery(insert);
			
			// database trigger updates wallet points for reward when activity_info inserted
			
			// try upgrading tier
			CallableStatement cstmt = RewardSystem.conn.prepareCall("{call update_wallet_tier(?,?)}");
			cstmt.setString(1, RewardSystem.userID);
			cstmt.setString(2, RewardSystem.loyaltyProgramID);
			cstmt.execute();
			
			return "Success";
				
		} catch (SQLException e) {
			//e.printStackTrace();
			return "Error";
		} finally {
			DatabaseUtilities.close(stmt);
			DatabaseUtilities.close(rs);
		}
	}
	
	public static String redeem(String rewardID) {
		// Use the active RR rule for this brand to determine if the customer can make a points 
		// purchase for that reward, and if they do, decrease the stock of that reward item
		Statement stmt = null;
		ResultSet rs = null;
		try {
			// get number of points for reward
			stmt = RewardSystem.conn.createStatement();
			String selectRR = "SELECT R.WORTH_POINTS, R.VERSION_NO, R.REWARD_TYPE_ID, I.VALUE " + 
					"FROM RR_RULES R, WALLET W, REWARD_INSTANCES I " +
					"WHERE W.LP_ID = R.LP_ID " +
					" AND W.C_ID = " + String.format("'%s'", RewardSystem.userID) +
					" AND R.LP_ID = " + String.format("'%s'", RewardSystem.loyaltyProgramID) +
					" AND I.REWARD_INSTANCE_ID = " + String.format("%s", rewardID) +
					" AND W.POINTS >= R.WORTH_POINTS" +
					" AND R.REWARD_TYPE_ID = I.REWARD_TYPE_ID" +
					" ORDER BY VERSION_NO DESC";
			rs = stmt.executeQuery(selectRR);
			if (!rs.next())
				return "Error";
			
			int value = rs.getInt("VALUE");
			String rewardData = "None";
			if (value > 0)
				rewardData = Integer.toString(value);
			//DatabaseUtilities.close(rs);
			Scanner scanner = new Scanner(System.in);
			System.out.println("Please give the current date and time (in format YYYY-MM-dd HH:mm:ss)");
			System.out.print("Current date and time:");
			String dateToInsert = StateOperator.scanner.nextLine();
			String values = String.format("(TO_DATE('%s', 'YYYY-MM-DD HH24:MI:SS'),%s, %d,'%s','%s','%s','%s', %d)", 
					//LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).toString(),
					dateToInsert,
					rewardID,
					-1 * rs.getInt("WORTH_POINTS"),
					rs.getString("REWARD_TYPE_ID"),
					rewardData,
					RewardSystem.userID,
					RewardSystem.loyaltyProgramID,
					rs.getInt("VERSION_NO"));
			String insert = "INSERT INTO REWARD_INFO VALUES " + values;
			stmt.executeQuery(insert);
			DatabaseUtilities.close(rs);
			
			// trigger updates wallet points for redeeming when reward_info inserted 
			
			// trigger sets reward_instance 'claimed_by' to the current users ID
			
			return "Success";
				
		} catch (SQLException e) {
			//e.printStackTrace();
			return "Error";
		} finally {
			DatabaseUtilities.close(stmt);
			DatabaseUtilities.close(rs);
		}
	}
	
}
