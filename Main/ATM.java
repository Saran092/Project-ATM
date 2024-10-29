// javac -cp .;D:\Project\SublimeText\Java\mysql-connector-java-8.0.25.jar ATM.java Main.java
// java -cp .;D:\Project\SublimeText\Java\mysql-connector-java-8.0.25.jar Main
import java.sql.*;
import java.util.*;


public class ATM{
	private int choice,PIN;
	private Long mobileNumber;
	private String accountHolderName;
	private Double balance=0.00;


	private Connection connect() throws SQLException {
		String Url = "jdbc:mysql://localhost:3306/atmproject";
        String User = "root";
        String Password = "Saran092&";
        return DriverManager.getConnection(Url,User,Password);
	}


	// Create an Account
	public void createAccount(Scanner input)
	{
		input.nextLine();
		System.out.println("Enter Your Name(Full Name):");
		accountHolderName=input.nextLine();
		System.out.println("Enter Your Mobile Number:");
		mobileNumber= input.nextLong();
		if(String.valueOf(mobileNumber).length() != 10){
			System.out.println("\nMake Check Your Mobile Number\n");
		}
		else{
			Random random = new Random();
	        long accountNumber = 100000000000L + (long)(random.nextDouble() * 900000000000L);
	        System.out.println("Your Account Number(Make Sure Remeber Your Account Number): " + accountNumber);
	        System.out.println("Enter Your PIN Number(Make Sure You Remeber That):");
			PIN = input.nextInt();

	        String Insert = "INSERT INTO userdetails (Name, account_number, mobile_number, balance, PIN) VALUES (?, ?, ?, ?, ?)";
	        try (Connection conn = connect();
	            PreparedStatement prsmt = conn.prepareStatement(Insert)) {
	        	prsmt.setString(1, accountHolderName);
	            prsmt.setLong(2,accountNumber);
	            prsmt.setLong(3, mobileNumber);
	            prsmt.setDouble(4, balance);
	            prsmt.setInt(5,PIN);
	            prsmt.executeUpdate();
	            System.out.println("\nAccount created successfully!\n");
	        } catch (SQLException e) {
	            System.out.println("\nError creating account: " + e.getMessage());
	        }
    	}
	}


	// View Balance

	public void viewBalance(Scanner input) {		
		int choice;
		do{
			System.out.println("1.Using Account Number\n2.Using Mobile Number\n3.Exit");
			System.out.print("Enter Your Choice: ");
			choice = input.nextInt();
			switch(choice){
				case 1:
					System.out.println("Enter Your Account Number:");
					long accountNumber =input.nextLong();
					System.out.println("Enter Your PIN Number:");
					PIN = input.nextInt();

					String select = "SELECT Name, balance FROM userdetails WHERE account_number = ? AND pin =?";
					try(Connection conn = connect();
						 PreparedStatement prsmt = conn.prepareStatement(select)){
						prsmt.setLong(1,accountNumber);
						prsmt.setInt(2,PIN);
						ResultSet rs = prsmt.executeQuery();

						if(rs.next()){
							System.out.println("UserName : "+ rs.getString("Name"));
							System.out.println("Current Balance: "+rs.getDouble("balance"));
						}else{
							System.out.println("No account found with that Account Number Or Wrong PIN Number..\n");
						}

					}catch (SQLException e) {
            			System.out.println("Error fetching balance: " + e.getMessage());
					}
					break;
				case 2:
					System.out.println("Enter Your Mobile Number:");
			        long mobileNumber = input.nextLong();
			        System.out.println("Enter Your PIN Number:");
					PIN = input.nextInt();

			        String sql = "SELECT Name, balance FROM userdetails WHERE mobile_number = ? AND pin = ?";
			        try (Connection conn = connect();
			             PreparedStatement prsmt = conn.prepareStatement(sql)) {
			            prsmt.setLong(1, mobileNumber);
			            prsmt.setInt(2,PIN);
			            ResultSet rs = prsmt.executeQuery();

			            if (rs.next()) {
			            	System.out.println("UserName : "+ rs.getString("Name"));
			                System.out.println("Current Balance: " + rs.getDouble("balance"));
			            } else {
			                System.out.println("No account found with that Mobile Number Or Wrong PIN Number..\n");
			            }

			        }catch (SQLException e) {
			            System.out.println("Error fetching balance: " + e.getMessage());
			        }
			        break;
			    case 3:
			    	System.out.println("\nExiting...");
			    	break;
			    default:
			    	System.out.println("Invalid Choice!..Try Again!..");
			    	break;
			}
		}while(choice!=3);
    }


