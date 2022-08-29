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

    enum FirstMenuOption {
        SIGNIN, SIGNUP, ARTICLES, SIGNOUT, EXIT;

    }

    enum SecondMenuOption {
        SEEARTICLE, EDITARTICLE, NEWARTICLE, PASSWORD, FIRSTMENU, EXIT;
    }

    enum EditMenuOption {
        TITLE, CREATEDATE, PUBLISH;
    }

    public static void main(String[] args) throws SQLException {
        System.out.println("\n******* Welcome to Article Viewer *******");
        firstMenu();
    }

    public static void firstMenu() throws SQLException {
        if (user != null)
            System.out.println("\n******* You are Signed In as " + user.getUsername() + " *******" +
                    "");
        else
            System.out.println("*****************************************");
        System.out.println("Press 1 --> Sign In");
        System.out.println("Press 2 --> Sign Up as a new user");
        System.out.println("Press 3 --> See the Published Articles");
        System.out.println("Press 4 --> Sign Out");
        System.out.println("Press 5 --> Exit Program");
        System.out.println("*****************************************");

        int value = Integer.parseInt(scanner.nextLine()) - 1;
        FirstMenuOption nextStep = FirstMenuOption.values()[value];
        switch (nextStep) {
            case SIGNIN:
                if (signIn())
                    secondMenu();
                else
                    firstMenu();
                break;
            case SIGNUP:
                if (signUp())
                    secondMenu();
                else
                    firstMenu();
                break;
            case ARTICLES:
                seePublishedArticles();
                seeArticleByID();
                firstMenu();
                break;
            case SIGNOUT:
                signOut();
                firstMenu();
                break;
            case EXIT:
                System.out.println("Are you sure you want to Exit? yes/no");
                if (scanner.nextLine().equals("no"))
                    firstMenu();
                else
                    System.out.println("Hope to see you soon ;)");
                ApplicationConstant.closeConnection();
                break;
            default:
                ApplicationConstant.closeConnection();
                break;
        }
    }

    public static void secondMenu() throws SQLException {
        System.out.println("\n******* You are Signed In as " + user.getUsername() + " *******");
        System.out.println("Press 1 --> See All your Articles");
        System.out.println("Press 2 --> Edit your Articles");
        System.out.println("Press 3 --> Add a New Article");
        System.out.println("Press 4 --> Change Password");
        System.out.println("Press 5 --> Previous Menu");
        System.out.println("Press 6 --> Exit Program");
        System.out.println("*****************************************");

        int value = Integer.parseInt(scanner.nextLine()) - 1;
        SecondMenuOption nextStep = SecondMenuOption.values()[value];

        switch (nextStep) {
            case SEEARTICLE:
                seeArticleByUserID();
                secondMenu();
                break;
            case EDITARTICLE:
                editArticleByUserID();
                secondMenu();
                break;
            case NEWARTICLE:
                addNewArticle();
                secondMenu();
                break;
            case PASSWORD:
                changePassword();
                secondMenu();
                break;
            case FIRSTMENU:
                firstMenu();
                break;
            case EXIT:
                System.out.println("Are you sure you want to Exit? yes/no");
                if (scanner.nextLine().equals("no"))
                    firstMenu();
                else {
                    System.out.println("Hope to see you soon ;)");
                    ApplicationConstant.closeConnection();
                }
                break;
            default:
                ApplicationConstant.closeConnection();
                break;
        }
    }

    public static void addNewArticle() throws SQLException {
        System.out.println("******** Adding New Article ********");
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
        System.out.println("*****************************************");
    }

    public static void editArticleByUserID() throws SQLException {
        System.out.println("******** Editing Article ********");
        System.out.println("Enter the Article ID to edit ");
        int articleID = Integer.parseInt(scanner.nextLine());
        Article article = articleRepository.seeArticleByID(articleID);
        if (article != null && article.getUserID() == user.getId()) {
            String name = userRepository.findUserByID(article.getUserID());
            System.out.println(article.getId() + " " + article.getTitle());
            System.out.println("  Author: " + name + " Date: " + article.getCreateDate());
            System.out.println("  Summery: " + article.getBrief());
            System.out.println("  Content: " + article.getContent());

            System.out.println("Press 1 --> Edit Title");
            System.out.println("Press 2 --> Edit Create Date");
            System.out.println("Press 3 --> Edit Publish Status");

            int value = Integer.parseInt(scanner.nextLine()) - 1;
            EditMenuOption editVal = EditMenuOption.values()[value];

            switch (editVal) {
                case TITLE -> {
                    System.out.println("Enter the new Title: ");
                    String newTitle = scanner.nextLine();
                    articleRepository.editTitle(article.getId(), newTitle);
                    System.out.println("Title edited successfully");
                }
                case CREATEDATE -> {
                    System.out.println("Enter the Create Date(format 1999-01-31 ");
                    Date newDate = Date.valueOf(scanner.nextLine());
                    articleRepository.editCreateDate(article.getId(), newDate);
                    System.out.println("Create Date edited successfully");
                }
                case PUBLISH -> {
                    System.out.println("press 1 to Publish the article,press 0 to Unpublish the article ");
                    boolean isPublished = Integer.parseInt(scanner.nextLine()) == 1;
                    articleRepository.editPublishStatus(article.getId(), isPublished);
                    System.out.println("Publish status updated successfully");
                }
                default -> System.out.println("nothing edited");
            }
        } else
            System.out.println("Incorrect Article ID");
        System.out.println("*****************************************");
    }

    public static void seeArticleByUserID() throws SQLException {
        System.out.println("******** All of Your Articles ********");
        List<Article> currentUserArticleList = new ArrayList<>();
        currentUserArticleList = articleRepository.articlesByUserID(user.getId());
        if (currentUserArticleList.size() == 0)
            System.out.println("You haven't published any Articles yet");
        for (Article article : currentUserArticleList) {
            System.out.println("-----------------------------------------");
            System.out.println("Article ID " + article.getId());
            System.out.println("  Title: " + article.getTitle());
            System.out.println("  Summery: " + article.getBrief());
            System.out.println("-----------------------------------------");
        }
        System.out.println("*****************************************");
    }

    public static void signOut() {
        user = null;
        System.out.println("******** Sign Out ********");
        System.out.println("Successfully signed out");
        System.out.println("*****************************************");
    }

    public static void seeArticleByID() throws SQLException {
        System.out.println("******** View Article ********");
        System.out.println("Enter Article ID to see the Content: ");
        int ID = Integer.parseInt(scanner.nextLine());
        Article article = articleRepository.seeArticleByID(ID);
        if (article != null && article.isPublished()) {
            String name = userRepository.findUserByID(article.getUserID());
            System.out.println(article.getId() + " " + article.getTitle());
            System.out.println("  Author: " + name + " Date: " + article.getCreateDate());
            System.out.println("  Summery: " + article.getBrief());
            System.out.println("  Content: " + article.getContent());
        } else
            System.out.println("Incorrect Article ID");
        System.out.println("*****************************************");
    }

    public static void seePublishedArticles() throws SQLException {
        System.out.println("******** Published Articles ********");
        List<Article> articleList = new ArrayList<>();
        articleList = articleRepository.allArticles();
        if (articleList.size() == 0)
            System.out.println("There is no Articles yet");
        for (Article article : articleList) {
            if (article.isPublished()) {
                System.out.println("-----------------------------------------------");
                System.out.println("Article ID " + article.getId());
                System.out.println("  Title: " + article.getTitle());
                System.out.println("  Summery: " + article.getBrief());
                System.out.println("-----------------------------------------------");
            }
        }
        System.out.println("*****************************************");
    }

    public static boolean changePassword() throws SQLException {
        System.out.println("******** Change Password ********");
        System.out.println("Enter your current Password...");
        String oldPass = scanner.nextLine();
        if (userRepository.checkPassword(user.getId(), oldPass)) {
            System.out.println("Enter your new Password...");
            String newPass = scanner.nextLine();
            if (userRepository.changePassword(user.getId(), newPass)) {
                user.setPassword(newPass);
                System.out.println("Password changed successfully!");
                System.out.println("*****************************************");
                return true;
            }
            System.out.println("An error occurred, please try later");
            System.out.println("*****************************************");
            return false;
        }
        System.out.println("Wrong Password");
        System.out.println("*****************************************");
        return false;
    }

    public static boolean signUp() throws SQLException {
        System.out.println("************ Sign Up ************");
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
                System.out.println("Dear " + user.getUsername() + " Your Password is your National Code, You can change it in the following Menu");
                System.out.println("*****************************************");
                return true;
            }
            System.out.println("This Username already exists");
            System.out.println("*****************************************");
            return false;
        }
        System.out.println("You are already Signed in, Want to Sign Up as another User? Sign Out first");
        System.out.println("*****************************************");
        return false;
    }

    public static boolean signIn() throws SQLException {
        System.out.println("************ Sign In ************");
        if (user == null) {
            System.out.println("Enter Username: ");
            String username = scanner.next();
            System.out.println("Enter Password: ");
            String password = scanner.next();
            scanner.nextLine();
            user = userRepository.userSignIn(username, password);
            if (user != null) {
                System.out.println("You have been successfully signed in!");
                System.out.println("*****************************************");
                return true;
            }
            System.out.println("........Sign in Failed.......");
            System.out.println("Your Username/Password is incorrect");
            System.out.println("*****************************************");
            return false;
        }
        System.out.println("You are already Signed in, Want to Sign in as another User? Sign Out first");
        System.out.println("*****************************************");
        return false;
    }
}

