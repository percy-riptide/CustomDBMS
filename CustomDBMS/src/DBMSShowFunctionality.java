import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class DBMSShowFunctionality {
    private static final String FILE_NAME = "user_credentials.percysecure";
    //display all the registered users
    void showUsers(){
        try {
            // Read user credentials from file and compare with input
            FileReader reader = new FileReader(new File(FILE_NAME));
            String[] credentials = null;
            BufferedReader br = new BufferedReader(reader);
            String line;
            System.out.println("Registered Users");
            while ((line = br.readLine()) != null) {
                credentials = line.split(":");
                System.out.println(credentials[0]);
            }
            reader.close();
        } 
        catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    //display all the schemas for the current user
    String[] showSchema(String username){
        File folder = new File("./"+username);
        File[] directoryIndex = folder.listFiles(File::isDirectory);
        String[] availableSchemas = new String[directoryIndex.length];
        int iterator = 0;
        for (File directory : directoryIndex) {
                availableSchemas[iterator++] = directory.getName();
            }
        return availableSchemas;
    }
    //show the tables present in the current schema
    void showTables(String username, String schema){
        File directory = new File("./"+username+"/"+schema);
        HashSet<String> fileNames = new HashSet<String>();
        if(directory.isDirectory()) {
            File[] files = directory.listFiles();
            for(File file : files) {
                if(file.isFile()) {
                    String fileName = file.getName();
                    int pos = fileName.lastIndexOf(".");
                    if(pos > 0) {
                        fileName = fileName.substring(0, pos);
                    }
                    fileNames.add(fileName);
                }
            }
        }        
        for(String fileName : fileNames) {
            System.out.println(fileName);
        }
    }
}
