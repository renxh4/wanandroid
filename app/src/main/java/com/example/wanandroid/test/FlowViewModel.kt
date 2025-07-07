package com.example.wanandroid.test

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class FlowViewModel : ViewModel() {
    val timeFlow = flow <Int>{
        var time = 0

        while (true){
            emit(time)
            delay(1000)
            time++
        }
    }
}