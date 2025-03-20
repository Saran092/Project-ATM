import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {

    Connect(){}

    protected Connection connect() throws SQLException 
	{
		String Url = "jdbc:mysql://localhost:3306/atm";
        String User = "root";
        String Password = "Saran6112@";
        return DriverManager.getConnection(Url,User,Password);
	}
}
