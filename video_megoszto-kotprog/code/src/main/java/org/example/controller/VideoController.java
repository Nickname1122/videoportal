package org.example.controller;

import org.example.dao.VideoDao;
import org.example.dao.VideoDaoImpl;
import org.example.model.Video;

import java.util.List;

public class VideoController {

    private VideoDao videoDao = new VideoDaoImpl();
    private static VideoController single_instance = null;

    public VideoController() {
    }

    public static VideoController getInstance() {
        if (single_instance == null) {
            single_instance = new VideoController();
        }

        return single_instance;

    }

    public List<Video> getVideo() {
        return videoDao.getVideo();
    }

    public int getIdFromTitle(String title) {
        return videoDao.getIdFromTitle(title);
    }

    public List<Video> getSearchVideo(String key) {
        return videoDao.getSearchVideo(key);
    }

    public List<Video> allVideo() {
        return videoDao.allVideo();
    }

    public boolean banVideo(int videoID) {
        return videoDao.banVideo(videoID);
    }

    public boolean deleteVideo(int videoID) {
        return videoDao.deleteVideo(videoID);
    }

    public boolean unbanVideo(int videoID) {
        return videoDao.unbanVideo(videoID);
    }

    public boolean addVideo(Video video) {
        return videoDao.addVideo(video);
    }

    public int getLikes(int videoID) {
        return videoDao.getLikes(videoID);
    }


    public void updateLikes(int videoID, int parseInt) {
        videoDao.updateLikes(videoID, parseInt);
    }

    public List<Video> getUserVideo(String username) {
        return videoDao.getUserVideo(username);
    }

    public boolean updateViewCount(int videoID, int viewCount) {
        return videoDao.updateViewCount(videoID, viewCount);
    }

    /*public String getPath(int videoID){
        return videoDao.getPath(videoID);
    }*/
}