import java.util.*;

public class Main{
	public static void main(String args[])
	{
		Scanner input = new Scanner(System.in);

		//                    👇 Objects             
		ATM atm = new ATM();
		Create obj = new Create();
		View obj1 = new View();
		Deposit obj2 = new Deposit();
		WithDraw obj3 = new WithDraw();
		int choice;//mobileNumber,PIN;
		// String accountHolderName;

		do{
			System.out.println("1.Create a New Account\n2.View Balance\n3.Deposit Amount from Account\n4.WithDraw Amount from Account\n 5.View Your Satatement\n6.Exit");
			System.out.print("Enter Your Choice: ");
			choice=input.nextInt();
			switch(choice)
			{
				case 1:
					obj.createAccount(input);
				break;
				case 2:
					obj1.viewBalance(input);
				break;
				case 3:
					obj2.depositAmount(input);
				break;
				case 4:
					obj3.withdrawAmount(input);
				break;
				case 5:
					atm.Statement(input);
				break;
				case 6:
					System.out.println("\nThank You Form Visiting Us......");
				break;
				default:
					System.out.println("Invalid Choice!..Try Again!..");
			    break;
			}

		}while(choice<6);
	}
}