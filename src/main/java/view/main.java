package view;
import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;


import java.io.*;
import java.util.List;

public class main {
    public static void main(String [] args) throws IOException, ParseException {

        String path = "DataBase\\data.json";
        JSONParser parser = new JSONParser();
        JSONArray a;
        if (new FileReader(path).read()==-1) a = new JSONArray();
        else a = (JSONArray) parser.parse(new FileReader(path));
        JSONObject json = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        try {
            json.put("name", "Google");
            json.put("employees", 140000);
            json.put("offices", List.of("Mountain View", "Los Angeles", "New York"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonObject.put("user", json);
        a.add(jsonObject);
        json = new JSONObject();
        try {
            json.put("name", "aa222a");
            json.put("empl2222222222222", 100);
            json.put("offices", List.of("Mountaiew", "Logeles", "Nrk"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonObject = new JSONObject();
        jsonObject.put("user", json);
        a.add(jsonObject);

        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            out.write(a.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
         parser = new JSONParser();
        a = (JSONArray) parser.parse(new FileReader(path));
     //   System.out.println(a.toString());
        for(Object j : a){
            JSONObject temp = (JSONObject) j;
            temp = (JSONObject) temp.get("user");
            System.out.println(temp.get("name"));
        }
    }
}
