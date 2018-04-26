import java.util.Date;

public class Event implements Comparable<Event>{
	public int ID;
	public int quarter;
	public int rating;
	public Date timestamp;
	public String name;
	
	public Event(int ID, int quarter, int rating, Date timestamp, String name) {
		this.ID = ID;
		this.quarter = quarter;
		this.rating = rating;
		this.timestamp = timestamp; 
		this.name = name;
	}	
	
	
	
	@Override
    public int compareTo(Event event) {
        
        return this.timestamp.compareTo(event.timestamp);
    }
	
	
}