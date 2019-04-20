package com.instapopulars.instapopular.repository.impl;

import com.instapopulars.instapopular.repository.InstapopularDAO;
import com.instapopulars.instapopular.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Map;

@Repository
public class InstapopularDAOImpl implements InstapopularDAO {

    private final UserRepository userRepository;

    public InstapopularDAOImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Map<String, Integer> getHestags() throws IOException {
        return null;
    }

    @Override
    public Map<String, Integer> getGroups() throws IOException {
        return null;
    }

    @Override
    public Map<String, Integer> getDoNotUnsubscribe() throws IOException {
        return null;
    }

    @Override
    public Map<String, Integer> getMyPhoto() throws IOException {
        return null;
    }

    @Override
    public Map<String, Integer> getPhotoAnalysisResults() throws IOException {
        return null;
    }

    @Override
    public void addHestag(String hastag) throws IOException {

    }

    @Override
    public void addGroup(String group) throws IOException {

    }

    @Override
    public void addDoNotUnsubscribe(String userName, String countLike) throws IOException {

    }

    @Override
    public void addMyPhoto(String key, String value) throws IOException {

    }

    @Override
    public void addMyPhotos(Map<String, Integer> photos) throws IOException {

    }

    @Override
    public void addPhotoAnalysisResults(Map<String, Integer> photos) throws IOException {

    }

    @Override
    public void removeHestag(String hastag) throws IOException {

    }

    @Override
    public void removeGroup(String group) throws IOException {

    }

    @Override
    public void removeDoNotUnsubscribe(String userName) throws IOException {

    }

    @Override
    public void removeMyPhoto(String photo) throws IOException {

    }
}
