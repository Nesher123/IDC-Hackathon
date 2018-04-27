import java.util.Date;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {
	
	public static ArrayList<Event> PBPToEventsList(JSONArray PBP) throws JSONException {
		ArrayList<Event> eventsList = new ArrayList<Event>();
	
		for (int i = 0; i < PBP.length(); i++) {
		    JSONObject currPlay = PBP.getJSONObject(i);
		    int ID = currPlay.getInt("id");
		    int quarter = currPlay.getInt("quarter");
		    int rating = currPlay.getInt("rating");
		    String timestamp = currPlay.getString("worldClockUTC");    
		    String name = currPlay.getString("eventName");
		    
		    //timestamp convert to date 
		    Date date = convertStringToDate(timestamp);
		    Event currEvent = new Event(ID, quarter, rating, date, name);
		    eventsList.add(currEvent);
		}
		return eventsList;
	}
	
	public static ArrayList<Event> videoListToEventsList(JSONArray videoList) throws JSONException{
		ArrayList<Event> eventsList = new ArrayList<Event>();
		
		for(int i = 0; i < videoList.length(); i++) {
			JSONObject currPlay = videoList.getJSONObject(i);
			int ID = currPlay.getInt("id");
			String name = currPlay.getString("eventName");
			
			Event currEvent = new Event(ID, 0, 0, null, name);
			eventsList.add(currEvent);
		}
		
		return eventsList;
	}
	
	public static Date convertStringToDate(String timestamp) {
		
		String dateOnly = timestamp.substring(0, timestamp.indexOf('T'));
		String[] splitStrDate = dateOnly.split("-");
		int year = Integer.parseInt(splitStrDate[0]);
		int month = Integer.parseInt(splitStrDate[1]);
		int day = Integer.parseInt(splitStrDate[2]);
		
		
		String timeOnly = timestamp.substring(timestamp.indexOf('T') + 1, timestamp.length());
		String[] splitStrTime = timeOnly.split(":");
		
		int hour = Integer.parseInt(splitStrTime[0]);
		int min = Integer.parseInt(splitStrTime[1]);
		int sec = Integer.parseInt(splitStrTime[2]);
			
		@SuppressWarnings("deprecation")
		Date date = new Date(year, month, day, hour, min, sec);
		return date; 
	}
}
