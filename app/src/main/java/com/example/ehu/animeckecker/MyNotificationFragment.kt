package com.example.ehu.animeckecker


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.ehu.animeckecker.databinding.FragmentMyNotificationBinding

class MyNotificationFragment : Fragment() {
    lateinit var binding: FragmentMyNotificationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_notification, container, false)
        return binding.root
    }

}
