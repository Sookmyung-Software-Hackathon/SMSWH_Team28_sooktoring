package com.example.sooktoring.Message

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.sooktoring.Message.Model.*
import com.example.sooktoring.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageException
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_chat_list.*
import kotlinx.android.synthetic.main.activity_chat_room.*
import kotlinx.android.synthetic.main.activity_chat_room.view.*
import kotlinx.android.synthetic.main.mentor_home.*

class ChatRoomActivity : AppCompatActivity(){
    private val TAG = ChatListActivity::class.java.simpleName
    val db = FirebaseFirestore.getInstance()
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        auth = FirebaseAuth.getInstance()

        val yourUid = intent.getStringExtra("yourUid")
        val myUid = intent.getStringExtra("myUid")
        val name = intent.getStringExtra("name")

        val adapter = GroupAdapter<GroupieViewHolder>()
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")
        //val readRef = database.getReference("message").child(myUid.toString()).child(yourUid.toString())

        val childEventListener = object:ChildEventListener{
            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                Log.d(TAG, "p0 : " + p0)
                val model = p0.getValue(ChatNewModel::class.java)
                val msg = model?.message.toString()
                val who = model?.who
                if (who == "me") {
                    adapter.add(MyChat(msg))
                }
                else { adapter.add(OtherChat(msg)) }

            }
            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }

        }

        recyclerview_chat.adapter = adapter
        //readRef.addChildEventListener(childEventListener)

        button.setOnClickListener {
            val message = editText.text.toString()

            val chat = ChatNewModel(
                myUid.toString(),
                yourUid.toString(),
                message,
                System.currentTimeMillis(),
                "me",
                //career.toString()
            )
            myRef.child(myUid.toString()).child(yourUid.toString()).push().setValue(chat)

            val chat_get = ChatNewModel(
                yourUid.toString(),
                myUid.toString(),
                message,
                System.currentTimeMillis(),
                "you",
                //career.toString()
                )

            //readRef.child(yourUid.toString()).child(myUid.toString()).push().setValue(chat_get)
            editText.setText("")
            keyboardHide()

        }

    }
        fun keyboardHide() {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(editText.windowToken, 0)
        }


}