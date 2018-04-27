import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class Main {
	public static void main(String[] args) throws IOException, JSONException, InterruptedException {
		//get from command line:
		//folderPath title gameIds teamIds
		
		int[] vidIDs; 
		String title = args[1];
		String systemType = "Eurobasket";
		
		//get user videos from directory
		String folderPath = args[0]; 
		ArrayList<Event> userVids = UserVideos.openDirectory(folderPath);
		//get PBP from WSC Sports server
		ArrayList<String> pbpArguments = new ArrayList<>();
		pbpArguments.add("gameIds=" + args[2]);
		pbpArguments.add("teamIds=" + args[3]);
		pbpArguments.add("minRating=4");
		pbpArguments.add("systemType=" + systemType);
		
		ArrayList<Event> PBPEvents = JSONParser.PBPToEventsList(ApiRequests.PBPGet(pbpArguments));
		System.out.println(PBPEvents.size());
		
		VideoCreator videoCreator = new VideoCreator(PBPEvents, userVids);
		vidIDs = videoCreator.getAllVideoIDs();
		
		JSONObject ID = ApiRequests.CreateManualPost(vidIDs, title);
		System.out.println(ID.toString());
		int id = ID.getInt("videoId");
		
		waitForOkStatus(id);
		
		ArrayList<String> urlArgs = new ArrayList<>();
		urlArgs.add("videoId=" + id);
		urlArgs.add("systemType=" + systemType);
		
		JSONObject URLobj = ApiRequests.videoUrlGet(urlArgs);
		String URL = URLobj.getString("videoUrl");
		System.out.println(URL);		
	}
	
	public static void waitForOkStatus(int videoId) throws IOException, JSONException, InterruptedException {
        while(ApiRequests.videoStatusGet(videoId).getInt("videoCreationStatus") != 3){
            	Thread.sleep(2000);
        }
	}
}
