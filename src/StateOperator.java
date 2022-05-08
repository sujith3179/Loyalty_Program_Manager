import java.util.Scanner;
// Handles creating views and handling input for different states.
public class StateOperator
{
	public static Scanner scanner = new Scanner(System.in);
	
	// Performs operations depending on the present system state.
	public static void operateState()
	{
		
		
		String input = "";
		
    	// Switch depending on system state
    	switch (RewardSystem.systemState)
    	{
    	
    	case "Home":
    		System.out.println("Home\n1. Login\n2. Sign Up\n3. showQueries\n4. Exit");
    		input = scanner.nextLine();
    		switch (input)
    		{
    		case "1":
    			RewardSystem.systemState = "Login";
    			break;
    		case "2":
    			RewardSystem.systemState = "User Type";
    			break;
    		case "3":
    			RewardSystem.systemState = "showQueries";
    			break;
    		case "4":
    			RewardSystem.systemState = "Exit";
    			break;
    		default:
    			// Give error for any other input
    			System.out.println("Error: please choose a valid input.");
    			break;
    		}
    		break;
    		
    	case "showQueries":
    		System.out.println("1. List all customrs that are not part of Brand02's Program.");
    		System.out.println("2. List all customers that have joined a loyalty program but have not participated in any activity in that program (list the customerid and the loyalty program id).");
    		System.out.println("3. List all the rewards that are part of Brand01 loyalty program.");
    		System.out.println("4. List all the loyalty programs that include 'refer a friend' as an activity in at least one of their reward rules.");
    		System.out.println("5. For Brand01, list for each activity type in their loyalty program, the number instances that have occurred.");
    		System.out.println("6. List customers of Brand01 that have redeemed at least twice");
    		System.out.println("7. All brands where total number of points redeemed overall is less than 500 points");
    		System.out.println("8. For Customer C0003, and Brand02, number of activities they have done in the period of 08/01/2021 and 9/30/2021.");
    		System.out.println("9. Go back");
    		input = scanner.nextLine();
    		switch (input)
    		{
    			case "1":
    				showQueryMethods.query1();
    				break;
    			
    			case "2":
    				showQueryMethods.query2();
    				break;
    			
    			case "3":
    				showQueryMethods.query3();
    				break;
    				
    			case "4":
    				showQueryMethods.query4();
    				break;
    				
    			case "5":
    				showQueryMethods.query5();
    				break;
    				
    			case "6":
    				showQueryMethods.query6();
    				break;
    				
    			case "7":
    				showQueryMethods.query7();
    				break;
    				
    			case "8":
    				showQueryMethods.query8();
    				break;
    				
    			case "9":
    				RewardSystem.systemState = "Home";
    				break;
    				
    			default:
    				System.out.println("Error: please choose a valid input");
    				break;
    		}
    		break;
    		
    	case "Login":
    		System.out.println("Enter login credentials.");
    		System.out.print("User ID:");
    		String id = scanner.nextLine();
    		System.out.print("Password:");
    		String password = scanner.nextLine();
    		System.out.println("1. Sign-in\n2. Go Back");
    		input = scanner.nextLine();
    		switch (input)
    		{
    		case "1":
    			// Perform login
    			String loginOutcome = LoginAndSignup.login(id, password);
    			if (loginOutcome != "Error")
    			{
    				// Login successful
    				RewardSystem.userID = id;
    				RewardSystem.userType = loginOutcome;
    				
    				// Move to landing page for respective user type
    				switch (loginOutcome)
    				{
    				case "Admin":
    					RewardSystem.systemState = "Admin: Landing";
    					break;
    				case "Customer":
    					RewardSystem.systemState = "Customer: Landing";
    					break;
    				case "Brand":
    					RewardSystem.systemState = "Brand: Landing";
    					break;
    				default:
    					System.out.println("Error: unexpected user type.");
    				}
    			}
    			else
    			{
    				System.out.println("Error: invalid login credentials. Please try again.");
    			}
    			break;
    		case "2":
    			RewardSystem.systemState = "Home";
    			break;
    		default:
    			System.out.println("Error: invalid option input. Please re-enter login credentials.");
    			break;
    		}
    		break;
    	
    	case "User Type":
    		System.out.println("Choose if you would like to make a Brand or Customer account.\n1. Brand Sign Up\n2. Customer Sign Up\n3. Go Back");
    		input = scanner.nextLine();
    		switch (input)
    		{
    		case "1":
    			RewardSystem.systemState = "Brand Sign Up";
    			break;
    			
    		case "2":
    			RewardSystem.systemState = "Customer Sign Up";
    			break;
    			
    		case "3":
    			RewardSystem.systemState = "Home";
    			break;
    		default:
    			// Give error for any other input
    			System.out.println("Error: please choose a valid input.");
    			break;
    		}
    		break;
    		
    	case "Brand Sign Up":
    		System.out.println("Enter information to sign up");
    		System.out.print("Brand ID (needed for login):");
    		String brandIDToSignUp = scanner.nextLine();
    		System.out.print("password (needed for login):");
    		String brandPasswordToSignUp = scanner.nextLine();
    		System.out.print("Brand name:");
    		String brandNameToSignUp = scanner.nextLine();
    		System.out.print("Brand address:");
    		String brandAddressToSignUp = scanner.nextLine();
    		System.out.print("Sign up date (enter today's date (in format YYYY-MM-DD)):");
    		String brandSignUpDate = scanner.nextLine();
    		System.out.println("1. Sign-up\n2. Go Back");
    		input = scanner.nextLine();
    		switch (input)
    		{
    		case "1":
    			// Perform signup with user type "Brand"
    			String brandSignUpOutcome = LoginAndSignup.brandSignup(brandIDToSignUp, brandPasswordToSignUp, brandNameToSignUp,
    					brandAddressToSignUp, brandSignUpDate);
    			
    			if(brandSignUpOutcome != "Success")
    			{
    				System.out.println("Error: Sign up failed. UserID may already be taken. Try again.");
    			}
    			else
    			{
    				System.out.println("Sign up successful, you may now log in.");
    				RewardSystem.systemState = "Login";
    			}
    			break;
    			
    		case "2":
    			RewardSystem.systemState = "User Type";
    			break;
    		default:
    			// Give error for any other input
    			System.out.println("Error: please choose a valid input.");
    			break;
    		}
    		break;
    		
    	case "Customer Sign Up":
    		System.out.println("Enter information to sign up");
    		System.out.print("Customer ID (needed for login):");
    		String custIDToSignUp = scanner.nextLine();
    		System.out.print("password (needed for login):");
    		String custPasswordToSignUp = scanner.nextLine();
    		System.out.print("Customer name:");
    		String custNameToSignUp = scanner.nextLine();
    		System.out.print("Customer address:");
    		String custAddressToSignUp = scanner.nextLine();
    		System.out.print("Phone number:");
    		String custPhoneNumber = scanner.nextLine();
    		System.out.println("1. Sign-up\n2. Go Back");
    		input = scanner.nextLine();
    		switch (input)
    		{
    		case "1":
    			// Perform signup with user type "Brand"
    			String custSignUpOutcome = LoginAndSignup.customerSignup(custIDToSignUp, custPasswordToSignUp, custNameToSignUp,
    					custAddressToSignUp, custPhoneNumber);
    			
    			if(custSignUpOutcome != "Success")
    			{
    				System.out.println("Error: Sign up failed. UserID may already be taken. Try again.");
    			}
    			else
    			{
    				System.out.println("Sign up successful, you may now log in.");
    				RewardSystem.systemState = "Login";
    			}
    			break;
    			
    		case "2":
    			RewardSystem.systemState = "User Type";
    			break;
    		default:
    			// Give error for any other input
    			System.out.println("Error: please choose a valid input.");
    			break;
    		}
    		break;
    	
    	//-----BEGIN ADMIN PAGES-----
    		
    	case "Admin: Landing":
    		System.out.println("1. Add brand\n2. Add customer\n3. Show brand's info\n4. Show customer's info\n5. Add activity type\n6. Add reward type\n7. Log out");
    		input = scanner.nextLine();
    		switch (input)
    		{
    		case "1":
    			RewardSystem.systemState = "Admin: Add Brand";
    			break;
    			
    		case "2":
    			RewardSystem.systemState = "Admin: Add Customer";
    			break;
    			
    		case "3":
    			RewardSystem.systemState = "Admin: Show Brand's Info";
    			break;
    			
    		case "4":
    			RewardSystem.systemState = "Admin: Show Customer's Info";
    			break;
    			
    		case "5":
    			RewardSystem.systemState = "Admin: Add Activity Type";
    			break;
    			
    		case "6":
    			RewardSystem.systemState = "Admin: Add Reward Type";
    			break;
    			
    		case "7":
    			RewardSystem.systemState = "Home";
    			RewardSystem.userID = "None";
    			break;
    			
    		default:
    			// Give error for any other input
    			System.out.println("Error: please choose a valid input.");
    			break;
    		}
    		break;
    	
    	case "Admin: Add Brand":
    		System.out.println("Please submit data necessary to create a brand.");
    		System.out.print("Brand ID:");
    		String newBrandID = scanner.nextLine();
    		System.out.print("Brand Password:");
    		String newBrandPassword = scanner.nextLine();
    		System.out.print("Brand name:");
    		String newBrandName = scanner.nextLine();
    		System.out.print("Brand Address:");
    		String newBrandAddress = scanner.nextLine();
    		System.out.print("Brand Join Date (YYYY-MM-DD):");
    		String newBrandJoinDate = scanner.nextLine();
    		System.out.println("1. addBrand\n2. Go Back");
    		input = scanner.nextLine();
    		switch (input)
    		{
    		case "1":
    			// Perform add brand
    			String addBrandOutcome = AdminMethods.addBrand(newBrandID, newBrandPassword,
    					newBrandName, newBrandAddress, newBrandJoinDate);
    			if (addBrandOutcome != "Error")
    			{
    				// Add brand successful
    				System.out.println("Brand added successfully.");
    				RewardSystem.systemState = "Admin: Landing";
    			}
    			else
    			{
    				System.out.println("Error: Failed to create brand. Please try again.");
    			}
    			break;
    		case "2":
    			RewardSystem.systemState = "Admin: Landing";
    			break;
    		default:
    			System.out.println("Error: invalid option input. Please re-enter brand information.");
    			break;
    		}
    		break;
    		
    	case "Admin: Add Customer":
    		System.out.println("Please submit data necessary to create a Customer.");
    		System.out.print("Customer ID:");
    		String newCustomerID = scanner.nextLine();
    		System.out.print("Customer Password:");
    		String newCustomerPassword = scanner.nextLine();
    		System.out.print("Customer name:");
    		String newCustomerName = scanner.nextLine();
    		System.out.print("Customer Address:");
    		String newCustomerAddress = scanner.nextLine();
    		System.out.print("Customer Phone Number:");
    		String newCustomerPhoneNumber = scanner.nextLine();
    		System.out.println("1. addCustomer\n2. Go Back");
    		input = scanner.nextLine();
    		switch (input)
    		{
    		case "1":
    			// Perform add customer
    			String addCustomerOutcome = AdminMethods.addCustomer(newCustomerID, newCustomerPassword,
    					newCustomerName, newCustomerAddress, newCustomerPhoneNumber);
    			if (addCustomerOutcome != "Error")
    			{
    				// Add brand successful
    				System.out.println("Customer added successfully.");
    				RewardSystem.systemState = "Admin: Landing";
    			}
    			else
    			{
    				System.out.println("Error: Failed to create customer. Please try again.");
    			}
    			break;
    		case "2":
    			RewardSystem.systemState = "Admin: Landing";
    			break;
    		default:
    			System.out.println("Error: invalid option input. Please re-enter customer information.");
    			break;
    		}
    		break;
    		
    	case "Admin: Show Brand's Info":
    		System.out.println("Enter ID of Brand to display.");
    		System.out.print("Brand's User ID:");
    		String brand_id = scanner.nextLine();
    		System.out.println("1. showBrandInfo\n2. Go Back");
    		input = scanner.nextLine();
    		switch (input)
    		{
    		case "1":
    			// Perform show brand info
    			String brandInfoOutcome = AdminMethods.showBrandInfo(brand_id);
    			if (brandInfoOutcome != "Error")
    			{
    				// brand info was just displayed, stay on this page.
    			}
    			else
    			{
    				System.out.println("Error: Failed to find brand ID. Please try again.");
    			}
    			break;
    		case "2":
    			RewardSystem.systemState = "Admin: Landing";
    			break;
    		default:
    			System.out.println("Error: invalid option input. Please re-enter brand ID.");
    			break;
    		}
    		break;
    		
    	case "Admin: Show Customer's Info":
    		System.out.println("Enter ID of Customer to display.");
    		System.out.print("Customer's User ID:");
    		String customer_id = scanner.nextLine();
    		System.out.println("1. showCustomerInfo\n2. Go Back");
    		input = scanner.nextLine();
    		switch (input)
    		{
    		case "1":
    			// Perform show customer info
    			String customerInfoOutcome = AdminMethods.showCustomerInfo(customer_id);
    			if (customerInfoOutcome != "Error")
    			{
    				// customer info was just displayed, stay on this page.
    			}
    			else
    			{
    				System.out.println("Error: Failed to find customer ID. Please try again.");
    			}
    			break;
    		case "2":
    			RewardSystem.systemState = "Admin: Landing";
    			break;
    		default:
    			System.out.println("Error: invalid option input. Please re-enter customer ID.");
    			break;
    		}
    		break;
    		
    	case "Admin: Add Activity Type":
    		System.out.println("Give name of new activity type.");
    		System.out.print("ACtivity Name:");
    		String activityTypeName = scanner.nextLine();
    		System.out.println("Give code of new activity type.");
    		System.out.print("Activity Code:");
    		String activityTypeCode = scanner.nextLine();
    		System.out.println("1. addActivityType\n2. Go Back");
    		input = scanner.nextLine();
    		switch (input)
    		{
    		case "1":
    			// Perform add activity
    			String addActivityOutcome = AdminMethods.addActivityType(activityTypeName, activityTypeCode);
    			if (addActivityOutcome != "Error")
    			{
    				// Add activity successful
    				System.out.println("Activity type added successfully.");
    				// Stay on this page
    			}
    			else
    			{
    				System.out.println("Error: Failed to create activity type. Please try again.");
    			}
    			break;
    		case "2":
    			RewardSystem.systemState = "Admin: Landing";
    			break;
    		default:
    			System.out.println("Error: invalid option input. Please re-enter activity information.");
    			break;
    		}
    		break;
    		
    	case "Admin: Add Reward Type":
    		System.out.println("Give name of new reward type.");
    		System.out.print("Reward Name:");
    		String rewardTypeName = scanner.nextLine();
    		System.out.println("Give code of new reward type.");
    		System.out.print("Reward Code:");
    		String rewardTypeCode = scanner.nextLine();
    		System.out.println("1. addRewardType\n2. Go Back");
    		input = scanner.nextLine();
    		switch (input)
    		{
    		case "1":
    			// Perform add activity
    			String addRewardOutcome = AdminMethods.addRewardType(rewardTypeName, rewardTypeCode);
    			if (addRewardOutcome != "Error")
    			{
    				// Add reward successful
    				System.out.println("Reward type added successfully.");
    				// Stay on this page
    			}
    			else
    			{
    				System.out.println("Error: Failed to create reward type. Please try again.");
    			}
    			break;
    		case "2":
    			RewardSystem.systemState = "Admin: Landing";
    			break;
    		default:
    			System.out.println("Error: invalid option input. Please re-enter reward information.");
    			break;
    		}
    		break;
    	
    	//----END OF ADMIN PAGES------
    		
    	//----BEGINNING BRAND PAGES-----
    		
    	case "Brand: Landing":
    		System.out.println("1. addLoyaltyProgram\n2. addRERules\n3. updateRERules\n4. addRRRules\n5. updateRRRules\n6. validateLoyaltyProgram\n7. Log out");
    		input = scanner.nextLine();
    		switch (input)
    		{
    		case "1":
    			RewardSystem.systemState = "Brand: LoyaltyProgram";
    			break;
    			
    		case "2":
    			RewardSystem.systemState = "Brand: addRERules";
    			break;
    			
    		case "3":
    			RewardSystem.systemState = "Brand: updateRERule";
    			break;
    			
    		case "4":
    			RewardSystem.systemState = "Brand: addRRRules";
    			break;
    			
    		case "5":
    			RewardSystem.systemState = "Brand: updateRRRule";
    			break;
    			
    		case "6":
    			RewardSystem.systemState = "Brand: validateLoyaltyProgram";
    			break;
    			
    		case "7":
    			RewardSystem.systemState = "Home";
    			RewardSystem.userID = "None";
    			break;
    			
    		default:
    			// Give error for any other input
    			System.out.println("Error: please choose a valid input.");
    			break;
    		}
    		break;
    		
    	case "Brand: LoyaltyProgram":
    		System.out.println("Give ID of loyalty program (may only do so once).");
    		System.out.print("LP ID:");
    		String loyaltyProgramID = scanner.nextLine();
    		System.out.println("Give name of loyalty program (may only do so once).");
    		System.out.print("LP Name:");
    		String loyaltyProgramName = scanner.nextLine();
    		System.out.println("Choose which type of loyalty program you wish to create (may only choose one)");
    		System.out.println("1. Regular\n2. Tier\n3. Go back");
    		input = scanner.nextLine();
    		String addLPOutcome;
    		switch (input)
    		{
    		case "1":
    			addLPOutcome = BrandMethods.addLoyaltyProgram("Regular",loyaltyProgramName, loyaltyProgramID);
    			if(addLPOutcome == "Error")
    			{
    				System.out.println("Error: Invalid inputs to create loyalty program. Please try again.");
    			}
    			else if(addLPOutcome == "Tier")
    			{
    				System.out.println("Note: You already have a tiered loyalty program. You may not change to an untiered program.");
    			}
    			else if(addLPOutcome == "Regular")
    			{
    				System.out.println("You already have a loyalty program. Moving you to loyalty program options.");
    				RewardSystem.systemState = "Brand: Regular";
    			}
    			else
    			{
    				System.out.println("Successfully created loyalty program.");
    				RewardSystem.systemState = "Brand: Regular";
    			}
    			
    			break;
    			
    		case "2":
    			addLPOutcome = BrandMethods.addLoyaltyProgram("Tier",loyaltyProgramName, loyaltyProgramID);
    			if(addLPOutcome == "Error")
    			{
    				System.out.println("Error: Invalid inputs to create loyalty program. Please try again.");
    			}
    			else if(addLPOutcome == "Tier" || addLPOutcome == "Regular")
    			{
    				System.out.println("You already have a loyalty program. Moving you to tiered loyalty program options.");
    				RewardSystem.systemState = "Brand: Tier";
    			}
    			else
    			{
    				System.out.println("Successfully created loyalty program.");
    				RewardSystem.systemState = "Brand: Tier";
    			}
    			break;
    			
    		case "3":
    			RewardSystem.systemState = "Brand: Landing";
    			break;
    			
    		default:
    			// Give error for any other input
    			System.out.println("Error: please choose a valid input.");
    			break;
    		}
    		break;
    		
    	case "Brand: Regular":
    		System.out.println("1. Activity Types\n2. Reward Types\n3. Go back");
    		input = scanner.nextLine();
    		switch (input)
    		{
    		case "1":
    			RewardSystem.systemState = "Brand: Activity Types";
    			break;
    			
    		case "2":
    			RewardSystem.systemState = "Brand: Reward Types";
    			break;
    			
    		case "3":
    			RewardSystem.systemState = "Brand: LoyaltyProgram";
    			break;
    			
    		default:
    			// Give error for any other input
    			System.out.println("Error: please choose a valid input.");
    			break;
    		}
    		break;
    	
    	case "Brand: Activity Types":
    		System.out.println("Choose an activity type to add or remove");
    		System.out.println("1. Purchase\n2. Leave a review\n3. Refer a friend\n4. Go back");
    		input = scanner.nextLine();
    		String activityToToggle = "";
    		switch (input)
    		{
    		case "1":
    			// Toggle purchase as activity type
    			activityToToggle = "Purchase";
    			break;
    			
    		case "2":
    			// Toggle review as an activity type
    			activityToToggle = "Review";
    			break;
    			
    		case "3":
    			// Toggle refer as an activity type
    			activityToToggle = "Refer";
    			break;
    		
    		case "4":
    			RewardSystem.systemState = "Brand: Regular";
    			break;
    			
    		default:
    			// Give error for any other input
    			System.out.println("Error: invalid option. Please try again.");
    			break;
    		}
    		// switch case determines activity to toggle, now toggle if needed
    		if(activityToToggle != "")
    		{
    			String toggleActivityOutcome = BrandMethods.toggleActivityType(activityToToggle);
    			if(toggleActivityOutcome == "Added")
    			{
    				System.out.println("Activity type was added to program");
    			}
    			else if(toggleActivityOutcome == "Already Added")
    			{
    				System.out.println("Activity type is already part of program");
    			}
    			else
    			{
    				System.out.println("Error: toggle activity type failed.");
    			}
    		}
    		
    		break;
    		
    	case "Brand: Reward Types":
    		System.out.println("Choose the number of the desired reward to create");
    		String rewardInstanceCount = scanner.nextLine();
    		System.out.println("1. Gift Card\n2. Free Product\n3. Go back");
    		input = scanner.nextLine();
    		String rewardType = "";
    		switch (input)
    		{
    		case "1":
    			rewardType = "Gift Card";	
    			break;
    			
    		case "2":
    			rewardType = "Free Product";
    			break;
    			
    		case "3":
    			rewardType = "";
    			RewardSystem.systemState = "Brand: Regular";
    			break;
  
    		default:
    			// Give error for any other input
    			System.out.println("Error: invalid option. Please try again.");
    			break;
    		}
    		// switch case determines reward to toggle, now toggle if needed
    		if(rewardType != "")
    		{
    			String toggleRewardOutcome = BrandMethods.addRewardInstances(rewardType, rewardInstanceCount);
    			if(toggleRewardOutcome == "Success")
    			{
    				System.out.println("Reward instances were added to program");
    			}
    			else
    			{
    				System.out.println("Error: failed to make instances.");
    			}
    		}
    		
    		break;
    		
    	case "Brand: Tier":
    		System.out.println("1. Tiers Set up\n2. Activity Types\n3. Reward Types\n4. Go back");
    		input = scanner.nextLine();
    		switch (input)
    		{
    		case "1":
    			RewardSystem.systemState = "Brand: Tiers Set Up";
    			break;
    			
    		case "2":
    			RewardSystem.systemState = "Brand: Activity Types (Tier)";
    			break;
    			
    		case "3":
    			RewardSystem.systemState = "Brand: Reward Types (Tier)";
    			break;
    			
    		case "4":
    			RewardSystem.systemState = "Brand: LoyaltyProgram";
    			break;
    			
    		default:
    			// Give error for any other input
    			System.out.println("Error: please choose a valid input.");
    			break;
    		}
    		break;
    	
    	case "Brand: Tiers Set Up":
    		System.out.println("Enter the information to set up the tiers.");
    		System.out.print("Number of tiers (max 3): ");
    		String numberOfTiers = scanner.nextLine();
    		if (Integer.parseInt(numberOfTiers) > 3)
    		{
    			System.out.println("You may only have up to three tiers. Using three tiers instead.");
    			numberOfTiers = "3";
    		}
    		System.out.print("Name of the tiers (space seperated, in increasing order of level):");
    		String namesOfTiers = scanner.nextLine();
    		System.out.print("Minimum points required for each tier (space seperated ints):");
    		String cutoffsOfTiers = scanner.nextLine();
    		System.out.print("Multipliers per tier (space seperated floats):");
    		String multipliersOfTiers = scanner.nextLine();
    		System.out.println("1. Set up\n2. Go back");
    		input = scanner.nextLine();
    		switch (input)
    		{
    		case "1":
    			// Set up tiers
    			String setUpTiersOutcome = BrandMethods.setUpTiers(numberOfTiers, namesOfTiers, cutoffsOfTiers, multipliersOfTiers);
    			if (setUpTiersOutcome != "Error")
    			{
    				// Tier set up successful
    				System.out.println("Tiers successfully added.");
    				RewardSystem.systemState = "Brand: Tier";
    			}
    			else
    			{
    				System.out.println("Error: failed to set up tiers. Please try again.");
    			}
    			break;

    		case "2":
    			RewardSystem.systemState = "Brand: Tier";
    			break;

    		default:
    			// Give error for any other input
    			System.out.println("Error: please choose a valid input. Re-enter tier info.");
    			break;
    		}
    		break;
    		
    	case "Brand: Activity Types (Tier)":
    		System.out.println("Choose an activity type to add or remove");
    		System.out.println("1. Purchase\n2. Leave a review\n3. Refer a friend\n4. Go back");
    		input = scanner.nextLine();
    		String tierActivityToToggle = "";
    		switch (input)
    		{
    		case "1":
    			// Toggle purchase as activity type
    			tierActivityToToggle = "Purchase";
    			break;
    			
    		case "2":
    			// Toggle review as an activity type
    			tierActivityToToggle = "Review";
    			break;
    			
    		case "3":
    			// Toggle refer as an activity type
    			tierActivityToToggle = "Refer";
    			break;
    		
    		case "4":
    			RewardSystem.systemState = "Brand: Tier";
    			break;
    			
    		default:
    			// Give error for any other input
    			System.out.println("Error: invalid option. Please try again.");
    			break;
    		}
    		// switch case determines activity to toggle, now toggle if needed
    		if(tierActivityToToggle != "")
    		{
    			String toggleTierActivityOutcome = BrandMethods.toggleActivityType(tierActivityToToggle);
    			if(toggleTierActivityOutcome == "Added")
    			{
    				System.out.println("Activity type was added to program");
    			}
    			else if(toggleTierActivityOutcome == "Already Added")
    			{
    				System.out.println("Note: You have already added this activity type to the program");
    			}
    			else
    			{
    				System.out.println("Error: toggle activity type failed.");
    			}
    		}
    		
    		break;
    		
    	case "Brand: Reward Types (Tier)":
    		System.out.println("Choose the number of the desired reward to create");
    		String rewardInstanceCountTiers = scanner.nextLine();
    		System.out.println("1. Gift Card\n2. Free Product\n3. Go back");
    		input = scanner.nextLine();
    		String rewardTypeTiers = "";
    		switch (input)
    		{
    		case "1":
    			rewardTypeTiers = "Gift Card";	
    			break;
    			
    		case "2":
    			rewardTypeTiers = "Free Product";
    			break;
    			
    		case "3":
    			rewardTypeTiers = "";
    			RewardSystem.systemState = "Brand: Regular";
    			break;
  
    		default:
    			// Give error for any other input
    			System.out.println("Error: invalid option. Please try again.");
    			break;
    		}
    		// switch case determines reward to toggle, now toggle if needed
    		if(rewardTypeTiers != "")
    		{
    			String toggleRewardOutcome = BrandMethods.addRewardInstances(rewardTypeTiers, rewardInstanceCountTiers);
    			if(toggleRewardOutcome == "Success")
    			{
    				System.out.println("Reward instances were added to program");
    			}
    			else
    			{
    				System.out.println("Error: failed to make instances.");
    			}
    		}
    		
    		break;
    		
    	case "Brand: addRERules":
    		System.out.println("Please input the details for the new RE Rule.");
    		
    		System.out.print("Brand reward code:");
    		String rewardRuleCode = scanner.nextLine();
    		
    		System.out.print("Name of activity category (ex. A01, A02, A03):");
    		String activityCategory = scanner.nextLine();
    		
    		System.out.print("Number of points for given activity category:");
    		String pointsPerActivity = scanner.nextLine();
    		
    		System.out.println("1. add RERule\n2. Go back");
    		input = scanner.nextLine();
    		switch (input)
    		{
    		case "1":
    			String addRERuleOutcome = BrandMethods.addRERule(rewardRuleCode, activityCategory, pointsPerActivity);
    			if(addRERuleOutcome != "Error")
    			{
    				System.out.println("Rule successfully added. You may add another rule.");
    			}
    			else
    			{
    				System.out.println("Error: failed to add rule.");
    			}
    			break;
    			
    		case "2":
    			RewardSystem.systemState = "Brand: Landing";
    			break;
    			
    		default:
    			// Give error for any other input
    			System.out.println("Error: invalid option. Please try again.");
    			break;
    		}
    		break;
    		
    	case "Brand: updateRERule":
    		System.out.println("Please input the details for the RE Rule to update.");
    		
    		System.out.print("Brand reward rule code:");
    		String updatedRewardRuleCode = scanner.nextLine();
    		
    		System.out.print("Name of activity category (ex. A01, A02, A03):");
    		String updatedActivityCategory = scanner.nextLine();
    		
    		System.out.print("Number of points for given activity category:");
    		String updatedPointsPerActivity = scanner.nextLine();
    		
    		System.out.println("1. updateRERule\n2. Go back");
    		input = scanner.nextLine();
    		switch (input)
    		{
    		case "1":
    			String updateRERuleOutcome = BrandMethods.addRERule(updatedRewardRuleCode, updatedActivityCategory, updatedPointsPerActivity);
    			if(updateRERuleOutcome != "Error")
    			{
    				System.out.println("Rule successfully updated. You may update another rule.");
    			}
    			else
    			{
    				System.out.println("Error: failed to update rule.");
    			}
    			break;
    			
    		case "2":
    			RewardSystem.systemState = "Brand: Landing";
    			break;
    			
    		default:
    			// Give error for any other input
    			System.out.println("Error: invalid option. Please try again.");
    			break;
    		}
    		break;
    		
    	case "Brand: addRRRules":
    		System.out.println("Please input the details for the new RR Rule.");
    		
    		System.out.print("Brand reward rule code:");
    		String RRRuleCode = scanner.nextLine();
    		
    		System.out.print("Name of reward category (ex. R01, R02):");
    		String RRRewardCategory = scanner.nextLine();
    		
    		System.out.print("Number of point cost for reward category:");
    		String RRPointsPerReward = scanner.nextLine();
    		
    		System.out.println("1. add RRRule\n2. Go back");
    		input = scanner.nextLine();
    		switch (input)
    		{
    		case "1":
    			String addRRRuleOutcome = BrandMethods.addRRRule(RRRuleCode, RRRewardCategory, RRPointsPerReward);
    			if(addRRRuleOutcome != "Error")
    			{
    				System.out.println("Rule successfully added. You may add another rule.");
    			}
    			else
    			{
    				System.out.println("Error: failed to add rule.");
    			}
    			break;
    			
    		case "2":
    			RewardSystem.systemState = "Brand: Landing";
    			break;
    			
    		default:
    			// Give error for any other input
    			System.out.println("Error: invalid option. Please try again.");
    			break;
    		}
    		break;
    		
    	case "Brand: updateRRRule":
    		System.out.println("Please input the details for the RE Rule to update.");
    		
    		System.out.print("Brand reward rule code:");
    		String updatedRRRuleCode = scanner.nextLine();
    		
    		System.out.print("Reward category ID (ex. R01, R02):");
    		String updatedRewardCategory = scanner.nextLine();
    		
    		System.out.print("Number of point cost for reward category:");
    		String updatedRRPointsPerReward = scanner.nextLine();
    		
    		System.out.println("1. updateRRRule\n2. Go back");
    		input = scanner.nextLine();
    		switch (input)
    		{
    		case "1":
    			String updateRRRuleOutcome = BrandMethods.addRRRule(updatedRRRuleCode, updatedRewardCategory, updatedRRPointsPerReward);
    			if(updateRRRuleOutcome != "Error")
    			{
    				System.out.println("Rule successfully updated. You may update another rule.");
    			}
    			else
    			{
    				System.out.println("Error: failed to update rule.");
    			}
    			break;
    			
    		case "2":
    			RewardSystem.systemState = "Brand: Landing";
    			break;
    			
    		default:
    			// Give error for any other input
    			System.out.println("Error: invalid option. Please try again.");
    			break;
    		}
    		break;
    		
    	case "Brand: validateLoyaltyProgram":
    		System.out.println("Validate current loyalty program?");
    		System.out.println("1. Validate\n2. Go back");
    		input = scanner.nextLine();
    		switch (input)
    		{
    		case "1":
    			String validateLoyaltyProgramOutcome = BrandMethods.validateLoyaltyProgram();
    			if(validateLoyaltyProgramOutcome == "Success")
    			{
    				System.out.println("Loyalty program successfully validated!");
    				RewardSystem.systemState = "Brand: Landing";
    			}
    			else
    			{
    				System.out.println("Error: validation failed.");
    				RewardSystem.systemState = "Brand: Landing";
    			}
    			break;
    			
    		case "2":
    			RewardSystem.systemState = "Brand: Landing";
    			break;
    			
    		default:
    			// Give error for any other input
    			System.out.println("Error: invalid option. Please try again.");
    			break;
    		}
    		break;
    		
		//----END OF BRAND VIEWS-------------
    		
		//----BEGINNING OF CUSTOMER VIEWS----
    		
    	case "Customer: Landing":
    		System.out.println("1. Enroll in Loyalty Program\n2. Reward Activities\n3. View Wallet\n4. Redeem Points\n5. Log out");
    		input = scanner.nextLine();
    		switch (input)
    		{
    		case "1":
    			RewardSystem.systemState = "Customer: Enroll In Loyalty Program";
    			break;
    			
    		case "2":
    			RewardSystem.systemState = "Customer: Reward Activities";
    			break;
    			
    		case "3":
    			RewardSystem.systemState = "Customer: View Wallet";
    			break;
    			
    		case "4":
    			RewardSystem.systemState = "Customer: Redeem Points";
    			break;
    			
    		case "5":
    			RewardSystem.systemState = "Home";
    			RewardSystem.userID = "None";
    			break;
    			
    		default:
    			// Give error for any other input
    			System.out.println("Error: please choose a valid input.");
    			break;
    		}
    		break;
    		
    	case "Customer: Enroll In Loyalty Program":
    		
    		// Display all available loyalty programs by name
    		System.out.println("Choose an available loyalty programs:");
    		CustomerMethods.showUnjoinedLoyaltyPrograms();
    		System.out.print("Loyalty Program ID to enroll in:");
    		String loyaltyIDToEnroll = scanner.nextLine();
    		System.out.println("1. Enroll in Loyalty Program\n2. Go back");
    		input = scanner.nextLine();
    		switch (input)
    		{
    		case "1":
    			// Attempt to enroll
    			String loyaltyProgramEnrollOutcome = CustomerMethods.enroll(loyaltyIDToEnroll);
    			
    			if(loyaltyProgramEnrollOutcome == "Success")
    			{
    				System.out.println("Enrollment successful.");
    				RewardSystem.systemState = "Customer: Landing";
    			}
    			else
    			{
    				System.out.println("Enrollment failed, please try again.");
    			}
    			break;
    			
    		case "2":
    			RewardSystem.systemState = "Customer: Landing";
    			break;
    			
    		default:
    			// Give error for any other input
    			System.out.println("Error: please choose a valid input.");
    			break;
    		}
    		break;
    		
    	case "Customer: Reward Activities":
    		
    		// Display the list of joined loyalty programs
    		System.out.println("Choose the loyalty program you performed the activity for:");
    		
    		CustomerMethods.showJoinedLoyaltyPrograms();
    		System.out.print("Chosen loyalty program:");
    		String loyaltyIDForActivities = scanner.nextLine();
    		boolean isEnrolledInLoyaltyProgram = CustomerMethods.isEnrolled(loyaltyIDForActivities);
    		
    		if(isEnrolledInLoyaltyProgram)
    		{
    			// we are enrolled with this loyalty program, save it to system variable
    			RewardSystem.loyaltyProgramID = loyaltyIDForActivities;
    			
    			// Determine which activities are valid for this program.
    			boolean hasPurchase = CustomerMethods.hasPurchase();
    			boolean hasReview = CustomerMethods.hasReview();
    			boolean hasRefer = CustomerMethods.hasRefer();
    			
    			if(hasPurchase)
    				System.out.println("1. Purchase");
    			if(hasReview)
    				System.out.println("2. Leave a review");
    			if(hasRefer)
    				System.out.println("3. Refer a friend");
    			System.out.println("4. Go back");
    			input = scanner.nextLine();
        		switch (input)
        		{
        		case "1":
        			if(hasPurchase)
        				RewardSystem.systemState = "Customer: Purchase";
        			else
        				System.out.println("Error: invalid input. Please try again.");
        			break;
        			
        		case "2":
        			if(hasReview)
        				RewardSystem.systemState = "Customer: Leave A Review";
        			else
        				System.out.println("Error: invalid input. Please try again.");
        			break;
        			
        		case "3":
        			if(hasRefer)
        				RewardSystem.systemState = "Customer: Refer A Friend";
        			else
        				System.out.println("Error: invalid input. Please try again.");
        			break;
        		
        		case "4":
        			RewardSystem.systemState = "Customer: Landing";
        			break;
        			
        		default:
        			System.out.println("Error: invalid input. Please try again");
        			break;
        		}
    		}
    		else
    		{
    			System.out.println("Error: you are not enrolled in the given loyalty program ID.");
    			RewardSystem.systemState = "Customer: Landing";
    		}
    		break;
    		
    	case "Customer: Purchase":
    		System.out.println("Enter purchase amount.");
    		System.out.print("Purchase amount:");
    		String amount = scanner.nextLine();
    		
    		System.out.println("Enter date and time of purchase (in format YYYY-MM-DD HH:mm:SS)");
    		System.out.print("Date of purchase:");
    		String dateOfPurchase = scanner.nextLine();
    		
    		System.out.println("Please enter gift card code, if any (if none, leave blank).");
    		String giftCardCode = scanner.nextLine();
    		System.out.println("1. Purchase\n2. Go back");
    		input = scanner.nextLine();
    		switch (input)
    		{
    		case "1":
    			// Make purchase
    			String PurchaseOutcome = CustomerMethods.purchase(amount, dateOfPurchase, giftCardCode);
    			
    			if(PurchaseOutcome == "Success")
    			{
    				System.out.println("Purchase successfully noted.");
    				RewardSystem.systemState = "Customer: Reward Activities";
    			}
    			else
    			{
    				System.out.println("Error: failed to add purchase. Please double-check input data and try again.");
    			}
    			break;
    			
    		case "2":
    			RewardSystem.systemState = "Customer: Landing";
    			break;
    			
    		default:
    			// Give error for any other input
    			System.out.println("Error: please choose a valid input.");
    			break;
    		}
    		break;
    		
    	case "Customer: Leave A Review":
    		System.out.println("Please enter your review. Press enter when you are done.");
    		System.out.print("Review:");
    		String review = scanner.nextLine();
    		
    		System.out.println("Enter date of review (in format YYYY-MM-DD HH:mm:SS)");
    		System.out.print("Date of review:");
    		String dateOfReview = scanner.nextLine();
    		
    		System.out.println("1. Leave a review\n2. Go back");
    		input = scanner.nextLine();
    		switch (input)
    		{
    		case "1":
    			// Make review
    			String ReviewOutcome = CustomerMethods.review(review, dateOfReview);
    			
    			if(ReviewOutcome == "Success")
    			{
    				System.out.println("Review successfully noted.");
    				RewardSystem.systemState = "Customer: Reward Activities";
    			}
    			else
    			{
    				System.out.println("Error: failed to add review. Please double-check input data and try again.");
    			}
    			break;
    			
    		case "2":
    			RewardSystem.systemState = "Customer: Landing";
    			break;
    			
    		default:
    			// Give error for any other input
    			System.out.println("Error: please choose a valid input.");
    			break;
    		}
    		break;
    		
    	case "Customer: Refer A Friend":
    		System.out.println("Please enter the user ID of the friend you have referred.");
    		System.out.print("Friend ID:");
    		String referredID = scanner.nextLine();
    		
    		// Check if referred ID exists
    		boolean isValidID = CustomerMethods.isValidID(referredID);
    		if(isValidID)
    		{
    			System.out.println("Enter date of referral (in format YYYY-MM-DD HH:mm:SS)");
        		System.out.print("Date of referral:");
        		String dateOfReferral = scanner.nextLine();
        		
        		System.out.println("1. Refer\n2. Go back");
        		input = scanner.nextLine();
        		switch (input)
        		{
        		case "1":
        			// Make referral
        			String ReferOutcome = CustomerMethods.refer(referredID, dateOfReferral);
        			
        			if(ReferOutcome == "Success")
        			{
        				System.out.println("Referal successfully noted.");
        			}
        			else
        			{
        				System.out.println("Error: failed to add referal. Please double-check input data and try again.");
        			}
        			break;
        			
        		case "2":
        			RewardSystem.systemState = "Customer: Landing";
        			break;
        			
        		default:
        			// Give error for any other input
        			System.out.println("Error: please choose a valid input.");
        			break;
        		}
    		}
    		else
    		{
    			// referred ID was not found
    			System.out.println("Error: no one is currently registered with that ID. Ensure the referred friend has already made an account and try again.");
    			RewardSystem.systemState = "Customer: Landing";
    		}
    		break;
    		
    	case "Customer: View Wallet":
    		// Display all wallets the user has
    		CustomerMethods.printAllWallets();
    		
    		System.out.println("1. Go back");
    		input = scanner.nextLine();
    		switch (input)
    		{
    		case "1":
    			RewardSystem.systemState = "Customer: Landing";
    			break;
    			
    		default:
    			// Give error for any other input
    			System.out.println("Error: please choose a valid input.");
    			break;
    		}
    		break;
    		
    	case "Customer: Redeem Points":
    		// Display the list of joined loyalty programs
    		System.out.println("Choose the loyalty program you wish to redeem points for:");
    		
    		CustomerMethods.showJoinedLoyaltyPrograms();
    		System.out.print("Chosen loyalty program:");
    		String loyaltyIDForRewards = scanner.nextLine();
    		boolean isEnrolledInRewardLoyaltyProgram = CustomerMethods.isEnrolled(loyaltyIDForRewards);
    		
    		if(isEnrolledInRewardLoyaltyProgram)
    		{
    			// we are enrolled with this loyalty program, save it to system variable
    			RewardSystem.loyaltyProgramID = loyaltyIDForRewards;
    		
	    		// Display all rewards for the current loyalty program
	    		CustomerMethods.printAllRewards();
	    		System.out.println("Please choose a reward ID from the above");
	    		System.out.print("Reward ID desired:");
	    		String rewardID = scanner.nextLine();
	    		
	    		System.out.println("1. Rewards Selection \n2. Go back");
	    		input = scanner.nextLine();
	    		switch (input)
	    		{
	    		case "1":
	    			// Try to redeem reward
	    			String redeemOutcome = CustomerMethods.redeem(rewardID);
	    			if(redeemOutcome == "Success")
	    			{
	    				System.out.println("Reward successfully redeemed!");
	    				RewardSystem.systemState = "Customer: Reward Activities";
	    			}
	    			else
	    			{
	    				System.out.println("Could not redeem. Check you have sufficient points to spend and try again.");
	    			}
	    			break;
	    		
	    		case "2":
	    			RewardSystem.systemState = "Customer: Landing";
	    			break;
	    			
	    		default:
	    			// Give error for any other input
	    			System.out.println("Error: please choose a valid input.");
	    			break;
	    		}
    		}
    		else
    		{
    			System.out.println("Error: you are not enrolled in the given loyalty program ID.");
    			RewardSystem.systemState = "Customer: Landing";
    		}
    		break;
    		
    	// End of state definitions
    	default:
    		// This should only occur for unsupported states.
    		System.out.println("Error: the requested state is invalid or not supported. Returning to home.");
    		RewardSystem.systemState = "Home";
    		break;
    	}
    }
	
}
