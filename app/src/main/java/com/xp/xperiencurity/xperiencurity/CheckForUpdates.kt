package com.xp.xperiencurity.xperiencurity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import com.xp.xperiencurity.xperiencurity.Model.DevicesToUpdate
import kotlinx.android.synthetic.main.activity_check_for_updates.*
import kotlinx.android.synthetic.main.check_for_updates_devices_layout.*


class CheckForUpdates : AppCompatActivity() {

    private lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_for_updates)

        ref = FirebaseDatabase.getInstance().reference.child("Devices")
        deviceView.layoutManager = LinearLayoutManager(this)

        firebaseData()

    }

    private fun firebaseData() {


        val option = FirebaseRecyclerOptions.Builder<DevicesToUpdate>()
            .setQuery(ref, DevicesToUpdate::class.java)
            .setLifecycleOwner(this)
            .build()


        val firebaseRecyclerAdapter = object: FirebaseRecyclerAdapter<DevicesToUpdate, MyViewHolder>(option) {


            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
                val itemView = LayoutInflater.from(this@CheckForUpdates).inflate(R.layout.check_for_updates_devices_layout,parent,false)
                return MyViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: DevicesToUpdate) {
                val placeID = getRef(position).key.toString()

                ref.child(placeID).addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(dbError: DatabaseError) {
                        Toast.makeText(this@CheckForUpdates, "Error Occurred "+ dbError.toException(), Toast.LENGTH_SHORT).show()
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if(itemCount == 0) {
                            progressBar.visibility = View.VISIBLE
                        }
                        else {
                            progressBar.visibility = View.GONE
                        }
                        holder.txtName.text = model.name
                        holder.txtDesc.text = model.desc
                    }
                })
                holder.checkBox.setOnClickListener {
                    if (holder.checkBox.isChecked) {
                        Toast.makeText(this@CheckForUpdates, holder.txtName.text.toString() + " Checked", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@CheckForUpdates, holder.txtName.text.toString() + " UnChecked", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        deviceView.adapter = firebaseRecyclerAdapter
        firebaseRecyclerAdapter.startListening()
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        internal var txtName:TextView = itemView!!.findViewById(R.id.txtName)
        internal var txtDesc:TextView = itemView!!.findViewById(R.id.txtDesc)
        internal var checkBox: CheckBox = itemView!!.findViewById(R.id.checkBox)
    }
}