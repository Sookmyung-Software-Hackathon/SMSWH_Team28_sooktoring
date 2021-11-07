package com.example.sooktoring.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sooktoring.Model.CaDTO
import com.example.sooktoring.Model.userModel
import com.example.sooktoring.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.ca_recyclerview_one.view.*
import kotlinx.android.synthetic.main.fragment_mypage.view.*
import kotlinx.android.synthetic.main.main_mentoring_fragment.view.*
import kotlinx.android.synthetic.main.mentor_home.view.*

class MyPageFragment : Fragment() {

    var firestore: FirebaseFirestore? = null
    var uid : String? = null


    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = LayoutInflater.from(activity).inflate(R.layout.fragment_mypage,container,false)

        firestore = FirebaseFirestore.getInstance()
        uid = FirebaseAuth.getInstance().currentUser?.uid



//        view.part01_recycler.adapter = DetailViewRecyclerViewAdapter()
//        view.part01_recycler.layoutManager = LinearLayoutManager(activity).also { it.orientation = LinearLayoutManager.HORIZONTAL }


        return view
    }

//    inner class DetailViewRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//
//        var UserModel : ArrayList<userModel> = arrayListOf()
//
//
//        init {
//            firestore?.collection("userInfo")?.orderBy("careerTag")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
//                UserModel.clear()
//
//                //contentUidList.clear()
//
//                //Sometimes, This code return null of querySnapshot when it signout
//                if(querySnapshot == null) return@addSnapshotListener
//
//                for(snapshot in querySnapshot!!.documents){
//                    var item = snapshot.toObject(userModel::class.java)
//                    UserModel.add(item!!)
//                }
//            }
//        }
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//
//            var view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_mypage, parent, false)
//            return CustomViewHolder(view)
//        }
//
//        inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view)
//
//        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//            var viewholder = (holder as CustomViewHolder).itemView
//
//            //User Career
//            viewholder.tv_user_name.text = UserModel!![position].career
//
//            //User Name
//            viewholder.mentor_tv_name.text = UserModel!![position].uname
//
//            //ProfileImages
//            if (UserModel!![position].imageUrl == null) {
//                viewholder.mentor_iv_profile.setImageResource(R.drawable.ic_init_profile)
//            } else {
//                Glide.with(holder.itemView.context).load(UserModel!![position].imageUrl).into(viewholder.mentor_iv_profile)
//            }
//
//            //rank
//            viewholder.tv_user_ranking.text = UserModel!![position].urank.plus(" 등급")
//
//            //rankimg
//            if (UserModel!![position].urank == "눈덩이") {
//                viewholder.iv_user_rank.setImageResource(R.drawable.ic_noondung)
//            } else if (UserModel!![position].urank == "눈송이") {
//                viewholder.iv_user_rank.setImageResource(R.drawable.img_snowflower)
//            } else if (UserModel!![position].urank == "퀸송이") {
//                viewholder.iv_user_rank.setImageResource(R.drawable.img_queensong)
//            }
//        }
//
//        override fun getItemCount(): Int {
//            return UserModel.size
//        }
//    }

}