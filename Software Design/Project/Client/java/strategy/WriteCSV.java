package strategy;

import java.util.*;

public class WriteCSV implements WriteStrategy {
    private static int fileCount = 1;

    @Override
    public void writeFile(List<model.Movie> list) {
        try (java.io.PrintWriter writer = new java.io.PrintWriter("Report_" + fileCount + ".csv")) {
            fileCount++;
            StringBuilder sb = new StringBuilder();
            sb.append("Title");
            sb.append(',');
            sb.append("Type");
            sb.append(',');
            sb.append("Category");
            sb.append(',');
            sb.append("Year");
            sb.append('\n');
            for (model.Movie movie : list) {
                sb.append(movie.getTitle());
                sb.append(',');
                sb.append(movie.getType());
                sb.append(',');
                sb.append(movie.getCategory());
                sb.append(',');
                sb.append(String.valueOf(movie.getYear()));
                sb.append('\n');
            }
            writer.write(sb.toString());
        } catch (java.io.FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
