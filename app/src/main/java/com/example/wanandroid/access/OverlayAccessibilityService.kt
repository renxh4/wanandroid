package com.example.wanandroid.access

import android.accessibilityservice.AccessibilityService
import android.graphics.Color
import android.view.View
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import android.widget.Button
import android.widget.FrameLayout
import android.view.Gravity
import android.widget.TextView
import android.os.CountDownTimer
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

class OverlayAccessibilityService : AccessibilityService() {
    private var overlayView: View? = null
    private var windowManager: WindowManager? = null
    private var countDownTimer: CountDownTimer? = null
    private var showOverlayReceiver: BroadcastReceiver? = null

    override fun onCreate() {
        super.onCreate()
        // 注册广播接收器
        val filter = IntentFilter("com.example.wanandroid.SHOW_OVERLAY")
        showOverlayReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                showOverlay()
            }
        }
        registerReceiver(showOverlayReceiver, filter)
    }

    override fun onServiceConnected() {
        showOverlay()
    }

    override fun onUnbind(intent: android.content.Intent?): Boolean {
        removeOverlay()
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        // 注销广播接收器
        showOverlayReceiver?.let { unregisterReceiver(it) }
        showOverlayReceiver = null
        removeOverlay()
        super.onDestroy()
    }

    private fun showOverlay() {
        if (overlayView != null) return

        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        // 根布局
        val rootLayout = FrameLayout(this)

        // 全屏透明拦截层
        val blockView = View(this).apply {
            setBackgroundColor(Color.parseColor("#80000000")) // 半透明黑色
            setOnTouchListener { _, _ -> true } // 拦截所有触摸
        }
        rootLayout.addView(blockView, FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        ))

        // 倒计时文本
        val countdownText = TextView(this).apply {
            textSize = 20f
            setTextColor(Color.BLUE)
            setBackgroundColor(Color.parseColor("#66000000"))
            setPadding(40, 20, 40, 20)
        }
        val countdownParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = Gravity.CENTER
        }
        rootLayout.addView(countdownText, countdownParams)

        // 退出按钮
        val exitButton = Button(this).apply {
            text = "×"
            textSize = 24f
            setBackgroundColor(Color.RED)
            setTextColor(Color.WHITE)
            setOnClickListener {
                removeOverlay()
            }
        }
        val btnParams = FrameLayout.LayoutParams(
            150, 150
        ).apply {
            gravity = Gravity.END or Gravity.TOP
            topMargin = 60
            rightMargin = 60
        }
        rootLayout.addView(exitButton, btnParams)

        // 确保倒计时文本和退出按钮在最上层
        countdownText.bringToFront()
        exitButton.bringToFront()

        overlayView = rootLayout

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                    WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            android.graphics.PixelFormat.TRANSLUCENT
        )
        params.gravity = Gravity.TOP or Gravity.START

        windowManager?.addView(overlayView, params)

        // 启动倒计时（例如10秒）
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(10_000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countdownText.text = "剩余 "+ (millisUntilFinished / 1000) + " 秒自动退出"
            }
            override fun onFinish() {
                removeOverlay()
            }
        }.start()
    }

    private fun removeOverlay() {
        countDownTimer?.cancel()
        countDownTimer = null
        overlayView?.let {
            windowManager?.removeView(it)
            overlayView = null
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        // 处理无障碍事件
    }

    override fun onInterrupt() {}
}