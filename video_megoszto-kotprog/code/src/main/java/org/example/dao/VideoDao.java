package org.example.dao;

import org.example.model.Video;

import java.util.List;

public interface VideoDao {

    List<Video> getVideo();

    List<Video> getUserVideo(String username);

    List<Video> getSearchVideo(String Key);

    List<Video> allVideo();

    int getIdFromTitle(String title);

    boolean banVideo(int videoID);

    boolean deleteVideo(int videoID);

    boolean unbanVideo(int videoID);

    boolean addVideo(Video video);

    boolean updateLikes(int videoID, int kedvelesekSzama);

    int getLikes(int videoID);

    boolean updateViewCount(int videoID, int viewCount);

    // String getPath(int videoID);

}
