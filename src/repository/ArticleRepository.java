package repository;


import entity.Article;
import util.ApplicationConstant;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ArticleRepository {
    public void allArticles(List<Article> articleList) throws SQLException {
        //return List<String concat title, brief>
        String sql = "SELECT * From article";
        PreparedStatement preparedStatement = ApplicationConstant.getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            Article article = new Article(resultSet.getInt(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getDate(5),
                    resultSet.getBoolean(6),
                    resultSet.getInt(7));
            articleList.add(article);
        }
    }

    public void seeArticlesByID() {
        //return Article
    }

    public void seeMyBriefArticles(int userID, int articleID) {
        //seeBriefArticlesByUserID
        //return List<String concat title, brief>
    }

    public void editMyArticle() {
        //editArticleByUserID
        //edit info
        //publish or unpublish
    }

    public void addNewArticle(String title, String brief, String content, Date date, int userID) {
        //return boolean
    }

    public void publishMyArticle() {
    }
}
