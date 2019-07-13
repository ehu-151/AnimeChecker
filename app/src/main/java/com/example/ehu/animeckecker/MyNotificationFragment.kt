package com.example.ehu.animeckecker


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.ehu.animeckecker.databinding.FragmentMyNotificationBinding
import com.example.ehu.animeckecker.viewmodel.MyNotificationViewModel

class MyNotificationFragment : Fragment() {
    lateinit var binding: FragmentMyNotificationBinding
    lateinit var viewModel: MyNotificationViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyNotificationBinding.inflate(inflater, container, false)
        binding.addNotification.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_myNotificationFragment_to_thisSeasonFragment)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //ViewModel初期化
        viewModel = ViewModelProviders.of(this.activity!!)
            .get(MyNotificationViewModel::class.java)
        viewModel.loadNotifyInfo(context!!)
        viewModel.row?.observe(this, Observer {
            if (it == null) {
                binding.listView.visibility = View.GONE
                binding.warning.visibility = View.VISIBLE
            } else {
                binding.listView.visibility = View.VISIBLE
                binding.warning.visibility = View.GONE
                binding.listView.adapter = MyNotificationAdapter(context!!, it)
            }
        })
    }
}
