package com.xp.xperiencurity.xperiencurity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_data_filter.*
import kotlinx.android.synthetic.main.data_filter_layout.*

@Suppress("UNUSED_ANONYMOUS_PARAMETER")
class DataFilter : AppCompatActivity() {

    private lateinit var ref: DatabaseReference
    private lateinit var listener: ValueEventListener
    private lateinit var firebaseRecyclerAdapter: FirebaseRecyclerAdapter<DataFilterModel, MyViewHolder>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_filter)

        ref = FirebaseDatabase.getInstance().reference.child("Device")
        deviceView.layoutManager = LinearLayoutManager(this)

        firebaseData()
    }

    override fun onDestroy() {
        ref.removeEventListener(listener)
        firebaseRecyclerAdapter.stopListening()
        super.onDestroy()
    }

    private fun firebaseData() {

        val option = FirebaseRecyclerOptions.Builder<DataFilterModel>()
            .setQuery(ref, DataFilterModel::class.java)
            .setLifecycleOwner(this)
            .build()


        firebaseRecyclerAdapter = object: FirebaseRecyclerAdapter<DataFilterModel, MyViewHolder>(option) {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
                val itemView = LayoutInflater.from(this@DataFilter).inflate(R.layout.data_filter_layout,parent,false)
                return MyViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: DataFilterModel) {
                val placeID = getRef(position).key.toString()

                if (model.type == "Alarm" || model.type == "Camera" || model.type == "Light" || model.type == "Speaker" || model.type == "TV" || model.type == "Thermostat") {
                    nodevice.visibility = View.GONE
                }

                listener = object: ValueEventListener {
                    override fun onCancelled(dbError: DatabaseError) {
                        Toast.makeText(this@DataFilter, "Error Occurred "+ dbError.toException(), Toast.LENGTH_SHORT).show()
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (itemCount == 0) {
                            progressBar.visibility = View.VISIBLE
                        } else {
                            progressBar.visibility = View.GONE
                        }
                        holder.txtName.text = model.name
                        holder.txtDesc.text = "Version: " + model.version.toString()
                        holder.txtType.text = "Type of device: " + model.type
                        if (dataSnapshot.hasChild("data_logging")) {
                            holder.switchfordatalog.isChecked = true
                        }
                        if (dataSnapshot.hasChild("location_tracking")) {
                            holder.switchforlocationtracking.isChecked = true
                        }
                        if (dataSnapshot.hasChild("history_tracking")) {
                            holder.switchforhistorytracking.isChecked = true
                        }
                        if (dataSnapshot.hasChild("ad_sense")) {
                            holder.switchforadsense.isChecked = true
                        }

                        holder.switchfordatalog.setOnCheckedChangeListener {buttonView, isChecked ->
                            if (isChecked) {
                                ref.child("$placeID/data_logging").setValue(true)
                            } else {
                                ref.child("$placeID/data_logging").removeValue()
                            }
                        }
                        holder.switchforlocationtracking.setOnCheckedChangeListener {buttonView, isChecked ->
                            if (isChecked) {
                                ref.child("$placeID/location_tracking").setValue(true)
                            } else {
                                ref.child("$placeID/location_tracking").removeValue()
                            }
                        }
                        holder.switchforhistorytracking.setOnCheckedChangeListener {buttonView, isChecked ->
                            if (isChecked) {
                                ref.child("$placeID/history_tracking").setValue(true)
                            } else {
                                ref.child("$placeID/history_tracking").removeValue()
                            }
                        }
                        holder.switchforadsense.setOnCheckedChangeListener {buttonView, isChecked ->
                            if (isChecked) {
                                ref.child("$placeID/ad_sense").setValue(true)
                            } else {
                                ref.child("$placeID/ad_sense").removeValue()
                            }
                        }
                    }
                }
                ref.child(placeID).addValueEventListener(listener)
            }
        }
        deviceView.adapter = firebaseRecyclerAdapter
        firebaseRecyclerAdapter.startListening()
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        internal var txtName: TextView = itemView!!.findViewById(R.id.txtName)
        internal var txtDesc: TextView = itemView!!.findViewById(R.id.txtDesc)
        internal var txtType: TextView = itemView!!.findViewById(R.id.txtType)
        internal var switchfordatalog: Switch = itemView!!.findViewById(R.id.switchfordatalog)
        internal var switchforlocationtracking: Switch = itemView!!.findViewById(R.id.switchforlocationtracking)
        internal var switchforhistorytracking: Switch = itemView!!.findViewById(R.id.switchforhistorytracking)
        internal var switchforadsense: Switch = itemView!!.findViewById(R.id.switchforadsense)
    }
}
