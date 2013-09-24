package kr.hs.kumoh.indieplatform.indie.navi.model.data;

public class ArtistData {
	public final static String IMAGE_URL = "http://chilchil.me/apps/server/indie/image";
	private String artistImgURL;
	private String artistName;
	private String artistDesc;
	private String labelName;
	private String debutYear;
	private String genreName;
	private String likeCnt;
	
	public ArtistData(String artistImgURL, String artistName, String labelName, String debutYear, String genreName, String likeCnt) {
		this.setArtistImgURL(artistImgURL);
		this.setArtistName(artistName);
		this.setLabelName(labelName);
		this.setDebutYear(debutYear);
		this.setGenreName(genreName);
		this.setLikeCnt(likeCnt);
	}
	
	public String getArtistImgURL() {
		return artistImgURL;
	}
	public void setArtistImgURL(String artistImgURL) {
		this.artistImgURL = artistImgURL;
	}
	public String getArtistName() {
		return artistName;
	}
	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}
	public String getArtistDesc() {
		return artistDesc;
	}
	public void setArtistDesc(String artistDesc) {
		this.artistDesc = artistDesc;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public String getDebutYear() {
		return debutYear;
	}
	public void setDebutYear(String debutYear) {
		this.debutYear = debutYear;
	}
	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}
	public String getGenreName() {
		return genreName;
	}
	public String getLikeCnt() {
		return likeCnt;
	}
	public void setLikeCnt(String likeCnt) {
		this.likeCnt = likeCnt;
	}
}
