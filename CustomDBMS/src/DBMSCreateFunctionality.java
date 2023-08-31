import java.io.*;
import java.util.*;

public class DBMSCreateFunctionality{
    void createOperation(String[] querySplit, String username, String schema) throws IOException{
        if(querySplit[1].equals("table")){//create table
            String[] columns = querySplit[3].split(",");
            String[] column = new String[columns.length];
            String[] datatype = new String[columns.length];
            //separate column name and their data types
            for(int iterator = 0; iterator < columns.length; iterator++){
                column[iterator] = columns[iterator].split("::")[0];
                datatype[iterator] = columns[iterator].split("::")[1];
            }
            List<List<String>> table = new ArrayList<>();
            List<List<String>> metadata = new ArrayList<>();
            List<String> columnHeaders = Arrays.asList(column);
            List<String> metadataHeaders = Arrays.asList("Column Name","Data Type");
            metadata.add(metadataHeaders);
            for(int iterator = 0; iterator <columns.length;iterator++){
                List<String> metaData = Arrays.asList(column[iterator],datatype[iterator]);
                metadata.add(metaData);
            }
            table.add(columnHeaders);
            //create metadata for the table being created
            File metadatatable = new File("./"+username+"/"+schema+"/"+querySplit[2]+".percymeta");
            try (PrintWriter writer = new PrintWriter(metadatatable)) {
                String delimiter = ":_P_:";
                for (List<String> row : metadata) {
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
            //create the actual table
            File datatable = new File("./"+username+"/"+schema+"/"+querySplit[2]+".percydb");
            try (PrintWriter writer = new PrintWriter(datatable)) {
                String delimiter = ":_P_:";
                for (List<String> row : table) {
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
        if(querySplit[1].equals("schema")){ //create schema
            File folder = new File("./"+username+"/"+querySplit[2]);
            if (!folder.exists()) {
                folder.mkdir();
            }
        }
    }
}
