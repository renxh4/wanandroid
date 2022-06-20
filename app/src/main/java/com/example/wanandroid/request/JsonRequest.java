package com.example.wanandroid.request;

import com.example.wanandroid.bean.WechatBean;
import com.example.wanandroid.net.BaseRequest;


public class JsonRequest extends BaseRequest<WechatBean> {
    public JsonRequest(String url) {
        super(url);
    }
}
