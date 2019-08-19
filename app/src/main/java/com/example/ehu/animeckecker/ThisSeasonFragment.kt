package com.example.ehu.animeckecker

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavAction
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ehu.animeckecker.databinding.FragmentThisSeasenBinding
import com.example.ehu.animeckecker.repository.LoginRepository
import com.example.ehu.animeckecker.util.AppSharedPreferences
import com.example.ehu.animeckecker.util.Status
import com.example.ehu.animeckecker.viewmodel.ThisSeasonViewModel
import java.util.*

class ThisSeasonFragment : Fragment() {

    lateinit var binding: FragmentThisSeasenBinding
    lateinit var token: String
    lateinit var viewModel: ThisSeasonViewModel

    private val adapter = ThisSeasonListAdapter()

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
        // argumentsの取得
        val rejectAnimeIds = arguments?.getIntArray("reject_anime_ids")
        // tokenの取得
        token = AppSharedPreferences(context!!).getToken()

        //ViewModel初期化
        viewModel = ViewModelProviders.of(this.activity!!)
            .get(ThisSeasonViewModel::class.java)
        binding.recycler.layoutManager = LinearLayoutManager(context)
        binding.recycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        binding.recycler.adapter = adapter
        viewModel.loadWork(token, getThisSeason(), rejectAnimeIds)
        viewModel.netWorkState.observe(this, Observer {
            Log.d("app_thisseason_work_e", it.toString())
            when (it) {
                Status.RUNNING -> {
                }
                Status.SUCCESS -> {
                }
                Status.FAILED_401 -> {
                    intentMain()
                }
            }
        })
        viewModel.workData.observe(this, Observer {
            Log.d("app_thisseason_work", it.size.toString() + it.map { it.title + "\t" }.toString())
            adapter.submitList(it)
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

    private fun intentMain() {
        val token = AppSharedPreferences(context!!).getToken()
        LoginRepository().logout(token)
        Toast.makeText(context, "ログインが失効しました。\nログインし直して下さい", Toast.LENGTH_SHORT).show()
        AppSharedPreferences(context!!).setIsLogin(false)
        AppSharedPreferences(context!!).setToken("")
        val nav = NavAction(R.id.nav_graph).destinationId
        Navigation.findNavController(this.view!!).navigate(nav)
    }
}
