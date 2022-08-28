package repository;


import entity.Article;
import util.ApplicationConstant;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticleRepository {
    public List<Article> allArticles() throws SQLException {
        String sql = "SELECT * From article";
        PreparedStatement preparedStatement = ApplicationConstant.getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List <Article> articleList = new ArrayList<>();
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

    public Article seeArticleByID(int ID) throws SQLException {
        String sql = "SELECT * From article WHERE id = ?";
        PreparedStatement preparedStatement = ApplicationConstant.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, ID);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Article article = new Article(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDate(5),
                    resultSet.getBoolean(6),
                    resultSet.getInt(7));
            return article;
        }
        return null;
    }

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

    public void editTitle(int articleID, String newTitle) throws SQLException {
        String sql = "UPDATE article SET title = ? WHERE id = ? ";
        PreparedStatement preparedStatement = ApplicationConstant.getConnection().prepareStatement(sql);
        preparedStatement.setInt(2, articleID);
        preparedStatement.setString(1, newTitle);
        preparedStatement.executeUpdate();
    }

    public void editCreateDate(int articleID, Date newDate) throws SQLException {
        String sql = "UPDATE article SET createdate = ? WHERE id = ? ";
        PreparedStatement preparedStatement = ApplicationConstant.getConnection().prepareStatement(sql);
        preparedStatement.setInt(2, articleID);
        preparedStatement.setDate(1, newDate);
        preparedStatement.executeUpdate();
    }

    public void editPublishStatus(int articleID,boolean isPublish) throws SQLException {
        String sql = "UPDATE article SET ispublished = ? WHERE id = ? ";
        PreparedStatement preparedStatement = ApplicationConstant.getConnection().prepareStatement(sql);
        preparedStatement.setInt(2, articleID);
        preparedStatement.setBoolean(1, isPublish);
        preparedStatement.executeUpdate();
    }

    public int addNewArticle(Article article) throws SQLException {
        String sql = "INSERT INTO article (title, brief, content,createdate,ispublished,userid) VALUES (?,?,?,?,?,?)";
        PreparedStatement preparedStatement = ApplicationConstant.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, article.getTitle());
        preparedStatement.setString(2, article.getBreif());
        preparedStatement.setString(3, article.getContent());
        preparedStatement.setDate(4, article.getCreateDate());
        preparedStatement.setBoolean(5, article.isPublished());
        preparedStatement.setInt(6, article.getUserID());
        preparedStatement.executeUpdate();

        String sqlForID = "SELECT id FROM article WHERE title = ? AND userid = ?";
        PreparedStatement prepared = ApplicationConstant.getConnection().prepareStatement(sqlForID);
        prepared.setString(1, article.getTitle());
        prepared.setInt(2, article.getUserID());
        ResultSet resultSet = prepared.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }
}
