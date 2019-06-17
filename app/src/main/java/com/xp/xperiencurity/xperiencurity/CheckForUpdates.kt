package com.xp.xperiencurity.xperiencurity

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment.DIRECTORY_DOWNLOADS
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
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_check_for_updates.*
import java.io.File
import android.os.Environment.getExternalStorageDirectory
import com.google.firebase.storage.StorageReference
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.*

class CheckForUpdates : AppCompatActivity(), CoroutineScope by MainScope() {

    private lateinit var ref: DatabaseReference
    private lateinit var storageRef: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_for_updates)

        ref = FirebaseDatabase.getInstance().reference.child("Device")
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

        val firebaseRecyclerAdapter = object : FirebaseRecyclerAdapter<DevicesToUpdateModel, MyViewHolder>(option) {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
                val itemView = LayoutInflater.from(this@CheckForUpdates)
                    .inflate(R.layout.check_for_updates_devices_layout, parent, false)
                return MyViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: DevicesToUpdateModel) {
                val placeID = getRef(position).key.toString()

                ref.child(placeID).addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(dbError: DatabaseError) {
                        Toast.makeText(
                            this@CheckForUpdates,
                            "Error Occurred " + dbError.toException(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (itemCount == 0) {
                            progressBar.visibility = View.VISIBLE
                        } else {
                            progressBar.visibility = View.GONE
                        }
                        holder.txtName.text = model.name
                        holder.txtDesc.text = model.desc
                    }
                })

                holder.checkBox.setOnClickListener {
                    curDevice = holder.txtName.text.toString()
                    if (holder.checkBox.isChecked) {
                        checkedDevices.add(curDevice.toLowerCase())
                    } else {
                        checkedDevices.remove(curDevice.toLowerCase())
                    }
                }

                checkForUpdatesBtn.setOnClickListener {
                    reqPermission()
                    if (chkPermission()) {
                        for (child in checkedDevices) {
                            download(child)
                        }
                    }
                }
            }
        }
        deviceView.adapter = firebaseRecyclerAdapter
        firebaseRecyclerAdapter.startListening()
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        internal var txtName = itemView!!.findViewById<TextView>(R.id.deviceName)
        internal var txtDesc = itemView!!.findViewById<TextView>(R.id.txtDesc)
        internal var checkBox = itemView!!.findViewById<CheckBox>(R.id.checkBox)
    }

    private fun download(deviceName: String) {
        val storage = FirebaseStorage.getInstance()
        // Create a storage reference from our app
        storageRef = storage.reference
        val deviceTitle = deviceName.capitalize()

        val rootPath = File(getExternalStorageDirectory(), "file_name")
        if (!rootPath.exists()) {
            rootPath.mkdirs()
        }

        // Create a reference with an initial file path and name
        val pathReference = storageRef.child("firmware/$deviceName.png")

        pathReference.downloadUrl.addOnSuccessListener {
            // Got download URL
            val url = it
            val request = DownloadManager.Request(url)
            request.setDescription("The file will be downloaded")

            request.setTitle("$deviceTitle firmware")

            request.allowScanningByMediaScanner()
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

            request.setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS, "$deviceName.png")

            // get download service and enqueue file
            val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            manager.enqueue(request)

            Log.i("CREATION", "$deviceName created!")
        }.addOnFailureListener {
            // Handle any errors
            Log.i("FAILURE", "$deviceName failed!")
            Toast.makeText(this@CheckForUpdates, "$deviceTitle firmware does not exist", Toast.LENGTH_SHORT).show()
        }
    }

    private fun chkPermission(): Boolean {
        var permissionWriteExt =
            ContextCompat.checkSelfPermission(this@CheckForUpdates, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return permissionWriteExt == PackageManager.PERMISSION_GRANTED
    }

    private fun reqPermission() {
        if (ContextCompat.checkSelfPermission(this@CheckForUpdates, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this@CheckForUpdates,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                ActivityCompat.requestPermissions(
                    this@CheckForUpdates,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
                )
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this@CheckForUpdates,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1
                )
            }
        } else {
            // Permission has already been granted
        }
    }

}