    //Deposit Amount

    public void depositAmount(Scanner input) {

    	int choice,changed;
    	double amount;
    	String update,select;
    	do{
    		System.out.println("1.Deposit Through Account Number\n2.Deposit Through Mobile Number\n3.Exit");
    		System.out.print("Enter Your Choice: ");
    		choice=input.nextInt();
    		switch(choice)
    		{
	    		case 1:
	    			System.out.println("Enter Your Account Number:");
	    			long accountNumber = input.nextLong();
	    			System.out.println("Enter Your PIN Number:");
	    			PIN = input.nextInt();
	    			System.out.println("Enter Amount to Deposit:");
        			amount = input.nextDouble();

        			update = "UPDATE userdetails SET balance = balance + ? WHERE account_number = ? AND pin =?";
        			select = "SELECT Name, balance FROM userdetails WHERE account_number= ? AND pin =?";
        			try(Connection conn = connect();
        				PreparedStatement dismt = conn.prepareStatement(select);
        				PreparedStatement prsmt = conn.prepareStatement(update)){

        				dismt.setLong(1, accountNumber);
        				dismt.setInt(2,PIN);
         				ResultSet rs = dismt.executeQuery();

         				if(rs.next()){
         					String accountName = rs.getString("Name");
         					prsmt.setDouble(1,amount);
        					prsmt.setLong(2,accountNumber);
        					prsmt.setInt(3,PIN);
        					changed = prsmt.executeUpdate();
        					if(changed >0 ){
        						ResultSet upRs = dismt.executeQuery();
        						if(upRs.next()){
        							System.out.println("Amount Deposit to Account Holder: "+ accountName +" Deposited Amount:"+ amount);
        							System.out.println("Current Balance: "+ upRs.getDouble("balance"));
        						}
        					}else {
			                	System.out.println("No account found with that Account Number or Wrong PIN.");
			            	}
         				}else {
			                System.out.println("No account found with that Account Number or Wrong PIN.");
			            }
			        } catch (SQLException e) {
			            System.out.println("Error during deposit: " + e.getMessage());
			        }
	    			break;
	    		case 2:
	    			System.out.println("Enter Your Mobile Number:");
			        long mobileNumber = input.nextLong();
			        System.out.println("Enter Your PIN Number:");
	    			PIN = input.nextInt();
			        System.out.println("Enter Amount to Deposit:");
			        amount = input.nextDouble();

			        update = "UPDATE userdetails SET balance = balance + ? WHERE mobile_number = ? AND pin =?";
			        select = "SELECT Name, balance FROM userdetails WHERE mobile_number= ? AND pin =?";
			        try (Connection conn = connect();
			        	 PreparedStatement dismt = conn.prepareStatement(select);
			             PreparedStatement prsmt = conn.prepareStatement(update)) {

			        	dismt.setLong(1,mobileNumber);
			        	dismt.setInt(2,PIN);
			        	ResultSet rs = dismt.executeQuery();
			        	if(rs.next()){
			        		String accountName = rs.getString("Name");
			        		prsmt.setDouble(1, amount);
			            	prsmt.setLong(2, mobileNumber);
			            	prsmt.setInt(3,PIN);

			           	 	changed = prsmt.executeUpdate();

			            	if (changed > 0) {
			            		ResultSet upRs = dismt.executeQuery();
			            		if(upRs.next()){
			                		System.out.println("Amount Deposit to Account Holder: "+ accountName +" Deposited Amount:"+ amount);
        							System.out.println("Current Balance: "+ upRs.getDouble("balance"));
			            		}
			            	} else {
			                	System.out.println("No account found with that mobile number or Worng PIN.");
			            	}
			        	} else {
			                	System.out.println("No account found with that mobile number or Worng PIN.");
			            }
			        } catch (SQLException e) {
			            System.out.println("Error during deposit: " + e.getMessage());
			        }
	    			break;
	    		case 3:
			    	System.out.println("\nExiting...");
			    	break;
			    default:
			    	System.out.println("Invalid Choice!..Try Again!..");
			    	break;
    		}
    	}while(choice!=3);       
    }

