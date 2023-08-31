import java.util.*;
import java.io.*;

public class DBMSUpdateFunctionality {
    void updateOperation(String[] querySplit, String username, String schema) throws IOException{
        List<List<String>> dataTable = new ArrayList<>();
        List<List<String>> result = new ArrayList<>();
        String[] dataSet = querySplit[3].split(",");
        String[] columns = new String[dataSet.length];
        String[] valuesSet = new String[dataSet.length];
        int columnIndex = 0;
        try (Scanner scanner = new Scanner(new File("./"+username+"/"+schema+"/"+querySplit[1]+".percydb"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                List<String> row = Arrays.asList(line.split(":_P_:"));
                dataTable.add(row);
            }
        }
        for(int i = 0; i<dataSet.length;i++){
            columns[i] = dataSet[i].split("=")[0];
            valuesSet[i] = dataSet[i].split("=")[1];
        }
        List<String> columnHeaders = Arrays.asList(columns);
        List<Integer> columnIndexMatches = new ArrayList<>();
        for (int i = 0; i < dataTable.get(0).size(); i++) {
            if (columnHeaders.contains(dataTable.get(0).get(i))) {
                columnIndexMatches.add(i);
            }
        }
        for (int i = 0; i < dataTable.get(0).size(); i++) {
            if ((dataTable.get(0).get(i)).equals(querySplit[5].split("=")[0])) {
                columnIndex = i;
            }
        }
        String element = querySplit[5].split("=")[1];
        List<Integer> rowIndexMatches = new ArrayList<>();
        for (int i = 0; i < dataTable.size(); i++) {
            List<String> row = dataTable.get(i);
            if (row.get(columnIndex).toLowerCase().equals(element.toLowerCase())) {
                rowIndexMatches.add(i);
            }
        }
        System.out.println(dataTable);
        result = dataTable;
        int iterator = 0;
        for(int i : rowIndexMatches){
            for(int j : columnIndexMatches){
                result.get(i).set(j, valuesSet[iterator]);
                iterator++;
            }
        }
        System.out.println(result);
        File updatedFile = new File("./"+username+"/"+schema+"/"+querySplit[1]+".percydb");
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
