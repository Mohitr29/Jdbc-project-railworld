package Service;

import ConnectionDB.DBUtil;
import UserLogIn.Authentications;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class LoanService {
    public void loans() {
        Scanner sc = new Scanner(System.in);
        int accountNum = 0;
        String name = "";
        int balance = 0;
        int loan_amount = 0;
        Authentications lg = new Authentications();

        String query = "SELECT BALANCE,NAME FROM users WHERE accountNum = ? ";
        String queryy = "INSERT INTO loan_Dept VALUES(?,?,?,?)";

        try (Connection con = DBUtil.getConnection(); PreparedStatement pss = con.prepareStatement(queryy); PreparedStatement ps = con.prepareStatement(query)) {
            System.out.println("enter your account number");

            accountNum = sc.nextInt();
            if (lg.loan_userExist(accountNum)) {
                System.out.println("You already have an loan please try again after some time");
                return;
            }
            ps.setInt(1, accountNum);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                balance = rs.getInt("BALANCE");

                name = rs.getString("NAME");

            }
            if (balance >= 10000) {
                int annualInterestRate=0;
                int duration=2;
                if (balance >= 10000 && balance <= 100000) {
                    annualInterestRate = 14;
                    System.out.println("rate of interest=14");
                } else if (balance >= 100000 && balance <= 1000000) {
                    annualInterestRate = 12;
                    System.out.println("rate of interest=12");
                } else if (balance >= 1000000 && balance <= 10000000) {
                    annualInterestRate = 10;
                    System.out.println("rate of interest=10");
                }

                System.out.println("you are eligible for loan : ");
                loan_amount = balance + (balance / 20);
                pss.setInt(1, accountNum);
                pss.setInt(2, loan_amount);
                pss.setInt(3,annualInterestRate);
                pss.setInt(4,duration);


                int sum = pss.executeUpdate();

                if (sum > 0) {
                    System.out.println("congratulations " + name + "your loan has been approved");
                    System.out.println("your approved loan amount : " + loan_amount);

                    System.out.println("here is your EMI for 2 year loan");
                    System.out.println("enter loan amount");
                    calculate();
                }
            } else {
                System.out.println("sorry " + name + " for inconvenience");
                System.out.println("you're not eligible for loan service ");
                System.out.println("your Account balance is less then eligibility criteria");


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void calculate() {
        Scanner sc = new Scanner(System.in);
        double principal = sc.nextInt(); // $100,000
        // Annual interest rate in percentage
        double annualInterestRate = 0; // 12%
        while (annualInterestRate == 0)
            if (principal >= 10000 && principal <= 100000) {
                annualInterestRate = 14;
                System.out.println("rate of interest=14");
            } else if (principal >= 100000 && principal <= 1000000) {
                annualInterestRate = 12;
                System.out.println("rate of interest=12");
            } else if (principal >= 1000000 && principal <= 10000000) {
                annualInterestRate = 10;
                System.out.println("rate of interest=10");
            }

        // Loan tenure in years
        int loanTenureYears = 2; // 2 years

        // Convert annual interest rate to monthly interest rate
        double monthlyInterestRate = annualInterestRate / 12 / 100;

        // Calculate the number of monthly installments
        int numberOfMonthlyInstallments = loanTenureYears * 12;

        // Calculate EMI using the formula
        double EMI = (principal * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, numberOfMonthlyInstallments)) / (Math.pow(1 + monthlyInterestRate, numberOfMonthlyInstallments) - 1);

        // Print the EMI
        System.out.printf("The Equated Monthly Installment (EMI) for two years  is: %.2f\n", EMI);
    }
}
