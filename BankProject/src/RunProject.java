import DAO.OpenAccDao;
import UserLogIn.Authentications;

import java.sql.SQLException;
import java.util.Scanner;

public class RunProject {

     Scanner scanner;
    OpenAccDao op=new OpenAccDao();
    Authentications lg=new Authentications();
    public RunProject() {
        scanner = new Scanner(System.in);

    }
    public static void main(String[] args) throws SQLException {
//        System.out.println("hello");
        RunProject runProject =new RunProject();
        runProject.run();
    }


    public void run() throws SQLException {

        showMenu();

        String userInput = scanner.nextLine();
        if (userInput == null || userInput == "") {
            System.out.println("Please Input Valid Number");
            run();
        } else {
            int choice = Integer.parseInt(userInput);

            switch (choice) {
                case 1:
                    op.createAcc();
                    break;
                case 2:
                    lg.logIn();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.out.println("Thankyou for reach out our Banking System ");

                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }


    private void showMenu() {
        System.out.println("\nBank Management System");
        System.out.println("1. Create Account");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }
}
