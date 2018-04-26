import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
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

	public static ArrayList<Event> openDirectory(String folderPath) {
		// list files
		File directory = new File(folderPath);
		File[] contents = directory.listFiles();

		ArrayList<Event> idsAndTimestampsOfUserVideos = new ArrayList<Event>();

		for (File f : contents) {
			// upload the video
			UploadVideoPost(f.getName(), f, m_SystemType);
		}

		ArrayList<Event> allUploadedClips = GetUploadedClips(m_SystemType);

		for (Event e : allUploadedClips) {
			// release the time-stamps
			for (File f : contents) {
				if (f.getName().equals(e.name)) {
					Date videoCreationTime = releaseTimeStamps(f.getName());
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
		File videoFile = new File(videoFilePath);
		if (!videoFile.exists()) {
			throw new FileNotFoundException("File " + videoFilePath + " not exists");
		}

		if (!videoFile.canRead()) {
			throw new IllegalStateException("No read permissions to file " + videoFilePath);
		}

		IsoFile isoFile = new IsoFile(new FileInputStream(videoFilePath).getChannel());
		MovieBox moov = isoFile.getMovieBox();
		Date creationTime = moov.getMovieHeaderBox().getCreationTime();

		return creationTime;
	}

	public static void main(String[] args) {
		openDirectory("C:\\Users\\neshe\\Desktop\\test");
	}
}
