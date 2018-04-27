import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;


public class UserVideos {
	/*
	 * 1. open directory 
	 * 2. go over all videos inside the directory. 
	 * 3. For each video, get its time-stamp 
	 * 	3.1. upload to WSC server 
	 * 	3.2. get ID 
	 * 4. return a sorted (by time-stamps) list containing all videos IDs AND time-stamps
	 * 
	 * ***** Bdikot!!!!!!
	 */

	static String m_SystemType = "Eurobasket";

	public static ArrayList<Event> openDirectory(String folderPath) throws IOException, JSONException {
		//list files
		File directory = new File(folderPath);
		File[] contents = directory.listFiles();
		
		ArrayList<Event> idsAndTimestampsOfUserVideos = new ArrayList<Event>();

		for (File f : contents) {
			// upload the video
			//ApiRequests.UploadVideoPost(f.getName(), f, m_SystemType);
			//currently uploading videos from swagger
			//TODO
		}

		JSONArray uploadedClips = ApiRequests.UploadedClipsGet();
		ArrayList<Event> uploadedClipsEvents = JSONParser.videoListToEventsList(uploadedClips);
		
		for (Event e : uploadedClipsEvents) {
			//release the time-stamps
			for (File f : contents) {
				if (f.getName().equals(e.name + ".m4v")) {
					//Date videoCreationTime = releaseTimeStamps(folderPath + "/" + f.getName());
					Date videoCreationTime;
					//TODO this is a test to force timestamp to be something, remove!!
					
					if(e.name.equals("luna")) videoCreationTime = new Date(2017, 9, 2, 15, 56, 32);
					else videoCreationTime = releaseTimeStamps(folderPath + "/" + f.getName());
					
					// request the id from the system according to the name.
					int id = e.ID;
					idsAndTimestampsOfUserVideos.add(new Event(id, 0, 0, videoCreationTime, e.name));
				}
			}
		}

		Collections.sort(idsAndTimestampsOfUserVideos);
		return idsAndTimestampsOfUserVideos;
	}

	public static Date releaseTimeStamps(String videoFilePath) throws IOException {
		Path vidPath = Paths.get(videoFilePath);
		BasicFileAttributes attr = Files.readAttributes(vidPath, BasicFileAttributes.class);
		FileTime creationTime = attr.creationTime();
		String creationTimeStr = creationTime.toString();
		String creationStrClipped = creationTimeStr.substring(0, creationTimeStr.length()-1);
		Date date = JSONParser.convertStringToDate(creationStrClipped);
		return date;
	}
}