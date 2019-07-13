package com.example.ehu.animeckecker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.ehu.animeckecker.databinding.RowMyNotificationBinding


class MyNotificationAdapter(private val context: Context, private val entity: List<MyNotificationRow>) :
    BaseAdapter() {
    lateinit var binding: RowMyNotificationBinding
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        binding = RowMyNotificationBinding.inflate(inflater, parent, false)
        binding.model = getItem(position)

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

}

