package Service;

import ConnectionDB.DBUtil;
import UserLogIn.Authentications;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class FixdepositService {
    public void fix_deposit() {
        Scanner sc = new Scanner(System.in);
        Authentications lg = new Authentications();
        int accNum = 0;
        String name = "";
        int balance = 0;
        int fd_amount = 0;
        int time;
        String query = "SELECT BALANCE,NAME FROM users where accountNum=?";
        String queryy = " INSERT INTO  fixeddeposit  (accountNum,amount,duration)VALUES(?,?,?);";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement pss = con.prepareStatement(queryy);
             PreparedStatement ps = con.prepareStatement(query)) {

            System.out.println("enter your account number");
            accNum = sc.nextInt();

            if (lg.fd_userExist(accNum)) {
                System.out.println("You already have an FD");
                return;
            }


            ps.setInt(1, accNum);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                balance = rs.getInt("BALANCE");
                System.out.println(balance);
                name = rs.getString("NAME");
                System.out.println(name);
            }

            System.out.println("how much amount you want to deposit in FD");

            fd_amount = sc.nextInt();
            System.out.println("for how many years");
            time = sc.nextInt();

            pss.setInt(1, accNum);
            pss.setInt(2, fd_amount);
            pss.setInt(3, time);

            int sum = pss.executeUpdate();
            if (sum > 0) {

                System.out.println("your amount " + fd_amount + " is fixed succcessfully for "+time+" years ");
            } else {
                System.out.println("wrong query");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
