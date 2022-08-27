package entity;

import java.util.Date;

public class Article {
    private int id;
    private String title;
    private String breif;
    private String content;
    private Date createDate;
    private boolean isPublished;
    private int userID;

    public Article(int id, String title, String breif, String content, Date createDate, boolean isPublished, int userID) {
        this.id = id;
        this.title = title;
        this.breif = breif;
        this.content = content;
        this.createDate = createDate;
        this.isPublished = isPublished;
        this.userID = userID;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBreif() {
        return breif;
    }

    public String getContent() {
        return content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public int getUserID() {
        return userID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBreif(String breif) {
        this.breif = breif;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }
}
