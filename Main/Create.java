import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.*;

public class Create extends Connect{

    private int PIN,custId;
	private Long mobileNumber;
	private String accountHolderName,CustomerID,Date;
	private Character ch;
	private Double balance=0.00;
	Random random = new Random();
	DateTimeFormatter formate = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void createAccount(Scanner input)
	{
		PreparedStatement prsmt = null;
		input.nextLine();
		System.out.println("Enter Your Name(Full Name):");
		accountHolderName=input.nextLine();
		System.out.println("Enter Your Mobile Number:");
		mobileNumber= input.nextLong();
		if(String.valueOf(mobileNumber).length() != 10){
			System.out.println("\nMake Check Your Mobile Number\n Length of your Mobile Number is "+String.valueOf(mobileNumber).length());
			return ;
		}
		custId = random.nextInt(900000);
		CustomerID = "CID"+ custId;
		System.out.println("\nYour CustomerID (Make Sure Remeber Your CustomerID): " + CustomerID);
		long accountNumber = 100000000000L + (long)(random.nextDouble() * 900000000000L);
		System.out.println("\nYour Account Number(Make Sure Remeber Your Account Number): " + accountNumber);
		System.out.println("\nEnter Your PIN Number(Make Sure You Remeber That):");
		PIN = input.nextInt();
		System.out.println("You Want to Pay Initial Amount Rs : 1000\n Enter Y/N :");
		ch =input.next().charAt(0);
		if(ch == 'Y' || ch == 'y')
			balance =1000.0;
		LocalDate CurrentDate = LocalDate.now();
		Date = CurrentDate.format(formate);
		String Insert = "INSERT INTO userdetails (Name,CustomerID,account_number,mobile_number,balance,PIN,CreationDate) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = connect()) 
		{
			prsmt = conn.prepareStatement(Insert);
			prsmt.setString(1, accountHolderName);
			prsmt.setString(2, CustomerID);
			prsmt.setLong(3,accountNumber);
			prsmt.setLong(4,mobileNumber);
			prsmt.setDouble(5,balance);
			prsmt.setInt(6,PIN);
			prsmt.setString(7, Date);
			prsmt.executeUpdate();
			System.out.println("\nAccount created successfully!\n");
		}
		catch (SQLException e) 
		{
			System.out.println("\nError creating account: " + e.getMessage());
		}
		finally
		{
			System.out.println("\nExiting Creating Account......\n");
		}
	}
}
