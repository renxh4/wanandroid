package com.example.wanandroid.bean;

import com.example.wanandroid.bean.ArticleBean;

import java.util.List;

public class ArticleTopBean {

    private List<ArticleBean.DataDTO.DatasDTO> data;
    private int errorCode;
    private String errorMsg;

    public List<ArticleBean.DataDTO.DatasDTO> getData() {
        return data;
    }

    public void setData(List<ArticleBean.DataDTO.DatasDTO> data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }


}
