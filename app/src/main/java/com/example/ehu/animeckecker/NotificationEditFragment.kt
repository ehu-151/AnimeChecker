package com.example.ehu.animeckecker


import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
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

    lateinit var beforeEditRow: MyNotificationRow

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
        binding.animeTitle.text = this.row.animeTitle
        binding.time.setOnClickListener { showTimePicker() }

        // isEditによって、入力データ、遷移を分ける
        if (isEdit) {
            beforeEditRow = row.copy()
            setUpAgainEdit()
        } else {
            setUpFirstEdit()
        }


        return binding.root
    }

    private fun setConfigToRow(animeId: Int, animeTiele: String) {
        // 放送時刻をセット
        val (hour, minute) = binding.time.text.toString().split(":").map { it.toInt() }
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
        this.row.id.clear()
        this.row.time.clear()
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
        setUpComponent()
        // createボタンの表示を変える
        binding.create.text = "通知を再設定"

        binding.create.setOnClickListener {
            if (!isEnmpyComponent()) return@setOnClickListener
            setConfigToRow(row.animeId, row.animeTitle)

            // 一旦全削除,前のidで回す。
            beforeEditRow.id.forEachIndexed { index, id ->
                AnimeAlarmManager().deleteNotificationAlarm(
                    context!!,
                    id,
                    this.row.animeId,
                    this.row.animeTitle,
                    beforeEditRow.time.values.toList()[index]
                )
            }

            // timeを取り出す。
            val beforeSecond = mutableListOf<Int>()
            val beforeSecondText = mutableListOf<String>()
            this.row.time.forEach {
                beforeSecond.add(it.key)
                beforeSecondText.add(it.value)
            }
            // idごとに、alarmをセット
            this.row.id.forEachIndexed { index, id ->
                AnimeAlarmManager().registerNotificationAlarm(
                    context!!,
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
            this.row.id.forEachIndexed { index, id ->
                AnimeAlarmManager().deleteNotificationAlarm(
                    context!!,
                    id,
                    this.row.animeId,
                    this.row.animeTitle,
                    beforeTimeText[index]
                )
            }
            Navigation.findNavController(it).navigate(R.id.action_no_stack_to_myNotificationFragment)
        }

    }

    /**
     * isEdit=true時に、放送開始時間,曜日,chip,を入力状態にする
     */
    private fun setUpComponent() {
        // 放送開始時間のセット
        val timeText = "${this.row.hour?.zeroFill()}:${this.row.minute?.zeroFill()}"
        binding.time.setText(timeText)
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
            if (!isEnmpyComponent()) return@setOnClickListener
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
                AnimeAlarmManager().registerNotificationAlarm(
                    context!!,
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

    private fun showTimePicker() {
        class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {
            override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
                // Use the current time as the default values for the picker
                val c = Calendar.getInstance()
                val hour = c.get(Calendar.HOUR_OF_DAY)
                val minute = c.get(Calendar.MINUTE)

                // Create a new instance of TimePickerDialog and return it
                return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
            }

            override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
                val hour = String.format("%02d", hourOfDay)
                val min = String.format("%02d", minute)
                val timeText = "$hour:$min"
                binding.time.setText(timeText)
            }
        }
        TimePickerFragment().show((activity as FragmentActivity).supportFragmentManager, "TAG")
    }

    // 入力されていない項目があるとToast
    private fun isEnmpyComponent(): Boolean {
        val isTime = binding.time.text.toString() != getString(R.string.tagging_time)
        var isNotification = false
        for (i in 0 until binding.chipGroupMinute.childCount) {
            val chip = binding.chipGroupMinute.getChildAt(i) as Chip
            if (chip.isChecked) isNotification = true
        }

        for (i in 0 until binding.chipGroupHour.childCount) {
            val chip = binding.chipGroupHour.getChildAt(i) as Chip
            if (chip.isChecked) isNotification = true
        }

        if (!isTime && !isNotification) {
            Toast.makeText(context, "放送時間と通知時間を指定してください", Toast.LENGTH_SHORT).show()
            return false
        } else if (!isTime) {
            Toast.makeText(context, "放送時間を指定してください", Toast.LENGTH_SHORT).show()
            return false
        } else if (!isNotification) {
            Toast.makeText(context, "通知時間を指定してください", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}
