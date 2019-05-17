package com.xp.xperiencurity.xperiencurity

import android.os.Bundle
import android.util.Log
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
import kotlinx.android.synthetic.main.activity_check_for_updates.*


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

        val checkedDevices = ArrayList<String>()
        lateinit var curDevice: String

        val option = FirebaseRecyclerOptions.Builder<DevicesToUpdateModel>()
            .setQuery(ref, DevicesToUpdateModel::class.java)
            .setLifecycleOwner(this)
            .build()


        val firebaseRecyclerAdapter = object: FirebaseRecyclerAdapter<DevicesToUpdateModel, MyViewHolder>(option) {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
                val itemView = LayoutInflater.from(this@CheckForUpdates).inflate(R.layout.check_for_updates_devices_layout,parent,false)
                return MyViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: DevicesToUpdateModel) {
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
                    curDevice = holder.txtName.text.toString()
                    if (holder.checkBox.isChecked) {
                        Toast.makeText(this@CheckForUpdates, "$curDevice Checked", Toast.LENGTH_SHORT).show()
                        checkedDevices.add(curDevice.toLowerCase())
                    } else {
                        Toast.makeText(this@CheckForUpdates,  "$curDevice UnChecked", Toast.LENGTH_SHORT).show()
                        checkedDevices.remove(curDevice.toLowerCase())
                    }
                }

                checkForUpdatesBtn.setOnClickListener {
                    checkedDevices.forEach {
                        Log.d("CheckForUpdates", it)
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
/*
    private fun downloadFirmware() {
        val storage = FirebaseStorage.getInstance()
        // Create a storage reference from our app
        val storageRef = storage.reference

        val deviceName = holder.txtName.text.toString().toLowerCase()
        // Create a reference with an initial file path and name
        val pathReference = storageRef.child("firmware/" + deviceName + "png")

        val localFile = File.createTempFile("firmwares", "png")

        pathReference.getFile(localFile).addOnSuccessListener {
            // Local temp file has been created
        }.addOnFailureListener {
            // Handle any errors
        }
    }
*/
}