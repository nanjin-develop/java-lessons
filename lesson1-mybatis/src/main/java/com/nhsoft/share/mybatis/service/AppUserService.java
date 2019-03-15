package com.nhsoft.share.mybatis.service;

import com.nhsoft.share.mybatis.model.AppUser;

import java.util.List;

public interface AppUserService {

    /**
     * 保存
     * @param model
     */
    AppUser save(AppUser model);

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
