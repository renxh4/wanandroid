package com.renxh.mock

import android.util.Log

object LogUtils {
    fun w(msg : String){
        val strLength = msg.length
        var start = 0
        var end = 2000
        for (i in 0..99) {
            //剩下的文本还是大于规定长度则继续重复截取并输出
            if (strLength > end) {
                Log.d("http--->$i", msg.substring(start, end))
                start = end
                end = end + 2000
            } else {
                Log.d("http--->", msg.substring(start, strLength))
                break
            }
        }
    }
}