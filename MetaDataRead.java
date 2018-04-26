import org.mp4parser.IsoFile;
import org.mp4parser.boxes.iso14496.part12.MovieBox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

public class MetaDataRead {

    public static void main(String[] args) throws IOException {

        read("C:\\Users\\AsafGetz\\Documents\\Desktop\\myVid.mp4");
    }


    public static void read(String videoFilePath) throws IOException {

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
            System.out.println(creationTime.toString());
    }
}
