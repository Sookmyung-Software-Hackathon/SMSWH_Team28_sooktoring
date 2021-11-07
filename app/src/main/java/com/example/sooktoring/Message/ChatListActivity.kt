package com.example.sooktoring.Message

import android.content.Intent
import android.os.Bundle
import android.util.Log.e
import androidx.appcompat.app.AppCompatActivity
import com.example.sooktoring.Message.Model.User
import com.example.sooktoring.Message.Model.UserItem
import com.example.sooktoring.R
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.logging.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_chat_list.*
import kotlinx.android.synthetic.main.activity_chat_room.*
import kotlinx.android.synthetic.main.fragment_chat.*
import kotlinx.android.synthetic.main.my_chatbox.*


class ChatListActivity :AppCompatActivity(){
    private val TAG = ChatListActivity::class.java.simpleName
    val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_list)

            /*myChatList.setOnClickListner{
            val intent = Intent(this,MyRoomActivity::class.java)
            startActivity(intent)
        }*/

        /*button.setOnClickListener {
            val intent = Intent(this, ChatRoomActivity::class.java)
            startActivity(intent)
        }*/
        val adapter = GroupAdapter<GroupieViewHolder>()


        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for(document in result){
                    adapter.add(UserItem(document.get("uname").toString(), document.get("uid").toString()))
                    android.util.Log.d(TAG,document.get("uname").toString())
                }
                recyclerview_list.adapter = adapter
            }

        adapter.setOnItemClickListener{item,view ->
            val uid = (item as UserItem).uid
            val name = (item as UserItem).name
            val intent = Intent(this,ChatRoomActivity::class.java)
            intent.putExtra("yourUid",uid)
            intent.putExtra("name",name)
            startActivity(intent)
        }
    }
}