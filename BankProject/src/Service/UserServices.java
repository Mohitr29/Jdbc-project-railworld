package Service;

import ConnectionDB.DBUtil;

import java.sql.*;
import java.util.Scanner;

public class UserServices {

    public void balanceCheck() {
        Scanner sc = new Scanner(System.in);
        int bal = 0;
        String query = "SELECT u.name, u.balance, u.accountNum, c.cardLimit, c.card_num, l.lockerNum, f.amount, f.intrestRate, f.duration, ld.loanAmount \n" +
                "FROM users u\n" +
                "LEFT JOIN loan_dept ld ON u.accountNum=ld.accountNum\n"+
                "LEFT JOIN credit_card c ON u.accountNum = c.accNum\n" +
                "LEFT JOIN locker l ON u.accountNum = l.accountNum\n" +
                "LEFT JOIN fixeddeposit f ON u.accountNum = f.accountNum\n" +
                "WHERE u.accountNum =?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement st = con.prepareStatement(query);
        ) {
            System.out.println("Enter your Account number For Balance inquiry");
            long ac = sc.nextLong();
            st.setLong(1, ac);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                System.out.println("Account Holder Name :"+rs.getString("name"));
                System.out.println("Account Number :"+rs.getInt("accountNum"));
                System.out.println("Account Balance :"+rs.getInt("balance"));
                System.out.println("Credit card Number :"+rs.getInt("card_num"));
                System.out.println("Credit card Limit :"+rs.getInt("cardLimit"));
                System.out.println("FD Amount :"+rs.getInt("amount"));
                System.out.println("FD Duration in Years :"+rs.getInt("duration"));
                System.out.println("Loan On Your Account :"+rs.getInt("loanAmount"));
                System.out.println("Locker Allotted On your Account :"+rs.getInt("lockerNum"));
                System.out.println();
                System.out.println();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void depositDao() {
        Scanner sc = new Scanner(System.in);
        int deposit = 0;
        try (Connection con = DBUtil.getConnection();
             Statement st = con.createStatement()) {

            System.out.println("Enter your Account number For Deposit:");
            long accnum = sc.nextLong();
            System.out.println("how much amount you want to deposit");
            long depositamount = sc.nextLong();
            String query = " UPDATE USERS SET BALANCE = BALANCE +" + depositamount + "  where accountNum = " + accnum;
            int rs = st.executeUpdate(query);

            if (rs != 0) {
                System.out.println("amount deposit");
                balanceCheck();
            } else {
                System.out.println("unable to deposit amount enternal error occer ");
            }
        } catch (SQLException e) {
            System.err.println("error");
            e.printStackTrace();
        }
    }


    public void widthdrawal() {
        Scanner sc = new Scanner(System.in);
        int balance1 = 0;
        String bal = "select balance from users where accountNum=?";
        try (Connection con = DBUtil.getConnection();
             Statement st = con.createStatement();
             PreparedStatement ps = con.prepareStatement(bal);
        ) {
            System.out.println("Enter your Account number For Withdrawal:");
            long accnum = sc.nextLong();
            System.out.println("how much amount you want to withdrawal ");
            long withdrawalAmount = sc.nextLong();
            ps.setLong(1, accnum);
            ResultSet rs1 = ps.executeQuery();
            while (rs1.next()) {
                balance1 = rs1.getInt("balance");
            }
            if (balance1 > withdrawalAmount) {
                String query = " UPDATE USERs SET BALANCE = BALANCE -" + withdrawalAmount + "  where accountNum = " + accnum;
                int rs = st.executeUpdate(query);
                if (rs != 0) {
                    System.out.println("amount withdrawal successfully");
                    balanceCheck();
                } else {
                    System.out.println("unable to withdrawal amount internal error occur ");
                }
            } else {
                System.out.println("Insufficient Balance");
            }
        } catch (SQLException e) {
            System.err.println("error");
            e.printStackTrace();
        }
    }

}

