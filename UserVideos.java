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

	static String m_SystemType = "Eurobasket";

	public static ArrayList<Event> openDirectory(String folderPath) throws IOException, JSONException {
		//list files
		File directory = new File(folderPath);
		File[] contents = directory.listFiles();
		for(File file : contents) {
			System.out.println(file.getName());
		}
		
		ArrayList<Event> idsAndTimestampsOfUserVideos = new ArrayList<Event>();

		for (File f : contents) {
			if(f.getName().contains(".png") || f.getName().contains(".jpg")) f = convertImgToVideo();
			// upload the video
			//ApiRequests.UploadVideoPost(f.getName(), f, m_SystemType);
			//currently uploading videos from swagger
		}

		JSONArray uploadedClips = ApiRequests.UploadedClipsGet();
		ArrayList<Event> uploadedClipsEvents = JSONParser.videoListToEventsList(uploadedClips);
		
		for (Event e : uploadedClipsEvents) {
			//release the time-stamps
			for (File f : contents) {
				if (f.getName().equals(e.name + ".mp4")) {
					Date videoCreationTime = releaseTimeStamps(folderPath + "/" + f.getName());
					
					// request the id from the system according to the name.
					int id = e.ID;
					idsAndTimestampsOfUserVideos.add(new Event(id, 0, 0, videoCreationTime, e.name));
				}
			}
		}

		Collections.sort(idsAndTimestampsOfUserVideos);
		return idsAndTimestampsOfUserVideos;
	}
	
	private static File convertImgToVideo() throws IOException {
		String filePath = "output";
		File fileP = new File(filePath);
		String commands = "D:\\ffmpeg-win32-static\\bin\\ffmpeg -f image2 -i "
		        + fileP + "\\image%5d.png " + fileP + "\\video.mp4";
		System.out.println(commands);
		Runtime.getRuntime().exec(commands);
		return fileP; 
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