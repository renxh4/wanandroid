package com.example.wanandroid.test

import android.util.Log
import com.example.wanandroid.activity.WebviewActivity
import com.example.wanandroid.test.Test.bfsGraph
import com.example.wanandroid.test.Test.bfsTree
import com.example.wanandroid.test.Test.dfs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TestCor {

}

class Activity : CoroutineScope by CoroutineScope(Dispatchers.Default) {

    fun onCreate() {
        launch {
            repeat(5) {
                delay(200L * it)
                log(it.toString())
            }
        }
        log("Activity Created")
    }

    fun onDestroy() {
        cancel()
        log("Activity Destroyed")
    }

    fun log(msg: String) {
        Log.d("mmm", "${Thread.currentThread().name} ---- ${msg}")
    }

}

fun main() {
//    val root: Node = Node(1)
//    root.left = Node(2)
//    root.right = Node(3)
//    root.left.left = Node(4)
//    root.left.right = Node(5)
//    root.right.left = Node(6)
//    bfsTree(root)

//    val g = GraphBFS(4)
//    g.addEdge(0, 1)
//    g.addEdge(0, 2)
//    g.addEdge(1, 2)
//    g.addEdge(2, 0)
//    g.addEdge(2, 3)
//    g.addEdge(3, 3)
//
////    bfsGraph(g,0,3,4)
//    dfs(g,0,3,4)

    ThreadTest().test()
}

inline fun runCatch(crossinline block: () -> Unit) {
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
    runCatch { return@runCatch }
}

fun aa(n: Int): Int {
    if (n == 1) {
        return 1
    }
    return aa(n - 1) + 1
}

fun bb(n: Int): Int {
    if (n == 1) {
        return 1
    }

    if (n == 2) {
        return 2
    }

    return bb(n-1)+bb(n-2)
}





