package Service;

import ConnectionDB.DBUtil;
import UserLogIn.Authentications;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

public class LockerService {
    public void locker() {
        Scanner sc = new Scanner(System.in);
        Authentications lg = new Authentications();
        int accountNum = 0;

        String query = "INSERT INTO  locker VALUES(?,?);";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(query);) {

            System.out.println("enter your account number");
            accountNum = sc.nextInt();

            if (lg.loan_userExist(accountNum)) {
                System.out.println("You already have an Locker");
                return;
            }
            int locer_num=new Random().nextInt(100,1000);
            ps.setInt(1,accountNum);
            ps.setInt(2,locer_num);
            int count= ps.executeUpdate();
            if (count>0){
                System.out.println("Congrats Your Locker has been Booked and Your Locker number ="+locer_num);
            }
            else {
                System.out.println("Sorry for Inconvenience");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
