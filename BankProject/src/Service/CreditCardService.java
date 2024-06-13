package Service;

import ConnectionDB.DBUtil;
import UserLogIn.Authentications;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

public class CreditCardService {
    public void credit_card() {
        Scanner sc = new Scanner(System.in);
        Authentications lg = new Authentications();
        int accountNum = 0;
        String name = "";
        int balance = 0;
        int limit = 0;
        Long card_num = new Random().nextLong(100000, 1000000);
        String query = " SELECT * FROM USERS WHERE accountNum = ? ";
        String queryy = "INSERT INTO CREDIT_CARD VALUES(?,?,?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement pss = con.prepareStatement(queryy);
             PreparedStatement ps = con.prepareStatement(query)) {
            System.out.println("enter your account number");
            accountNum = sc.nextInt();
            if (lg.credit_userExist(accountNum)) {
                System.out.println("You've an credit card");
                return;
            }
            System.out.println(" ");
            ps.setInt(1, accountNum);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                balance = rs.getInt("BALANCE");
                name = rs.getString("name");
            }
            if (balance >= 5000) {
                System.out.println("yor are eligible for credit card services");
                limit = balance + (balance / 10);

                pss.setInt(1, accountNum);
                pss.setInt(2, limit);
                pss.setLong(3, card_num);
                int count = pss.executeUpdate();
                if (count > 0) {
                    System.out.println("congratulations " + name + " your credit card has been approved");
                    System.out.println("your card limit is : " + limit);
                    System.out.println("your card number is :" + card_num);
                }
            } else {
                System.out.println("sorry " + name + " for inconvenience");
                System.out.println("you're not eligible for Credit card service ");
                System.out.println("your Account balance is less then eligibility criteria");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
