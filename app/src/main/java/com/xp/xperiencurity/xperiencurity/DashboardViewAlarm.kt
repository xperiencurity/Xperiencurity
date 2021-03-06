package com.xp.xperiencurity.xperiencurity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_data_filter.*
import kotlinx.android.synthetic.main.activity_data_filter.deviceView
import kotlinx.android.synthetic.main.activity_data_filter.progressBar
import kotlinx.android.synthetic.main.activity_dashboard_view.nodevice as nodevice1

class DashboardViewAlarm : AppCompatActivity() {

    private lateinit var ref: DatabaseReference
    private lateinit var listener: ValueEventListener
    private lateinit var firebaseRecyclerAdapter: FirebaseRecyclerAdapter<RemoveDeviceModel, MyViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_view)

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
        val option = FirebaseRecyclerOptions.Builder<RemoveDeviceModel>()
            .setQuery(ref, RemoveDeviceModel::class.java)
            .setLifecycleOwner(this)
            .build()

        firebaseRecyclerAdapter = object: FirebaseRecyclerAdapter<RemoveDeviceModel, MyViewHolder>(option) {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
                val itemView = LayoutInflater.from(this@DashboardViewAlarm).inflate(R.layout.dashboard_view_alarm_layout,parent,false)
                return MyViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: RemoveDeviceModel) {
                val placeID = getRef(position).key.toString()

                if (model.type == "Alarm") {
                    nodevice.visibility = View.GONE
                }
                else {
                    holder.cardView.removeAllViews()
                }

                listener = object: ValueEventListener {
                    override fun onCancelled(dbError: DatabaseError) {
                        Toast.makeText(this@DashboardViewAlarm, "Error Occurred "+ dbError.toException(), Toast.LENGTH_SHORT).show()
                    }

                    @SuppressLint("SetTextI18n")
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (itemCount == 0) {
                            progressBar.visibility = View.VISIBLE
                        } else {
                            progressBar.visibility = View.GONE
                        }
                        holder.txtName.text = model.name
                        holder.txtDesc.text = "Version: " + model.version.toString()
                        holder.txtType.text = "Type of device: " + model.type
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
        internal var cardView: CardView = itemView!!.findViewById(R.id.cardView)
    }
}
