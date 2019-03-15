package com.nhsoft.share.mybatis.controller;

import com.nhsoft.share.mybatis.dto.AppUserDTO;
import com.nhsoft.share.mybatis.dto.BaseResponse;
import com.nhsoft.share.mybatis.model.AppUser;
import com.nhsoft.share.mybatis.service.AppUserService;
import com.nhsoft.share.mybatis.util.CopyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/appUser")
@Slf4j
public class AppUserController {


    @Autowired
    private AppUserService appUserService;

    @PostMapping("save")
    @ResponseBody
    BaseResponse save(@RequestBody AppUserDTO dto){

        try {
            appUserService.save( CopyUtil.to(dto, AppUser.class));
            return new BaseResponse(BaseResponse.SUCCESS);

        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new BaseResponse(BaseResponse.ERROR);

        }

    }

    @PostMapping("update")
    @ResponseBody
    BaseResponse update(@RequestBody AppUserDTO dto){

        try {
            appUserService.update( CopyUtil.to(dto, AppUser.class));
            return new BaseResponse(BaseResponse.SUCCESS);

        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new BaseResponse(BaseResponse.ERROR);

        }

    }

    @GetMapping("list")
    @ResponseBody
    BaseResponse list(){

        try {
            List<AppUser> appUserList = appUserService.list();
            return new BaseResponse(BaseResponse.SUCCESS,  CopyUtil.toList(appUserList, AppUserDTO.class));

        }catch (Exception e){
            log.error(e.getMessage(), e);
            return new BaseResponse(BaseResponse.ERROR);

        }

    }
}
