import java.sql.DriverManager;
import java.sql.SQLException;

public class Sample {
    public static void main(String[] args) throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());
        System.out.println("Hello world");
    }
}
