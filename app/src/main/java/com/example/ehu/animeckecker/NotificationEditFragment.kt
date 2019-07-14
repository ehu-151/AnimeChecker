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

    private fun setConfigToRow(animeId: Int, animeTiele: String) {
        // 放送時刻をセット
        val (hour, minute) = binding.editText.text.toString().split(":").map { it.toInt() }
        this.row.hour = hour
        this.row.minute = minute
        this.row.second = 0
        // chipから曜日を取得
        for (i in 0 until binding.dayOfWeek.childCount) {
            val chip = binding.dayOfWeek.getChildAt(i) as Chip
            if (chip.isChecked) {
                this.row.dayOfWeek = chip.tag.toString().toInt()
                break
            }
        }
        // chipから時間を取得
        for (i in 0 until binding.chipGroupMinute.childCount) {
            val chip = binding.chipGroupMinute.getChildAt(i) as Chip
            if (chip.isChecked) {
                // idは、animeIdとbeforeSecondを連結させたInt
                val sId = "${animeId}${chip.tag.toString().toInt()}"
                this.row.id.add(sId.toInt())
                this.row.time.put(chip.tag.toString().toInt(), chip.text.toString())
            }
        }

        for (i in 0 until binding.chipGroupHour.childCount) {
            val chip = binding.chipGroupHour.getChildAt(i) as Chip
            if (chip.isChecked) {
                val sId = "${this.row.animeId}${chip.tag.toString().toInt()}"
                this.row.id.add(sId.toInt())
                this.row.time.put(chip.tag.toString().toInt(), chip.text.toString())
            }
        }
    }

    private fun setUpAgainEdit() {
        // Deleteボタンの表示
        binding.delete.visibility = View.VISIBLE
        // 既知データを表示(セット)
        setUpConfig()
        binding.create.setOnClickListener {
            setConfigToRow(row.animeId, row.animeTitle)
            // timeを取り出す。
            val beforeSecond = mutableListOf<Int>()
            val beforeSecondText = mutableListOf<String>()
            this.row.time.forEach {
                beforeSecond.add(it.key)
                beforeSecondText.add(it.value)
            }
            // idごとに、alarmをセット
            this.row.id.forEachIndexed { index, id ->
                AnimeAlarmManager(context!!).registerNotificationAlarm(
                    id, this.row.animeId, this.row.animeTitle,
                    this.row.dayOfWeek!!, this.row.hour!!, this.row.minute!!, this.row.second!!,
                    beforeSecond[index], beforeSecondText[index]
                )
            }

            Navigation.findNavController(it).navigate(R.id.action_no_stack_to_myNotificationFragment)
        }
        binding.cancel.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_no_stack_to_myNotificationFragment)
        }
        binding.delete.setOnClickListener {
            // このアニメの通知をすべて削除する。
            val beforeTimeText = this.row.time.values.toList()
            this.row.id.forEachIndexed { index, i ->
                AnimeAlarmManager(context!!).deleteNotificationAlarm(id, this.row.animeTitle, beforeTimeText[index])
            }
        }

    }

    /**
     * isEdit=true時に、放送開始時間,曜日,chip,を入力状態にする
     */
    private fun setUpConfig() {
        // 放送開始時間のセット
        val timeText = "${this.row.hour?.zeroFill()}:${this.row.minute?.zeroFill()}"
        binding.editText.setText(timeText)
        // 曜日のセット
        binding.mon.isChecked = false
        for (i in 0 until binding.dayOfWeek.childCount) {
            val chip = binding.dayOfWeek.getChildAt(i) as Chip
            if (chip.tag.toString().toInt() == this.row.dayOfWeek) {
                chip.isChecked = true
                break
            }
        }
        // chipのセット
        for (i in 0 until binding.chipGroupMinute.childCount) {
            val chip = binding.chipGroupMinute.getChildAt(i) as Chip
            this.row.time.forEach {
                if (chip.tag.toString().toInt() == it.key) chip.isChecked = true
            }
        }

        for (i in 0 until binding.chipGroupHour.childCount) {
            val chip = binding.chipGroupHour.getChildAt(i) as Chip
            this.row.time.forEach {
                if (chip.tag.toString().toInt() == it.key) chip.isChecked = true
            }
        }
    }

    private fun setUpFirstEdit() {
        binding.create.setOnClickListener {
            setConfigToRow(row.animeId, row.animeTitle)
            // timeを取り出す。
            val beforeSecond = mutableListOf<Int>()
            val beforeSecondText = mutableListOf<String>()
            this.row.time.forEach {
                beforeSecond.add(it.key)
                beforeSecondText.add(it.value)
            }
            // idごとに、alarmをセット
            this.row.id.forEachIndexed { index, id ->
                AnimeAlarmManager(context!!).registerNotificationAlarm(
                    id, this.row.animeId, this.row.animeTitle,
                    this.row.dayOfWeek!!, this.row.hour!!, this.row.minute!!, this.row.second!!,
                    beforeSecond[index], beforeSecondText[index]
                )
            }

            Navigation.findNavController(it).navigate(R.id.action_no_stack_to_myNotificationFragment)
        }
        binding.cancel.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_edit_first_cancel)
        }
    }

    fun Int.zeroFill(): String {
        return String.format("%02d", this)
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
