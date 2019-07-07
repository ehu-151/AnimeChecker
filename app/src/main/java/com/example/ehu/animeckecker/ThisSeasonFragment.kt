package com.example.ehu.animeckecker

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.ehu.animeckecker.databinding.FragmentThisSeasenBinding
import com.example.ehu.animeckecker.repository.ThisSeasonRepositoty
import com.example.ehu.animeckecker.util.AppSharedPreferences
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ThisSeasonFragment : Fragment() {

    lateinit var binding: FragmentThisSeasenBinding
    lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_this_seasen, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // tokenの取得
        token = AppSharedPreferences(context!!).getToken()

        GlobalScope.launch {
            val repositoty = ThisSeasonRepositoty().getWorks("2014-autumn", token)
            Log.d("Anime", repositoty.toString())
        }
    }
}
