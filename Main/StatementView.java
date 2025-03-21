import java.sql.*;
import java.util.*;



public class StatementView extends Connect{
	public void Statement(Scanner input)
	{
		int choice,PIN;
		String customerID,Name,Ddate,Wdate,Select;
		Double Damount,Wamount,balance;
		Long ACnu;
		PreparedStatement prsmt = null;
		ResultSet rs = null;

		do{
			System.out.println("1.Deposit and WithDraw Statement\n2.Deposit Statement\n3.WithDraw Statement\n4.Exit");
			System.out.println("Enter Your Choice: ");
			choice = input.nextInt();
			switch (choice) {
				case 1:
					input.nextLine();
					System.out.println("Enter Your Customer ID: ");
					customerID = input.nextLine();
					System.out.println("Enter Your PIN: ");
					PIN = input.nextInt();

					Select = "SELECT Userdetails.Name,Userdetails.CustomerID,Userdetails.Account_number,Userdetails.Balance,Deposit.DepositAmount,Deposit.DepositDate,Withdraw.WithdrawAmount,Withdraw.WithdrawDate "+
									"from Userdetails LEFT JOIN Deposit ON Userdetails.id = Deposit.userID LEFT JOIN Withdraw ON "+
									"Userdetails.id = Withdraw.userId WHERE Userdetails.CustomerID = ? AND Userdetails.PIN = ? ORDER BY Userdetails.Name";
					try(Connection conn = connect())
					{
						prsmt = conn.prepareStatement(Select);
						
						prsmt.setString(1, customerID);
						prsmt.setInt(2, PIN);
						
						rs = prsmt.executeQuery();
						System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n","UserName","Customer ID","Account Number","Balance","Deposit Amount","Deposit Date","Withdraw Amount","Withdraw Date");
						while(rs.next())
						{
							Name = rs.getString("Name");
							customerID = rs.getString("CustomerID");
							ACnu = rs.getLong("Account_number");
							balance = rs.getDouble("Balance");
							Damount = rs.getDouble("DepositAmount");
							Ddate = rs.getString("DepositDate");
							Wamount = rs.getDouble("WithdrawAmount");
							Wdate = rs.getString("WithdrawDate");
							System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%-20s%-20s\n",Name,customerID,ACnu,balance,Damount,Ddate,Wamount,Wdate);
						}
					}
					catch(SQLException e)
					{
						System.out.println("\n Error : "+e.getMessage());
					}
					finally
					{
						System.out.println("\nExiting Statement....\n");
					}
				break;
				case 2:
					input.nextLine();
					System.out.println("Enter Your Customer ID: ");
					customerID = input.nextLine();
					System.out.println("Enter Your PIN: ");
					PIN = input.nextInt();

					Select = "SELECT Userdetails.Name,Userdetails.CustomerID,Userdetails.Account_number,Userdetails.Balance,Deposit.DepositAmount,Deposit.DepositDate "+
					"from Userdetails LEFT JOIN Deposit ON Userdetails.id = Deposit.userID  WHERE Userdetails.CustomerID = ? AND Userdetails.PIN = ? ORDER BY Userdetails.Name";

					try(Connection conn = connect())
					{
						prsmt = conn.prepareStatement(Select);
						
						prsmt.setString(1, customerID);
						prsmt.setInt(2, PIN);
						
						rs = prsmt.executeQuery();
						System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s\n","UserName","Customer ID","Account Number","Balance","Deposit Amount","Deposit Date");
						while(rs.next())
						{
							Name = rs.getString("Name");
							customerID = rs.getString("CustomerID");
							ACnu = rs.getLong("Account_number");
							balance = rs.getDouble("Balance");
							Damount = rs.getDouble("DepositAmount");
							Ddate = rs.getString("DepositDate");
							System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s\n",Name,customerID,ACnu,balance,Damount,Ddate);
						}
					}
					catch(SQLException e)
					{
						System.out.println("\n Error : "+e.getMessage());
					}
					finally
					{
						System.out.println("\nExiting Statement From Deposit....\n");
					}
				break;
				case 3:
				input.nextLine();
				System.out.println("Enter Your Customer ID: ");
				customerID = input.nextLine();
				System.out.println("Enter Your PIN: ");
				PIN = input.nextInt();

				Select = "SELECT Userdetails.Name,Userdetails.CustomerID,Userdetails.Account_number,Userdetails.Balance,Withdraw.WithdrawAmount,Withdraw.WithdrawDate "+
						"from Userdetails Left JOIN Withdraw ON Userdetails.id = Withdraw.userId WHERE Userdetails.CustomerID = ? AND Userdetails.PIN = ? ORDER BY Userdetails.Name";

				try(Connection conn = connect())
				{
					prsmt = conn.prepareStatement(Select);
					
					prsmt.setString(1, customerID);
					prsmt.setInt(2, PIN);
					
					rs = prsmt.executeQuery();
					System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s\n","UserName","Customer ID","Account Number","Balance","WithDraw Amount","WithDraw Date");
					while(rs.next())
					{
						Name = rs.getString("Name");
						customerID = rs.getString("CustomerID");
						ACnu = rs.getLong("Account_number");
						balance = rs.getDouble("Balance");
						Wamount = rs.getDouble("WithdrawAmount");
						Wdate = rs.getString("WithdrawDate");
						System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s\n",Name,customerID,ACnu,balance,Wamount,Wdate);
					}
				}
				catch(SQLException e)
				{
					System.out.println("\n Error : "+e.getMessage());
				}
				finally
				{
					System.out.println("\nExiting Statement From WithDraw....\n");
				}
				break;
				case 4:
					System.out.println("\nExit From Statement Menu .....\n");
				break;
				default:
					System.out.println("\nInvalid Choice!..\nTry Again!..");
				break;
			}
		}while(choice<4);
	}
}