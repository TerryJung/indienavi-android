package kr.hs.kumoh.indieplatform.indie.navi.model.data;

public class ConcertData {
	private String concertName;
	private String placeName;
	private String concertDate;
	private String concertTime;
	private String concertImgURL;
	
	public ConcertData(String concertName, String placeName, String concertDate,
								 String concertImgURL) {
		// TODO Auto-generated constructor stub
		this.setConcertName(concertName);
		this.setPlaceName(placeName);
		this.setConcertDate(concertDate);
		this.setConcertImgURL(concertImgURL);
		
	}
	
	public String getConcertName() {
		return concertName;
	}
	public void setConcertName(String concertName) {
		this.concertName = concertName;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public String getConcertDate() {
		return concertDate;
	}
	public void setConcertDate(String concertDate) {
		this.concertDate = concertDate;
	}
	public String getConcertImgURL() {
		return concertImgURL;
	}
	public void setConcertImgURL(String concertImgURL) {
		this.concertImgURL = concertImgURL;
	}
}
