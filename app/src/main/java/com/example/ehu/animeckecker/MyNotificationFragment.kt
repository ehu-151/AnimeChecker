package com.example.ehu.animeckecker


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.ehu.animeckecker.databinding.FragmentMyNotificationBinding
import com.example.ehu.animeckecker.util.AppSharedPreferences
import com.example.ehu.animeckecker.viewmodel.MyNotificationViewModel

class MyNotificationFragment : Fragment() {
    lateinit var binding: FragmentMyNotificationBinding
    lateinit var viewModel: MyNotificationViewModel
    // ThisSeasonに渡す、既に設定したanimeId
    var rejecteAnimeIds: IntArray = IntArray(0)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyNotificationBinding.inflate(inflater, container, false)
        binding.addNotification.setOnClickListener {
            val bundle = bundleOf("reject_anime_ids" to rejecteAnimeIds)
            Navigation.findNavController(it).navigate(R.id.action_myNotificationFragment_to_thisSeasonFragment, bundle)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (!loginCheck()) Navigation.findNavController(this.view!!).navigate(R.id.action_main_go_login)

        //ViewModel初期化
        viewModel = ViewModelProviders.of(this.activity!!)
            .get(MyNotificationViewModel::class.java)
        viewModel.loadNotifyInfo(context!!)
        viewModel.row?.observe(this, Observer {
            if (it == null) {
                binding.listView.visibility = View.GONE
                binding.warning.visibility = View.VISIBLE
            } else {
                rejecteAnimeIds = IntArray(it.size)
                it.forEachIndexed { index, myNotificationRow ->
                    rejecteAnimeIds.set(index, myNotificationRow.animeId)
                }
                binding.listView.visibility = View.VISIBLE
                binding.warning.visibility = View.GONE
                binding.listView.adapter = MyNotificationAdapter(context!!, it)
            }
        })
    }

    private fun loginCheck(): Boolean {
        return AppSharedPreferences(context!!).getIsLogin()
    }
}
