package com.example.sooktoring.Message.Model

import com.example.sooktoring.R
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.my_chatbox.view.*

class MyChat(val msg: String) : Item<GroupieViewHolder>() {
    override fun getLayout(): Int {
        return R.layout.my_chatbox
    }

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.tv_msg.text = msg
    }

}