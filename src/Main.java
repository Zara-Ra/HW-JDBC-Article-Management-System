import repository.UserRepository;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        System.out.println("Enter username: ");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.next();
        System.out.println("Enter password: ");
        String password = scanner.next();
        UserRepository userRepository = new UserRepository();
        System.out.println(userRepository.userSignIn(username,password));

    }
}
