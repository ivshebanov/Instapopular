package ru.instapopular.repository.impl;

import ru.instapopular.model.MyGroup;
import ru.instapopular.model.Hashtag;
import ru.instapopular.model.Like;
import ru.instapopular.model.Photo;
import ru.instapopular.repository.InstapopularDAO;
import ru.instapopular.repository.MyGroupRepository;
import ru.instapopular.repository.HashtagRepository;
import ru.instapopular.repository.LikeRepository;
import ru.instapopular.repository.PhotoRepository;
import ru.instapopular.repository.UsrRepository;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Repository
public class InstapopularDAOImpl implements InstapopularDAO {

    private final UsrRepository usrRepository;
    private final MyGroupRepository myGroupRepository;
    private final HashtagRepository hashtagRepository;
    private final LikeRepository likeRepository;
    private final PhotoRepository photoRepository;

    public InstapopularDAOImpl(UsrRepository usrRepository, MyGroupRepository myGroupRepository, HashtagRepository hashtagRepository, LikeRepository likeRepository, PhotoRepository photoRepository) {
        this.usrRepository = usrRepository;
        this.myGroupRepository = myGroupRepository;
        this.hashtagRepository = hashtagRepository;
        this.likeRepository = likeRepository;
        this.photoRepository = photoRepository;
    }

    @Override
    public Map<String, Integer> getHestags() {
        HashMap<String, Integer> resultMap = new HashMap<>();
        Iterable<Hashtag> myHashtagRepositoryAll = hashtagRepository.findAll();
        for (Hashtag hashtag : myHashtagRepositoryAll) {
            resultMap.put(hashtag.getHashtag(), hashtag.getStatus());
        }
        return resultMap;
    }

    @Override
    public Map<String, Integer> getGroups() {
        HashMap<String, Integer> resultMap = new HashMap<>();
        Iterable<MyGroup> myGroupRepositoryAll = myGroupRepository.findAll();
        for (MyGroup group : myGroupRepositoryAll) {
            int active = 0;
            if (group.isActive()) active = 1;
            resultMap.put(group.getMyGroup(), active);
        }
        return resultMap;
    }

    @Override
    public Map<String, Integer> getDoNotUnsubscribe() throws IOException {
        HashMap<String, Integer> resultMap = new HashMap<>();
        Iterable<Like> myLikeRepositoryAll = likeRepository.findAll();
        for (Like like : myLikeRepositoryAll){

        }
        return resultMap;
    }

    @Override
    public Map<String, Integer> getMyPhoto() {
        HashMap<String, Integer> resultMap = new HashMap<>();
        Iterable<Photo> myPhotoRepositoryAll = photoRepository.findAll();
        for (Photo photo : myPhotoRepositoryAll) {
            int active = 0;
            if (photo.isActive()) active = 1;
            resultMap.put(photo.getPhoto(), active);
        }
        return resultMap;
    }

    @Override
    public Map<String, Integer> getPhotoAnalysisResults() {
        HashMap<String, Integer> resultMap = new HashMap<>();
        Iterable<Like> myLikeRepositoryAll = likeRepository.findAll();
        for (Like like : myLikeRepositoryAll) {
            resultMap.put(like.getGuys(), like.getCountLike());
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
