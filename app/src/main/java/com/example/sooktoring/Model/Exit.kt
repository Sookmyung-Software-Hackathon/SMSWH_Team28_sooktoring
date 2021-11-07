package com.example.sooktoring.Model

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.sooktoring.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_mypage.*

class Exit : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var auth = FirebaseAuth.getInstance()

        exit.setOnClickListener {
            auth.currentUser?.delete()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()

        }
    }

}