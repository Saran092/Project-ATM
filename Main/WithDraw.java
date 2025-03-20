import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.*;

public class WithDraw extends Connect{
    int choice,changed,PIN,id;
    double amount;
    String update,select,sql,CustomerID,CustID;
    PreparedStatement dismt = null;
    PreparedStatement prsmt = null;
    PreparedStatement sam = null;
    DateTimeFormatter formate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate currenDate = LocalDate.now();
    String Date = currenDate.format(formate);
    public void withdrawAmount(Scanner input){
		do{
			System.out.println("1.WithDrawn Through Account Number\n2.WithDrawn Through Customer ID\n3.Exit");
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

                    sql = "INSERT into Withdraw (WithdrawAmount,WithdrawDate,userID,CustID) VALUES(?, ?, ?, ?)";
					update = "UPDATE userdetails SET balance = balance -? WHERE account_number = ? AND pin = ? AND balance >=?";
					select = "SELECT id,Name,CustomerID,balance FROM userdetails WHERE account_number= ? AND pin =?";

					try(Connection conn = connect())
					{
						dismt = conn.prepareStatement(select);
						prsmt = conn.prepareStatement(update);
                        sam = conn.prepareStatement(sql);
						dismt.setLong(1,accountNumber);
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
							prsmt.setDouble(4,amount);

                            sam.executeUpdate();
							changed = prsmt.executeUpdate();

							if(changed >0)
							{
								ResultSet upRs = dismt.executeQuery();
								if(upRs.next()){
									System.out.println("\nWithdrawn From Account Name: "+accountName);
									System.out.println("\nYou WithDraw : "+ amount);
									System.out.println("\nCurrent Balance: "+upRs.getDouble("balance"));
								}
							}
							else
								System.out.println("\nInsufficient balance or no account found..");
						}	
						else
						System.out.println("\nInsufficient balance or no account found..");
					} 
					catch (SQLException e) 
					{
						System.out.println("\nError during withdrawal: " + e.getMessage());
					}
					finally
					{
						System.out.println("\nExiting Withdrawal ......\n");
					}
				break;
				case 2:
                    input.nextLine();
					System.out.println("Enter Your Customer ID :");
					CustomerID = input.nextLine();
					System.out.println("Enter Your PIN Number:");
					PIN = input.nextInt();
					System.out.println("Enter Amount to Withdraw:");
					amount = input.nextDouble();

                    sql = "INSERT into Withdraw (WithdrawAmount,WithdrawDate,userID,CustID) VALUES(?, ?, ?, ?)";
					update = "UPDATE userdetails SET balance = balance - ? WHERE CustomerID = ? AND pin =? AND balance >= ?";
					select = "SELECT id,Name,CustomerID,balance FROM userdetails WHERE CustomerID = ? AND pin =?";

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
							prsmt.setDouble(4,amount);

                            sam.executeUpdate();
							changed = prsmt.executeUpdate();

							if(changed >0)
							{
								ResultSet upRs = dismt.executeQuery();
								if(upRs.next()){
									System.out.println("\nWithdrawn From Account Name: "+accountName);
									System.out.println("\nYou WithDraw : "+ amount);
									System.out.println("\nCurrent Balance: "+upRs.getDouble("balance"));
								}
							}
							else
							System.out.println("\nInsufficient balance or no account found..");
						}
						else
							System.out.println("\nInsufficient balance or no account found..");
					
					} 
					catch (SQLException e) {
						System.out.println("\nError during withdrawal: " + e.getMessage());
					}
					finally
					{
						System.out.println("\nExiting Withdrawal ......\n");
					}
				break;
				case 3:
					System.out.println("\n	Exit From Withdrawal	\n");
				break;
				default:
					System.out.println("\nInvalid Choice!..Try Again!..");
				break;
			}
		}while(choice!=3);
    }
}
