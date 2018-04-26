import java.sql.Date;

public class Event {
	public int ID;
	public int quarter;
	public int rating;
	public Date timestamp;
	
	public Event(int ID, int quarter, int rating, Date timestamp) {
		this.ID = ID;
		this.quarter = quarter;
		this.rating = rating;
		this.timestamp = timestamp; 
	}
}