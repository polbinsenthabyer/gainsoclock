package com.gainsoclock.backend.utilities;

import com.gainsoclock.backend.dtos.ResponseDto;
import com.gainsoclock.backend.types.ResponseType;

import java.util.Date;

public class ResponseUtility {

    public static ResponseDto getSuccessResponse(String message){
        ResponseDto res = new ResponseDto();
        res.setResponseType(ResponseType.SUCCESS);
        res.setCode("S0001");
        res.setClassName(getClassName());
        res.setMethodName(getMethodName());
        res.setMessage(message);
        res.setData(null);
        res.setResponseTime(new Date());
        return res;
    }

    public static ResponseDto getSuccessResponse(String message, Object data){
        ResponseDto res = new ResponseDto();
        res.setResponseType(ResponseType.SUCCESS);
        res.setCode("S0001");
        res.setClassName(getClassName());
        res.setMethodName(getMethodName());
        res.setMessage(message);
        res.setData(data);
        res.setResponseTime(new Date());
        return res;
    }

    public static ResponseDto getErrorResponse(String code, String message){
        ResponseDto res = new ResponseDto();
        res.setResponseType(ResponseType.ERROR);
        res.setCode(code);
        res.setClassName(getClassName());
        res.setMethodName(getMethodName());
        res.setMessage(message);
        res.setData(null);
        res.setResponseTime(new Date());
        return res;
    }

    public static ResponseDto getErrorResponse(String code, String message, Object data){
        ResponseDto res = new ResponseDto();
        res.setResponseType(ResponseType.ERROR);
        res.setCode(code);
        res.setClassName(getClassName());
        res.setMethodName(getMethodName());
        res.setMessage(message);
        res.setData(data);
        res.setResponseTime(new Date());
        return res;
    }


    public static String getMethodName() {
        return Thread.currentThread().getStackTrace()[3].getMethodName();
    }

    public static String getClassName() {
        String fullClassName = Thread.currentThread().getStackTrace()[3].getClassName();
        return fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
    }
}
