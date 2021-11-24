package com.example.ghumatarti.ModelClass;

public class FavouritesSongModel {

    String loggedemail;
    String songId;
    String songName;
    String songUrl;
    String imageUrl;
    String songArtist;
    String songDuration;

    public FavouritesSongModel() {
    }

    public FavouritesSongModel(String loggedemail, String songId, String songName, String songUrl, String imageUrl, String songArtist, String songDuration) {
        this.loggedemail = loggedemail;
        this.songId = songId;
        this.songName = songName;
        this.songUrl = songUrl;
        this.imageUrl = imageUrl;
        this.songArtist = songArtist;
        this.songDuration = songDuration;
    }

    public String getLoggedemail() {
        return loggedemail;
    }

    public void setLoggedemail(String loggedemail) {
        this.loggedemail = loggedemail;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public void setSongArtist(String songArtist) {
        this.songArtist = songArtist;
    }

    public String getSongDuration() {
        return songDuration;
    }

    public void setSongDuration(String songDuration) {
        this.songDuration = songDuration;
    }
}
