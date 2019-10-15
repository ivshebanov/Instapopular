package ru.instapopular.repository;

import java.io.IOException;
import java.util.Map;

public interface InstapopularDAO {

    Map<String, Integer> getHestags() throws IOException;

    Map<String, Integer> getGroups() throws IOException;

    Map<String, Integer> getDoNotUnsubscribe() throws IOException;

    Map<String, Integer> getMyPhoto() throws IOException;

    Map<String, Integer> getPhotoAnalysisResults() throws IOException;

    void addHestag(String hastag) throws IOException;

    void addGroup(String group) throws IOException;

    void addDoNotUnsubscribe(String userName, String countLike) throws IOException;

    void addMyPhoto(String key, String value) throws IOException;

    void addMyPhotos(Map<String, Integer> photos) throws IOException;

    void addPhotoAnalysisResults(Map<String, Integer> photos) throws IOException;

    void removeHestag(String hastag) throws IOException;

    void removeGroup(String group) throws IOException;

    void removeDoNotUnsubscribe(String userName) throws IOException;

    void removeMyPhoto(String photo) throws IOException;
}
