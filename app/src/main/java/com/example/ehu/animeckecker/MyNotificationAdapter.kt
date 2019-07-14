package com.example.ehu.animeckecker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import com.example.ehu.animeckecker.databinding.RowMyNotificationBinding
import com.google.android.material.chip.Chip


class MyNotificationAdapter(private val context: Context, private val entity: List<MyNotificationRow>) :
    BaseAdapter() {
    lateinit var binding: RowMyNotificationBinding
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        binding = RowMyNotificationBinding.inflate(inflater, parent, false)
        binding.model = getItem(position)

        // chipを表示
        entity[position].time?.map { (second, text) ->
            binding.notificationGroup.addView(setUpChip(second.toString(), text))
        }
        // onClick
        binding.animeAlarm.setOnClickListener {
            // 画面遷移：
            // 再編に必要なisEdit, MyNotificationRowを渡す。
            val bundle = bundleOf("is_edit" to true, "my_notification_row" to getItem(position))
            Navigation.findNavController(it)
                .navigate(R.id.action_edit_again, bundle)
        }

        return binding.root
    }

    override fun getItem(position: Int): MyNotificationRow {
        return entity[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return entity.size
    }

    private fun setUpChip(chipTag: String, chipText: String): Chip {
        // Chipを追加
        val chip = Chip(context).apply {
            text = (chipText + "前")
            tag = chipTag
            isCheckedIconVisible = false
            isCheckable = false
        }
        return chip
    }

}

