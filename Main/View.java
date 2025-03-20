import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class View extends Connect{

    public void viewBalance(Scanner input) {		
		int choice,PIN;
		String customerID;
		PreparedStatement prsmt = null;

		do{
			System.out.println("1.Using Account Number\n2.Using Customer ID\n3.Exit");
			System.out.print("Enter Your Choice: ");
			choice = input.nextInt();
			switch(choice)
			{
				case 1:

					System.out.println("Enter Your Account Number:");
					long accountNumber =input.nextLong();
					System.out.println("Enter Your PIN Number:");
					PIN = input.nextInt();

					String select = "SELECT Name, balance FROM userdetails WHERE account_number = ? AND pin =?";

					try(Connection conn = connect())
					{
						prsmt = conn.prepareStatement(select);

						prsmt.setLong(1,accountNumber);
						prsmt.setInt(2,PIN);

						ResultSet rs = prsmt.executeQuery();

						if(rs.next())
						{
							System.out.println("\nUserName : "+ rs.getString("Name"));
							System.out.println("\nCurrent Balance: "+rs.getDouble("balance"));
						}
						else
							System.out.println("\nNo account found with that Account Number Or Wrong PIN Number..\n");
					}
					catch (SQLException e) 
					{
						System.out.println("\nError fetching balance: " + e.getMessage());
					}
					finally
					{
						System.out.println("\nExiting View Balance......");
					}

				break;
				case 2:
					input.nextLine();
					System.out.println("Enter Your Customer ID:");
					customerID = input.nextLine();
					System.out.println("Enter Your PIN Number:");
					PIN = input.nextInt();

					String sql = "SELECT Name, balance FROM userdetails WHERE CustomerID = ? AND pin = ?";

					try (Connection conn = connect()) 
					{
						prsmt = conn.prepareStatement(sql);

						prsmt.setString(1, customerID);
						prsmt.setInt(2,PIN);
						ResultSet rs = prsmt.executeQuery();

						if (rs.next()) 
						{
							System.out.println("\nUserName : "+ rs.getString("Name"));
							System.out.println("\nCurrent Balance: " + rs.getDouble("balance"));
						} 
						else
							System.out.println("\nNo account found with that Customer ID Or Wrong PIN Number..\n");

						}
						catch (SQLException e) {
							System.out.println("\nError fetching balance: " + e.getMessage());
						}
						finally
						{
							System.out.println("\nExiting View Balance......");
						}
				break;
				default:
					System.out.println("\nInvalid Choice!..Try Again!..");
				break;
			}
		}while(choice!=3);
    }
    
}
