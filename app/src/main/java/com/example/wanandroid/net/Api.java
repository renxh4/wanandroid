package com.example.wanandroid.net;

public interface Api {

    String BASE = "https://www.wanandroid.com";

    String getArticle = BASE + "/article/list/0/json";
    String getBanner = BASE + "/banner/json";

    String getTop = BASE + "/article/top/json";

    String getQuestion = BASE + "/wenda/list/%s/json";


}
