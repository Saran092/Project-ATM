import java.sql.*;
import java.util.*;

import javax.naming.spi.DirStateFactory.Result;


public class StatementView extends Connect{
	public void Statement(Scanner input)
	{
		int choice,PIN;
		String customerID,print,Name,Ddate,Wdate;
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

					String Select = "SELECT Userdetails.Name,Userdetails.CustomerID,Userdetails.Account_number,Userdetails.Balance,Deposit.DepositAmount,Deposit.DepositDate,Withdraw.WithdrawAmount,Withdraw.WithdrawDate "+
									"from Userdetails INNER JOIN Deposit ON Userdetails.id = Deposit.userID INNER JOIN Withdraw ON "+
									"Userdetails.id = Withdraw.userId WHERE Userdetails.CustomerID = ? AND Userdetails.PIN = ?";
					try(Connection conn = connect())
					{
						prsmt = conn.prepareStatement(Select);
						
						prsmt.setString(1, customerID);
						prsmt.setInt(2, PIN);
						
						rs = prsmt.executeQuery();
						print ="UserName		|"+
								"Customer ID		|"+
								"Account Number		|"+
								"Balance 		|"+
								"Deposit Amount		|"+
								"Deposit Date		|"+
								"Withdraw Amount		|"+
								"Withdraw Date		";
						System.out.println(print);
						if(rs.next())
						{
							Name = rs.getString("Name");
							customerID = rs.getString("CustomerID");
							ACnu = rs.getLong("Account_number");
							balance = rs.getDouble("Balance");
							Damount = rs.getDouble("DepositAmount");
							Ddate = rs.getString("DepositDate");
							Wamount = rs.getDouble("WithdrawAmount");
							Wdate = rs.getString("WithdrawDate");
							System.out.prinf("%-10s");
					}
					catch(SQLException e)
					{
						System.out.println("\n Error : "+e.getMessage());
					}
					finally
					{
						System.out.println("Exiting Statement....");
					}
				break;
			
				default:
					System.out.println("\nInvalid Choice!..Try Again!..");
				break;
			}
		}while(choice<4);
	}
}