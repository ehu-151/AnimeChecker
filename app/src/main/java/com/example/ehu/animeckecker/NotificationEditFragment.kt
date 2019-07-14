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
    lateinit var inflater: LayoutInflater
    var container: ViewGroup? = null
    lateinit var row: MyNotificationRow

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.inflater = inflater
        this.container = container
        // argumentの受け取り
        val isEdit = arguments?.getBoolean("is_edit")!!
        this.row = arguments?.getSerializable("my_notification_row") as MyNotificationRow

        // isEdit共通
        binding = FragmentNotificationEditBinding.inflate(inflater, container, false)

        // isEditによって、入力データ、遷移を分ける
        if (isEdit) {
            setUpAgainEdit()
        } else {
            setUpFirstEdit()
        }


        return binding.root
    }

    private fun setAlarm(animeId: Int, animeTiele: String) {
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
                    Random().nextInt(), animeId, animeTiele,
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
                    Random().nextInt(), animeId, animeTiele,
                    dayOfWeek, hour, minute, 0,
                    chip.tag.toString().toInt(), chip.text.toString()
                )
            }
        }
    }

    private fun setUpAgainEdit() {
        setUpConfig()
        binding.create.setOnClickListener {
            setAlarm(row.animeId, row.animeTitle)
            Navigation.findNavController(it).navigate(R.id.action_no_stack_to_myNotificationFragment)
        }
        binding.cancel.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_no_stack_to_myNotificationFragment)
        }
    }

    /**
     * isEdit=true時に、曜日,chip,放送開始時間,を入力状態にする
     */
    private fun setUpConfig() {
        // 曜日のセット
        binding.mon.isChecked = false
        for (i in 0 until binding.dayOfWeek.childCount) {
            val chip = binding.dayOfWeek.getChildAt(i) as Chip
            if (chip.tag.toString().toInt() == row.dayOfWeek) {
                chip.isChecked = true
                break
            }
        }
    }

    private fun setUpFirstEdit() {
        binding.create.setOnClickListener {
            setAlarm(row.animeId, row.animeTitle)
            Navigation.findNavController(it).navigate(R.id.action_no_stack_to_myNotificationFragment)
        }
        binding.cancel.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_edit_first_cancel)
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
