import java.io.*;
import java.util.*;

public class DBMSTruncateFunctionality {
    void truncateOperation(String[] querySplit, String username, String schema) throws IOException{
        //delete all rows in the table except the column header row
        if(querySplit[1].toLowerCase().equals("table")){
            List<List<String>> updatedTable = new ArrayList<>();
            int iterator = 0;
            try (Scanner scanner = new Scanner(new File("./"+username+"/"+schema+"/"+querySplit[2]+".percydb"))) {
                while (scanner.hasNextLine() && iterator < 1) {
                    String line = scanner.nextLine();
                    List<String> row = Arrays.asList(line.split(":_P_:"));
                    updatedTable.add(row);
                    iterator++;
                }
            }
            //update the file to contain only 1 row (column headers)
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
}
