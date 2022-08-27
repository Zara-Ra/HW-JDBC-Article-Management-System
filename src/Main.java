import entity.User;
import repository.ArticleRepository;
import repository.UserRepository;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static UserRepository userRepository = new UserRepository();
    private static ArticleRepository articleRepository = new ArticleRepository();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException {
        //signIn();
        signUp();
    }

    public static boolean signUp() throws SQLException {
        System.out.println("Enter Username: ");
        String username = scanner.next();
        System.out.println("Enter National Code: ");
        String nationalCode = scanner.next();
        System.out.println("Enter Birthday in this format 2015-03-31: ");
        Date birhtday = Date.valueOf(scanner.next());
        if (userRepository.userSignUp(username, nationalCode, birhtday) != null) {
            System.out.println("Sign up complete");
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
        if (userRepository.userSignIn(username, password) != null) {
            System.out.println("You have been successfully signed in!");
            return true;
        }
        System.out.println("...Sign in Failed...");
        return false;
            /*System.out.println("Your Username or Password is incorrect");
            System.out.println("Press 1 to Try Again");
            System.out.println("Press 2 to Sign up for a new account");
            System.out.println("Press q to Exit");
            */
    }
}

