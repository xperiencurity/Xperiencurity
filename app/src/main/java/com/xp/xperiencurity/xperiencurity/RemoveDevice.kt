package com.xp.xperiencurity.xperiencurity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import kotlinx.android.synthetic.main.activity_remove_device.*

class RemoveDevice : AppCompatActivity() {

    private lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remove_device)

        ref = FirebaseDatabase.getInstance().reference.child("Device")
        deviceView.layoutManager = LinearLayoutManager(this)

        firebaseData()
    }

    private fun firebaseData() {

        val option = FirebaseRecyclerOptions.Builder<RemoveDeviceModel>()
            .setQuery(ref, RemoveDeviceModel::class.java)
            .setLifecycleOwner(this)
            .build()


        val firebaseRecyclerAdapter = object: FirebaseRecyclerAdapter<RemoveDeviceModel, MyViewHolder>(option) {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
                val itemView = LayoutInflater.from(this@RemoveDevice).inflate(R.layout.remove_device_layout,parent,false)
                return MyViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: RemoveDeviceModel) {
                val placeID = getRef(position).key.toString()

                ref.child(placeID).addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(dbError: DatabaseError) {
                        Toast.makeText(this@RemoveDevice, "Error Occurred "+ dbError.toException(), Toast.LENGTH_SHORT).show()
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if(itemCount == 0) {
                            progressBar.visibility = View.VISIBLE
                        }
                        else {
                            progressBar.visibility = View.GONE
                        }
                        holder.txtName.text = model.name
                        holder.txtDesc.text = model.version.toString()
                        holder.txtType.text = model.type

                    }
                })

                /*holder.checkBox.setOnClickListener {
                    curDevice = holder.txtName.text.toString()
                    if (holder.checkBox.isChecked) {
                        checkedDevices.add(curDevice.toLowerCase())
                    } else {
                        checkedDevices.remove(curDevice.toLowerCase())
                    }
                }*/

                holder.checkBox.setOnClickListener {
                    if (holder.checkBox.isChecked) {
                        Toast.makeText(this@RemoveDevice, "${model.name} has been checked", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@RemoveDevice, "${model.name} has been unchecked", Toast.LENGTH_SHORT).show()
                    }
                    removebutton.setOnClickListener {
                        if (holder.checkBox.isChecked) {
                            ref.child(placeID).removeValue()
                        }
                    }
                }



            }
        }

        deviceView.adapter = firebaseRecyclerAdapter
        firebaseRecyclerAdapter.startListening()
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        internal var txtName: TextView = itemView!!.findViewById(R.id.txtName)
        internal var txtDesc: TextView = itemView!!.findViewById(R.id.txtDesc)
        internal var txtType: TextView = itemView!!.findViewById(R.id.txtType)
        internal var checkBox: CheckBox = itemView!!.findViewById(R.id.checkBox)
    }
}
