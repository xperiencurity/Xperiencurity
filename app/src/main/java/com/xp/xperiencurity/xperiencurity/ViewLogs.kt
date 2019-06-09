package com.xp.xperiencurity.xperiencurity

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_view_logs.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.File


class ViewLogs : AppCompatActivity(), CoroutineScope by MainScope() {

    private lateinit var ref: DatabaseReference
    private lateinit var storageRef: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_logs)

        chkPermission()

        ref = FirebaseDatabase.getInstance().reference.child("Logs")
        logView.layoutManager = LinearLayoutManager(this)

        backgroundData()

    }

    private fun backgroundData() {
        launch {
            firebaseData()
        }
    }

    private fun firebaseData() {
        lateinit var logTitle: String

        val option = FirebaseRecyclerOptions.Builder<LogViewModel>()
            .setQuery(ref, LogViewModel::class.java)
            .setLifecycleOwner(this)
            .build()

        val firebaseRecyclerAdapter = object: FirebaseRecyclerAdapter<LogViewModel, MyViewHolder>(option) {

            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
                val itemView = LayoutInflater.from(this@ViewLogs).inflate(R.layout.activity_view_logs,parent,false)
                return MyViewHolder(itemView)
            }

            override fun onBindViewHolder(holder: MyViewHolder, position: Int, model: LogViewModel) {
                val placeID = getRef(position).key.toString()

                ref.child(placeID).addValueEventListener(object: ValueEventListener {
                    override fun onCancelled(dbError: DatabaseError) {
                        Toast.makeText(this@ViewLogs, "Error Occurred "+ dbError.toException(), Toast.LENGTH_SHORT).show()
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if(itemCount == 0) {
                            progressBar.visibility = View.VISIBLE
                        }
                        else {
                            progressBar.visibility = View.GONE
                        }
                        holder.logName.text = model.logname
                        holder.txtDate.text = model.date
                        holder.viewBtn.setOnClickListener {
                            logTitle = holder.logName.text.toString()
                            download(logTitle)
                        }
                    }
                })
            }
        }
        logView.adapter = firebaseRecyclerAdapter
        firebaseRecyclerAdapter.startListening()
    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        internal var logName: TextView = itemView!!.findViewById(R.id.logName)
        internal var txtDate: TextView = itemView!!.findViewById(R.id.txtDate)
        internal var viewBtn: Button = itemView!!.findViewById(R.id.viewBtn)
    }

    private fun download(deviceName: String) {
        val storage = FirebaseStorage.getInstance()
        // Create a storage reference from our app
        storageRef = storage.reference

        val rootPath = File(Environment.getExternalStorageDirectory(), "file_name")
        if (!rootPath.exists()) {
            rootPath.mkdirs()
        }

        // Create a reference with an initial file path and name
        val pathReference = storageRef.child("log/$deviceName.txt")

        pathReference.downloadUrl.addOnSuccessListener {
            // Got download URL
            val url = it
            val request = DownloadManager.Request(url)
            request.setDescription("The following file will be downloaded")
            val deviceTitle = deviceName.capitalize()
            request.setTitle("$deviceTitle log has been downloaded")

            request.allowScanningByMediaScanner()
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)

            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "$deviceName.txt")

            // get download service and enqueue file
            val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            manager.enqueue(request)

            Log.i("CREATION", "$deviceName created!")
        }.addOnFailureListener {
            // Handle any errors
            Log.i("FAILURE", "$deviceName failed!")
        }
    }

    private fun chkPermission() {
        if (ContextCompat.checkSelfPermission(this@ViewLogs, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@ViewLogs,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this@ViewLogs,arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),1)
            }
        } else {
            // Permission has already been granted
        }
    }
}