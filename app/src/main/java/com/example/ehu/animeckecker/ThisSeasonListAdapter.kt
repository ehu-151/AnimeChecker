package com.example.ehu.animeckecker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.ehu.animeckecker.databinding.RowThisSeasonBinding
import com.example.ehu.animeckecker.remote.Works


class ThisSeasonListAdapter(private val context: Context, private val works: List<Works>) : BaseAdapter() {
    lateinit var binding: RowThisSeasonBinding
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        binding = DataBindingUtil.inflate(inflater, R.layout.row_this_season, parent, false)
        binding.model = getItem(position)
        binding.title.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_thisSeasonFragment_to_notificationEditFragment)
        }
        return binding.root
    }

    override fun getItem(position: Int): Works {
        return works[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return works.size
    }

    class WorksHolder(
        val title: TextView
    )

}