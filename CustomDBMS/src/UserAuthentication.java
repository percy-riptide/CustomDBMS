import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.io.Console;

public class UserAuthentication {
    private static final String FILE_NAME = "user_credentials.percysecure"; //file to store credentials
    private static final String HASH_ALGORITHM = "MD5"; //hash algorithm
    void registerUser() {
        // Get username and password from user
        Console console = System.console();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username: ");
        String username = scanner.nextLine();
        char[] invisiblePassword = console.readPassword("Enter password: ");
        String password = new String(invisiblePassword);
        boolean userAlreadyRegistered = false;

        try {
            FileReader reader = new FileReader(new File(FILE_NAME));
            BufferedReader br = new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null && !userAlreadyRegistered) {
                String[] credentials = line.split(":");
                if (credentials[0].equals(username)) {
                    userAlreadyRegistered = true;
                }
            }
            // Generate hash of password
            // Save username and hashed password to file
            if(!userAlreadyRegistered){
                MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
                byte[] hashedPassword = md.digest(password.getBytes());
                FileWriter writer = new FileWriter(new File(FILE_NAME), true);
                writer.write(username + ":" + bytesToHex(hashedPassword) + "\n");
                writer.close();
                File folder = new File(username);
                if (!folder.exists()) {
                    folder.mkdir();
                }
            }
            if(userAlreadyRegistered){
                System.out.println("This user is already present, please login");
            }

        } catch (NoSuchAlgorithmException | IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    String[] loginUser() {
        // Get username and password from user
        Console console = System.console();
        Scanner scanner = new Scanner(System.in);
        String loginSuccess = "false";
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        char[] invisiblePassword = console.readPassword("Enter password: ");
        String password = new String(invisiblePassword);

        try {
            // Generate hash of password
            MessageDigest md = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] hashedPassword = md.digest(password.getBytes());

            // Read user credentials from file and compare with input
            FileReader reader = new FileReader(new File(FILE_NAME));
            BufferedReader br = new BufferedReader(reader);
            String line;
            boolean foundUser = false;
            while ((line = br.readLine()) != null) {
                String[] credentials = line.split(":");
                if (credentials[0].equals(username)) {
                    foundUser = true;
                    if (credentials[1].equals(bytesToHex(hashedPassword))) {
                        System.out.println("Login successful!\n");
                        loginSuccess = "true";
                    } else {
                        System.out.println("Incorrect password!");
                        loginSuccess = "false";
                    }
                    break;
                }
            }
            if (!foundUser) {
                System.out.println("User not found!");
            }
            reader.close();

        } catch (NoSuchAlgorithmException | IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        String[] result = {username,loginSuccess};
        return result;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}