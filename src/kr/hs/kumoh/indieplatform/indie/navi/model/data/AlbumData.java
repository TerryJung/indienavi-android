package kr.hs.kumoh.indieplatform.indie.navi.model.data;

public class AlbumData {
	private String albumCoverURL;
	private String albumTitle;
	private String albumYear;
	private String albumTitleSong;
	
	public AlbumData(String albumCoverURL, String albumTitle, String albumYear, String albumTitleSong) {
		// TODO Auto-generated constructor stub
		this.setAlbumCoverURL(albumCoverURL);
		this.setAlbumTitle(albumTitle);
		this.setAlbumYear(albumYear);
		this.setAlbumTitleSong(albumTitleSong);
		
	}
	public String getAlbumCoverURL() {
		return albumCoverURL;
	}
	public void setAlbumCoverURL(String albumCoverURL) {
		this.albumCoverURL = albumCoverURL;
	}
	public String getAlbumTitle() {
		return albumTitle;
	}
	public void setAlbumTitle(String albumTitle) {
		this.albumTitle = albumTitle;
	}
	public String getAlbumYear() {
		return albumYear;
	}
	public void setAlbumYear(String albumYear) {
		this.albumYear = albumYear;
	}
	public String getAlbumTitleSong() {
		return albumTitleSong;
	}
	public void setAlbumTitleSong(String albumTitleSong) {
		this.albumTitleSong = albumTitleSong;
	}
}
