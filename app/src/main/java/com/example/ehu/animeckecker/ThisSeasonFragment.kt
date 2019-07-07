package com.example.ehu.animeckecker

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.ehu.animeckecker.databinding.FragmentThisSeasenBinding
import com.example.ehu.animeckecker.util.AppSharedPreferences
import com.example.ehu.animeckecker.util.Status
import com.example.ehu.animeckecker.viewmodel.ThisSeasonViewModel

class ThisSeasonFragment : Fragment() {

    lateinit var binding: FragmentThisSeasenBinding
    lateinit var token: String
    lateinit var viewModel: ThisSeasonViewModel

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

        //ViewModel初期化
        viewModel = ViewModelProviders.of(this.activity!!)
            .get(ThisSeasonViewModel::class.java)
        viewModel.loadWorks(token)
        viewModel.workData.observe(this, Observer {
            when (it) {
                is Status.Logging -> {

                }
                is Status.Success -> {
                    Log.d("APPC", it.data.toString())
                }
                is Status.Failure -> {
                    Toast.makeText(this.context, "ロードに失敗しました", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
