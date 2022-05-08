public class RewardProgram {

    static final String jdbcURL 
	= "jdbc:oracle:thin:@ora.csc.ncsu.edu:1521:orcl01";
    
    public static void main(String[] args) 
    {
    	// Initialize system state to home
    	RewardSystem.systemState = "Home";
    	
    	// Open database connection
    	try
    	{
        	RewardSystem.conn = DatabaseUtilities.createConnection();
    	}
    	catch(Throwable oops)
    	{
    		System.out.println("Error occurred when establishing database connection.");
            oops.printStackTrace();
    	}
    	
    	// General structure will be looped until system is exited.
    	while(true)
    	{
    		// Perform operations for the current state
    		StateOperator.operateState();

    		// Break if system state ever becomes exit
    		if(RewardSystem.systemState == "Exit")
    		{
    			
    			DatabaseUtilities.close(RewardSystem.conn);
    			StateOperator.scanner.close();
    			break;
    		}
    	}
    	
       
    }
}