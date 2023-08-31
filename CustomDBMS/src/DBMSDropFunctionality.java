import java.io.*;
import java.util.*;

public class DBMSDropFunctionality {
    void dropOperation(String[] querySplit, String username, String schema) throws IOException{
        if(querySplit[1].toLowerCase().equals("table")){
            Scanner scanner = new Scanner(System.in);
            //delete the file that contains the table
            File deletingFile = new File("./"+username+"/"+schema+"/"+querySplit[2]+".percydb");
            System.out.print("Are you sure you want to delete the table "+querySplit[2]+"?\n");
            System.out.print("This is a irreversible operation!\n");
            System.out.print("1. Delete\n2. Cancel\n");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            if(choice == 1){
                if(deletingFile.delete()){
                    System.out.print("\n"+querySplit[2]+" deleted successfully\n");
                    deletingFile = new File("./"+username+"/"+schema+"/"+querySplit[2]+".percymeta");
                    deletingFile.delete();
                }
                else{
                    System.out.println("\nNo Such table Present\n");
                }
            }
            else if(choice == 2){
                System.out.print("\nExecution Cancelled\n");
            }
            else{
                System.out.print("\nPlease choose a valid option!\n");
            }
        }
        //delete the whole folder that contains all the tables
        if(querySplit[1].toLowerCase().equals("schema")){
            Scanner scanner = new Scanner(System.in);
            File deletingFolder = new File("./"+username.trim()+"/"+querySplit[2].trim()+"/");
            System.out.print("Are you sure you want to delete the whole schema "+querySplit[2]+"?\n");
            System.out.print("This will also delete all the tables present in the schema!\n");
            System.out.print("1. Delete\n2. Cancel\n");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            if(choice == 1){
                if(deleteFolder(deletingFolder)){
                    System.out.print("\n"+querySplit[2]+" deleted successfully\n");
                }
                else{
                    System.out.println("\nNo Such Schema Present\n");
                }
            }
            else if(choice == 2){
                System.out.print("\nExecution Cancelled\n");
            }
            else{
                System.out.print("\nPlease choose a valid option!\n");
            }   
        }
    }
    //function to delete all files in folder recursively before deleting the folder
    public static boolean deleteFolder(File folder) {
        // Get all the files and sub-directories inside the folder
        File[] files = folder.listFiles();
        
        // If the folder is empty, delete it
        if (files != null && files.length == 0) {
            return(folder.delete());
        }
        
        // Delete all the files and sub-directories recursively
        for (File file : files) {
            if (file.isDirectory()) {
                deleteFolder(file);
            } else {
                file.delete();
            }
        }
        
        // Delete the empty folder
        return(folder.delete());
    }
}
