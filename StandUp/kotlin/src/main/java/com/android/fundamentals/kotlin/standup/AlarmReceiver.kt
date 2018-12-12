package com.android.fundamentals.kotlin.standup

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.android.example.kotlin.notifyme.AlarmNotificationEvent
import com.android.example.kotlin.notifyme.RxBus

class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        RxBus.publish(AlarmNotificationEvent())
    }
}