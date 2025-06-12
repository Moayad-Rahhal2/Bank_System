import DAO.AccountDAO;
import DAO.TransactionDAO;
import DAO.UserDAO;
import Service.AuthService;
import Service.BackupService;
import Service.BankService;
import Util.ConsoleUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws SQLException {
        try {
            // 1. Database Connection
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bank_system",
                    "springstudent",  // Replace with your MySQL username
                    "springstudent"   // Replace with your MySQL password
            );

            // 2. Initialize DAOs
            UserDAO userDao = new UserDAO(connection);
            AccountDAO accountDao = new AccountDAO(connection);
            TransactionDAO transactionDao = new TransactionDAO(connection);

            AuthService authService = new AuthService(userDao);
            BankService bankService = new BankService(accountDao, transactionDao);

            new ConsoleUI(authService, bankService).start();
        }catch(Exception e){
            e.printStackTrace();
    }
    }
}