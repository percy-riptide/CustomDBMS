import java.io.*;
import java.util.*;

public class DBMSInsertFunctionality {
    void insertOperation(String[] querySplit, String username, String schema) throws IOException{
        String[] values = querySplit[4].split(","); //get the values to insert
        List<List<String>> updatedTable = new ArrayList<>();
        List<String> updatingValues = Arrays.asList(values);
        try (Scanner scanner = new Scanner(new File("./"+username+"/"+schema+"/"+querySplit[2]+".percydb"))) {
            //add all the values to the table
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                List<String> row = Arrays.asList(line.split(":_P_:"));
                updatedTable.add(row);
            }
        }
        updatedTable.add(updatingValues);
        //write table to a file
        File updatedFile = new File("./"+username+"/"+schema+"/"+querySplit[2]+".percydb");
        try (PrintWriter writer = new PrintWriter(updatedFile)) {
            String delimiter = ":_P_:";
            for (List<String> row : updatedTable) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < row.size(); i++) {
                    String cell = row.get(i);
                    sb.append(String.format(cell));
                    if (i < row.size() - 1) {
                        sb.append(delimiter);
                    }
                }
                writer.println(sb.toString());
            }
        }
    }
}