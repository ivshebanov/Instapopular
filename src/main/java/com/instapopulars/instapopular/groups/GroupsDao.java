package com.instapopulars.instapopular.groups;

import com.instapopulars.instapopular.DAO.DriverDao;
import com.instapopulars.instapopular.DAO.InstagramDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GroupsDao {

    private final InstagramDao instagramDao;
    private final DriverDao driverDao;

    @Autowired
    public GroupsDao(InstagramDao instagramDao, DriverDao driverDao) {
        this.instagramDao = instagramDao;
        this.driverDao = driverDao;
    }
}
