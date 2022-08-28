package com.renxh.mock.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.renxh.mock.MockSdk;

import java.util.ArrayList;

public class DbUtils {
    private final SQLiteDatabase sqliteDatabase;
    public String urlKey = "url";
    public String requestKey = "request";
    public String responseKey = "response";
    public String tableName = "api";
    public String mockresponse = "mockresponse";
    //0 关 1 开
    public String openmock = "openmock";

    // 步骤1：创建DatabaseHelper对象
    // 注：此时还未创建数据库

    public DbUtils(Context context) {
        SQLiteOpenHelper dbHelper = new DatabaseHelper(MockSdk.INSTANCE.getApplication(), "test_carson");
        // 步骤2：真正创建 / 打开数据库
        // 创建 or 打开 可读/写的数据库
        sqliteDatabase = dbHelper.getWritableDatabase();
    }


    public void inster(Api api) {
        // 使用sql语句插入
        // a. 创建ContentValues对象
        ContentValues values = new ContentValues();

        // b. 向该对象中插入键值对
        values.put(urlKey, api.url);
        values.put(requestKey, api.request);
        values.put(responseKey, api.response);
        values.put(mockresponse, api.mockresponse);
        values.put(openmock, api.mockopen);
        //其中，key = 列名，value = 插入的值
        //注：ContentValues内部实现 = HashMap，区别在于：ContenValues Key只能是String类型，Value可存储基本类型数据 & String类型

        // c. 插入数据到数据库当中：insert()
        sqliteDatabase.insert(tableName, null, values);
        // 参数1：要操作的表名称
        // 参数2：SQl不允许一个空列，若ContentValues是空，那么这一列被明确的指明为NULL值
        // 参数3：ContentValues对象

    }

    public void updata(Api api) {
        /**
         *  操作2：修改数据 = update（）
         */
        // a. 创建一个ContentValues对象
        ContentValues values = new ContentValues();
        values.put(urlKey, api.url);
        values.put(requestKey, api.request);
        values.put(responseKey, api.response);

        // b. 调用update方法修改数据库：将id=1 修改成 name = zhangsan
        sqliteDatabase.update(tableName, values, "url=?", new String[]{api.url});
        // 参数1：表名(String)
        // 参数2：需修改的ContentValues对象
        // 参数3：WHERE表达式（String），需数据更新的行； 若该参数为 null, 就会修改所有行；？号是占位符
        // 参数4：WHERE选择语句的参数(String[]), 逐个替换 WHERE表达式中 的“？”占位符;

        // 注：调用完upgrate（）后，则会回调 数据库子类的onUpgrade()
    }

    public void updataMockOpen(String url,String open) {
        /**
         *  操作2：修改数据 = update（）
         */
        // a. 创建一个ContentValues对象
        ContentValues values = new ContentValues();
        values.put(openmock, open);

        // b. 调用update方法修改数据库：将id=1 修改成 name = zhangsan
        sqliteDatabase.update(tableName, values, "url=?", new String[]{url});
        // 参数1：表名(String)
        // 参数2：需修改的ContentValues对象
        // 参数3：WHERE表达式（String），需数据更新的行； 若该参数为 null, 就会修改所有行；？号是占位符
        // 参数4：WHERE选择语句的参数(String[]), 逐个替换 WHERE表达式中 的“？”占位符;

        // 注：调用完upgrate（）后，则会回调 数据库子类的onUpgrade()
    }

    public void updataMockResponse(String url,String mockResponse) {
        /**
         *  操作2：修改数据 = update（）
         */
        // a. 创建一个ContentValues对象
        ContentValues values = new ContentValues();
        values.put(mockresponse, mockResponse);

        // b. 调用update方法修改数据库：将id=1 修改成 name = zhangsan
        sqliteDatabase.update(tableName, values, "url=?", new String[]{url});
        // 参数1：表名(String)
        // 参数2：需修改的ContentValues对象
        // 参数3：WHERE表达式（String），需数据更新的行； 若该参数为 null, 就会修改所有行；？号是占位符
        // 参数4：WHERE选择语句的参数(String[]), 逐个替换 WHERE表达式中 的“？”占位符;

        // 注：调用完upgrate（）后，则会回调 数据库子类的onUpgrade()
    }

    public void delete(String url){
        /**
         *  操作3：删除数据 = delete()
         */
        // 删除 id = 1的数据
        sqliteDatabase.delete(tableName, "url=?", new String[]{url});
        // 参数1：表名(String)
        // 参数2：WHERE表达式（String），需删除数据的行； 若该参数为 null, 就会删除所有行；？号是占位符
        // 参数3：WHERE选择语句的参数(String[]), 逐个替换 WHERE表达式中 的“？”占位符;
    }

    public boolean haveItem(String url) {
        Cursor cursor = sqliteDatabase.query(tableName, new String[]{urlKey, requestKey, responseKey},
                "url=?", new String[]{url}, null, null, null);
        ArrayList<Api> apis = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Api api = new Api();
            api.url = cursor.getString(0);
            api.request = cursor.getString(1);
            api.response = cursor.getString(2);
            // do something useful with these
            cursor.moveToNext();
            apis.add(api);
        }
        cursor.close();

        return apis.size() != 0;
    }

    public ArrayList<Api> queryItem(String url) {
        Cursor cursor = sqliteDatabase.query(tableName, new String[]{urlKey, mockresponse,openmock },
                "url=?", new String[]{url}, null, null, null);
        ArrayList<Api> apis = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Api api = new Api();
            api.url = cursor.getString(0);
            api.mockresponse = cursor.getString(1);
            api.mockopen = cursor.getString(2);
            // do something useful with these
            cursor.moveToNext();
            apis.add(api);
        }
        cursor.close();

        return apis;
    }

    public ArrayList<Api> query() {
        Cursor cursor = sqliteDatabase.query(tableName, new String[]{urlKey, requestKey, responseKey,mockresponse,openmock},
                null, null, null, null, null);

        ArrayList<Api> apis = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Api api = new Api();
            api.url = cursor.getString(0);
            api.request = cursor.getString(1);
            api.response = cursor.getString(2);
            api.mockresponse = cursor.getString(3);
            api.mockopen = cursor.getString(4);

            // do something useful with these
            cursor.moveToNext();
            apis.add(api);
        }
        cursor.close();

        return apis;

    }


}
