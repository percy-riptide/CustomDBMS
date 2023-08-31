import java.io.*;
import java.util.*;

public class DBMSSelectFunctionality {
    void selectOperation(String[] querySplit, String username, String schema) throws IOException{
        List<List<String>> dataTable = new ArrayList<>();
        List<List<String>> result = new ArrayList<>();
        int columnIndex = 0;
        try (Scanner scanner = new Scanner(new File("./"+username+"/"+schema+"/"+querySplit[3]+".percydb"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                List<String> row = Arrays.asList(line.split(":_P_:"));
                dataTable.add(row);
            }
        }
        //display all the rows
        if(querySplit[1].equals("*")){
            if(querySplit.length > 4){
                //check for where condition
                if(querySplit[4].toLowerCase().equals("where")){
                    for (int i = 0; i < dataTable.get(0).size(); i++) {
                        if ((dataTable.get(0).get(i)).equals(querySplit[4].split("=")[0])) {
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
                    //add the filtered results to the result table
                    result.add(dataTable.get(0));
                    for(int i : rowIndexMatches){
                        result.add(dataTable.get(i));
                    }
                }
            }
            else{
                result = dataTable;
            }
        }
        //check if they want only a few columns instead of all
        else if(!querySplit[1].equals("*") && !querySplit[1].isEmpty()){
            String[] columns = querySplit[1].split(",");
            List<String> columnHeaders = Arrays.asList(columns);
            List<Integer> columnIndexMatches = new ArrayList<>();
            for (int i = 0; i < dataTable.get(0).size(); i++) {
                if (columnHeaders.contains(dataTable.get(0).get(i))) {
                    columnIndexMatches.add(i);
                }
            }
            result.add(columnHeaders);
            if(querySplit.length > 4){
                //check if they have a where condition
                if(querySplit[4].toLowerCase().equals("where")){
                    for (int i = 0; i < dataTable.get(0).size(); i++) {
                        if ((dataTable.get(0).get(i)).equals(querySplit[4].split("=")[0])) {
                            columnIndex = i;
                        }
                    }
                    String element = querySplit[5].split("=")[1];
                    List<Integer> rowIndexMatches = new ArrayList<>();
                    for (int i = 1; i < dataTable.size(); i++) {
                        List<String> row = dataTable.get(i);
                        if (row.get(columnIndex).toLowerCase().equals(element.toLowerCase())) {
                            rowIndexMatches.add(i);
                        }
                    }
                    for (int i : rowIndexMatches) {
                        List<String> rowData = new ArrayList<>();
                        for (int j : columnIndexMatches) {
                            if (j < dataTable.get(i).size()) {
                                rowData.add(dataTable.get(i).get(j));
                            } else {
                                rowData.add("");
                            }
                        }
                        result.add(rowData);
                    }
                }
            }
            else{
                for (int i = 1; i < dataTable.size(); i++) {
                    List<String> rowData = new ArrayList<>();
                    for (int j : columnIndexMatches) {
                        if (j < dataTable.get(i).size()) {
                            rowData.add(dataTable.get(i).get(j));
                        } else {
                            rowData.add("");
                        }
                    }
                    result.add(rowData);
                }
            }
        }
        //display the results in a neat tabular format
        int[] maxWidths = new int[result.get(0).size()];
        for (List<String> row : result) {
            for (int i = 0; i < row.size(); i++) {
                maxWidths[i] = Math.max(maxWidths[i], row.get(i).length());
            }
        }   
        for (List<String> row : result) {
            for (int i = 0; i < row.size(); i++) {
                String cell = row.get(i);
                int width = maxWidths[i];
                System.out.print(String.format("%-" + width + "s", cell));
                if (i < row.size() - 1) {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }
}