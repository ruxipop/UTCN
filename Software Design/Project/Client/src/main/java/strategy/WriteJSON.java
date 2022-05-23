package strategy;

import java.util.*;

public class WriteJSON implements WriteStrategy {
    private static int fileCount = 1;

    @Override
    public void writeFile(List<model.Movie> list) {
        org.json.simple.JSONArray movies = new org.json.simple.JSONArray();
        for (model.Movie movie : list) {
            org.json.simple.JsonObject jsonObject = new org.json.simple.JsonObject();

            jsonObject.put("year", String.valueOf(movie.getYear()));
            jsonObject.put("type", movie.getType());
            jsonObject.put("category", movie.getCategory());
            jsonObject.put("title", movie.getTitle());
            movies.add(jsonObject);
        }
        try (java.io.FileWriter file = new java.io.FileWriter("Report_" + fileCount + ".json")) {
            fileCount++;
            file.write(movies.toJSONString());
            file.flush();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
