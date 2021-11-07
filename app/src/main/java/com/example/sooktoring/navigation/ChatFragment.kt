package com.example.sooktoring.navigation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sooktoring.Message.ChatListActivity
import com.example.sooktoring.R

class ChatFragment : Fragment() {
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_chat,container,false)
        val intent = Intent (requireActivity(), ChatListActivity::class.java)
        requireActivity()?.startActivity(intent)

        return view
    }
}