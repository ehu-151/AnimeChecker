package com.example.ehu.animeckecker


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.ehu.animeckecker.databinding.FragmentNotificationEditBinding
import com.example.ehu.animeckecker.util.AnimeAlarmReceiver
import com.google.android.material.chip.Chip
import java.util.*


class NotificationEditFragment : Fragment() {
    lateinit var binding: FragmentNotificationEditBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notification_edit, container, false)
        binding.create.setOnClickListener {
            setAlarm()
            Navigation.findNavController(it).navigate(R.id.action_global_notificationEditFragment)
        }
        binding.cancel.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_notificationEditFragment_to_thisSeasonFragment)
        }
        binding.createDebug.setOnClickListener {
            setAlarmDebug()
            Navigation.findNavController(it).navigate(R.id.action_global_notificationEditFragment)
        }
        return binding.root
    }

    private fun setAlarm() {
        // chipから時間を取得
        val notificationTime = mutableListOf<Int>()
        for (i in 0 until binding.chipGroupMinute.childCount) {
            val chip = binding.chipGroupMinute.getChildAt(i) as Chip
            notificationTime.add(chip.tag.toString().toInt())
        }
        for (i in 0 until binding.chipGroupHour.childCount) {
            val chip = binding.chipGroupHour.getChildAt(i) as Chip
            notificationTime.add(chip.tag.toString().toInt())
        }
        // 放送時刻を取得
        val (hour, minute) = binding.editText.text.toString().split(":").map { it.toInt() }
        val startedAt = Calendar.getInstance()
        startedAt.set(Calendar.HOUR_OF_DAY, hour)
        startedAt.set(Calendar.MINUTE, minute)
        startedAt.set(Calendar.MINUTE, 0)
        // alarmをセット
        registerNotificationAlarm("ポケモン", notificationTime[0], startedAt)
    }

    private fun setAlarmDebug() {
        val notificationTime = mutableListOf<Int>()
        for (i in 0 until binding.chipGroupSecound.childCount) {
            val chip = binding.chipGroupSecound.getChildAt(i) as Chip
            notificationTime.add(chip.tag.toString().toInt())
        }
        val startedAt = Calendar.getInstance()
        startedAt.timeInMillis = System.currentTimeMillis()
        startedAt.add(Calendar.SECOND, notificationTime[0])
        // alarmをセット
        scheduleNotification("ポケモン", notificationTime[0].toString() + "秒", startedAt)
    }

    private fun registerNotificationAlarm(animeTitle: String, beforeSecond: Int, startedAt: Calendar) {
        startedAt.add(Calendar.SECOND, -beforeSecond)
        scheduleNotification(animeTitle, beforeSecond.toString() + "秒", startedAt)
    }

    /**
     * calendar.getTime()で表示される時間に通知する。
     */
    private fun scheduleNotification(animeTitle: String, time: String, calendar: Calendar) {
        // intent
        val notificationIntent = Intent(context, AnimeAlarmReceiver::class.java).apply {
            putExtra(AnimeAlarmReceiver.ANIME_TITLE, animeTitle)
            putExtra(AnimeAlarmReceiver.NOTIFICATION_TIME, time)
        }
        //pendingIntent
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        //alarm
        val alarmManager = context?.getSystemService(ALARM_SERVICE) as AlarmManager?
        alarmManager!!.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }
}
