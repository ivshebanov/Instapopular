package com.instapopulars.instapopular.DAO;

import java.io.IOException;
import java.util.Map;

public interface IntapopularDAO {
    Map<String, Integer> getHestagFromProperties() throws IOException;
    Map<String, Integer> getGroupsFromProperties() throws IOException;
    Map<String, Integer> getDoNotUnsubscribe() throws IOException;
    Map<String, Integer> getMyPhoto() throws IOException;
    Map<String, Integer> getPhotoAnalysisResults() throws IOException;
    void addHestagInProperties(String hastag) throws IOException;
    void addGroupsInProperties(String group) throws IOException;
    void addDoNotUnsubscribe(String userName, String countLike) throws IOException;
    void addMyPhoto(String key, String value) throws IOException;
    void addMyPhotos(Map<String, Integer> photos) throws IOException;
    void addPhotoAnalysisResults(Map<String, Integer> photos) throws IOException;
    void removeHestagFromProperties(String hastag) throws IOException;
    void removeGroupsFromProperties(String group) throws IOException;
    void removeDoNotUnsubscribe(String userName) throws IOException;
    void removeMyPhoto(String photo) throws IOException;
}
