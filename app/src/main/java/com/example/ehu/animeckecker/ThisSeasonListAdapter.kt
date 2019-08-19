package com.example.ehu.animeckecker

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ehu.animeckecker.databinding.RowThisSeasonBinding
import com.example.ehu.animeckecker.remote.Works


class ThisSeasonListAdapter() : PagedListAdapter<Works, ThisSeasonListAdapter.ViewHolder>(DIFF_CALLBACK) {
    lateinit var binding: RowThisSeasonBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = RowThisSeasonBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(getItem(position))
        Log.d(
            "app_thisseason_list",
            "id:${getItem(position)!!.id}\ttitle:${getItem(position)!!.title}\tposition:\t$position\tlayoutPosition:${holder.layoutPosition}\tadapterPosition:${holder.adapterPosition}"
        )
        binding.title.setOnClickListener {
            Log.d(
                "app_thisseason_list",
                "\tid:${getItem(holder.layoutPosition)!!.id}\ttitle:${getItem(holder.layoutPosition)!!.title}\tposition:\t$position\tlayoutPosition:${holder.layoutPosition}\tadapterPosition:${holder.adapterPosition}"
            )
            val myNotificationRow = MyNotificationRow(
                animeId = getItem(holder.layoutPosition)!!.id,
                animeTitle = getItem(holder.layoutPosition)!!.title
            )
            val bundle = bundleOf("is_edit" to false, "my_notification_row" to myNotificationRow)
            Navigation.findNavController(it)
                .navigate(R.id.action_first_edit_to, bundle)
        }
    }

//    override fun getItem(position: Int): Works {
//        return works[position]
//    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    class ViewHolder(private val binding: RowThisSeasonBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindTo(works: Works?) {
            binding.model = works
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Works>() {
            override fun areItemsTheSame(oldItem: Works, newItem: Works): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Works, newItem: Works): Boolean {
                return oldItem == newItem
            }
        }
    }
}