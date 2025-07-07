package com.example.wanandroid.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.wanandroid.R
import com.example.wanandroid.databinding.ActivityCoroutineBinding
import com.example.wanandroid.databinding.ActivityVideoBinding
import com.example.wanandroid.test.Activity
import com.example.wanandroid.test.FlowViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlin.system.measureTimeMillis

class CoroutineActivity : AppCompatActivity() {

    private val mainViewModel by viewModels<FlowViewModel>()

    lateinit var binding : ActivityCoroutineBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            lifecycleScope.launch{
                mainViewModel.timeFlow.collect{
                    Log.d("mmm",it.toString())
                }
            }
        }
    }

    private fun start() {
        log("开始")

        var result = runBlocking {
            delay(500)
            log("runBlocking")
            41
            launch {
                log("runBlocking1")
            }

            launch(Dispatchers.IO) {
                log("runBlocking2")
            }
        }

        log("runBlocking result = ${result}")

        GlobalScope.launch {
            log("GlobalScope launch2")

            launch {
                delay(100)
                log("GlobalScope launch3")
            }

            launch {
                delay(200)
                log("GlobalScope launch4")
            }
        }


    }

    fun main() = runBlocking {
        launch {
            delay(100)
            log("Task from runBlocking")
        }
        coroutineScope {
            launch {
                delay(500)
                log("Task from nested launch")
            }
            delay(50)
            log("Task from coroutine scope")
        }
        log("Coroutine scope is over")
    }

    fun main1() = runBlocking {
        launch {
            delay(100)
            log("Task from runBlocking")
        }
        supervisorScope {
            launch {
                try {
                    delay(500)
                    log("Task throw Exception")
                    throw Exception("failed")
                }catch (e: Exception){

                }
            }
            launch {
                delay(600)
                log("Task from nested launch")
            }


        }
        log("Coroutine scope is over")
    }

    fun main3() = runBlocking {
        val activity = Activity()
        activity.onCreate()
        delay(1000)
        activity.onDestroy()
        delay(1000)
    }
    private val mainScope = MainScope()


    fun main4() {
        val time = measureTimeMillis {
            runBlocking {
                val asyncA = async {
                    delay(3000)
                    1
                }
                val asyncB = async {
                    delay(4000)
                    2
                }
                log(asyncA.await().toString() + asyncB.await().toString())
            }
        }
        log(time.toString())
    }

    fun main5() = runBlocking<Unit> {
        launch {
            log("main runBlocking")
        }
        launch(Dispatchers.Default) {
            log("Default")
            launch(Dispatchers.Unconfined) {
                log("Unconfined 1")
            }
        }
        launch(Dispatchers.IO) {
            log("IO")
            launch(Dispatchers.Unconfined) {
                log("Unconfined 2")
            }
        }
        launch(newSingleThreadContext("MyOwnThread")) {
            log("newSingleThreadContext")
            launch(Dispatchers.Unconfined) {
                log("Unconfined 4")
            }
        }
        launch(Dispatchers.Unconfined) {
            log("Unconfined 3")
        }
        GlobalScope.launch {
            log("GlobalScope")
        }
    }

    fun main6() = runBlocking {
        val parentJob = coroutineScope {
            launch {
                repeat(3) { i ->
                    launch {
                        delay((i + 1) * 200L)
                        log("Coroutine $i is done")
                    }
                }
                log("request: I'm done and I don't explicitly join my children that are still active")
            }
        }

        launch {
            log("bbbbbb")
        }
    }


    private val ioScope = CoroutineScope(Dispatchers.IO)

    private fun fetchDocs() {
        runBlocking {
            val result = ioScope.async {
                delay(500)
                log("taskA throw AssertionError")
                throw AssertionError()
            }

            result.await()
        }

    }

    fun main7() = runBlocking {
        val handler = CoroutineExceptionHandler { _, exception ->
            log("Caught $exception")
        }
        val job = GlobalScope.launch(handler) {
            throw AssertionError()
        }
        val deferred = GlobalScope.async(handler) {
            throw ArithmeticException()
        }
        joinAll(job, deferred)
        deferred.await()
        1
    }


    fun main8() = runBlocking {
        val supervisor = SupervisorJob()
        with(CoroutineScope(coroutineContext + supervisor)) {
            val firstChild = launch(CoroutineExceptionHandler { _, _ -> }) {
                log("First child is failing")
                throw AssertionError("First child is cancelled")
            }
            val secondChild = launch {
                firstChild.join()
                log("First child is cancelled: ${firstChild.isCancelled}, but second one is still active")
                try {
                    delay(Long.MAX_VALUE)
                } finally {
                    log("Second child is cancelled because supervisor is cancelled")
                }
            }
            firstChild.join()
            log("Cancelling supervisor")
            //取消所有协程
            supervisor.cancel()
            secondChild.join()
        }
    }





    //内联函数aa中的函数参数eat使用noinline修饰后才能在方法体中传递给非内联函数bb
    inline fun aa(name: String, noinline eat: () -> Unit){
        bb(eat)
    }

    fun bb(drink: ()-> Unit){}

    inline fun aa1(name: String, crossinline eat: () -> Unit){
        println("1")
        //aa间接调用Lambda形参eat()
        runOnUiThread(){
            eat()
        }
        println("2")
    }

    fun cc(){
        //使用局部返回，不影响Lambda之后的执行流程，即上面eat()之后的代码
        aa("张三"){
            return@aa
        }
    }


    inline fun runCatch(block: () -> Unit) {
        try {
            print("before lambda")
            block()
            print("after lambda")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun run() {
        // 普通 lambda 不允许 return
        runCatch { return }
    }




    fun log(msg : String){
        Log.d("mmm","${Thread.currentThread().name} ---- ${msg}")
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }
}