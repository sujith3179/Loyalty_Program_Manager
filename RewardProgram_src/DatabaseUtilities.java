import java.sql.*;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;

/**
 *
 * @author Geoff
 */
// Acknowledgments: This example is a modification of code provided 
// by Dimitri Rakitine.

// Usage from command line on key.csc.ncsu.edu: 
// see instructions in FAQ
// Website for Oracle setup at NCSU : http://www.csc.ncsu.edu/techsupport/technotes/oracle.php

public class DatabaseUtilities {

    static final String jdbcURL 
	= "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl01";

    // Performs a given update. Returns 1 if successful, 0 otherwise.
    public static int executeUpdate(String update) 
    {
        try 
        {
			// Load the driver. This creates an instance of the driver
			// and calls the registerDriver method to make Oracle Thin
			// driver available to clients.
	
	        Class.forName("oracle.jdbc.OracleDriver");
	        
	        // Load in username and password from config.properties file.
	        
	        String user="";
	        String passwd="";
	        try (InputStream input = new FileInputStream("config.properties")) 
	        {
	        	Properties prop = new Properties();
	        	prop.load(input);
	        	
	        	user = prop.getProperty("user");
	        	passwd = prop.getProperty("passwd");
	        }
	        catch (IOException ex)
	        {
	        	ex.printStackTrace();
	        	return 0;
	        }

	        // Use create_connection() here?
            Connection conn = null;
            Statement stmt = null;

			// Get a connection from the first driver in the
			// DriverManager list that recognizes the URL jdbcURL
	
			conn = DriverManager.getConnection(jdbcURL, user, passwd);
	
			// Create a statement object that will be sending your
			// SQL statements to the DBMS
	
			stmt = conn.createStatement();
	
			// Create tables here
			stmt.executeUpdate(update);
			
			// Close all connections at end
        	close(stmt);
        	close(conn);
			
        } 
        catch(Throwable oops) 
        {
            oops.printStackTrace();
            return 0;
        }
        
        // return 1 if no errors
        return 1;
    }

    // Creates a connection, allowing easier preparedstatement usage.
    // Returns null if an error stopped connection from being made.
    public static Connection createConnection() 
    {
        try 
        {
			// Load the driver. This creates an instance of the driver
			// and calls the registerDriver method to make Oracle Thin
			// driver available to clients.
	
	        Class.forName("oracle.jdbc.OracleDriver");
	        
	        // Load in username and password from config.properties file.
	        
	        String user="";
	        String passwd="";
	        try (InputStream input = new FileInputStream("config.properties")) 
	        {
	        	Properties prop = new Properties();
	        	prop.load(input);
	        	
	        	user = prop.getProperty("user");
	        	passwd = prop.getProperty("passwd");
	        }
	        catch (IOException ex)
	        {
	        	ex.printStackTrace();
	        	return null;
	        }

            Connection conn = null;

			// Get a connection from the first driver in the
			// DriverManager list that recognizes the URL jdbcURL
	
			conn = DriverManager.getConnection(jdbcURL, user, passwd);
	
			return conn;
			
        } 
        catch(Throwable oops) 
        {
            oops.printStackTrace();
            return null;
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
