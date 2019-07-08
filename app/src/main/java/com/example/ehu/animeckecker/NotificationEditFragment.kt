package com.example.ehu.animeckecker


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.ehu.animeckecker.databinding.FragmentNotificationEditBinding


class NotificationEditFragment : Fragment() {
    lateinit var binding: FragmentNotificationEditBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notification_edit, container, false)
        binding.create.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_global_notificationEditFragment)
        }
        binding.cancel.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_notificationEditFragment_to_thisSeasonFragment)
        }
        return binding.root
    }
}
