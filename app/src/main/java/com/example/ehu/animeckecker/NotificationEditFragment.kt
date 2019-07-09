package com.example.ehu.animeckecker


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.ehu.animeckecker.databinding.FragmentNotificationEditBinding
import com.example.ehu.animeckecker.util.AnimeAlarmManager
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
        // 放送時刻を取得
        val (hour, minute) = binding.editText.text.toString().split(":").map { it.toInt() }
        val startedAt = Calendar.getInstance()
        Log.d("startedAt", startedAt.getTime().toString())
        startedAt.set(Calendar.HOUR_OF_DAY, hour)
        startedAt.set(Calendar.MINUTE, minute)
        startedAt.set(Calendar.SECOND, 0)
        Log.d("startedAt", startedAt.getTime().toString())
        // chipから時間を取得
        val notificationMutute = mutableListOf<Int>()
        for (i in 0 until binding.chipGroupMinute.childCount) {
            val chip = binding.chipGroupMinute.getChildAt(i) as Chip
            if (chip.isChecked) {
                Log.d("startedAt", startedAt.getTime().toString())
                // alarmをセット
                AnimeAlarmManager(context!!).registerNotificationAlarm(
                    "ポケモン",
                    startedAt,
                    chip.tag.toString().toInt(),
                    chip.text.toString()
                )
            }
        }

        for (i in 0 until binding.chipGroupHour.childCount) {
            val chip = binding.chipGroupHour.getChildAt(i) as Chip
            if (chip.isChecked) {
                // alarmをセット
                AnimeAlarmManager(context!!).registerNotificationAlarm(
                    "ポケモン",
                    startedAt,
                    chip.tag.toString().toInt(),
                    chip.text.toString()
                )
            }
        }
    }

    private fun setAlarmDebug() {
        val notificationTime = mutableListOf<Int>()
        for (i in 0 until binding.chipGroupSecound.childCount) {
            val chip = binding.chipGroupSecound.getChildAt(i) as Chip
            if (chip.isChecked) notificationTime.add(chip.tag.toString().toInt())
        }
        val startedAt = Calendar.getInstance()
        startedAt.timeInMillis = System.currentTimeMillis()
        startedAt.add(Calendar.SECOND, notificationTime[0])
        // alarmをセット
//        scheduleNotification("ポケモン", notificationTime[0].toString() + "秒", startedAt)
    }

}
