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

    public static void main(String[] args) throws SQLException {
        System.out.println("\nWelcome to Article Viewer");
        firstMenu();
    }

    public static void firstMenu() throws SQLException {
        if (user != null)
            System.out.println("\nYou are Signed In as " + user.getUsername());
        System.out.println("Sign in press 1");
        System.out.println("Sign up press 2");
        System.out.println("See the Published Articles press 3");
        System.out.println("Sign out press 4");
        System.out.println("Exit Program press 5");

        int nextStep = Integer.parseInt(scanner.nextLine());
        switch (nextStep) {
            case 1:
                if (signIn())
                    secondMenu();
                else
                    firstMenu();
                break;
            case 2:
                if (signUp())
                    secondMenu();
                else
                    firstMenu();
                break;
            case 3:
                seePublishedArticles();
                seeArticleByID();
                firstMenu();
                break;
            case 4:
                signOut();
                firstMenu();
                break;
            default:
                break;
        }
    }

    public static void secondMenu() throws SQLException {
        System.out.println("\nYou are Signed In as " + user.getUsername());
        System.out.println("See all your Articles press 1");
        System.out.println("Edit your Articles press 2");
        System.out.println("Add a New Article press 3");
        System.out.println("Change Password press 4");
        System.out.println("Previous Menu press 5");
        System.out.println("Exit Program press 6");

        int nextStep = Integer.parseInt(scanner.nextLine());
        switch (nextStep) {
            case 1:
                seeArticleByUserID();
                secondMenu();
                break;
            case 2:
                editArticleByUserID();
                secondMenu();
                break;
            case 3:
                addNewArticle();
                secondMenu();
                break;
            case 4:
                changePassword();
                secondMenu();
                break;
            case 5:
                firstMenu();
                break;
            case 6:
                break;
        }
    }

    public static void addNewArticle() throws SQLException {
        System.out.print("Enter Article Title: ");
        String title = scanner.nextLine();

        System.out.print("Enter Brief: ");
        String brief = scanner.nextLine();

        System.out.print("Enter Content:(Enter Quit when done) ");
        String QuitChecker = scanner.nextLine();
        String content = "";
        while (!QuitChecker.equals("Quit")) {
            content = content.concat(QuitChecker);
            QuitChecker = scanner.nextLine();
        }

        System.out.print("Enter Create Date(Format 2011-02-31) ");
        Date createDate = Date.valueOf(scanner.next());
        scanner.nextLine();

        System.out.println("Would you like to Publish your Article yes/no?");
        String answerYesNo = scanner.nextLine();
        boolean isPublished = answerYesNo.equals("yes");

        Article article = new Article(title, brief, content, createDate, isPublished, user.getId());
        int articleID = articleRepository.addNewArticle(article);
        article.setId(articleID);
        System.out.println("Article ID " + article.getId() + " Successfully added to the data base");
    }

    public static void editArticleByUserID() throws SQLException {
        System.out.println("Enter the Article ID to edit ");
        int articleID = Integer.parseInt(scanner.nextLine());
        Article article = articleRepository.seeArticleByID(articleID);
        if (article != null && article.getUserID() == user.getId()) {
            System.out.println(article.getId() + " " + article.getTitle());
            System.out.println("  Author: " + article.getUserID() + " Date: " + article.getCreateDate());
            System.out.println("  Summery: " + article.getBrief());
            System.out.println("  Content: " + article.getContent());

            System.out.println("Edit Title press '1' ");
            System.out.println("Edit Create Date press '2' ");
            System.out.println("Edit Publish status press '3' ");

            int editVal = Integer.parseInt(scanner.nextLine());
            switch (editVal) {
                case 1 -> {
                    System.out.println("Enter the new Title:");
                    String newTitle = scanner.nextLine();
                    articleRepository.editTitle(article.getId(), newTitle);
                    System.out.println("Title edited successfully");
                }
                case 2 -> {
                    System.out.println("Enter the Create Date(format 1999-01-31 ");
                    Date newDate = Date.valueOf(scanner.nextLine());
                    articleRepository.editCreateDate(article.getId(), newDate);
                    System.out.println("Create Date edited successfully");
                }
                case 3 -> {
                    System.out.println("press 1 to Publish the article,press 0 to Unpublish the article ");
                    boolean isPublished = Integer.parseInt(scanner.nextLine()) == 1;
                    articleRepository.editPublishStatus(article.getId(), isPublished);
                    System.out.println("Publish status updated successfully");
                }
                default -> System.out.println("nothing edited");
            }
        } else
            System.out.println("Incorrect Article ID");
    }

    public static void seeArticleByUserID() throws SQLException {
        List<Article> currentUserArticleList = new ArrayList<>();
        currentUserArticleList = articleRepository.articlesByUserID(user.getId());
        if (currentUserArticleList.size() == 0)
            System.out.println("You haven't published an Article yet");
        for (Article article : currentUserArticleList) {
            System.out.println("Article ID " + article.getId());
            System.out.println("  Title: " + article.getTitle());
            System.out.println("  Summery: " + article.getBrief());
            System.out.println();
        }
    }

    public static void signOut() {
        user = null;
        System.out.println("Successfully signed out");
    }

    public static void seeArticleByID() throws SQLException {
        System.out.println("Enter Article ID to see the Content: ");
        int ID = Integer.parseInt(scanner.nextLine());
        Article article = articleRepository.seeArticleByID(ID);
        if (article != null && article.isPublished()) {
            System.out.println(article.getId() + " " + article.getTitle());
            System.out.println("  Author: " + article.getUserID() + " Date: " + article.getCreateDate());
            System.out.println("  Summery: " + article.getBrief());
            System.out.println("  Content: " + article.getContent());
        } else
            System.out.println("Incorrect Article ID");
    }

    public static void seePublishedArticles() throws SQLException {
        List<Article> articleList = new ArrayList<>();
        articleList = articleRepository.allArticles();
        if (articleList.size() == 0)
            System.out.println("There is no Articles yet");
        for (Article article : articleList) {
            if (article.isPublished()) {
                System.out.println("Article ID " + article.getId());
                System.out.println("  Title: " + article.getTitle());
                System.out.println("  Summery: " + article.getBrief());
                System.out.println();
            }
        }
    }

    public static boolean changePassword() throws SQLException {
        System.out.println("Enter your current Password...");
        String oldPass = scanner.nextLine();
        if (userRepository.checkPassword(user.getId(), oldPass)) {
            System.out.println("Enter your new Password...");
            String newPass = scanner.nextLine();
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
        if (user == null) {
            System.out.println("Enter Username: ");
            String username = scanner.next();
            System.out.println("Enter National Code: ");
            String nationalCode = scanner.next();
            System.out.println("Enter Birthday in this format 2015-03-31: ");
            Date birthday = Date.valueOf(scanner.next());
            scanner.nextLine();
            user = userRepository.userSignUp(username, nationalCode, birthday);
            if (user != null) {
                System.out.println("Sign up complete");
                System.out.println("Dear " + user.getUsername() + " Your Password is your National Code, You can change it after you Sign in again");
                return true;
            }
            System.out.println("This Username already exists");
            return false;
        }
        System.out.println("You are already Signed in, Want to Sign Up as another User? Sign Out first");
        return false;
    }

    public static boolean signIn() throws SQLException {
        if (user == null) {
            System.out.println("Enter Username: ");
            String username = scanner.next();
            System.out.println("Enter Password: ");
            String password = scanner.next();
            scanner.nextLine();
            user = userRepository.userSignIn(username, password);
            if (user != null) {
                System.out.println("You have been successfully signed in!");
                return true;
            }
            System.out.println("...Sign in Failed...");
            System.out.println("Your Username/Password is incorrect");
            return false;
        }
        System.out.println("You are already Signed in, Want to Sign in as another User? Sign Out first");
        return false;
    }
}

