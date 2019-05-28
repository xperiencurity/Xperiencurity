package com.xp.xperiencurity.xperiencurity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import com.xp.xperiencurity.xperiencurity.Model.DataFilterAlarmAdapter
import kotlinx.android.synthetic.main.activity_check_for_updates.*

class DataFilterAlarm : AppCompatActivity() {

    private lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_filter_alarm)

        ref = FirebaseDatabase.getInstance().reference.child("Device(Alarm)")
        deviceView.layoutManager = LinearLayoutManager(this)

        firebaseData()
    }


    private fun firebaseData() {
        /*val checkedDevices = ArrayList<String>()
        lateinit var curDevice: String*/

        val option = FirebaseRecyclerOptions.Builder<DataFilterAlarmAdapter>()
            .setQuery(ref, DataFilterAlarmAdapter::class.java)
            .setLifecycleOwner(this)
            .build()


        val firebaseRecyclerAdapter = object: FirebaseRecyclerAdapter<DataFilterAlarmAdapter, MyViewHolder>(option) {


            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
                val itemView = LayoutInflater.from(this@DataFilterAlarm).inflate(R.layout.data_filter_alarm_layout,parent,false)
                return MyViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: DataFilterAlarmAdapter) {
                val placeID = getRef(position).key.toString()

                ref.child(placeID).addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(dbError: DatabaseError) {
                        Toast.makeText(this@DataFilterAlarm, "Error Occurred "+ dbError.toException(), Toast.LENGTH_SHORT).show()
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if(itemCount == 0) {
                            progressBar.visibility = View.VISIBLE
                        }
                        else {
                            progressBar.visibility = View.GONE
                        }
                        holder.txtName.text = model.name
                        holder.txtDesc.text = model.version
                    }
                })

                /*holder.checkBox.setOnClickListener {
                    curDevice = holder.txtName.text.toString()
                    if (holder.checkBox.isChecked) {
                        Toast.makeText(this@DataFilterAlarm, "$curDevice Checked", Toast.LENGTH_SHORT).show()
                        checkedDevices.add(curDevice.toLowerCase())
                    } else {
                        Toast.makeText(this@DataFilterAlarm,  "$curDevice UnChecked", Toast.LENGTH_SHORT).show()
                        checkedDevices.remove(curDevice.toLowerCase())
                    }
                }*/
            }
        }
        deviceView.adapter = firebaseRecyclerAdapter
        firebaseRecyclerAdapter.startListening()
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        internal var txtName: TextView = itemView!!.findViewById(R.id.txtName)
        internal var txtDesc: TextView = itemView!!.findViewById(R.id.txtDesc)
    }
}
