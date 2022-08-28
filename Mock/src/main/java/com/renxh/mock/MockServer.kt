package com.renxh.mock

import android.content.Context
import android.util.Log
import com.koushikdutta.async.AsyncServer
import com.koushikdutta.async.http.body.AsyncHttpRequestBody
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

        server.get("/mockopen") { request, response ->
            try {
                var open = request.get("open")
                Log.d("mockopen", open)
                var url = request.get("url")
                Log.d("mockopen", url)
                MockSdk.db?.updataMockOpen(url,open)
                response.code(200).send("success");
            } catch (e: IOException) {
                e.printStackTrace();
                response.code(500).end();
            }
        }

        server.get("/mockresponse") { request, response ->
            try {
                var json = request.get("json")
                Log.d("mockopen", json)
                var url = request.get("url")
                Log.d("mockopen", url)
                MockSdk.db?.updataMockResponse(url,json)
                response.code(200).send("success");
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
                jsonObject.put("mockopen",it.mockopen?:"0")
                jsonObject.put("mockresponse",it.mockresponse?:"")

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