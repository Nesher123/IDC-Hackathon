import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class ApiRequests {
    private static String authorizationToken;

    public static void main(String[] args) throws IOException, JSONException, InterruptedException {

        int [] ids = {11010250,10991451};
        UploadVideoPost("test","C:\\Users\\AsafGetz\\Documents\\Desktop\\myVid.mp4");
    }

    // Get Functions

    public static JSONArray UploadedClipsGet() throws IOException, JSONException {
        authorizationToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1laWQiOjEwMDA1OCwidW5pcXVlX25hbWUiOjEwMDAwMDU4MiwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwNS8wNS9pZGVudGl0eS9jbGFpbXMvc3lzdGVtIjoxMDEsIlRlYW1zUGVybWlzc2lvbnMiOiJbXSIsImlzcyI6InNlbGYiLCJhdWQiOiJodHRwOi8vY2xpcHJvLnR2IiwiZXhwIjoxNTI0OTIyNDg2LCJuYmYiOjE1MjQ2NjMyODZ9.30z3g7GZg87tku5QjNMLC7booSud-CsbE5XI-JDKZ3Y";
        String url = "http://hacktonexternalapi.azurewebsites.net/Api/GetUploadedClips";
        URL object = new URL(url);

        HttpURLConnection con = (HttpURLConnection) object.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Authorization", authorizationToken);
        con.setRequestMethod("GET");

        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return parseToJSON(sb.toString());
    }

    public static JSONArray PBPGet() throws IOException, JSONException {
        authorizationToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1laWQiOjEwMDA1OCwidW5pcXVlX25hbWUiOjEwMDAwMDU4MiwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwNS8wNS9pZGVudGl0eS9jbGFpbXMvc3lzdGVtIjoxMDEsIlRlYW1zUGVybWlzc2lvbnMiOiJbXSIsImlzcyI6InNlbGYiLCJhdWQiOiJodHRwOi8vY2xpcHJvLnR2IiwiZXhwIjoxNTI0OTIyNDg2LCJuYmYiOjE1MjQ2NjMyODZ9.30z3g7GZg87tku5QjNMLC7booSud-CsbE5XI-JDKZ3Y";
        String url = "http://hacktonexternalapi.azurewebsites.net/Api/SearchClipPbP/?request.gameIds=29010&request.teamIds=6385&request.minRating=5&request.systemType=Eurobasket";
        URL object = new URL(url);
        HttpURLConnection con = (HttpURLConnection) object.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Authorization", authorizationToken);
        System.out.println(con.getResponseMessage());

        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return parseToJSON(sb.toString());
    }

    // Post Functions

    private static String CreateManualPost(int [] eventsId, String videoName) throws IOException, InterruptedException {

        String queryParam = buildManualVideoQuery(eventsId,videoName);

        authorizationToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1laWQiOjEwMDA1OCwidW5pcXVlX25hbWUiOjEwMDAwMDU4MiwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwNS8wNS9pZGVudGl0eS9jbGFpbXMvc3lzdGVtIjoxMDEsIlRlYW1zUGVybWlzc2lvbnMiOiJbXSIsImlzcyI6InNlbGYiLCJhdWQiOiJodHRwOi8vY2xpcHJvLnR2IiwiZXhwIjoxNTI0OTIyNDg2LCJuYmYiOjE1MjQ2NjMyODZ9.30z3g7GZg87tku5QjNMLC7booSud-CsbE5XI-JDKZ3Y";
        String url = "http://hacktonexternalapi.azurewebsites.net/Api/CreateManual";
        URL object = new URL(url);

        HttpURLConnection con = (HttpURLConnection) object.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", authorizationToken);
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        con.setDoInput(true);

        OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
        wr.write(queryParam);
        wr.flush();
        System.out.println(con.getResponseMessage());

        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    private static String UploadVideoPost(String videoName,String content) throws IOException, JSONException {

        String queryParam = buildUploadQuery(videoName, content);
        System.out.println(queryParam);
        authorizationToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1laWQiOjEwMDA1OCwidW5pcXVlX25hbWUiOjEwMDAwMDU4MiwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwNS8wNS9pZGVudGl0eS9jbGFpbXMvc3lzdGVtIjoxMDEsIlRlYW1zUGVybWlzc2lvbnMiOiJbXSIsImlzcyI6InNlbGYiLCJhdWQiOiJodHRwOi8vY2xpcHJvLnR2IiwiZXhwIjoxNTI0OTIyNDg2LCJuYmYiOjE1MjQ2NjMyODZ9.30z3g7GZg87tku5QjNMLC7booSud-CsbE5XI-JDKZ3Y";
        String url = "http://hacktonexternalapi.azurewebsites.net/Api/UploadClip";
        URL object = new URL(url);

        HttpURLConnection con = (HttpURLConnection) object.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", authorizationToken);
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        con.setDoInput(true);

        OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
        wr.write(queryParam);
        wr.flush();
        System.out.println(con.getResponseMessage());

        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    //Helper Functions

    private static JSONArray parseToJSON(String request) throws JSONException {
            JSONArray jsonArr = new JSONArray(request);
            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObj = jsonArr.getJSONObject(i);
                System.out.println(jsonObj);
            }
            return jsonArr;
        }

    //  private static ArrayList<Event> convertJsonToArray();

    private static String buildManualVideoQuery(int[] eventsId, String videoName) {

        String list = Arrays.toString(eventsId).replace("[", "").replace("]", "");
        String result = "{\n" +
                "  \"eventClipIds\": [\n    " + list + " \n" +
                "  ],\n" +
                "  \"title\": \"" + videoName + "\",\n" +
                "  \"systemType\": \"Eurobasket\"\n" +
                "}";
        return result;
    }

    private static String buildUploadQuery(String videoName, String content) {
        String result = "{\n" +
                "  \"name\": \"" + videoName + "\",\n" +
                "  \"content\": \"" + content + "\",\n" +
                "  \"systemType\": \"Eurobasket\"\n" +
                "}";
        return result;
    }
}
