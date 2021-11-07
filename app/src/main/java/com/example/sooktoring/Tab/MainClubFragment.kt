package com.example.sooktoring.Tab

import android.graphics.Insets.add
import android.graphics.Rect
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.Insets.add
import androidx.core.view.OneShotPreDrawListener.add
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
import kotlinx.android.synthetic.main.item_ca_recycler.view.*
import kotlinx.android.synthetic.main.main_club_fragment.*
import kotlinx.android.synthetic.main.main_club_fragment.view.*
import kotlinx.android.synthetic.main.main_mentoring_fragment.view.*
import kotlinx.android.synthetic.main.mentor_home.view.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import kotlin.collections.ArrayList
import java.util.Locale

class MainClubFragment : Fragment() {

    var firestore: FirebaseFirestore? = null
    var uid : String? = null

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {

        var view = inflater.inflate(R.layout.main_club_fragment, container, false)

        firestore = FirebaseFirestore.getInstance()
        uid = FirebaseAuth.getInstance().currentUser?.uid

        view.ca_part01_recycler.adapter = DetailViewRecyclerViewAdapter()
        view.ca_part01_recycler.layoutManager = LinearLayoutManager(activity).also { it.orientation = LinearLayoutManager.HORIZONTAL }

        view.ca_part02_recycler.adapter = DetailViewRecyclerViewAdapter2()
        view.ca_part02_recycler.layoutManager = LinearLayoutManager(activity).also { it.orientation = LinearLayoutManager.VERTICAL }

        val spaceDecoration = HorizontalSpaceItemDecoration(20)
        val spaceDecoration2 = VerticalSpaceItemDecoration(20)
        view.ca_part01_recycler.addItemDecoration(spaceDecoration)
        view.ca_part02_recycler.addItemDecoration(spaceDecoration2)

        return view
    }

    inner class DetailViewRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        var caDTOList : ArrayList<CaDTO> = arrayListOf()

        init {
            firestore?.collection("caInfo")?.orderBy("EndRecruit")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                caDTOList.clear()

                //Sometimes, This code return null of querySnapshot when it signout
                if(querySnapshot == null) return@addSnapshotListener

                for(snapshot in querySnapshot!!.documents){
                    var item = snapshot.toObject(CaDTO::class.java)
                    caDTOList.add(item!!)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.ca_recyclerview_one, parent, false)
                return CustomViewHolder(view)
        }

        inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view)

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewholder = (holder as CustomViewHolder).itemView

            viewholder.ca_engName.text = caDTOList!![position].caEngname
            viewholder.ca_tv_ctype.text = caDTOList!![position].catype
            viewholder.ca_tv_name.text = caDTOList!![position].caname
            viewholder.ca_tv_inAndout.text = caDTOList!![position].cainNout

        }

        override fun getItemCount(): Int {
            return caDTOList.size
        }

    }


    inner class HorizontalSpaceItemDecoration(private val verticalSpaceHeight: Int) :
        RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.right = verticalSpaceHeight
        }
    }

    inner class VerticalSpaceItemDecoration(private val verticalSpaceHeight: Int) :
        RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect, view: View, parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.bottom = verticalSpaceHeight
        }
    }

//    inner class DetailViewRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//
//            var caDTO : ArrayList<CaDTO> = arrayListOf()
//
//            init {
//                firestore?.collection("caInfo")?.orderBy("EndRecruit")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
//                    caDTO.clear()
//
//                    //Sometimes, This code return null of querySnapshot when it signout
//                    if(querySnapshot == null) return@addSnapshotListener
//
//                    for(snapshot in querySnapshot!!.documents){
//                        var item = snapshot.toObject(CaDTO::class.java)
//                        caDTO.add(item!!)
//                    }
//                }
//            }
//
//            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//                var view = LayoutInflater.from(parent.context).inflate(R.layout.ca_recyclerview_one, parent, false)
//                return CustomViewHolder(view)
//            }
//
//            inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view)
//
//            override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//                var viewholder = (holder as CustomViewHolder).itemView
//
//                viewholder.ca_engName.text = caDTO!![position].caEngname
//                viewholder.ca_tv_ctype.text = caDTO!![position].catype
//                viewholder.ca_tv_name.text = caDTO!![position].caname
//                viewholder.ca_tv_inAndout.text = caDTO!![position].cainNout
//            }
//
//            override fun getItemCount(): Int {
//                return caDTO.size
//            }
//    }

    inner class DetailViewRecyclerViewAdapter2 : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        var caDTO: ArrayList<CaDTO> = arrayListOf()

        init {
            firestore?.collection("caInfo")?.orderBy("EndRecruit")
                ?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    caDTO.clear()

                    //contentUidList.clear()

                    //Sometimes, This code return null of querySnapshot when it signout
                    if (querySnapshot == null) return@addSnapshotListener

                    for (snapshot in querySnapshot!!.documents) {
                        var item = snapshot.toObject(CaDTO::class.java)
                        caDTO.add(item!!)
//                        var item = snapshot.toObject(CaDTO::class.java)
//                        caDTO.add(item!!)
                    }
                }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

            var view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_ca_recycler, parent, false)
            return CustomViewHolder(view)
        }

        inner class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view)

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewholder = (holder as CustomViewHolder).itemView

            viewholder.ca2_EngName.text = caDTO!![position].caEngname
            viewholder.ca2_type.text = caDTO!![position].catype
            viewholder.ca2_name.text = caDTO!![position].caname

            var startRecruit = caDTO!![position].startRecruit.toString()
            var endRecruit = caDTO!![position].EndRecruit.toString()
            var newline = "\n~"

            viewholder.ca2_recruit_period.text = startRecruit.plus(newline).plus(endRecruit)
        }


        override fun getItemCount(): Int {
            return caDTO.size
        }
    }
}