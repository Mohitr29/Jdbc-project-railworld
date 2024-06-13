package UserLogIn;

import ConnectionDB.DBUtil;
import DAO.OpenAccDao;

import java.sql.*;
import java.util.Scanner;

public class Authentications {

    public void logIn() throws SQLException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your username");
        String uid = sc.next();
        System.out.println("Enter password");
        int pass = sc.nextInt();
        String query = "select password,accountNum,id from users where id=?";
        try (
                Connection con = DBUtil.getConnection();
                PreparedStatement ps = con.prepareStatement(query);
        ) {
            ps.setString(1, uid);
            ResultSet rs = ps.executeQuery();

            if (user_exist(uid)) {
                while (rs.next()) {
                    int pass2 = rs.getInt("password");
                    int accNum = rs.getInt("accountNum");
                    String id = rs.getString("id");
                    if (id.equals(uid) && (pass2 == pass)) {
                        System.out.println();
                        System.out.println("--------------------------------------------------------");
                        System.out.println("LogIn SuccessFull for AccountNumber :" + accNum);
                        new OpenAccDao().accountOpration();
                    } else {
                        System.out.println("Incorrect Password");
                        return;
                    }
                }
            }
        else {
                System.out.println();
                System.out.println("User Not Found invalid Id");
                System.out.println("Please Create Account For this "+uid+" username");
            }
        }
    }

    public boolean user_exist(String email) throws SQLException {
        String query="select * from users where id=?";
        try(Connection con=DBUtil.getConnection();
            PreparedStatement ps=con.prepareStatement(query);){
            ps.setString(1,email);
            ResultSet rs=ps.executeQuery();
            if (rs.next()){
                return true;
            }
            else {
                return false;
            }
            }
    }

    public boolean credit_userExist(long accountNum) throws SQLException {
        String query="select * from credit_card where accNum=?";
        try(Connection con=DBUtil.getConnection();
            PreparedStatement ps=con.prepareStatement(query);){
            ps.setLong(1,accountNum);
            ResultSet rs=ps.executeQuery();
            if (rs.next()){
                return true;
            }
            else {
                return false;
            }
        }
    }

    public boolean loan_userExist(long accountNum) throws SQLException {
        String query="select * from loan_dept where accountNum=?";
        try(Connection con=DBUtil.getConnection();
            PreparedStatement ps=con.prepareStatement(query);){
            ps.setLong(1,accountNum);
            ResultSet rs=ps.executeQuery();
            if (rs.next()){
                return true;
            }
            else {
                return false;
            }
        }
    }

    public boolean fd_userExist(long accountNum) throws SQLException {
        String query="select * from fixedDeposit where accountNum=?";
        try(Connection con=DBUtil.getConnection();
            PreparedStatement ps=con.prepareStatement(query);){
            ps.setLong(1,accountNum);
            ResultSet rs=ps.executeQuery();
            if (rs.next()){
                return true;
            }
            else {
                return false;
            }
        }
    }

    public boolean locker_userExist(long accountNum) throws SQLException {
        String query="select * from locker where accountNum=?";
        try(Connection con=DBUtil.getConnection();
            PreparedStatement ps=con.prepareStatement(query);){
            ps.setLong(1,accountNum);
            ResultSet rs=ps.executeQuery();
            if (rs.next()){
                return true;
            }
            else {
                return false;
            }
        }
    }




}
