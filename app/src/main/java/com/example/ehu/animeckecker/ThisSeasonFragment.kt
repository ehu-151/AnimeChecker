package com.example.ehu.animeckecker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.ehu.animeckecker.databinding.FragmentThisSeasenBinding
import com.example.ehu.animeckecker.util.AppSharedPreferences
import com.example.ehu.animeckecker.util.Status
import com.example.ehu.animeckecker.viewmodel.ThisSeasonViewModel
import java.util.*

class ThisSeasonFragment : Fragment() {

    lateinit var binding: FragmentThisSeasenBinding
    lateinit var token: String
    lateinit var viewModel: ThisSeasonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentThisSeasenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // tokenの取得
        token = AppSharedPreferences(context!!).getToken()

        //ViewModel初期化
        viewModel = ViewModelProviders.of(this.activity!!)
            .get(ThisSeasonViewModel::class.java)
        viewModel.loadWorks(token, getThisSeason())
        viewModel.workData.observe(this, Observer {
            when (it) {
                is Status.Logging -> {

                }
                is Status.Success -> {
                    binding.listView.adapter = ThisSeasonListAdapter(context!!, it.data.works)
                }
                is Status.Failure -> {
                    Toast.makeText(this.context, "ロードに失敗しました", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun getThisSeason(): String {
        val c = Calendar.getInstance()
        c.timeZone = TimeZone.getTimeZone("Asia/Tokyo")
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val seasonList = listOf("winter", "spring", "summer", "autumn")
        val thisSeason = seasonList.get(((month + 1) - 1) / 3)
        return "$year-$thisSeason"
    }
}
