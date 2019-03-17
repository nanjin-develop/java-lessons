package com.nhsoft.share.mybatis.service.impl;


import com.nhsoft.share.mybatis.dao.AppUserDao;
import com.nhsoft.share.mybatis.model.AppUser;
import com.nhsoft.share.mybatis.service.AppUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
public class AppUserServiceImpl implements AppUserService {

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

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

        SqlSession sqlSession1 = sqlSessionFactory.openSession(true);
        AppUserDao appUserDao1 = sqlSession1.getMapper(AppUserDao.class);

        log.info("我开启啦session1************************\r\n");


        appUserDao1.list();
//        sqlSession1.commit();

        log.info("第一次查询************************\r\n" );

        SqlSession sqlSession2 = sqlSessionFactory.openSession(true);
        AppUserDao appUserDao2 = sqlSession2.getMapper(AppUserDao.class);

        log.info("我开启啦session2************************\r\n");

        List<AppUser> list = appUserDao2.list();

        log.info("第二次查询************************\r\n");
        sqlSession2.commit();

        return list;
    }
}
