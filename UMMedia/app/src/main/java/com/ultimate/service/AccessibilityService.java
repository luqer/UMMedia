package com.ultimate.service;

import android.accessibilityservice.GestureDescription;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Path;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

public class AccessibilityService extends android.accessibilityservice.AccessibilityService {
    private static final String TAG = "AccessibilityService";
    private GestureResultCallback gestureResultCallback;
    private Handler handler;
    public AccessibilityService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //从系统设置里面开启辅助功能时回调1
        Log.d(TAG, "onCreate");
        handler = new Handler(Looper.myLooper());
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        //从系统设置里面开启辅助功能时回调2
        Log.d(TAG, "onServiceConnected");
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        //当有事件被监听到时，会回调该方法，AccessibilityEvent 里包含事件类型，发生源头等，
        int eventType = event.getEventType();
        // 输出事件的字符串type
        Log.d(TAG, "onAccessibilityEvent typeStr:" + eventType);
        AccessibilityNodeInfo rootInActiveWindow = getRootInActiveWindow();
        //通过名称获取控件
        List<AccessibilityNodeInfo> nodeInfobyText = rootInActiveWindow.findAccessibilityNodeInfosByText("浏览");
        //通过名称获取控件
//        List<AccessibilityNodeInfo> nodeInfobyId = rootInActiveWindow.findAccessibilityNodeInfosByViewId("com.alipay.mobile.payee:id/payee_NextBtn");
        //通过名称获取控件
//        AccessibilityNodeInfo nodeInfobyFocus = rootInActiveWindow.findFocus(FOCUS_INPUT);
//        performGlobalAction(GLOBAL_ACTION_BACK);
//        performGlobalAction(GLOBAL_ACTION_HOME);
//        performGlobalAction(GLOBAL_ACTION_NOTIFICATIONS);
//        performGlobalAction(GLOBAL_ACTION_RECENTS);

    }

    @SuppressLint("NewApi")
    private GestureDescription getGestureDescription(Path path, long startTime, long duration) {
        return new GestureDescription.Builder()
                .addStroke(new GestureDescription.StrokeDescription(path, startTime + 100, duration))
                .build();
    }

    @SuppressLint("NewApi")
    private GestureResultCallback getGestureResultCallback() {
        if (gestureResultCallback != null)
            return gestureResultCallback;
        return gestureResultCallback = new GestureResultCallback() {
            @Override
            public void onCompleted(GestureDescription gestureDescription) {
                super.onCompleted(gestureDescription);
            }

            @Override
            public void onCancelled(GestureDescription gestureDescription) {
                super.onCancelled(gestureDescription);
            }
        };
    }

    private void clickOnScreen() {
        Path path = new Path();
        if (VERSION.SDK_INT >= VERSION_CODES.N) {
            dispatchGesture(getGestureDescription(path, System.currentTimeMillis(), 1000L), getGestureResultCallback(),handler);
        }
    }

    @Override
    public void onInterrupt() {

        Log.d(TAG, "onInterrupt");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
