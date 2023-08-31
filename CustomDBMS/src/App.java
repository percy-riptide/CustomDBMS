import java.io.File;
import java.io.IOException;
import java.util.*;

public class App {
    public static void main(String[] args) throws IOException {
        //initiate objects for all the classes
        UserAuthentication uaObj = new UserAuthentication();
        DBMSShowFunctionality sfObj = new DBMSShowFunctionality();
        DBMSSelectFunctionality selectObj = new DBMSSelectFunctionality();
        DBMSInsertFunctionality insertObj = new DBMSInsertFunctionality();
        DBMSCreateFunctionality createObj = new DBMSCreateFunctionality();
        DBMSDeleteFunctionality deleteObj = new DBMSDeleteFunctionality();
        DBMSUpdateFunctionality updateObj = new DBMSUpdateFunctionality();
        DBMSDescFunctionality descObj = new DBMSDescFunctionality();
        DBMSTruncateFunctionality truncateObj = new DBMSTruncateFunctionality();
        DBMSDropFunctionality dropObj = new DBMSDropFunctionality();
        String loggedUser = null;
        String loginStatus = null;
        String[] loginInfo = null;
        String chosenSchema = null;
        // Display menu
        while (true && loggedUser == null) {
            System.out.println("1. Register new user");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1-3): ");

            // Get user choice
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    uaObj.registerUser();
                    break;
                case 2:
                    loginInfo = uaObj.loginUser();
                    loggedUser = loginInfo[0];
                    loginStatus = loginInfo[1];
                    break;
                case 3:
                    System.out.println("Exiting program...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
        //display the welcome screen if login is successful
        if(loginStatus.toLowerCase().equals("true")){    
            System.out.println("Welcome to the DBMS user! Please choose one of the following:");
            boolean schemaSelection = false;
            //the use schema command was implemented using console as well as java function
            while(!schemaSelection){
                System.out.println("1. Create Schema\n2. Choose Schema");
                System.out.print("Enter Your Choice: ");
                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();
                if(choice == 1){
                    System.out.print("Enter Schema name (without spaces): ");
                    String schemaName = scanner.next();
                    File folder = new File("./"+loggedUser+"/"+schemaName);
                    //create the schema if it isnt present
                    if (!folder.exists()) {
                        folder.mkdir();
                    }
                    System.out.print(schemaName+" created, you will use this schema\n");
                    chosenSchema = schemaName;
                    schemaSelection = true;
                }
                else if(choice == 2){
                    //show all the schemas available
                    System.out.println("\nAvailable Schemas are:");
                    String[] availableSchemas = sfObj.showSchema(loggedUser);
                    int iterator = 1;
                    for (String schema : availableSchemas) {
                        System.out.print(iterator+". "+schema+"\n");
                        iterator++;
                    }
                    System.out.print("\nChoose a schema: ");
                    Scanner schemaScanner = new Scanner(System.in);
                    int schemaChoice = schemaScanner.nextInt();  
                    boolean schemaChosen = false;
                    while(!schemaChosen){
                        if(schemaChoice > availableSchemas.length || schemaChoice < 1){
                            System.out.print("Please choose a proper schema");
                        }
                        else if(schemaChoice <= availableSchemas.length){
                            chosenSchema = availableSchemas[schemaChoice-1];
                            schemaChosen = true;
                        }
                    }
                    schemaSelection = true;
                }
                else{
                    System.out.print("Please choose a valid option!");
                }
            }
            //prompt user to execute a query ort exit the program
            while(true){
                System.out.println("\nWhat would you like to do?\n1. Execute Query\n2. Exit");
                System.out.print("Enter your choice: ");
                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();
                if(choice == 1){
                    System.out.print("\nEnter Query: ");
                    scanner.nextLine();
                    String query = scanner.nextLine();
                    String[] querySplit = query.split(" ");
                    //get the first word in the sentence to check which command to execute
                    if(querySplit[0].toLowerCase().equals("select")){
                        selectObj.selectOperation(querySplit, loggedUser, chosenSchema);
                    }
                    if(querySplit[0].toLowerCase().equals("insert")){
                        insertObj.insertOperation(querySplit, loggedUser, chosenSchema);
                    }
                    if(querySplit[0].toLowerCase().equals("update")){
                        updateObj.updateOperation(querySplit, loggedUser, chosenSchema);
                    }
                    if(querySplit[0].toLowerCase().equals("delete")){
                        deleteObj.deleteOperation(querySplit, loggedUser, chosenSchema);
                    }
                    if(querySplit[0].toLowerCase().equals("create")){
                        createObj.createOperation(querySplit, loggedUser, chosenSchema);
                    }
                    if(querySplit[0].toLowerCase().equals("desc")){
                        descObj.descOperation(querySplit, loggedUser, chosenSchema);
                    }
                    if(querySplit[0].toLowerCase().equals("show")){
                        if(querySplit[1].toLowerCase().equals("users")){
                            sfObj.showUsers();
                        }
                        if(querySplit[1].toLowerCase().equals("schemas")){
                            String[] schemas = sfObj.showSchema(loggedUser);
                            for(String schema : schemas){
                                System.out.println(schema);
                            }
                        }
                        if(querySplit[1].toLowerCase().equals("tables")){
                            sfObj.showTables(loggedUser,chosenSchema);
                        }
                    }
                    if(querySplit[0].toLowerCase().equals("use")){
                        if(querySplit[1].toLowerCase().equals("schema")){
                            chosenSchema = querySplit[2];
                        }
                        else{
                            System.out.println("Invalid Query: The proper syntax is use schema schema_name");
                        }
                    }
                    if(querySplit[0].toLowerCase().equals("truncate")){
                        truncateObj.truncateOperation(querySplit, loggedUser, chosenSchema);
                    }
                    if(querySplit[0].toLowerCase().equals("drop")){
                        dropObj.dropOperation(querySplit, loggedUser, chosenSchema);
                    }
                }
                else if (choice == 2){
                    System.out.println("\nThank you!");
                    System.exit(1);
                }
                else{
                    System.out.println("Enter Correct Choice!\n");
                }
            }
        }
        else{
            System.out.println("Login Failed, please try again");
        }
    }
}