import java.util.*;

public class Main{
	public static void main(String args[])
	{
		Scanner input = new Scanner(System.in);
		ATM atm = new ATM();
		int choice,mobileNumber,PIN;
		String accountHolderName;

		do{
			System.out.println("1.Create a New Account\n2.View Balance\n3.Deposit Amount from Account\n4.WithDraw Amount from Account\n5.Exit");
			choice=input.nextInt();
			switch(choice)
			{
				case 1:
					atm.createAccount(input);
					break;
				case 2:
					atm.viewBalance(input);
					break;
				case 3:
					atm.depositAmount(input);
					break;
				case 4:
					atm.withdrawAmount(input);
					break;
				case 5:
					System.out.println("\nExiting...");
					break;
				default:
					System.out.println("Invalid Choice!..Try Again!..");
			    	break;
			}

		}while(choice<5);
	}
}