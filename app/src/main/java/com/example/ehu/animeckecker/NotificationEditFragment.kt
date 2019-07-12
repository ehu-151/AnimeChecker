package com.example.ehu.animeckecker


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        binding = FragmentNotificationEditBinding.inflate(inflater, container, false)
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
        // chipから曜日を取得
        var dayOfWeek = 0
        for (i in 0 until binding.dayOfWeek.childCount) {
            val chip = binding.dayOfWeek.getChildAt(i) as Chip
            if (chip.isChecked) {
                dayOfWeek = chip.tag.toString().toInt()
                break
            }
        }
        // chipから時間を取得
        for (i in 0 until binding.chipGroupMinute.childCount) {
            val chip = binding.chipGroupMinute.getChildAt(i) as Chip
            if (chip.isChecked) {
                // alarmをセット
                AnimeAlarmManager(context!!).registerNotificationAlarm(
                    1, 1, "ポケモン",
                    dayOfWeek, hour, minute, 0,
                    chip.tag.toString().toInt(), chip.text.toString()
                )
            }
        }

        for (i in 0 until binding.chipGroupHour.childCount) {
            val chip = binding.chipGroupHour.getChildAt(i) as Chip
            if (chip.isChecked) {
                // alarmをセット
                AnimeAlarmManager(context!!).registerNotificationAlarm(
                    1, 1, "ポケモン",
                    dayOfWeek, hour, minute, 0,
                    chip.tag.toString().toInt(), chip.text.toString()
                )
            }
        }
    }

    private fun setAlarmDebug() {
        val notificationTime = mutableListOf<Int>()
        for (i in 0 until binding.chipGroupSecound.childCount) {
            val chip = binding.chipGroupSecound.getChildAt(i) as Chip
            if (chip.isChecked) {
                val startedAt = Calendar.getInstance()
                startedAt.timeInMillis = System.currentTimeMillis()
//                AnimeAlarmManager(context!!).registerNotificationAlarm(
//                    "ポケモン",
//                    startedAt,
//                    chip.tag.toString().toInt(),
//                    chip.text.toString()
//                )
            }
        }
    }
}
