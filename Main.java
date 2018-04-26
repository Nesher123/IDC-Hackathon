import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		//get user videos from directory
		String folderPath = "/Users/Ronitamir/Desktop";
		ArrayList<Event> userVids = UserVideos.openDirectory(folderPath);
		
		//get PBP from WSC Sports server
		ArrayList<Event> PBP = new ArrayList<Event>();
		
		VideoCreator videoCreator = new VideoCreator(userVids, PBP);
		videoCreator.createAndSaveVid();
	}
}
