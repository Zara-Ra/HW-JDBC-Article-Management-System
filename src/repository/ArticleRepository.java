package repository;

import java.util.Date;

public class ArticleRepository {
    public void seeAllArticles() {
        //return List<String concat title, brief>
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
