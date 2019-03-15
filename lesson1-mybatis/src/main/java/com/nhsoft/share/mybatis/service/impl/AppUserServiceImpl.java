package com.nhsoft.share.mybatis.service.impl;


import com.nhsoft.share.mybatis.dao.AppUserDao;
import com.nhsoft.share.mybatis.model.AppUser;
import com.nhsoft.share.mybatis.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private AppUserDao appUserDao;

    @Override
    public AppUser save(AppUser model) {
        appUserDao.save(model);
        return model;
    }

    @Override
    public void update(AppUser model) {
        appUserDao.update(model);
        appUserDao.update(model);
    }

    @Override
    public void delete(Integer userNum) {
        appUserDao.delete(userNum);
    }

    @Override
    public List<AppUser> list() {
        appUserDao.list();
        return appUserDao.list();
    }
}
