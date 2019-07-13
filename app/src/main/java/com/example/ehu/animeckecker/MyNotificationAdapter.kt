package com.example.ehu.animeckecker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.ehu.animeckecker.databinding.RowMyNotificationBinding
import com.google.android.material.chip.Chip


class MyNotificationAdapter(private val context: Context, private val entity: List<MyNotificationRow>) :
    BaseAdapter() {
    lateinit var binding: RowMyNotificationBinding
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        binding = RowMyNotificationBinding.inflate(inflater, parent, false)
        binding.model = getItem(position)

        entity[position].time.map { (second, text) ->
            binding.notificationGroup.addView(setUpChip(second.toString(), text))
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

