package ru.instapopular.repository.impl;

import ru.instapopular.model.MyGroup;
import ru.instapopular.model.MyHashtag;
import ru.instapopular.model.MyLike;
import ru.instapopular.model.MyPhoto;
import ru.instapopular.repository.InstapopularDAO;
import ru.instapopular.repository.MyGroupRepository;
import ru.instapopular.repository.MyHashtagRepository;
import ru.instapopular.repository.MyLikeRepository;
import ru.instapopular.repository.MyPhotoRepository;
import ru.instapopular.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class InstapopularDAOImpl implements InstapopularDAO {

    private final UserRepository userRepository;
    private final MyGroupRepository myGroupRepository;
    private final MyHashtagRepository myHashtagRepository;
    private final MyLikeRepository myLikeRepository;
    private final MyPhotoRepository myPhotoRepository;

    public InstapopularDAOImpl(UserRepository userRepository, MyGroupRepository myGroupRepository, MyHashtagRepository myHashtagRepository, MyLikeRepository myLikeRepository, MyPhotoRepository myPhotoRepository) {
        this.userRepository = userRepository;
        this.myGroupRepository = myGroupRepository;
        this.myHashtagRepository = myHashtagRepository;
        this.myLikeRepository = myLikeRepository;
        this.myPhotoRepository = myPhotoRepository;
    }

    @Override
    public Map<String, Integer> getHestags() {
        HashMap<String, Integer> resultMap = new HashMap<>();
        Iterable<MyHashtag> myHashtagRepositoryAll = myHashtagRepository.findAll();
        for (MyHashtag hashtag : myHashtagRepositoryAll) {
            resultMap.put(hashtag.getHashtag(), hashtag.getStatus());
        }
        return resultMap;
    }

    @Override
    public Map<String, Integer> getGroups() {
        HashMap<String, Integer> resultMap = new HashMap<>();
        Iterable<MyGroup> myGroupRepositoryAll = myGroupRepository.findAll();
        for (MyGroup group : myGroupRepositoryAll) {
            resultMap.put(group.getGroup(), group.getStatus());
        }
        return resultMap;
    }

    @Override
    public Map<String, Integer> getDoNotUnsubscribe() throws IOException {
        HashMap<String, Integer> resultMap = new HashMap<>();
        Iterable<MyLike> myLikeRepositoryAll = myLikeRepository.findAll();
        for (MyLike like : myLikeRepositoryAll){

        }
        return resultMap;
    }

    @Override
    public Map<String, Integer> getMyPhoto() {
        HashMap<String, Integer> resultMap = new HashMap<>();
        Iterable<MyPhoto> myPhotoRepositoryAll = myPhotoRepository.findAll();
        for (MyPhoto photo : myPhotoRepositoryAll) {
            resultMap.put(photo.getPhoto(), photo.getStatus());
        }
        return resultMap;
    }

    @Override
    public Map<String, Integer> getPhotoAnalysisResults() {
        HashMap<String, Integer> resultMap = new HashMap<>();
        Iterable<MyLike> myLikeRepositoryAll = myLikeRepository.findAll();
        for (MyLike like : myLikeRepositoryAll) {
            resultMap.put(like.getUser(), like.getCount());
        }
        return resultMap;
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
