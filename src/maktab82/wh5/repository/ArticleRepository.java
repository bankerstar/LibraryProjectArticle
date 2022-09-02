package maktab82.wh5.repository;

import maktab82.wh5.entity.Article;
import maktab82.wh5.util.ApplicationConstant;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticleRepository {
    public List<Article> articlesByUserID(int userID) throws SQLException {
        String sql = "SELECT * From article WHERE userid = ?";
        PreparedStatement preparedStatement = ApplicationConstant.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, userID);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Article> articleList = new ArrayList<>();
        while (resultSet.next()) {
            Article article = new Article(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDate(5),
                    resultSet.getBoolean(6),
                    resultSet.getInt(7));
            articleList.add(article);
        }
        return articleList;
    }

    public Article findArticleById(int id) throws SQLException {
        String selectSql = "Select * from article where id=?";
        PreparedStatement preparedStatement = ApplicationConstant.getConnection().prepareStatement(selectSql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int articleId = resultSet.getInt(1);
            String title = resultSet.getString(2);
            String brief = resultSet.getString(3);
            String content = resultSet.getString(4);
            Date createDate = resultSet.getDate(5);
            boolean isPublished = resultSet.getBoolean(6);
            int userId = resultSet.getInt(6);
            return new Article(articleId, title, brief, content, createDate, isPublished, userId);
        }
        return null;
    }

    public void editTitle(int articleID, String newTitle) throws SQLException {
        String sql = "UPDATE article SET title = ? WHERE id = ? ";
        PreparedStatement preparedStatement = ApplicationConstant.getConnection().prepareStatement(sql);
        preparedStatement.setInt(2, articleID);
        preparedStatement.setString(1, newTitle);
        preparedStatement.executeUpdate();
    }
//  String insertSql = "INSERT INTO article ( title, brief, content, createDate, isPublished, userId) VALUES (?,?,?,?,?,?)";

    public void editCreateDate(int articleID, Date createDate) throws SQLException {
        String sql = "UPDATE Article SET createDate = ? WHERE id = ? ";
        PreparedStatement preparedStatement = ApplicationConstant.getConnection().prepareStatement(sql);
        preparedStatement.setInt(2, articleID);
        preparedStatement.setDate(1, (java.sql.Date) createDate);
        preparedStatement.executeUpdate();
    }

    public void editPublishStatus(int articleID, boolean isPublish) throws SQLException {
        String sql = "UPDATE article SET isPublished = ? WHERE id = ? ";
        PreparedStatement preparedStatement = ApplicationConstant.getConnection().prepareStatement(sql);
        preparedStatement.setInt(2, articleID);
        preparedStatement.setBoolean(1, isPublish);
        preparedStatement.executeUpdate();
    }

    public List<Article> allArticles() throws SQLException {
        String sql = "SELECT * From article";
        PreparedStatement preparedStatement = ApplicationConstant.getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Article> articleList = new ArrayList<>();
        while (resultSet.next()) {
            Article article = new Article(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDate(5),
                    resultSet.getBoolean(6),
                    resultSet.getInt(7));
            articleList.add(article);
        }
        return articleList;
    }

    public int createNewArticle(Article article) throws SQLException {
        String insertSql = "INSERT INTO article ( title, brief, content, createDate, isPublished, userId) VALUES (?,?,?,?,?,?)";
        PreparedStatement preparedStatement = ApplicationConstant.getConnection().prepareStatement(insertSql);
        preparedStatement.setString(1, article.getTitle());
        preparedStatement.setString(2, article.getBrief());
        preparedStatement.setString(3, article.getContent());
        preparedStatement.setDate(4, (java.sql.Date) article.getCreateDate());
        preparedStatement.setBoolean(5, article.isPublished());
        preparedStatement.setInt(6, article.getUser_id());
        preparedStatement.executeUpdate();

        String sqlForID = "SELECT id FROM Article WHERE title = ? AND userid = ?";
        PreparedStatement prepared = ApplicationConstant.getConnection().prepareStatement(sqlForID);
        prepared.setString(1, article.getTitle());
        prepared.setInt(2, article.getUser_id());
        ResultSet resultSet = prepared.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    public String deleteArticle(int id ,String name) throws SQLException {
        String deleteSql = "DELETE * FROM Article  WHERE id=? or name=?";
        PreparedStatement preparedStatement = ApplicationConstant.getConnection().prepareStatement(deleteSql);
        preparedStatement.setInt(1, id);
        preparedStatement.setString(1, name);
        int indexDelete = preparedStatement.executeUpdate();
        String Output = indexDelete + "deleted recorde ";
        return Output;
    }

}
