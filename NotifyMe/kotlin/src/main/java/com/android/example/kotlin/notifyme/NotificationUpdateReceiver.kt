package com.android.example.kotlin.notifyme

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationUpdateReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
       RxBus.publish(NotificationUpdateEvent())
    }
}