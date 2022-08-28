package repository;

import entity.User;
import util.ApplicationConstant;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserRepository {
    public boolean checkPassword(int id, String password) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usertable WHERE id = ? AND password = ?";
        PreparedStatement preparedStatement = ApplicationConstant.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        if (resultSet.getInt(1) == 1)
            return true;
        return false;
    }

    public boolean changePassword(int id, String password) throws SQLException {
        String sql = "UPDATE usertable SET password = ? WHERE id = ? ";
        PreparedStatement preparedStatement = ApplicationConstant.getConnection().prepareStatement(sql);
        preparedStatement.setInt(2, id);
        preparedStatement.setString(1, password);
        if (preparedStatement.executeUpdate() == 1)
            return true;
        return false;
    }

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
            return new User(resultSet.getInt(1), username, nationalCode, birthday, nationalCode);
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
            return new User(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDate(4),
                    resultSet.getString(5));
        }
        return null;
    }
}
