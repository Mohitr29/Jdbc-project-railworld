package DAO;

import ConnectionDB.DBUtil;
import Model.User;
import Service.*;
import UserLogIn.Authentications;

import java.sql.*;
import java.util.Scanner;


public class OpenAccDao {
    Scanner scanner=new Scanner(System.in);
    Authentications lg=new Authentications();
    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO users(name, balance, id, password, accountNum, ifsc)" +
                " VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getName());
            stmt.setDouble(2, user.getDeposit());
            stmt.setString(3, user.getEmailId());
            stmt.setInt(4, user.getPassword());
            stmt.setDouble(5, user.getAccNum());
            stmt.setString(6, user.getIfsc());
            stmt.executeUpdate();
        }
    }

    public void createAcc() throws SQLException {
        System.out.print("Enter Full name: ");
        String name = scanner.nextLine();
        System.out.print("Enter deposit: ");
        double deposit = scanner.nextDouble();
        System.out.print("Enter your user id : ");
        String email=scanner.next();
        if (lg.user_exist(email)){
            System.out.println("user Already exist please logIn ");
            return;
        }
        System.out.println("Enter Password in Number: ");
        int password=scanner.nextInt();
        User us=new User();
        us.setName(name);
        us.setDeposit(deposit);
        us.setEmailId(email);
        us.setPassword(password);
        us.setAccNum();
        new OpenAccDao().addUser(us);
        System.out.println("User added successfully!");
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("Your name :"+us.getName());
        System.out.println("Your Account Number :"+us.getAccNum());
        System.out.println("Your Account Balance :"+us.getDeposit());
        System.out.println();
        accountOpration();
    }

    public void accountOpration(){

        System.out.println("Enter what you want to do");

        System.out.println("1: Deposit");
        System.out.println("2: Withdrawal");
        System.out.println("3: Balance Check");
        System.out.println("4: Get Credit Card");
        System.out.println("5: Create FD");
        System.out.println("6: Get Loan ");
        System.out.println("7: Get Locker");
        System.out.println("8: Logout");
        System.out.println("-------------------------------------------------------");
        System.out.println();
        System.out.println("Enter your Choice");
        int choice= scanner.nextInt();

        switch (choice){
            case 1:
                new UserServices().depositDao();
                accountOpration();
                break;
            case 2:
                new UserServices().widthdrawal();
                accountOpration();
                break;
            case 3:
                new UserServices().balanceCheck();
                accountOpration();
                break;
            case 4:
                new CreditCardService().credit_card();
                accountOpration();
                break;
            case 5:
                new FixdepositService().fix_deposit();
                accountOpration();
                break;

            case 6:
                new LoanService().loans();
                accountOpration();
                break;
            case 7:
                new LockerService().locker();
                accountOpration();
                break;
            case 8:
                System.out.println("Logging out success.....");
                System.out.println("Thanks For Connecting With Us :)");
                System.exit(0);
            default:
                System.out.println("Enter right choice--");
        }
    }
}
