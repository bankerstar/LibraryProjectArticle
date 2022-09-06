package maktab82.wh5.entity;

import java.util.Date;

public class Users {
    private int id;
    private String username;
    private String nationalCode;
    private Date birthday;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Users(int id, String username, String nationalCode, Date birthday, String password) {
        this.id = id;
        this.username = username;
        this.nationalCode = nationalCode;
        this.birthday = birthday;
        this.password = password;
    }

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public boolean changePassword(String oldPass, String newPass){
        if(oldPass.equals(password)){
            this.password = newPass;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", nationalCode=" + nationalCode +
                ", birthday=" + birthday +

                '}';
    }
}
