package entity;


import java.sql.Date;

public class User {
    private int id;
    private String username;
    private String nationalCode;
    private Date birthday;
    private String password;

    public User(int id, String username, String nationalCode, Date birthday, String password) {
        this.id = id;
        this.username = username;
        this.nationalCode = nationalCode;
        this.birthday = birthday;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
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
                ", nationalCode='" + nationalCode + '\'' +
                ", birthday=" + birthday +
                '}'+"\n";
    }
}
