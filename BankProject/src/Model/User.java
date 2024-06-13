package Model;

import java.util.Random;

public class User {
    private String name;
    private double deposit;
    private String emailId;
    private int password;
    private long accNum;
    private String ifsc="SBIN0030120";

    public User(String name, double deposit, String emailId, int password) {
        this.name = name;
        this.deposit = deposit;
        this.emailId = emailId;
        this.password = password;
    }

    public void setAccNum(){
        int accNum=new Random().nextInt(100000000,1000000000);
        this.accNum=accNum;

    }
    public long getAccNum(){
        return accNum;
    }
    public String getIfsc(){
        return ifsc;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "CreateAccount{" +
                "name='" + name + '\'' +
                ", deposit=" + deposit +
                ", id='" + emailId + '\'' +
                ", password=" + password +
                '}';
    }
}
