package com.renxh.mock

import android.app.Application
import com.renxh.mock.db.DbUtils

object MockSdk {
    var application : Application? = null
    var db  : DbUtils? = null
    fun init(application: Application){
        this.application = application
         db = DbUtils(application)
    }
}