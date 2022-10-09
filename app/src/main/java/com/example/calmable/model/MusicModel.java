package com.example.calmable.model;

public class MusicModel {

    public String id;
    public String songName;
    public String imageView;
    public String url;
    private String isFav;
    private String sugIndex;

    public MusicModel() {
    }

    public MusicModel(String id, String songName, String url, String imageView) {
        this.id = id;
        this.songName = songName;
        this.url = url;
        this.imageView = imageView;
    }

//    public MusicModel(String id, String songName, String url, String imageView, String isFav) {
//        this.id = id;
//        this.songName = songName;
//        this.url = url;
//        this.imageView = imageView;
//        this.isFav = isFav;
//    }


    public MusicModel(String id, String songName, String imageView, String url, String isFav, String calmingIndex) {
        this.id = id;
        this.songName = songName;
        this.imageView = imageView;
        this.url = url;
        this.isFav = isFav;
        this.sugIndex = calmingIndex;
    }


    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageView() {
        return imageView;
    }

    public void setImageView(String imageView) {
        this.imageView = imageView;
    }

    public String getIsFav() {
        return isFav;
    }

    public void setIsFav(String isFav) {
        this.isFav = isFav;
    }

    public String getSugIndex() {
        return sugIndex;
    }

    public void setSugIndex(String sugIndex) {
        this.sugIndex = sugIndex;
    }

    @Override
    public String toString() {
        return "MusicModel{" +
                "id='" + id + '\'' +
                ", songName='" + songName + '\'' +
                ", imageView=" + imageView +
                ", url='" + url + '\'' +
                ", isFav='" + isFav + '\'' +
                '}';
    }
}
