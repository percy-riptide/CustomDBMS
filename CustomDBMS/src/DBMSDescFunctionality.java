import java.util.*;
import java.io.*;

public class DBMSDescFunctionality {
    void descOperation(String[] querySplit, String username, String schema) throws IOException{
        List<List<String>> result = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("./"+username+"/"+schema+"/"+querySplit[1]+".percymeta"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                List<String> row = Arrays.asList(line.split(":_P_:"));
                result.add(row);
            }
        }
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
