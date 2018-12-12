package com.android.fundamentals.kotlin.standup

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.support.v4.app.NotificationCompat
import android.widget.Toast
import android.widget.ToggleButton
import com.android.example.kotlin.notifyme.AlarmNotificationEvent
import com.android.example.kotlin.notifyme.RxBus

class MainActivity : AppCompatActivity() {

    // Constants for the notification actions buttons.
    companion object {
        // Notification channel ID.
        private const val PRIMARY_CHANNEL_ID = "primary_notification_channel"
        // Notification ID.
        private const val NOTIFICATION_ID = 0
    }

    private val mAlarmToggle by lazy {findViewById<ToggleButton>(R.id.alarmToggle)}
    private val mAlarmManager by lazy { getSystemService(Context.ALARM_SERVICE) as? AlarmManager}
    // Set up the Notification Broadcast Intent.
    private val mAlarmPendingIntent by lazy { PendingIntent.getBroadcast(this, NOTIFICATION_ID, Intent(this, AlarmReceiver::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT) }
    // Create a notification manager object.
    private val mNotifyManager by lazy { getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager }

    private val mAlarmDispose = RxBus.listen(AlarmNotificationEvent::class.java).subscribe {
        // Deliver the notification
        mNotifyManager?.notify(NOTIFICATION_ID, getNotificationBuilder().build())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChannel()
        startAlarm()

        
        mAlarmToggle.setOnCheckedChangeListener { _, isChecked ->
            val toastMessage = if(isChecked) {
                startAlarm()
                getString(R.string.alarm_on_toast)
            } else {
                mNotifyManager?.cancelAll()
                mAlarmManager?.cancel(mAlarmPendingIntent)
                getString(R.string.alarm_off_toast)
            }
            // Show a toast to say the alarm is turned on or off.
            Toast.makeText(this@MainActivity, toastMessage, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        if(!mAlarmDispose.isDisposed) mAlarmDispose.dispose()
        super.onDestroy()
    }

    private fun startAlarm() {

        val repeatInterval = (2 * 1000).toLong()

        val triggerTime = SystemClock.elapsedRealtime() + repeatInterval
        mAlarmManager?.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, repeatInterval, mAlarmPendingIntent)
    }

    /**
     * Creates a Notification channel, for OREO and higher.
     */
    private fun createNotificationChannel() {


        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            val notificationChannel = NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Stand up notification",
                    NotificationManager.IMPORTANCE_HIGH)

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Notifies every 15 minutes to " + "stand up and walk"

            mNotifyManager?.createNotificationChannel(notificationChannel)
        }
    }

    /**
     * Helper method that builds the notification.
     *
     * @return NotificationCompat.Builder: notification build with all the
     * parameters.
     */
    private fun getNotificationBuilder(): NotificationCompat.Builder {

        // Set up the pending intent that is delivered when the notification
        // is clicked.
        val contentIntent = Intent(this, MainActivity::class.java)
        val contentPendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, contentIntent, PendingIntent
                .FLAG_UPDATE_CURRENT)

        // Build the notification with all of the parameters.
        return NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stand_up)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_text))
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
    }
}
