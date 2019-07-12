package com.example.ehu.animeckecker


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.ehu.animeckecker.databinding.FragmentMyNotificationBinding

class MyNotificationFragment : Fragment() {
    lateinit var binding: FragmentMyNotificationBinding
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

}
