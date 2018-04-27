import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class ApiRequests {
    private static String authorizationToken;

    public static void main(String[] args) throws IOException, JSONException, InterruptedException {
        File file = new File("C:\\Users\\AsafGetz\\Documents\\Desktop\\HackIDC\\myVid.mp4");
       // UploadVideoPost("Asaf_try",file,"Eurobasket");
        UploadVideoPost2("http://hacktonexternalapi.azurewebsites.net/Api/UploadClip");
    }

    // Get Functions

    public static JSONObject videoUrlGet(ArrayList<String> params) throws IOException, JSONException {
        authorizationToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1laWQiOjEwMDA1OCwidW5pcXVlX25hbWUiOjEwMDAwMDU4MiwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwNS8wNS9pZGVudGl0eS9jbGFpbXMvc3lzdGVtIjoxMDEsIlRlYW1zUGVybWlzc2lvbnMiOiJbXSIsImlzcyI6InNlbGYiLCJhdWQiOiJodHRwOi8vY2xpcHJvLnR2IiwiZXhwIjoxNTI0OTIyNDg2LCJuYmYiOjE1MjQ2NjMyODZ9.30z3g7GZg87tku5QjNMLC7booSud-CsbE5XI-JDKZ3Y";
        String url = "http://hacktonexternalapi.azurewebsites.net/Api/GetVideoUrl?";
        String urlParams = buildGetParams(params,url);
        URL object = new URL(urlParams);

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
        return(new JSONObject(sb.toString()));
    }

    public static JSONObject videoStatusGet(int videoId) throws IOException, JSONException {
        authorizationToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1laWQiOjEwMDA1OCwidW5pcXVlX25hbWUiOjEwMDAwMDU4MiwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwNS8wNS9pZGVudGl0eS9jbGFpbXMvc3lzdGVtIjoxMDEsIlRlYW1zUGVybWlzc2lvbnMiOiJbXSIsImlzcyI6InNlbGYiLCJhdWQiOiJodHRwOi8vY2xpcHJvLnR2IiwiZXhwIjoxNTI0OTIyNDg2LCJuYmYiOjE1MjQ2NjMyODZ9.30z3g7GZg87tku5QjNMLC7booSud-CsbE5XI-JDKZ3Y";
        String url = "http://hacktonexternalapi.azurewebsites.net/Api/GetVideoCreationStatus?videoId=" + videoId ;
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
        return(new JSONObject(sb.toString()));
    }

    public static JSONArray UploadedClipsGet() throws IOException, JSONException {
        authorizationToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1laWQiOjEwMDA1OCwidW5pcXVlX25hbWUiOjEwMDAwMDU4MiwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwNS8wNS9pZGVudGl0eS9jbGFpbXMvc3lzdGVtIjoxMDEsIlRlYW1zUGVybWlzc2lvbnMiOiJbXSIsImlzcyI6InNlbGYiLCJhdWQiOiJodHRwOi8vY2xpcHJvLnR2IiwiZXhwIjoxNTI0OTIyNDg2LCJuYmYiOjE1MjQ2NjMyODZ9.30z3g7GZg87tku5QjNMLC7booSud-CsbE5XI-JDKZ3Y";
        String url = "http://hacktonexternalapi.azurewebsites.net/Api/GetUploadedClips/?systemType=Eurobasket";
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

    public static JSONArray PBPGet(ArrayList<String> params) throws IOException, JSONException {
        authorizationToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1laWQiOjEwMDA1OCwidW5pcXVlX25hbWUiOjEwMDAwMDU4MiwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwNS8wNS9pZGVudGl0eS9jbGFpbXMvc3lzdGVtIjoxMDEsIlRlYW1zUGVybWlzc2lvbnMiOiJbXSIsImlzcyI6InNlbGYiLCJhdWQiOiJodHRwOi8vY2xpcHJvLnR2IiwiZXhwIjoxNTI0OTIyNDg2LCJuYmYiOjE1MjQ2NjMyODZ9.30z3g7GZg87tku5QjNMLC7booSud-CsbE5XI-JDKZ3Y";
        String url = "http://hacktonexternalapi.azurewebsites.net/Api/SearchClipPbP/?";

        String urlParams = buildGetParams(params,url);
        System.out.println(urlParams);
        URL object = new URL(urlParams);
        HttpURLConnection con = (HttpURLConnection) object.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("Authorization", authorizationToken);;

        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return parseToJSON(sb.toString());
    }

    // Post Functions

    public static JSONObject CreateManualPost(int [] eventsId, String videoName) throws IOException, InterruptedException, JSONException {

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

        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return(new JSONObject(sb.toString()));
}

    public static String UploadVideoPost(String videoName,File videoToUpload,String systemType) throws IOException {
        String url = "http://hacktonexternalapi.azurewebsites.net/Api/UploadClip";
        MultipartUtility multipartUtility = new MultipartUtility(url);

        //Add String params
        multipartUtility.addFormField("name",videoName);
        multipartUtility.addFilePart("content",videoToUpload);


        //Add File
        multipartUtility.addFormField("systemType",systemType);
        return multipartUtility.finish();
    }

    //Helper Functions

    public static String UploadVideoPost2(String url) throws IOException {
        String authorizationToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJuYW1laWQiOjEwMDA1OCwidW5pcXVlX25hbWUiOjEwMDAwMDU4MiwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwNS8wNS9pZGVudGl0eS9jbGFpbXMvc3lzdGVtIjoxMDEsIlRlYW1zUGVybWlzc2lvbnMiOiJbXSIsImlzcyI6InNlbGYiLCJhdWQiOiJodHRwOi8vY2xpcHJvLnR2IiwiZXhwIjoxNTI0OTIyNDg2LCJuYmYiOjE1MjQ2NjMyODZ9.30z3g7GZg87tku5QjNMLC7booSud-CsbE5XI-JDKZ3Y";
        File file = new File("C:\\Users\\AsafGetz\\Documents\\Desktop\\HackIDC\\myVid.mp4");

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        FileBody uploadFilePart = new FileBody(file);
        MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.STRICT);
        reqEntity.addPart("content", uploadFilePart);
        httpPost.addHeader("Accept","application/json");
        httpPost.addHeader("Authorization",authorizationToken);
        httpPost.addHeader("Connection","keep-alive");
        httpPost.addHeader("Content-Type",reqEntity.getContentType().getValue());

        HttpParams httpParams = new BasicHttpParams();
        httpParams.setParameter("name", "asaf test");
        httpParams.setParameter("systemType", "BCL");
      //  httpParams.setParameter("content", file);
        httpPost.setParams(httpParams);

        HttpResponse response = httpclient.execute(httpPost);
        return response.toString();
    }


    private static JSONArray parseToJSON(String request) throws JSONException {
            JSONArray jsonArr = new JSONArray(request);
            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObj = jsonArr.getJSONObject(i);
                System.out.println(jsonObj);
            }
            return jsonArr;
        }

    // This function builds json-like query for CreateManual
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

    // This function builds url get request according to given params
    private static String buildGetParams(ArrayList<String> requests,String url){
        boolean notFirstConcatination = false;
        for(String s : requests)
        {
            if(notFirstConcatination)
            {
                url += "&";
            }

            notFirstConcatination = true;
            url += "request." + s.toString();
        }
        return url;
    }
}
