import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class VideoCreator {
	
	//after PBP lookup such as searchClipPBP(game, action, team, player, minRating, systemType)
	//TODO decide on rating threshold
	ArrayList<Event> PBP; 
	
	//each video is a made up of an ID and a timestamp 
	//the list of user videos is sorted by timestamp 
	ArrayList<Event> videosFromUser;
	ArrayList<Event> highlights; 
	
	ArrayList<Event> allVids; 
	//final list of videos that will be included (each item is the videoID and it will be in order that we want in the vid)
	int[] finalVidIDs;
	
	//we must determine how many highlights we want to put into the vid
	int numOfHighlights;

	//ID and Timestamp for each videoFromUser
	public VideoCreator(ArrayList<Event> PBP, ArrayList<Event> videosFromUser) {
		this.PBP = PBP;
		this.videosFromUser = videosFromUser;
	}
	
	//function that calls the WSC function createVideo on finalVidIDs and saves final vid
	public void createAndSaveVid() {
		//sort the PBP
		Collections.sort(PBP);
		//create the highlights
		chooseHighlights();
		//interpolate with vids from user
		interpolateVids();
		//create list of IDS of vids
		vidListToIDList();
		//generate the request
		String command = generateCreateVideoRequest();
		//call to create vid
		//TODO import their API
		createVideo(finalVidIDs);
		//save the vid 
		saveVideo();
	}
	
	//Choose relevant highlights from PBP based on timestamp and numOfHighlights
	//put the vids ID's and timestamps into the list of highlight
	private void chooseHighlights() {
		//TODO determine what the desired interval is
		//seconds * milliseconds/second
		int desiredInterval = 10 * 10000; 
		
		//go through highlights and find ones in interval 
		for(Event highlight : PBP) {
			for(Event userVid : videosFromUser) {
				if(getTimeDifference(highlight.timestamp, userVid.timestamp) <= desiredInterval) highlights.add(highlight);
			}
		}
		
		//TODO check if we have enough highlights if not forcefully add more
	}
	
	private long getTimeDifference(Date first, Date second) {
		return Math.abs(first.getTime() - second.getTime()); 
	}
	
	//Order the highlights and the vids from users in the final list in the order we want
	private void interpolateVids() {
		allVids.addAll(highlights);
		allVids.addAll(PBP);
		
		//sort the vids by timestamp
		Collections.sort(allVids);
		
		//now we have the number of vids that we'll have so we initaite the array
		finalVidIDs = new int[allVids.size()];
	}
	
	private void vidListToIDList() {
		for(int i = 0; i < allVids.size(); i++) {
			finalVidIDs[i] = allVids.get(i).ID;
		}
	}
	
	private String generateCreateVideoRequest() {
		return null; 
	}

	private void saveVideo() {
		//TODO should we save or upload to youtube? 
	}
	
	//OPTIONAL decide which vids should be used
	private void filterOutUserVids() {
		
	}
}
