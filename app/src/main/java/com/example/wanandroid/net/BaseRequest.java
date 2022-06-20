package com.example.wanandroid.net;
import android.os.Build;
import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class BaseRequest<T> {

    private final String mUrl;
    private Type params;

    public BaseRequest(String url) {
        this.mUrl = url;
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            params = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
        }
    }

    public void request(CallBack<T> t) {
        OkhttpManager.INSTANCE.get(mUrl, new OkhttpManager.CallBack() {
            @Override
            public void success(String json) {
                if (params != null) {
                    T bean = new Gson().fromJson(json, params);
                    t.success(bean);
                }
            }

            @Override
            public void fail(String msg) {

            }
        });
    }

    public interface CallBack<T> {
        void success(T t);
    }


    //把泛型T生成对象并返回
    public T getType() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType){
            Type params = ((ParameterizedType) genericSuperclass).getActualTypeArguments()[0];
            try {
                String typeName;
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                    typeName = params.getTypeName();
                } else {
                    String s = params.toString();
                    int i = (int) s.indexOf(" ");
                    typeName = params.toString().substring(i + 1, s.length());
                }
                Class<?> aClass = Class.forName(typeName);
                Constructor<?> constructor = aClass.getConstructor();
                Object o = constructor.newInstance();
                String s = params.toString();
                int i = (int) s.indexOf(" ");

                Log.d("mmm3", params.toString().substring(i + 1, s.length()));
                Log.d("mmm1", params.toString());
                Log.d("mmm2", params.getTypeName());
                return (T) o;
            } catch (ClassNotFoundException | NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
