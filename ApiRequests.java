import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ApiRequests {
    private static String authorizationToken;

    public static void main(String[] args) throws IOException, JSONException {
          UploadVideoPost();
    }


    // Get Functions

    public static JSONArray UploadedClipsGet() throws IOException, JSONException {
        authorizationToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1laWQiOjEwMDA1OCwidW5pcXVlX25hbWUiOjEwMDAwMDU4MiwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwNS8wNS9pZGVudGl0eS9jbGFpbXMvc3lzdGVtIjoxMDEsIlRlYW1zUGVybWlzc2lvbnMiOiJbXSIsImlzcyI6InNlbGYiLCJhdWQiOiJodHRwOi8vY2xpcHJvLnR2IiwiZXhwIjoxNTI0OTIyNDg2LCJuYmYiOjE1MjQ2NjMyODZ9.30z3g7GZg87tku5QjNMLC7booSud-CsbE5XI-JDKZ3Y";
        String url="http://hacktonexternalapi.azurewebsites.net/Api/GetUploadedClips";
        URL object= new URL(url);

        HttpURLConnection con = (HttpURLConnection) object.openConnection();
        con.setDoOutput(true);
        con.setDoInput(true);
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty( "Authorization",authorizationToken);
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
        String url="http://hacktonexternalapi.azurewebsites.net/Api/SearchClipPbP/?request.gameIds=29010&request.teamIds=6385&request.minRating=5&request.systemType=Eurobasket";
        URL object= new URL(url);
        HttpURLConnection con = (HttpURLConnection) object.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty( "Authorization",authorizationToken);
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

    private static JSONArray UploadVideoPost() throws IOException, JSONException {
        authorizationToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1laWQiOjEwMDA1OCwidW5pcXVlX25hbWUiOjEwMDAwMDU4MiwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwNS8wNS9pZGVudGl0eS9jbGFpbXMvc3lzdGVtIjoxMDEsIlRlYW1zUGVybWlzc2lvbnMiOiJbXSIsImlzcyI6InNlbGYiLCJhdWQiOiJodHRwOi8vY2xpcHJvLnR2IiwiZXhwIjoxNTI0OTIyNDg2LCJuYmYiOjE1MjQ2NjMyODZ9.30z3g7GZg87tku5QjNMLC7booSud-CsbE5XI-JDKZ3Y";
        String url="http://hacktonexternalapi.azurewebsites.net/Api/GetUploadedClips?systemType=Eurobasket";
        String urlParameters  = "param1=a&param2=b&param3=c";
        byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
        int    postDataLength = postData.length;
        URL object= new URL(url);

        HttpURLConnection con = (HttpURLConnection) object.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);

        StringBuffer queryParam = new StringBuffer();
        OutputStream output = con.getOutputStream();


        output.flush();


        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return parseToJSON(sb.toString());
    }

    //Helper Functions

    private static JSONArray parseToJSON(String request) throws JSONException {
        JSONArray jsonArr = new JSONArray(request);
        for (int i = 0; i < jsonArr.length(); i++)
        {
            JSONObject jsonObj = jsonArr.getJSONObject(i);
            System.out.println(jsonObj);
        }
        return jsonArr;
    }

  //  private static ArrayList<Event> convertJsonToArray();

}
