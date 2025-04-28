package com.example.wanandroid.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.wanandroid.R
import com.example.wanandroid.databinding.ActivityCoroutineBinding
import com.example.wanandroid.databinding.ActivityVideoBinding

class CoroutineActivity : AppCompatActivity() {
    lateinit var binding : ActivityCoroutineBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutineBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}