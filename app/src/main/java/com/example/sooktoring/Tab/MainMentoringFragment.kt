package com.example.sooktoring.Tab

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sooktoring.Model.userModel
import com.example.sooktoring.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.main_club_fragment.*
import kotlinx.android.synthetic.main.main_mentoring_fragment.*
import kotlinx.android.synthetic.main.main_mentoring_fragment.view.*
import kotlinx.android.synthetic.main.mentor_home.view.*

class MainMentoringFragment : Fragment() {

    var firestore: FirebaseFirestore? = null
    var uid : String? = null

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        var view = inflater.inflate(R.layout.main_mentoring_fragment, container, false)

        firestore = FirebaseFirestore.getInstance()
        uid = FirebaseAuth.getInstance().currentUser?.uid

        view.part01_recycler.adapter = DetailViewRecyclerViewAdapter()
        view.part01_recycler.layoutManager = LinearLayoutManager(activity).also { it.orientation = LinearLayoutManager.HORIZONTAL }

        view.part02_recycler.adapter = DetailViewRecyclerViewAdapter()
        view.part02_recycler.layoutManager = LinearLayoutManager(activity).also { it.orientation = LinearLayoutManager.HORIZONTAL }

        view.part03_recycler.adapter = DetailViewRecyclerViewAdapter()
        view.part03_recycler.layoutManager = LinearLayoutManager(activity).also { it.orientation = LinearLayoutManager.HORIZONTAL }

        view.part04_recycler.adapter = DetailViewRecyclerViewAdapter()
        view.part04_recycler.layoutManager = LinearLayoutManager(activity).also { it.orientation = LinearLayoutManager.HORIZONTAL }


        val spaceDecoration = VerticalSpaceItemDecoration(20)
        view.part01_recycler.addItemDecoration(spaceDecoration)
        view.part02_recycler.addItemDecoration(spaceDecoration)
        view.part03_recycler.addItemDecoration(spaceDecoration)
        view.part04_recycler.addItemDecoration(spaceDecoration)

        return view
    }

    inner class VerticalSpaceItemDecoration(private val verticalSpaceHeight: Int) :
        RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.right = verticalSpaceHeight
        }
    }

    inner class DetailViewRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        var UserModel : ArrayList<userModel> = arrayListOf()
        var contentUidList : ArrayList<String> = arrayListOf()

        init {
            firestore?.collection("userInfo")?.orderBy("uid")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                UserModel.clear()
                contentUidList.clear()

                //Sometimes, This code return null of querySnapshot when it signout
                if(querySnapshot == null) return@addSnapshotListener

                for(snapshot in querySnapshot!!.documents){
                    var item = snapshot.toObject(userModel::class.java)
                    UserModel.add(item!!)
                    contentUidList.add(snapshot.id)
                }
                notifyDataSetChanged()
            }
        }

        override fun getItemViewType(position: Int): Int {
            if (UserModel!![position].careerTag == "UXUI") {
                return 0
            } else if (UserModel!![position].careerTag == "ADV") {
                return 1
            } else if (UserModel!![position].careerTag == "BRAND") {
                return 2
            } else if (UserModel!![position].careerTag == "FREE") {
                return 3
            } else {
                return 99
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

            var view = LayoutInflater.from(parent.context).inflate(R.layout.mentor_home, parent, false)
            return CustomViewHolder(view)
        }

        inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view)

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewholder = (holder as CustomViewHolder).itemView

            println("holder: " + holder)

            if(UserModel!![position].careerTag == "UXUI") {
                //User id
                viewholder.career.text = UserModel!![position].career

                viewholder.name.text = UserModel!![position].uname

                //ProfileImages
                if (UserModel!![position].imageUrl == null) {
                    viewholder.home_img_profile.setImageResource(R.drawable.ic_init_profile)
                } else {
                    Glide.with(holder.itemView.context).load(UserModel!![position].imageUrl).into(viewholder.home_img_profile)
                }

                //rank
                viewholder.tv_user_rank.text = UserModel!![position].urank

                //rankimg
                if (UserModel!![position].urank == "눈덩이") {
                    viewholder.iv_user_rank.setImageResource(R.drawable.ic_noondung)
                } else if (UserModel!![position].urank == "눈송이") {
                    viewholder.iv_user_rank.setImageResource(R.drawable.img_snowflower)
                } else if (UserModel!![position].urank == "퀸송이") {
                    viewholder.iv_user_rank.setImageResource(R.drawable.img_queensong)
                }
            }

            if(UserModel!![position].careerTag == "ADV") {
                //User id
                viewholder.career.text = UserModel!![position].career

                viewholder.name.text = UserModel!![position].uname

                //ProfileImages
                if (UserModel!![position].imageUrl == null) {
                    viewholder.home_img_profile.setImageResource(R.drawable.ic_init_profile)
                } else {
                    Glide.with(holder.itemView.context).load(UserModel!![position].imageUrl).into(viewholder.home_img_profile)
                }

                //rank
                viewholder.tv_user_rank.text = UserModel!![position].urank

                //rankimg
                if (UserModel!![position].urank == "눈덩이") {
                    viewholder.iv_user_rank.setImageResource(R.drawable.ic_noondung)
                } else if (UserModel!![position].urank == "눈송이") {
                    viewholder.iv_user_rank.setImageResource(R.drawable.img_snowflower)
                } else if (UserModel!![position].urank == "퀸송이") {
                    viewholder.iv_user_rank.setImageResource(R.drawable.img_queensong)
                }
            }

            if(UserModel!![position].careerTag == "BRAND") {
                //User id
                viewholder.career.text = UserModel!![position].career

                viewholder.name.text = UserModel!![position].uname

                //ProfileImages
                if (UserModel!![position].imageUrl == null) {
                    viewholder.home_img_profile.setImageResource(R.drawable.ic_init_profile)
                } else {
                    Glide.with(holder.itemView.context).load(UserModel!![position].imageUrl).into(viewholder.home_img_profile)
                }

                //rank
                viewholder.tv_user_rank.text = UserModel!![position].urank

                //rankimg
                if (UserModel!![position].urank == "눈덩이") {
                    viewholder.iv_user_rank.setImageResource(R.drawable.ic_noondung)
                } else if (UserModel!![position].urank == "눈송이") {
                    viewholder.iv_user_rank.setImageResource(R.drawable.img_snowflower)
                } else if (UserModel!![position].urank == "퀸송이") {
                    viewholder.iv_user_rank.setImageResource(R.drawable.img_queensong)
                }
            }

            if(UserModel!![position].careerTag == "FREE") {
                //User id
                viewholder.career.text = UserModel!![position].career

                viewholder.name.text = UserModel!![position].uname

                //ProfileImages
                if (UserModel!![position].imageUrl == null) {
                    viewholder.home_img_profile.setImageResource(R.drawable.ic_init_profile)
                } else {
                    Glide.with(holder.itemView.context).load(UserModel!![position].imageUrl).into(viewholder.home_img_profile)
                }

                //rank
                viewholder.tv_user_rank.text = UserModel!![position].urank

                //rankimg
                if (UserModel!![position].urank == "눈덩이") {
                    viewholder.iv_user_rank.setImageResource(R.drawable.ic_noondung)
                } else if (UserModel!![position].urank == "눈송이") {
                    viewholder.iv_user_rank.setImageResource(R.drawable.img_snowflower)
                } else if (UserModel!![position].urank == "퀸송이") {
                    viewholder.iv_user_rank.setImageResource(R.drawable.img_queensong)
                }
            }

        }

        override fun getItemCount(): Int {
            return UserModel.size
        }

    }


}