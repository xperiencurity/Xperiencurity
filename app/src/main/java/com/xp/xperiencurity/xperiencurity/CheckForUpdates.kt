package com.xp.xperiencurity.xperiencurity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.xp.xperiencurity.xperiencurity.Adapter.CheckForUpdatesAdapter
import com.xp.xperiencurity.xperiencurity.Interface.LoadMore
import com.xp.xperiencurity.xperiencurity.Model.DevicesToUpdate
import kotlinx.android.synthetic.main.activity_check_for_updates.*
import java.util.*
import kotlin.collections.ArrayList

class CheckForUpdates : AppCompatActivity(), LoadMore {

    var items:MutableList<DevicesToUpdate?> = ArrayList()
    lateinit var adapter:CheckForUpdatesAdapter

    override fun onLoadMore() {
        if (items!!.size <  50) {
            items!!.add(null)
            adapter.notifyItemInserted(items.size-1)

            Handler().postDelayed( {
                items.removeAt(items.size-1) //remove null item
                adapter.notifyItemRemoved(items.size)

                //Random new data
                val index = items.size
                val end = index+10

                for (i in index until end) {
                    val name = UUID.randomUUID().toString()
                    val item = DevicesToUpdate(name, name.length)
                    items.add(item)
                }

                adapter.notifyDataSetChanged()
                adapter.setLoaded()
            }
            , 3000) // delay 3 seconds
        }
        else {
            Toast.makeText(this, "MAX DATA IS 50", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_for_updates)

        random10Data()

        //init view
        deviceView.layoutManager = LinearLayoutManager(this)
        adapter = CheckForUpdatesAdapter(deviceView, this, items)
        deviceView.adapter = adapter
        adapter.setLoadMore(this)
    }

    private fun random10Data() {
        for (i in 0..9) {
            val name = UUID.randomUUID().toString()
            val item = DevicesToUpdate(name, name.length)
            items.add(item)
        }
    }
}
