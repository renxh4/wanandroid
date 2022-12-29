package com.example.wanandroid.utils

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import android.widget.RemoteViews
import androidx.core.graphics.drawable.toBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


object WidgetUtils {


//    /**
//     * 直接添加小组件到桌面
//     * 需要快捷方式权限
//     */
//    fun pinWidget(): Boolean {
//        try {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                val appWidgetManager = AppWidgetManager.getInstance(AppContext.getContext())
//                val myProvider =
//                    ComponentName(AppContext.getContext(), SmallAppWidget::class.java)
//                val intent = Intent("action.pin.widget")
//                if (appWidgetManager.isRequestPinAppWidgetSupported) {
//                    // 设置为快速添加
//                    // Create the PendingIntent object only if your app needs to be notified
//                    // that the user allowed the widget to be pinned. Note that, if the pinning
//                    // operation fails, your app isn't notified. This callback receives the ID
//                    // of the newly-pinned widget (EXTRA_APPWIDGET_ID).
//                    val successIntent = PendingIntent.getBroadcast(
//                        AppContext.getContext(),
//                        0,
//                        intent,
//                        PendingIntent.FLAG_UPDATE_CURRENT
//                    )
//                    //需要快捷方式权限
//                    val re = appWidgetManager.requestPinAppWidget(myProvider, null, successIntent)
//                    return true
//                }
//            }
//        } catch (e: Exception) {
//        }
//        return false
//    }
//
//    fun setIcon(context: Context,views: RemoteViews,
//                appWidgetManager: AppWidgetManager,
//                appWidgetId: Int,
//                big: Boolean) {
//        views.setViewVisibility(R.id.appwidget_text, View.VISIBLE)
//        views.setViewVisibility(R.id.widget_dongtai_root_root, View.GONE)
//        if (big) {
//            views.setImageViewResource(R.id.appwidget_text, R.drawable.icon_4_4)
//        } else {
//            views.setImageViewResource(R.id.appwidget_text, R.drawable.icon_2_2)
//        }
//        val fullIntent = Intent(context, MainActivity::class.java)
//        val Pfullintent =
//            PendingIntent.getActivity(context, 0, fullIntent, PendingIntent.FLAG_UPDATE_CURRENT)
//        views.setOnClickPendingIntent(R.id.appwidget_text, Pfullintent)
//        appWidgetManager.updateAppWidget(appWidgetId, views)
//    }
//
//    fun updataNewFeed(context: Context, queryWidgetsCoverFeedBean: QueryWidgetsCoverFeedBean) {
//        val appWidgetManager = AppWidgetManager.getInstance(AppContext.getContext())
//        val smallIds = getSmallIds(appWidgetManager)
//        val bigIds = getBigIds(appWidgetManager)
//        smallIds.forEach {
//            if (queryWidgetsCoverFeedBean == null ||
//                queryWidgetsCoverFeedBean.pictureList == null ||
//                queryWidgetsCoverFeedBean.pictureList.size <= 0 ||
//                queryWidgetsCoverFeedBean.friendNickname ==null ||
//                queryWidgetsCoverFeedBean.friendAvatar ==null
//            ) {
//                val views =
//                    RemoteViews(AppContext.getContext().packageName, R.layout.new_app_widget)
//                setIcon(context,views, appWidgetManager, it, false)
//            } else {
//                val views =
//                    RemoteViews(AppContext.getContext().packageName, R.layout.new_app_widget)
//                views.setViewVisibility(R.id.appwidget_text, View.GONE)
//                views.setViewVisibility(R.id.widget_dongtai_root_root, View.VISIBLE)
//                views.setViewVisibility(R.id.widget_new_msg, View.GONE)
//                views.setViewVisibility(R.id.widget_dongtai, View.VISIBLE)
//                views.setViewVisibility(R.id.widget_name, View.VISIBLE)
//                views.setTextViewText(R.id.widget_name, getName(queryWidgetsCoverFeedBean)?:"")
//                appWidgetManager.updateAppWidget(it, views)
//
//                if (queryWidgetsCoverFeedBean.pictureList != null && queryWidgetsCoverFeedBean.pictureList.size > 0) {
//                    getBitmap(
//                        queryWidgetsCoverFeedBean.pictureList[0],
//                        views,
//                        R.id.widget_photo,
//                        appWidgetManager,
//                        it
//                    )
//                }
//                if (StringUtils.isNotEmpty(queryWidgetsCoverFeedBean.friendAvatar)){
//                    getBitmapCircle(
//                        queryWidgetsCoverFeedBean.friendAvatar,
//                        views,
//                        R.id.widget_avatar,
//                        appWidgetManager,
//                        it
//                    )
//                }
//            }
//        }
//
//        bigIds.forEach {
//            if (queryWidgetsCoverFeedBean == null ||
//                queryWidgetsCoverFeedBean.pictureList == null ||
//                queryWidgetsCoverFeedBean.pictureList.size <= 0 ||
//                queryWidgetsCoverFeedBean.friendNickname ==null ||
//                queryWidgetsCoverFeedBean.friendAvatar ==null) {
//                val views =
//                    RemoteViews(AppContext.getContext().packageName, R.layout.big_new_app_widget)
//                setIcon(context,views, appWidgetManager, it, true)
//            } else {
//                val views =
//                    RemoteViews(AppContext.getContext().packageName, R.layout.big_new_app_widget)
//                views.setViewVisibility(R.id.appwidget_text, View.GONE)
//                views.setViewVisibility(R.id.widget_dongtai_root_root, View.VISIBLE)
//                views.setViewVisibility(R.id.widget_new_msg, View.GONE)
//                views.setViewVisibility(R.id.widget_dongtai, View.VISIBLE)
//                views.setViewVisibility(R.id.widget_name, View.VISIBLE)
//                views.setTextViewText(R.id.widget_name, getName(queryWidgetsCoverFeedBean)?:"")
//                appWidgetManager.updateAppWidget(it, views)
//
//                if (queryWidgetsCoverFeedBean.pictureList != null && queryWidgetsCoverFeedBean.pictureList.size > 0) {
//                    getBitmap(
//                        queryWidgetsCoverFeedBean.pictureList[0],
//                        views,
//                        R.id.widget_photo,
//                        appWidgetManager,
//                        it
//                    )
//                }
//                if (StringUtils.isNotEmpty(queryWidgetsCoverFeedBean.friendAvatar)){
//                    getBitmapCircle(
//                        queryWidgetsCoverFeedBean.friendAvatar,
//                        views,
//                        R.id.widget_avatar,
//                        appWidgetManager,
//                        it
//                    )
//                }
//            }
//        }
//
//        smallIds.forEach {
//            val views = RemoteViews(AppContext.getContext().packageName, R.layout.new_app_widget)
//            goto(context, views, appWidgetManager, it, queryWidgetsCoverFeedBean.feedComponentType)
//        }
//
//        bigIds.forEach {
//            val views =
//                RemoteViews(AppContext.getContext().packageName, R.layout.big_new_app_widget)
//            goto(context, views, appWidgetManager, it, queryWidgetsCoverFeedBean.feedComponentType)
//        }
//    }
//
//
//    fun getName(queryWidgetsCoverFeedBean: QueryWidgetsCoverFeedBean): String? {
//        if (StringUtils.isNotEmpty(queryWidgetsCoverFeedBean.friendRemarkName)) {
//            return queryWidgetsCoverFeedBean.friendRemarkName
//        } else {
//            return queryWidgetsCoverFeedBean.friendNickname
//        }
//    }
//
//
//    fun updataNONewFeed(context: Context, queryWidgetsCoverFeedBean: QueryWidgetsCoverFeedBean) {
//        val appWidgetManager = AppWidgetManager.getInstance(AppContext.getContext())
//        val smallIds = getSmallIds(appWidgetManager)
//        val bigIds = getBigIds(appWidgetManager)
//        smallIds.forEach {
//            if (queryWidgetsCoverFeedBean == null ||
//                queryWidgetsCoverFeedBean.pictureList == null ||
//                queryWidgetsCoverFeedBean.pictureList.size <= 0 ||
//                queryWidgetsCoverFeedBean.friendNickname ==null ||
//                queryWidgetsCoverFeedBean.friendAvatar ==null) {
//                val views =
//                    RemoteViews(AppContext.getContext().packageName, R.layout.new_app_widget)
//                setIcon(context,views, appWidgetManager, it, false)
//            } else {
//                val views =
//                    RemoteViews(AppContext.getContext().packageName, R.layout.new_app_widget)
//                views.setViewVisibility(R.id.appwidget_text, View.GONE)
//                views.setViewVisibility(R.id.widget_dongtai_root_root, View.VISIBLE)
//                views.setViewVisibility(R.id.widget_new_msg, View.VISIBLE)
//                views.setViewVisibility(R.id.widget_dongtai, View.GONE)
//                views.setViewVisibility(R.id.widget_name, View.GONE)
//                views.setTextViewText(R.id.widget_name1, getName(queryWidgetsCoverFeedBean)?:"")
//                views.setTextViewText(R.id.widget_detial, queryWidgetsCoverFeedBean.pictureDesc?:"")
//                appWidgetManager.updateAppWidget(it, views)
//
//                if (queryWidgetsCoverFeedBean.pictureList != null && queryWidgetsCoverFeedBean.pictureList.size > 0) {
//                    getBitmap(
//                        queryWidgetsCoverFeedBean.pictureList[0],
//                        views,
//                        R.id.widget_photo,
//                        appWidgetManager,
//                        it
//                    )
//                }
//                if (StringUtils.isNotEmpty(queryWidgetsCoverFeedBean.friendAvatar)){
//                    getBitmapCircle(
//                        queryWidgetsCoverFeedBean.friendAvatar,
//                        views,
//                        R.id.widget_avatar,
//                        appWidgetManager,
//                        it
//                    )
//                }
//            }
//
//        }
//
//        bigIds.forEach {
//            if (queryWidgetsCoverFeedBean == null ||
//                queryWidgetsCoverFeedBean.pictureList == null ||
//                queryWidgetsCoverFeedBean.pictureList.size <= 0 ||
//                queryWidgetsCoverFeedBean.friendNickname ==null ||
//                queryWidgetsCoverFeedBean.friendAvatar ==null) {
//                val views =
//                    RemoteViews(AppContext.getContext().packageName, R.layout.big_new_app_widget)
//                setIcon(context,views, appWidgetManager, it, true)
//            } else {
//                val views =
//                    RemoteViews(AppContext.getContext().packageName, R.layout.big_new_app_widget)
//                views.setViewVisibility(R.id.appwidget_text, View.GONE)
//                views.setViewVisibility(R.id.widget_dongtai_root_root, View.VISIBLE)
//                views.setViewVisibility(R.id.widget_new_msg, View.VISIBLE)
//                views.setViewVisibility(R.id.widget_dongtai, View.GONE)
//                views.setViewVisibility(R.id.widget_name, View.GONE)
//                views.setTextViewText(R.id.widget_name1, getName(queryWidgetsCoverFeedBean)?:"")
//                views.setTextViewText(R.id.widget_detial, queryWidgetsCoverFeedBean.pictureDesc?:"")
//                appWidgetManager.updateAppWidget(it, views)
//                if (queryWidgetsCoverFeedBean.pictureList != null && queryWidgetsCoverFeedBean.pictureList.size > 0) {
//                    getBitmap(
//                        queryWidgetsCoverFeedBean.pictureList[0],
//                        views,
//                        R.id.widget_photo,
//                        appWidgetManager,
//                        it
//                    )
//                }
//                if (StringUtils.isNotEmpty(queryWidgetsCoverFeedBean.friendAvatar)){
//                    getBitmapCircle(
//                        queryWidgetsCoverFeedBean.friendAvatar,
//                        views,
//                        R.id.widget_avatar,
//                        appWidgetManager,
//                        it
//                    )
//                }
//
//            }
//        }
//
//        smallIds.forEach {
//            val views = RemoteViews(AppContext.getContext().packageName, R.layout.new_app_widget)
//            goto(context, views, appWidgetManager, it, queryWidgetsCoverFeedBean.feedComponentType)
//        }
//
//        bigIds.forEach {
//            val views =
//                RemoteViews(AppContext.getContext().packageName, R.layout.big_new_app_widget)
//            goto(context, views, appWidgetManager, it, queryWidgetsCoverFeedBean.feedComponentType)
//        }
//
//    }
//
//
//    private fun getBitmap(url: String,
//                          views: RemoteViews,
//                          id: Int,
//                          appWidgetManager: AppWidgetManager,
//                          appWidgetId: Int) {
//        val request = ImageRequest.Builder(AppContext.getContext())
//            .data(url)
//            .allowHardware(false)
//            .build()
//        GlobalScope.launch(Dispatchers.Main) {
//            val drawable = AppContext.getContext().imageLoader.execute(request).drawable
//            val bitmap = drawable?.toBitmap()
//            if (bitmap !=null){
//                var bit = ViewUtils.getcornerBitmap(ViewUtils.cropBitmap(bitmap), ViewUtils.dp2px(20f))
//                views.setImageViewBitmap(id, bit)
//                appWidgetManager.partiallyUpdateAppWidget(appWidgetId, views)
//            }
//        }
//    }
//
//    private fun getBitmapCircle(url: String,
//                                views: RemoteViews,
//                                id: Int,
//                                appWidgetManager: AppWidgetManager,
//                                appWidgetId: Int) {
//        val request = ImageRequest.Builder(AppContext.getContext())
//            .data(url)
//            .transformations(CircleCropTransformation())
//            .build()
//        GlobalScope.launch(Dispatchers.Main) {
//            val drawable = AppContext.getContext().imageLoader.execute(request).drawable
//            val bitmap = drawable?.toBitmap()
//            if (bitmap!=null){
//                views.setImageViewBitmap(id, bitmap)
//                appWidgetManager.partiallyUpdateAppWidget(appWidgetId, views)
//            }
//        }
//    }
//
//    fun goto(context: Context,
//             views: RemoteViews,
//             appWidgetManager: AppWidgetManager,
//             widgetId: Int,
//             feedComponentType: Int) {
//        val fullIntent = Intent(context, MainActivity::class.java)
//        fullIntent.putExtra("opentype", feedComponentType)
//        val Pfullintent =
//            PendingIntent.getActivity(context, 0, fullIntent, PendingIntent.FLAG_UPDATE_CURRENT)
//        views.setOnClickPendingIntent(R.id.widget_dongtai_root_root, Pfullintent)
//        appWidgetManager.partiallyUpdateAppWidget(widgetId, views)
//    }
//
//    private fun getWidgetClickIntent(context: Context): PendingIntent? {
//        val intent = Intent(context, MainActivity::class.java)
//        intent.putExtra("opentype", 1)
//        val pendingIntent =
//            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
//        return pendingIntent
//    }
//
//
//    private fun getSmallIds(appWidgetManager: AppWidgetManager): IntArray {
//        return appWidgetManager
//            .getAppWidgetIds(
//                ComponentName(
//                    AppContext.getContext(),
//                    SmallAppWidget::class.java
//                )
//            )
//    }
//
//    private fun getBigIds(appWidgetManager: AppWidgetManager): IntArray {
//        return appWidgetManager
//            .getAppWidgetIds(
//                ComponentName(
//                    AppContext.getContext(),
//                    BigAppWidget::class.java
//                )
//            )
//    }
//
//    /**
//     * 获取小组件数
//     */
//    fun getWidgetCount(): Int {
//        val appWidgetManager = AppWidgetManager.getInstance(AppContext.getContext())
//        val mediumIds = getBigIds(appWidgetManager)
//        val smallIds = getSmallIds(appWidgetManager)
//        return mediumIds.size + smallIds.size
//    }
}