import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class VideoCreator {
	
	//after PBP lookup such as searchClipPBP(game, action, team, player, minRating, systemType)
	ArrayList<Event> PBP = new ArrayList<Event>();
	
	//each video is a made up of an ID and a timestamp 
	//the list of user videos is sorted by timestamp 
	ArrayList<Event> videosFromUser = new ArrayList<Event>();
	ArrayList<Event> highlights = new ArrayList<Event>();
	
	ArrayList<Event> allVids = new ArrayList<Event>();
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
	public int[] getAllVideoIDs() {
		//sort the PBP
		removeReplay();
		//System.out.println(PBP.size());
		Collections.sort(PBP);
		//System.out.println(PBP.size());
		//create the highlights
		chooseHighlights();
		//interpolate with vids from user
		interpolateVids();
		//create list of IDS of vids
		vidListToIDList();
		
		return finalVidIDs;
	}
	
	public void removeReplay(){
		ArrayList<Event> PBPnoReplay = (ArrayList<Event>) PBP.clone();
		for(Event e : PBP){
			if(e.name.contains("Replay")){
				PBPnoReplay.remove(e);
			}
		}
		PBP = (ArrayList<Event>) PBPnoReplay.clone();
	}
	
	//Choose relevant highlights from PBP based on timestamp and numOfHighlights
	//put the vids ID's and timestamps into the list of highlight
	private void chooseHighlights() {
		//seconds * milliseconds/second
		int desiredInterval = 15 * 10000; 
		int numVids = videosFromUser.size();
		
		int numHighlights;
		if(numVids < 5) numHighlights = numVids;
		else numHighlights = 10 - numVids; 
		//System.out.println(PBP.size());
		
		//go through highlights and find ones in interval 
		for(Event highlight : PBP) {
			for(Event userVid : videosFromUser) {
				if(highlights.size() < numHighlights) {
					if((getTimeDifference(highlight.timestamp, userVid.timestamp) <= desiredInterval) && !(highlight.eventsListContains(highlights))) {
						highlights.add(highlight);
					}
				}
			}
		}
		System.out.println(highlights.size());
		
		//check if we have enough highlights if not forcefully add more
		int quarter = 1;
		int diff = numHighlights - highlights.size();
		if(PBP.size() < diff) diff = PBP.size();
		int i = 0;
		int desiredRating = 5;
		
		if(highlights.size() < numHighlights) {
			while(diff > 0) {
				if(i >= PBP.size()) {
					i = 0;
					if(quarter == 4) desiredRating = 4;
					else quarter++;
				}
				
				Event currHighlight = PBP.get(i);

				if(currHighlight.quarter == quarter && currHighlight.rating == desiredRating && !(currHighlight.eventsListContains(highlights))) {
					highlights.add(currHighlight);
					diff--;
					if(quarter == 4) quarter = 1;
					else quarter++;
				}
				i++; 
			}
//			for(int j = 0; j < diff; j++) {
//				Event currHighlight = PBP.get(j);
//				if(!(currHighlight.eventsListContains(highlights))) {
//					highlights.add(currHighlight);
//				}
//			}
		}
	}
	
	private long getTimeDifference(Date first, Date second) {
		return Math.abs(first.getTime() - second.getTime()); 
	}
	
	//Order the highlights and the vids from users in the final list in the order we want
	private void interpolateVids() {
		allVids.addAll(highlights);
		allVids.addAll(videosFromUser);
		
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
}