    // WithDraw Amount

    public void withdrawAmount(Scanner input) {
    	int choice,changed;
    	double amount;
    	String update,select;

    	do{
    		System.out.println("1.WithDrawn Through Account Number\n2.WithDrawn Through Mobile Number\n3.Exit");
    		System.out.print("Enter Your Choice: ");
    		choice = input.nextInt();
    		switch(choice)
    		{
	    		case 1:
	    			System.out.println("Enter Your Account Number:");
	    			long accountNumber = input.nextLong();
	    			System.out.println("Enter Your PIN Number:");
	    			PIN = input.nextInt();
	    			System.out.println("Enter Amount to Withdraw:");
	    			amount = input.nextDouble();

	    			update = "UPDATE userdetails SET balance = balance -? WHERE account_number = ? AND pin = ? AND balance >=?";
	    			select = "SELECT Name, balance FROM userdetails WHERE account_number= ? AND pin =?";
	    			try(Connection conn = connect();
	    				 PreparedStatement dismt = conn.prepareStatement(select);
	    				 PreparedStatement prsmt = conn.prepareStatement(update)){
	    				dismt.setLong(1,accountNumber);
	    				dismt.setInt(2,PIN);
	    				ResultSet rs = dismt.executeQuery();
	    				if(rs.next()){
	    					String accountName = rs.getString("Name");
		    				prsmt.setDouble(1,amount);
		    				prsmt.setLong(2,accountNumber);
		    				prsmt.setInt(3,PIN);
		    				prsmt.setDouble(4,amount);
		    				changed = prsmt.executeUpdate();
		    				if(changed >0){
		    					ResultSet upRs = dismt.executeQuery();
		    					if(upRs.next()){
		    						System.out.println("Withdrawn From Account Name: "+accountName);
		    						System.out.println("You WithDraw : "+ amount);
		    						System.out.println("Current Balance: "+upRs.getDouble("balance"));
		    					}
		    				}
		    				else{
		    					System.out.println("Insufficient balance or no account found..");
		    				} 
	    				}else{
		    					System.out.println("Insufficient balance or no account found..");
		    			}
	    			} catch (SQLException e) {
			            	System.out.println("Error during withdrawal: " + e.getMessage());
			        }
	    			break;
	    		case 2:
	    			System.out.println("Enter Your Mobile Number:");
			        long mobileNumber = input.nextLong();
			        System.out.println("Enter Your PIN Number:");
			        PIN = input.nextInt();
			        System.out.println("Enter Amount to Withdraw:");
			       	amount = input.nextDouble();

			        update = "UPDATE userdetails SET balance = balance - ? WHERE mobile_number = ? AND pin =? AND balance >= ?";
			        select = "SELECT Name, balance FROM userdetails WHERE mobile_number= ? AND pin =?";
			        try (Connection conn = connect();
			        	 PreparedStatement dismt = conn.prepareStatement(select);
			             PreparedStatement prsmt = conn.prepareStatement(update)) {
			          
			            dismt.setLong(1,mobileNumber);
			            dismt.setInt(2,PIN);
			            ResultSet rs = dismt.executeQuery();

			            if(rs.next()){
			            	String accountName = rs.getString("Name");
			            	prsmt.setDouble(1, amount);
				            prsmt.setLong(2, mobileNumber);
				            prsmt.setInt(3,PIN);
				            prsmt.setDouble(4,amount);
				            changed = prsmt.executeUpdate();
				            if(changed >0){
			    					ResultSet upRs = dismt.executeQuery();
			    					if(upRs.next()){
			    						System.out.println("Withdrawn From Account Name: "+accountName);
			    						System.out.println("You WithDraw : "+ amount);
			    						System.out.println("Current Balance: "+upRs.getDouble("balance"));
			    					}
			    				}
			    				else{
			    					System.out.println("Insufficient balance or no account found..");
			    				}
			            }else{
		    					System.out.println("Insufficient balance or no account found..");
		    			}
			        } catch (SQLException e) {
			            System.out.println("Error during withdrawal: " + e.getMessage());
			        }
	    			break;
	    		case 3:
	    			System.out.println("\nExiting...");
	    			break;
	    		default:
	    			System.out.println("Invalid Choice!..Try Again!..");
	    			break;
    		}
    	}while(choice!=3);
    }
}