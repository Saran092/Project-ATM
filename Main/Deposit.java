import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.*;

public class Deposit extends Connect{
    
    public void depositAmount(Scanner input) 
	{
		int choice,changed,PIN,id;
		double amount;
		String update,select,sql,CustomerID,CustID;
		PreparedStatement dismt = null;
		PreparedStatement prsmt = null;
		PreparedStatement sam = null;
        DateTimeFormatter formate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.now();
        String Date = currentDate.format(formate);
		do{

			System.out.println("1.Deposit Through Account Number\n2.Deposit Through Customer ID\n3.Exit");
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

					sql = "INSERT INTO deposit(DepositAmount,DepositDate,userId,CustID) VALUES(?, ?, ?, ?)";
					update = "UPDATE userdetails SET balance = balance + ? WHERE account_number = ? AND pin =?";
					select = "SELECT id,Name,CustomerID,balance FROM userdetails WHERE account_number= ? AND pin =?";

					try(Connection conn = connect())
					{
						dismt = conn.prepareStatement(select);
						prsmt = conn.prepareStatement(update);
						sam = conn.prepareStatement(sql);
						dismt.setLong(1, accountNumber);
						dismt.setInt(2,PIN);
						ResultSet rs = dismt.executeQuery();

         				if(rs.next())
						{
							String accountName = rs.getString("Name");
							id = rs.getInt("id");
                            CustID = rs.getString("CustomerID");

							sam.setDouble(1, amount);
                            sam.setString(2, Date);
							sam.setInt(3, id);
                            sam.setString(4, CustID);

							prsmt.setDouble(1,amount);
							prsmt.setLong(2,accountNumber);
							prsmt.setInt(3,PIN);

							sam.executeUpdate();
							changed = prsmt.executeUpdate();

							if(changed >0 )
							{
								ResultSet upRs = dismt.executeQuery();
								if(upRs.next())
								{
									System.out.println("\nAmount Deposit to Account Holder Name : "+ accountName +"\nDeposited Amount:"+ amount);
									System.out.println("\nCurrent Balance: "+ upRs.getDouble("balance"));
								}
							}
							else
								System.out.println("\nNo account found with that Account Number or Wrong PIN.");
         				}
						else
							System.out.println("\nNo account found with that Account Number or Wrong PIN.");
					} 
					catch (SQLException e) 
					{
						System.out.println("\nError during deposit: " + e.getMessage());
					}
					finally
					{
						System.out.println("\nExiting Deposit ......\n");
					}
				break;
				case 2:
                    input.nextLine();
					System.out.println("Enter Your Customer ID:");
					CustomerID = input.nextLine();
					System.out.println("Enter Your PIN Number:");
					PIN = input.nextInt();
					System.out.println("Enter Amount to Deposit:");
					amount = input.nextDouble();

                    sql = "INSERT INTO deposit(DepositAmount,DepositDate, userId,CustID) VALUES(?, ?, ?, ?)";
					update = "UPDATE userdetails SET balance = balance + ? WHERE CustomerID = ? AND pin =?";
					select = "SELECT id,Name,CustomerID,balance FROM userdetails WHERE CustomerID= ? AND pin =?";

					try (Connection conn = connect()) 
					{
						dismt = conn.prepareStatement(select);
						prsmt = conn.prepareStatement(update);
                        sam = conn.prepareStatement(sql);

						dismt.setString(1,CustomerID);
						dismt.setInt(2,PIN);
						ResultSet rs = dismt.executeQuery();

						if(rs.next())
						{
							String accountName = rs.getString("Name");
                            id = rs.getInt("id");
                            CustID = rs.getString("CustomerID");

                            sam.setDouble(1, amount);
                            sam.setString(2, Date);
                            sam.setInt(3, id);
                            sam.setString(4, CustID);

							prsmt.setDouble(1, amount);
							prsmt.setString(2, CustomerID);
							prsmt.setInt(3,PIN);
                            
                            sam.executeUpdate();
							changed = prsmt.executeUpdate();

							if (changed > 0) 
							{
								ResultSet upRs = dismt.executeQuery();
								if(upRs.next()){
									System.out.println("\nAmount Deposit to Account Holder: "+ accountName +" Deposited Amount:"+ amount);
									System.out.println("\nCurrent Balance: "+ upRs.getDouble("balance"));
								}
							} 
							else {
								System.out.println("\nNo account found with that Customer ID or Worng PIN.");
							}
						} 
						else
							System.out.println("\nNo account found with that Customer ID or Worng PIN.");
					} catch (SQLException e) {
						System.out.println("\nError during deposit: " + e.getMessage());
					}
					finally
					{
						System.out.println("\nExiting Deposit ......\n");
					}
				break;
				case 3:
					System.out.println("\n	Exit From Deposit....	\n");
				default:
					System.out.println("\nInvalid Choice!..Try Again!..\n");
					break;
			}
		}while(choice!=3);       
    }
}
