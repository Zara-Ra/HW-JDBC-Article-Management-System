import entity.Article;
import entity.User;
import repository.ArticleRepository;
import repository.UserRepository;
import util.ApplicationConstant;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = ApplicationConstant.getScanner();
    private static UserRepository userRepository = new UserRepository();
    private static ArticleRepository articleRepository = new ArticleRepository();
    private static User user;
    private static List<Article> articleList = new ArrayList<>();
    private static List<Article> currentUserArticleList = new ArrayList<>();

    public static void main(String[] args) throws SQLException {
        signIn();
        //signUp();
        //changePassword();
        /*seePublishedArticles();
        System.out.println("Enter Article ID to see the Content: ");
        seeArticleByID();
        */
        //exit();
        seeArticleByUserID();
        editArticleByUserID();
    }

    public static void editArticleByUserID() throws SQLException {
        System.out.println("Enter the Article ID to edit ");
        int articleID = scanner.nextInt();
        Article article = articleRepository.seeArticleByID(articleID);
        if (article != null && article.getUserID() == user.getId()) {
            System.out.println(article.getId() + " " + article.getTitle());
            System.out.println("  Author: " + article.getUserID() + " Date: " + article.getCreateDate());
            System.out.println("  Summery: " + article.getBreif());
            System.out.println("  Content: " + article.getContent());

            System.out.println("Edit Title press '1' ");
            System.out.println("Edit Create Date press '2' ");
            System.out.println("Edit Publish status press '3' ");

            int editVal = scanner.nextInt();
            switch (editVal){
                case 1:
                    System.out.println("Enter the new Title:");
                    scanner.nextLine();
                    String newTitle = scanner.nextLine();
                    articleRepository.editTitle(article.getId(),newTitle);
                    break;
                case 2:
                    System.out.println("Enter the Create Date(format 1999-01-31 ");
                    Date newDate = Date.valueOf(scanner.next());
                    articleRepository.editCreateDate(article.getId(),newDate);
                    break;
                case 3:
                    System.out.println("press 1 to Publish the article,press 0 to Unpublish the article ");
                    boolean ispublished = false;
                    if(scanner.nextInt() == 1)
                        ispublished = true;
                    articleRepository.editPublishStatus(article.getId(),ispublished);
                    break;
                default:
                    System.out.println("nothing edited");
            }
        }
        else
            System.out.println("Incorrect Article ID");
    }
    public static void seeArticleByUserID() throws SQLException {
        articleRepository.articlesByUserID(user.getId(), currentUserArticleList);
        if (currentUserArticleList.size() == 0)
            System.out.println("You haven't published an Article yet");
        for (int i = 0; i < currentUserArticleList.size(); i++) {
            Article article = currentUserArticleList.get(i);
            System.out.println("Article ID " + article.getId());
            System.out.println("  Title: " + article.getTitle());
            System.out.println("  Summery: " + article.getBreif());
            System.out.println();
        }
    }

    public static void exit() {
        user = null;
        System.out.println("Successfully signed out");
    }

    public static void seeArticleByID() throws SQLException {
        int ID = scanner.nextInt();
        Article article = articleRepository.seeArticleByID(ID);
        if (article != null && article.isPublished() == true) {
            System.out.println(article.getId() + " " + article.getTitle());
            System.out.println("  Author: " + article.getUserID() + " Date: " + article.getCreateDate());
            System.out.println("  Summery: " + article.getBreif());
            System.out.println("  Content: " + article.getContent());
        } else
            System.out.println("Incorrect Article ID");
    }

    public static void seePublishedArticles() throws SQLException {
        articleRepository.allArticles(articleList);
        if (articleList.size() == 0)
            System.out.println("There is no Articles yet");
        for (int i = 0; i < articleList.size(); i++) {
            Article article = articleList.get(i);
            if (article.isPublished()) {
                System.out.println("Article ID " + article.getId());
                System.out.println("  Title: " + article.getTitle());
                System.out.println("  Summery: " + article.getBreif());
                System.out.println();
            }
        }
    }

    public static boolean changePassword() throws SQLException {
        System.out.println("Enter your old Password...");
        String oldPass = scanner.next();
        if (userRepository.checkPassword(user.getId(), oldPass)) {
            System.out.println("Enter your new Password...");
            String newPass = scanner.next();
            if (userRepository.changePassword(user.getId(), newPass)) {
                user.setPassword(newPass);
                System.out.println("Password changed successfully!");
                return true;
            }
            System.out.println("An error occurred, please try later");
            return false;
        }
        System.out.println("Wrong Password");
        return false;
    }

    public static boolean signUp() throws SQLException {
        System.out.println("Enter Username: ");
        String username = scanner.next();
        System.out.println("Enter National Code: ");
        String nationalCode = scanner.next();
        System.out.println("Enter Birthday in this format 2015-03-31: ");
        Date birhtday = Date.valueOf(scanner.next());
        user = userRepository.userSignUp(username, nationalCode, birhtday);
        if (user != null) {
            System.out.println("Sign up complete");
            System.out.println("Dear " + user.getUsername() + " Your Password is your National Code, You can change it in the first menu");
            return true;
        }
        System.out.println("This Username already exists");
        return false;
    }

    public static boolean signIn() throws SQLException {
        System.out.println("Enter Username: ");
        String username = scanner.next();
        System.out.println("Enter Password: ");
        String password = scanner.next();
        user = userRepository.userSignIn(username, password);
        if (user != null) {
            System.out.println("You have been successfully signed in!");
            return true;
        }
        System.out.println("...Sign in Failed...");
        System.out.println("Your Username or Password is incorrect");
        return false;

            /*System.out.println("Press 1 to Try Again");
            System.out.println("Press 2 to Sign up for a new account");
            System.out.println("Press q to Exit");
            */
    }
}

