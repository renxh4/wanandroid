package com.renxh.mock

import android.app.Application
import android.util.Log
import com.renxh.mock.db.DbUtils

object MockSdk {
    var application : Application? = null
    var db  : DbUtils? = null
    fun init(application: Application){
        this.application = application
         db = DbUtils(application)
        MockServer.init(application)
        var ipAddress = IPConfig.getIpAddress(application)
        Log.d("MockSdk","${ipAddress}:5566")
    }
}