package com.ddt.incubator.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.ddt.incubator.R
import com.ddt.incubator.ui.introscreen.IntroActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private var userFirstTime = true
    private val CHANNEL_ID = "Habits notifications"
    private val notificationId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Checks whether it's the users first time
        loadData()

        //If it's the users first time, it will store that the user saw the intro screen, and ignore this the next time
        if (userFirstTime) {
            userFirstTime = false
            saveData()

            val i = Intent(this, IntroActivity::class.java)
            startActivity(i)
            finish()
        }

        setupActionBarWithNavController(findNavController(R.id.navHostFragment))

        createNotificationChannel()
        sendNotification()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.navHostFragment)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


    private fun saveData() {
        val sp = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE)
        sp.edit().apply {
            putBoolean("BOOLEAN_FIRST_TIME", userFirstTime)
            apply()
        }
    }

    private fun loadData() {
        val sp = getSharedPreferences("SHARED_PREFS", MODE_PRIVATE)
        userFirstTime = sp.getBoolean("BOOLEAN_FIRST_TIME", true)

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Incubator"
            val descriptionText = "Notify about marking progress"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }


    private fun sendNotification() {
        val resultIntent = Intent(this, MainActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("Incubator's waiting")
            .setContentText("Don't forget about your eggs")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setVibrate(LongArray(1))
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builder.build())
        }
    }
}