package kr.hs.kumoh.indieplatform.indie.navi.model.data;

public class ArtistData {
	private String artistName;
	private String artistDesc;
	private String labelName;
	private String debutYear;
	private String genreName;
	private int likeCnt;
	
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
	public int getLikeCnt() {
		return likeCnt;
	}
	public void setLikeCnt(int likeCnt) {
		this.likeCnt = likeCnt;
	}
}
