package entity;


import java.sql.Date;

public class Article {
    private int id;
    private String title;
    private String brief;
    private String content;
    private Date createDate;
    private boolean isPublished;
    private int userID;

    public Article(int id, String title, String brief, String content, Date createDate, boolean isPublished, int userID) {
        this.id = id;
        this.title = title;
        this.brief = brief;
        this.content = content;
        this.createDate = createDate;
        this.isPublished = isPublished;
        this.userID = userID;
    }

    public Article(String title, String brief, String content, Date createDate, boolean isPublished, int userID) {
        this.title = title;
        this.brief = brief;
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

    public String getBrief() {
        return brief;
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

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBrief(String brief) {
        this.brief = brief;
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

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", brief='" + brief + '\'' +
                ", content='" + content + '\'' +
                ", createDate=" + createDate +
                ", isPublished=" + isPublished +
                ", userID=" + userID +
                '}';
    }

}
