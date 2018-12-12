package com.android.example.kotlin.notifyme

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {

    companion object {
        // Constants for the notification actions buttons.
        private const val ACTION_UPDATE_NOTIFICATION = "com.android.example.kotlin.notifyme.ACTION_UPDATE_NOTIFICATION"
        // Notification channel ID.
        private const val PRIMARY_CHANNEL_ID = "primary_notification_channel"
        // Notification ID.
        private const val NOTIFICATION_ID = 0
    }


    private val buttonNotify by lazy { findViewById<Button>(R.id.notify) }
    private val buttonCancel by lazy { findViewById<Button>(R.id.cancel) }
    private val buttonUpdate by lazy { findViewById<Button>(R.id.update) }

    // Create a notification manager object.
    private val mNotifyManager by lazy { getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel()

        buttonNotify.setOnClickListener { sendNotification() }
        buttonUpdate.setOnClickListener { updateNotification() }
        buttonCancel.setOnClickListener { cancelNotification() }

        // Reset the button states. Enable only Notify button and disable
        // update and cancel buttons.
        setNotificationButtonState(true, false, false)

        RxBus.listen(NotificationUpdateEvent::class.java).subscribe {updateNotification()}
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
                    getString(R.string.notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH)

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = getString(R.string.notification_channel_description)

            mNotifyManager.createNotificationChannel(notificationChannel)
        }
    }

    /**
     * OnClick method for the "Notify Me!" button.
     * Creates and delivers a simple notification.
     */
    private fun sendNotification() {

        // Sets up the pending intent to update the notification.
        // Corresponds to a press of the Update Me! button.
        val updateIntent = Intent(this, NotificationUpdateReceiver::class.java)
        val updatePendingIntent = PendingIntent.getBroadcast(this,
                NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT)

        // Build the notification with all of the parameters using helper
        // method.
        val notifyBuilder = getNotificationBuilder()

        // Add the action button using the pending intent.
        notifyBuilder.addAction(R.drawable.ic_update,
                getString(R.string.update), updatePendingIntent)

        // Deliver the notification.
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build())

        // Enable the update and cancel buttons but disables the "Notify
        // Me!" button.
        setNotificationButtonState(false, true, true)
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
        val notificationIntent = Intent(this, MainActivity::class.java)
        val notificationPendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT)

        // Build the notification with all of the parameters.
        return NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText(getString(R.string.notification_text))
                .setSmallIcon(R.drawable.ic_android)
                .setAutoCancel(true).setContentIntent(notificationPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
    }

    /**
     * OnClick method for the "Update Me!" button. Updates the existing
     * notification to show a picture.
     */
    private fun updateNotification() {

        // Load the drawable resource into the a bitmap image.
        val androidImage = BitmapFactory
                .decodeResource(resources, R.drawable.mascot_1)

        // Build the notification with all of the parameters using helper
        // method.
        val notifyBuilder = getNotificationBuilder()

        // Update the notification style to BigPictureStyle.
        notifyBuilder.setStyle(NotificationCompat.BigPictureStyle()
                .bigPicture(androidImage)
                .setBigContentTitle(getString(R.string.notification_updated)))

        // Deliver the notification.
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build())

        // Disable the update button, leaving only the cancel button enabled.
        setNotificationButtonState(false, false, true)
    }

    /**
     * OnClick method for the "Cancel Me!" button. Cancels the notification.
     */
    private fun cancelNotification() {
        // Cancel the notification.
        mNotifyManager.cancel(NOTIFICATION_ID)

        // Reset the buttons.
        setNotificationButtonState(true, false, false)
    }

    /**
     * Helper method to enable/disable the buttons.
     *
     * @param isNotifyEnabled, boolean: true if notify button enabled
     * @param isUpdateEnabled, boolean: true if update button enabled
     * @param isCancelEnabled, boolean: true if cancel button enabled
     */
    private fun setNotificationButtonState(isNotifyEnabled: Boolean, isUpdateEnabled: Boolean, isCancelEnabled: Boolean) {
        buttonNotify.isEnabled = isNotifyEnabled
        buttonUpdate.isEnabled = isUpdateEnabled
        buttonCancel.isEnabled = isCancelEnabled
    }
}
