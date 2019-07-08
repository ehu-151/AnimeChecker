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
            registerNotificationAlarm("ポケモン", 3)

            Navigation.findNavController(it).navigate(R.id.action_global_notificationEditFragment)
        }
        binding.cancel.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_notificationEditFragment_to_thisSeasonFragment)
        }
        return binding.root
    }

    private fun setAlarm(){
        // chipから時間を取得
        val notificationTime= mutableListOf<Int>()
        for (i in 0 until binding.chipGroup.childCount) {
            val chip = binding.chipGroup.getChildAt(i) as Chip
            notificationTime.add(chip.tag.toString().toInt())
        }
        for (i in 0 until binding.chipGroup2.childCount) {
            val chip = binding.chipGroup2.getChildAt(i) as Chip
            notificationTime.add(chip.tag.toString().toInt())
        }
        // alarmをセット
    }

    private fun registerNotificationAlarm(animeTitle: String, second: Int) {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.add(Calendar.SECOND, second)
        scheduleNotification(animeTitle, second.toString() + "秒", calendar)
    }

    private fun scheduleNotification(animeTitle: String, time: String, calendar: Calendar) {
        // intent
        val notificationIntent = Intent(context, AnimeAlarmReceiver::class.java).apply {
            putExtra(AnimeAlarmReceiver.ANIME_TITLE, animeTitle)
            putExtra(AnimeAlarmReceiver.NOTIFICATION_TIME, time)
        }
        //pendingIntent
        val pendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        //alarm
        val alarmManager = context?.getSystemService(ALARM_SERVICE) as AlarmManager?
        alarmManager!!.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }
}
