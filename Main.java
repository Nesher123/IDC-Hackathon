import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class Main {
	public static void main(String[] args) throws IOException, JSONException, InterruptedException {
		int[] vidIDs; 
		String title = "test";
		String systemType = "Eurobasket";
		
		//get user videos from directory
		String folderPath = "/Users/Ronitamir/Desktop/Test";
		ArrayList<Event> userVids = UserVideos.openDirectory(folderPath);
		
		System.out.println(userVids.size());
		
		//get PBP from WSC Sports server
		ArrayList<String> pbpArguments = new ArrayList<>();
		pbpArguments.add("gameIds=29010");
		pbpArguments.add("teamIds=6385");
		pbpArguments.add("minRating=4");
		pbpArguments.add("systemType=Eurobasket");
		
		ArrayList<Event> PBPEvents = JSONParser.PBPToEventsList(ApiRequests.PBPGet(pbpArguments));
		//System.out.println(PBPEvents.size());
		
		VideoCreator videoCreator = new VideoCreator(PBPEvents, userVids);
		vidIDs = videoCreator.getAllVideoIDs();
		
		JSONObject ID = ApiRequests.CreateManualPost(vidIDs, title);
		System.out.println(ID.toString());
		int id = ID.getInt("videoId");
		
		ArrayList<String> urlArgs = new ArrayList<>();
		urlArgs.add("id=" + id);
		urlArgs.add("systemType=Eurobasket");
		
		JSONObject URLobj = ApiRequests.videoUrlGet(urlArgs);
		String URL = URLobj.getString("videoUrl");
		System.out.println(URL);		
	}
}
