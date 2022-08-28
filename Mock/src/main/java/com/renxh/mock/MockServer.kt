package com.renxh.mock

import android.content.Context
import com.koushikdutta.async.AsyncServer
import com.koushikdutta.async.http.server.AsyncHttpServer
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException



object MockServer {
    private val server = AsyncHttpServer()
    private val mAsyncServer = AsyncServer()

    fun init(context: Context) {
        server.get("/") { request, response ->
            try {
                response.send(getIndexContent(context));
            } catch (e: IOException) {
                e.printStackTrace();
                response.code(500).end();
            }
        }

        server.listen(mAsyncServer, 5566)


        server.get("/mock") { request, response ->
            try {
                response.send(getApi(context));
            } catch (e: IOException) {
                e.printStackTrace();
                response.code(500).end();
            }
        }

        server.listen(mAsyncServer, 5566)
    }


    private fun getIndexContent(context: Context): String {
        val inputStream = context.resources.assets.open("pp.html")
        val sb = StringBuffer()
        inputStream.bufferedReader().forEachLine {
            sb.append(it)
        }
        return sb.toString()
    }

    private fun getApi(context: Context): String {
        var query = MockSdk.db?.query()
        var jsonArray = JSONArray()
        query?.apply {
            this.forEach {
                var jsonObject = JSONObject()
                jsonObject.put("url",it.url)
//                jsonObject.put("request",it.request)
                jsonObject.put("response",it.response)
                jsonArray.put(jsonObject)
            }
        }
        return jsonArray.toString()
    }

    fun release() {
        server.stop();
        mAsyncServer.stop();
    }


}