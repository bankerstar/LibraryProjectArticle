package maktab82.wh5.repository;

import maktab82.wh5.entity.Users;
import maktab82.wh5.util.ApplicationConstant;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    public Users loginByUserPassword(String userName, String password) throws SQLException {
        String selectSql = "SELECT * FROM  users WHERE username=? AND password=?";
        PreparedStatement preparedStatement = ApplicationConstant.getConnection().prepareStatement(selectSql);
        preparedStatement.setString(1, userName);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            userName = resultSet.getString(1);
            password = resultSet.getString(2);

            return new Users(userName, password);
        }
        return null;
    }

    public String findUserByID(int ID) throws SQLException {
        String sql = "SELECT username FROM users WHERE id = ?";
        PreparedStatement preparedStatement = ApplicationConstant.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, ID);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getString(1);
    }

    public Users RegisterUserPassword(String userName, String nationalCode) throws SQLException {
        String insertSql = "INSERT INTO users (username,nationalCode,password) VALUES (?,?,?)";
        PreparedStatement preparedStatement = ApplicationConstant.getConnection().prepareStatement(insertSql);
        preparedStatement.setString(1, userName);
        preparedStatement.setString(2, nationalCode);
        preparedStatement.setString(3, nationalCode);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            userName = resultSet.getString(1);
            nationalCode = resultSet.getString(2);

            return new Users(userName, nationalCode);
        }
        return null;
    }

    public boolean checkedPassword(int id, String password) throws SQLException {
        String checkedSql = "SELECT COUNT(*) FROM users WHERE id = ? AND password = ?";
        PreparedStatement preparedStatement = ApplicationConstant.getConnection().prepareStatement(checkedSql);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        if (resultSet.getInt(1) == 1)
            return true;
        return false;
    }

    public boolean changedPassword(int id, String password) throws SQLException {
        String sql = "UPDATE users SET password = ? WHERE id = ? ";
        PreparedStatement preparedStatement = ApplicationConstant.getConnection().prepareStatement(sql);
        preparedStatement.setInt(2, id);
        preparedStatement.setString(1, password);
        if (preparedStatement.executeUpdate() == 1)
            return true;
        return false;
    }
}
