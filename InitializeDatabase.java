import java.sql.*;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.Scanner;
import java.io.File;
/**
 *
 * @author Geoff and sujith
 */
// Acknowledgments: This example is a modification of code provided 
// by Dimitri Rakitine.

// Usage from command line on key.csc.ncsu.edu: 
// see instructions in FAQ
// Website for Oracle setup at NCSU : http://www.csc.ncsu.edu/techsupport/technotes/oracle.php

public class InitializeDatabase {

    static final String jdbcURL 
	= "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl01";

    public static void main(String[] args) {
        try {

		// Load the driver. This creates an instance of the driver
		// and calls the registerDriver method to make Oracle Thin
		// driver available to clients.

        Class.forName("oracle.jdbc.OracleDriver");
        
        // Load in username and password from config.properties file.
        
        String user="";
        String passwd="";
        try (InputStream input = new FileInputStream("config.properties")) {
        	Properties prop = new Properties();
        	prop.load(input);
        	
        	user = prop.getProperty("user");
        	passwd = prop.getProperty("passwd");
        }
        catch (IOException ex) {
        	ex.printStackTrace();
        }

            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;

		// Get a connection from the first driver in the
		// DriverManager list that recognizes the URL jdbcURL

		conn = DriverManager.getConnection(jdbcURL, user, passwd);

		// Create a statement object that will be sending your
		// SQL statements to the DBMS

		stmt = conn.createStatement();

		// Drop old tables if they exist
		
		stmt.executeUpdate(" BEGIN " +
				" EXECUTE IMMEDIATE 'DROP TABLE HAS_ACTIVITIES'; " +
				" EXCEPTION " +
				" WHEN OTHERS THEN NULL; " +
				" END; ");
		
		stmt.executeUpdate(" BEGIN " +
				" EXECUTE IMMEDIATE 'DROP TABLE HAS_REWARDS'; " +
				" EXCEPTION " +
				" WHEN OTHERS THEN NULL; " +
				" END; ");
		
		stmt.executeUpdate(" BEGIN " +
				" EXECUTE IMMEDIATE 'DROP TABLE SET_UP'; " +
				" EXCEPTION " +
				" WHEN OTHERS THEN NULL; " +
				" END; ");
		
		stmt.executeUpdate(" BEGIN " +
				" EXECUTE IMMEDIATE 'DROP TABLE REWARD_INFO'; " +
				" EXCEPTION " +
				" WHEN OTHERS THEN NULL; " +
				" END; ");
		
		stmt.executeUpdate(" BEGIN " +
				" EXECUTE IMMEDIATE 'DROP TABLE REWARD_INSTANCES'; " +
				" EXCEPTION " +
				" WHEN OTHERS THEN NULL; " +
				" END; ");
		
		stmt.executeUpdate(" BEGIN " +
				" EXECUTE IMMEDIATE 'DROP TABLE ACTIVITY_INFO'; " +
				" EXCEPTION " +
				" WHEN OTHERS THEN NULL; " +
				" END; ");
		
		stmt.executeUpdate(" BEGIN " +
				" EXECUTE IMMEDIATE 'DROP TABLE WALLET'; " +
				" EXCEPTION " +
				" WHEN OTHERS THEN NULL; " +
				" END; ");
		
		stmt.executeUpdate(" BEGIN " +
				" EXECUTE IMMEDIATE 'DROP TABLE RE_RULES'; " +
				" EXCEPTION " +
				" WHEN OTHERS THEN NULL; " +
				" END; ");

				stmt.executeUpdate(" BEGIN " +
				" EXECUTE IMMEDIATE 'DROP TABLE RR_RULES'; " +
				" EXCEPTION " +
				" WHEN OTHERS THEN NULL; " +
				" END; ");
		
		stmt.executeUpdate(" BEGIN " +
				" EXECUTE IMMEDIATE 'DROP TABLE ACTIVITY_TYPE'; " +
				" EXCEPTION " +
				" WHEN OTHERS THEN NULL; " +
				" END; ");
				
		stmt.executeUpdate(" BEGIN " +
				" EXECUTE IMMEDIATE 'DROP TABLE REWARD_TYPE'; " +
				" EXCEPTION " +
				" WHEN OTHERS THEN NULL; " +
				" END; ");		
				
		stmt.executeUpdate(" BEGIN " +
		" EXECUTE IMMEDIATE 'DROP TABLE TIERS'; " +
		" EXCEPTION " +
		" WHEN OTHERS THEN NULL; " +
		" END; ");
		
		stmt.executeUpdate(" BEGIN " +
				" EXECUTE IMMEDIATE 'DROP TABLE LOYALTY_PROGRAM'; " +
				" EXCEPTION " +
				" WHEN OTHERS THEN NULL; " +
				" END; ");
		
		stmt.executeUpdate(" BEGIN " +
				" EXECUTE IMMEDIATE 'DROP TABLE CUSTOMER'; " +
				" EXCEPTION " +
				" WHEN OTHERS THEN NULL; " +
				" END; ");

		stmt.executeUpdate(" BEGIN " +
				" EXECUTE IMMEDIATE 'DROP TABLE BRANDS'; " +
				" EXCEPTION " +
				" WHEN OTHERS THEN NULL; " +
				" END; ");

		stmt.executeUpdate(" BEGIN " +
				" EXECUTE IMMEDIATE 'DROP TABLE USER_TABLE'; " +
				" EXCEPTION " +
				" WHEN OTHERS THEN NULL; " +
				" END; ");
		
		Scanner scanner = new Scanner(new File("database_creation.sql"));
		while (scanner.hasNextLine())
		{
			String line = scanner.nextLine();
			if(line.length() < 2 )
				continue;
			stmt.executeUpdate(line);
		}
		scanner.close();
		
		// Seed brand values
		Scanner scanner2 = new Scanner(new File("database_seeding_brands.sql"));
		while (scanner2.hasNextLine())
		{
			String line = scanner2.nextLine();
			if(line.length() < 2 )
				continue;
			
			stmt.executeQuery(line);
		}
		scanner2.close();
		// Perform callable statements to insert reward instances
		CallableStatement cStmt = conn.prepareCall("{call add_reward_instances(?,?,?,?)}");
		cStmt.setString(1, "R01");
		cStmt.setInt(2, 50);
		cStmt.setString(3, "TLP01");
		cStmt.setInt(4, 40);
		cStmt.execute();
		
		cStmt = conn.prepareCall("{call add_reward_instances(?,?,?,?)}");
		cStmt.setString(1, "R02");
		cStmt.setInt(2, 0);
		cStmt.setString(3, "TLP01");
		cStmt.setInt(4, 25);
		cStmt.execute();
		
		cStmt = conn.prepareCall("{call add_reward_instances(?,?,?,?)}");
		cStmt.setString(1, "R01");
		cStmt.setInt(2, 50);
		cStmt.setString(3, "TLP02");
		cStmt.setInt(4, 30);
		cStmt.execute();
		
		cStmt = conn.prepareCall("{call add_reward_instances(?,?,?,?)}");
		cStmt.setString(1, "R02");
		cStmt.setInt(2, 0);
		cStmt.setString(3, "TLP02");
		cStmt.setInt(4, 50);
		cStmt.execute();
		
		cStmt = conn.prepareCall("{call add_reward_instances(?,?,?,?)}");
		cStmt.setString(1, "R01");
		cStmt.setInt(2, 50);
		cStmt.setString(3, "RLP01");
		cStmt.setInt(4, 25);
		cStmt.execute();
		
		// Seed customer
		Scanner scanner3 = new Scanner(new File("database_seeding_customers.sql"));
		while (scanner3.hasNextLine())
		{
			String line = scanner3.nextLine();
			if(line.length() < 2 )
				continue;
			
			stmt.executeQuery(line);
		}
		scanner3.close();
		
		cStmt = conn.prepareCall("{call UPDATE_WALLET_TIER(?,?)}");
		cStmt.setString(1, "C0005");
		cStmt.setString(2, "TLP01");
		cStmt.execute();
		
		cStmt = conn.prepareCall("{call UPDATE_WALLET_TIER(?,?)}");
		cStmt.setString(1, "C0005");
		cStmt.setString(2, "RLP01");
		cStmt.execute();
		
		cStmt = conn.prepareCall("{call UPDATE_WALLET_TIER(?,?)}");
		cStmt.setString(1, "C0003");
		cStmt.setString(2, "TLP02");
		cStmt.execute();
		
		// Add initial wallet events
		cStmt = conn.prepareCall("{call CREATE_INITIAL_WALLET_EVENT(?,?,?)}");
		cStmt.setString(1, "C0001");
		cStmt.setString(2, "TLP01");
		cStmt.setString(3,  "2021-06-10 01:00:00");
		cStmt.execute();
		
		cStmt = conn.prepareCall("{call CREATE_INITIAL_WALLET_EVENT(?,?,?)}");
		cStmt.setString(1, "C0001");
		cStmt.setString(2, "TLP02");
		cStmt.setString(3,  "2021-06-10 01:00:00");
		cStmt.execute();
		
		cStmt = conn.prepareCall("{call CREATE_INITIAL_WALLET_EVENT(?,?,?)}");
		cStmt.setString(1, "C0002");
		cStmt.setString(2, "TLP01");
		cStmt.setString(3,  "2021-07-02 01:00:00");
		cStmt.execute();
		
		cStmt = conn.prepareCall("{call CREATE_INITIAL_WALLET_EVENT(?,?,?)}");
		cStmt.setString(1, "C0003");
		cStmt.setString(2, "TLP02");
		cStmt.setString(3,  "2021-07-30 01:00:00");
		cStmt.execute();
		
		cStmt = conn.prepareCall("{call CREATE_INITIAL_WALLET_EVENT(?,?,?)}");
		cStmt.setString(1, "C0003");
		cStmt.setString(2, "RLP01");
		cStmt.setString(3,  "2021-07-30 01:00:00");
		cStmt.execute();
		
		cStmt = conn.prepareCall("{call CREATE_INITIAL_WALLET_EVENT(?,?,?)}");
		cStmt.setString(1, "C0005");
		cStmt.setString(2, "TLP01");
		cStmt.setString(3,  "2021-08-10 01:00:00");
		cStmt.execute();
		
		cStmt = conn.prepareCall("{call CREATE_INITIAL_WALLET_EVENT(?,?,?)}");
		cStmt.setString(1, "C0005");
		cStmt.setString(2, "TLP02");
		cStmt.setString(3,  "2021-08-10 01:00:00");
		cStmt.execute();
		
		cStmt = conn.prepareCall("{call CREATE_INITIAL_WALLET_EVENT(?,?,?)}");
		cStmt.setString(1, "C0005");
		cStmt.setString(2, "RLP01");
		cStmt.setString(3,  "2021-08-10 01:00:00");
		cStmt.execute();
		
		close(rs);
        close(stmt);
        close(conn);
		
        } catch(Throwable oops) {
            oops.printStackTrace();
        }
    }

    static void close(Connection conn) {
        if(conn != null) {
            try { conn.close(); } catch(Throwable whatever) {}
        }
    }

    static void close(Statement st) {
        if(st != null) {
            try { st.close(); } catch(Throwable whatever) {}
        }
    }

    static void close(ResultSet rs) {
        if(rs != null) {
            try { rs.close(); } catch(Throwable whatever) {}
        }
    }
}
