package com.example.sooktoring

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.sooktoring.Model.ChatDTO
import com.example.sooktoring.Model.userActivityModel
import com.example.sooktoring.Model.userCareerModel
import com.example.sooktoring.Model.userModel
import com.example.sooktoring.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private var mBinding: ActivityRegisterBinding? = null
    private val binding get() = mBinding!!

    var auth: FirebaseAuth? = null
    var firestore : FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.tvPasswordErrorMsg.visibility = View.GONE
        binding.tvCheckboxErrorMsg.visibility = View.GONE

        binding.btnJoin.setOnClickListener {
            registerEvent()
        }

        binding.cbCheckAll.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.cbCheck1.isChecked = true
                binding.cbCheck2.isChecked = true
                binding.cbCheck3.isChecked = true
            } else {
                binding.cbCheck1.isChecked = false
                binding.cbCheck2.isChecked = false
                binding.cbCheck3.isChecked = false
            }
        }

    }

    fun registerEvent() {

        var UserName = binding.etName.text.toString()
        var UserEmail = binding.etMail.text.toString()
        var UserPassword = binding.etPassword.text.toString()
        var UserPassword_check = binding.etPasswordCheck.text.toString()
        var UserFirstMajor = binding.firstMajor.text.toString()
        var UserAdminDate = binding.admin.text.toString()

        val database : FirebaseDatabase = FirebaseDatabase.getInstance()


        // EditText 공백 체크
        if (UserName.equals("")) {
            binding.etName.setBackgroundResource(R.drawable.edittext_background_error)
        } else {
            binding.etName.setBackgroundResource(R.drawable.edittext_background)
        }

        if (UserEmail.equals("")) {
            binding.etMail.setBackgroundResource(R.drawable.edittext_background_error)
        } else {
            binding.etMail.setBackgroundResource(R.drawable.edittext_background)
        }

        if (UserPassword.equals("")) {
            binding.etPassword.setBackgroundResource(R.drawable.edittext_background_error)
        } else {
            binding.etPassword.setBackgroundResource(R.drawable.edittext_background)
        }

        if (UserPassword_check.equals("") || UserPassword_check.equals(UserPassword) == false) {
            binding.etPasswordCheck.setBackgroundResource(R.drawable.edittext_background_error)

            if(UserPassword_check.equals(UserPassword) == false) {
                binding.tvPasswordErrorMsg.visibility = View.VISIBLE
            } else {
                binding.tvPasswordErrorMsg.visibility = View.GONE
            }
        } else {
            binding.etPasswordCheck.setBackgroundResource(R.drawable.edittext_background)

            if(UserPassword.equals(UserPassword_check)) {
                binding.tvPasswordErrorMsg.visibility = View.GONE
            }
        }

        if (UserFirstMajor.equals("")) {
            binding.firstMajor.setBackgroundResource(R.drawable.edittext_background_error)
        } else {
            binding.etPassword.setBackgroundResource(R.drawable.edittext_background)
        }

        if (UserAdminDate.equals("")) {
            binding.admin.setBackgroundResource(R.drawable.edittext_background_error)
        } else {
            binding.admin.setBackgroundResource(R.drawable.edittext_background)
        }

        val check = binding.cbCheck1.isChecked == true && binding.cbCheck2.isChecked == true && binding.cbCheck3.isChecked == true

        if (binding.cbCheck1.isChecked == true && binding.cbCheck2.isChecked == true && binding.cbCheck3.isChecked == true) {
            binding.tvCheckboxErrorMsg.visibility = View.GONE
        } else {
            binding.tvCheckboxErrorMsg.visibility = View.VISIBLE
        }

        if (binding.cbCheck1.isSelected == true) {
            Toast.makeText(this, "cbCheck1 성공", Toast.LENGTH_LONG).show()
        }


        if (UserName.equals("") != null && UserEmail.equals("") != null && UserPassword.equals("") != null && UserPassword_check.equals("") != null
            && check == true && UserFirstMajor.equals("") != null && UserAdminDate.isEmpty() == false) {
            auth?.createUserWithEmailAndPassword(UserEmail,
                binding.etPassword.text.toString())?.addOnCompleteListener {
                    task ->
                if(task.isSuccessful) {
                    var chatDTO = ChatDTO()
                    var mUserModel = userModel()
                    var mUserActivityModel = userActivityModel()
                    var mUserCareerModel = userCareerModel()
                    val uid = FirebaseAuth.getInstance().uid?:""

                    //chatDTO
                    chatDTO.uName = UserName
                    chatDTO.uid = uid

                    firestore = FirebaseFirestore.getInstance()
                    firestore?.collection("users")?.document()?.set(chatDTO)

                    //userModel
                    mUserModel.uname = UserName
                    mUserModel.uid = uid
                    mUserModel.userId = UserEmail
                    mUserModel.urank = "눈덩이"
                    mUserModel.firstMajor = UserFirstMajor
                    mUserModel.admin = UserAdminDate
                    firestore?.collection("userInfo")?.document()?.set(mUserModel)

                    //userActivityModel
                    mUserActivityModel.uname = UserName
                    mUserActivityModel.uid = uid
                    firestore?.collection("userActivity")?.document()?.set(mUserActivityModel)


                    //userCareerModel
                    mUserCareerModel.uname = UserName
                    mUserCareerModel.uid = uid
                    firestore?.collection("userCareer")?.document()?.set(mUserCareerModel)

                    // Creating a user account
                    moveRegisterNextPage(task.result?.user)
                } else if(task.exception?.message.isNullOrEmpty()){
                    // Show the error message
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                } else {
                    // Login if you have account
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun moveRegisterNextPage(user: FirebaseUser?){
        if(user != null) {
            startActivity(Intent(this, Register_verif_email::class.java))
            finish()
        }
    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }
}