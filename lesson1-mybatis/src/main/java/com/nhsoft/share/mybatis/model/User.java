package com.nhsoft.share.mybatis.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {


    private static final long serialVersionUID = 522953737836221061L;
    private Integer userNum;
    private String userName;
    private String userCode;
    private Integer userSex;

}
