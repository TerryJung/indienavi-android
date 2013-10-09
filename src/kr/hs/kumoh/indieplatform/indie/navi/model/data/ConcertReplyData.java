package kr.hs.kumoh.indieplatform.indie.navi.model.data;

public class ConcertReplyData {

	private String userName;
	private String replyText;
	private String replyDate;
	
	public ConcertReplyData(String userName, String replyText, String replyDate) {
		// TODO Auto-generated constructor stub
		this.setUserName(userName);
		this.setReplyText(replyText);
		this.setReplyDate(replyDate);
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getReplyText() {
		return replyText;
	}
	public void setReplyText(String replyText) {
		this.replyText = replyText;
	}
	public String getReplyDate() {
		return replyDate;
	}
	public void setReplyDate(String replyDate) {
		this.replyDate = replyDate;
	}
}
