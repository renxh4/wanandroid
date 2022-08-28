package com.renxh.mock

import com.renxh.mock.db.Api
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import java.nio.charset.Charset

class RXMockInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestLog = generateRequestLog(request)
        val response = chain.proceed(request)
        val responseLog = generateResponseLog(response)
        LogUtils.w(requestLog.plus(responseLog))
        val mediaType = response.body()!!.contentType()
        var queryItem = MockSdk.db?.queryItem(request.url().toString())
        if (queryItem != null && queryItem.size > 0) {
            var api = queryItem.get(0)
            if (api.mockopen.equals("1")) {
                return response.newBuilder()
                    .body(ResponseBody.create(mediaType, api.mockresponse))
                    .build()
            } else {
                return response
            }
        } else {
            MockSdk.db?.inster(Api().apply {
                this.url = request.url().toString()
                this.request = requestLog
                this.response = responseLog
                this.mockopen = "0"
            })
        }

        return response

    }

    private fun generateRequestLog(request: Request): String {
        val stringBuffer = StringBuffer()
        val url = request.url()
        stringBuffer.append("url = ").append(url).append("\r\n")
        val method = request.method()
        stringBuffer.append("method = ").append(method).append("\r\n")
        val headers = request.headers()
        stringBuffer.append("Headers = ").append(headers.toString()).append("\r\n")
        request.body()?.apply {
            val buffer = Buffer()
            writeTo(buffer)
            val charset = contentType()?.charset(Charset.forName("UTF-8"))
                ?: Charset.forName("UTF-8")
            val str = buffer.readString(charset)
            stringBuffer.append("body = ").append(JsonUtil.formatJson(str)).append("\r\n")
        }
        return stringBuffer.toString()
    }


    private fun generateResponseLog(response: Response): String {
        val stringBuffer = StringBuffer()
        val code = response.code()
        stringBuffer.append("respose code  = ").append(code).append("\r\n")
        val headers = response.headers()
        stringBuffer.append("respose headers  = ").append(headers.toString()).append("\r\n")


        response.body()?.apply {
            val source = source()
            source.request(Long.MAX_VALUE)
            val buffer = source.buffer
            val charset = contentType()?.charset(Charset.forName("UTF-8"))
                ?: Charset.forName("UTF-8")
            if (contentLength().toInt() != 0) {
                buffer.clone().readString(charset).let { result ->
                    stringBuffer.append("respose body  = ").append(JsonUtil.formatJson(result))
                        .append("\r\n")
                }
            }
        }
        return stringBuffer.toString()
    }

}