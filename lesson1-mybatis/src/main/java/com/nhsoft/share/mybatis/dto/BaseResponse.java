package com.nhsoft.share.mybatis.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse implements Serializable {

    private Integer code;
    private String message;

    public BaseResponse(String reponse){
        String arr[] = reponse.split(",");
        code = Integer.parseInt(arr[0]);
        message = arr[1];
    }

    public static final String SUCCESS = "0,success";
    public static final String ERROR = "-1,error";
}
