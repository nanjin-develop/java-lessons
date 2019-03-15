package com.nhsoft.share.mybatis.dao;

import com.nhsoft.share.mybatis.model.AppUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface AppUserDao {

    /**
     * 保存
     * @param model
     */
    void save(AppUser model);

    /**
     * 修改
     * @param model
     */
    void update(AppUser model);

    /**
     * 删除
     * @param userNum
     */
    void delete(Integer userNum);

    /**
     * 查询
     * @return
     */
    List<AppUser> list();
}
