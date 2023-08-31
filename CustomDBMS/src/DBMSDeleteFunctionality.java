import java.util.*;
import java.io.*;

public class DBMSDeleteFunctionality {
    void deleteOperation(String[] querySplit, String username, String schema) throws IOException{
        List<List<String>> dataTable = new ArrayList<>();
        List<List<String>> result = new ArrayList<>();
        int columnIndex = 0;
        //scan the stored table
        try (Scanner scanner = new Scanner(new File("./"+username+"/"+schema+"/"+querySplit[2]+".percydb"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                List<String> row = Arrays.asList(line.split(":_P_:"));
                dataTable.add(row);
            }
        }
        //store the table in result variable
        result = dataTable;
        for (int i = 0; i < dataTable.get(0).size(); i++) {
            if ((dataTable.get(0).get(i)).equals(querySplit[4].split("=")[0])) {
                columnIndex = i;
            }
        }
        String element = querySplit[4].split("=")[1];
        for (int i = 1; i < dataTable.size(); i++) {
            List<String> row = dataTable.get(i);
            if (row.get(columnIndex).toLowerCase().equals(element.toLowerCase())) {
                result.remove(i);
            }
        }
        //update the file to hold the new results
        File updatedFile = new File("./"+username+"/"+schema+"/"+querySplit[2]+".percydb");
        try (PrintWriter writer = new PrintWriter(updatedFile)) {
            String delimiter = ":_P_:";
            for (List<String> row : result) {
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
