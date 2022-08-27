package repository;

import entity.User;
import util.ApplicationConstant;

import java.sql.*;


public class UserRepository {
    public User userSignUp(String username, String nationalCode, Date birthday) throws SQLException {
        String sql = "INSERT INTO usertable (username,nationalcode,birthday,password) VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = ApplicationConstant.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, nationalCode);
        preparedStatement.setDate(3, birthday);
        preparedStatement.setString(4, nationalCode);
        preparedStatement.executeUpdate();

        String sqlForID = "SELECT id FROM usertable WHERE username = ? AND nationalcode = ?";
        PreparedStatement prepared = ApplicationConstant.getConnection().prepareStatement(sqlForID);
        prepared.setString(1, username);
        prepared.setString(2, nationalCode);
        ResultSet resultSet = prepared.executeQuery();
        if (resultSet.next()) {
            User newUser = new User(resultSet.getInt(1), username, nationalCode, birthday, nationalCode);
            return newUser;
        }
        return null;
    }

    public User userSignIn(String username, String password) throws SQLException {

        String sql = "SELECT * From usertable WHERE username = ? AND password = ? ";
        PreparedStatement preparedStatement = ApplicationConstant.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            User newUser = new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDate(4), resultSet.getString(5));
            return newUser;
        }
        return null;
    }
    public void test() throws SQLException {
        String readQuery = "SELECT *  FROM usertable";
        PreparedStatement statement = ApplicationConstant.getConnection().prepareStatement(readQuery);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int person_id = resultSet.getInt(1);
            System.out.println(person_id);
        }

    }
}
