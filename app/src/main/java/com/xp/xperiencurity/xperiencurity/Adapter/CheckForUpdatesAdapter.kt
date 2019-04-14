package com.xp.xperiencurity.xperiencurity.Adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xp.xperiencurity.xperiencurity.Interface.LoadMore
import com.xp.xperiencurity.xperiencurity.Model.DevicesToUpdate
import com.xp.xperiencurity.xperiencurity.R
import kotlinx.android.synthetic.main.check_for_updates_devices_layout.view.*
import kotlinx.android.synthetic.main.check_for_updates_loading_layout.view.*
import kotlinx.android.synthetic.main.nothing.view.*

internal class LoadingViewHolder(itemView:View): RecyclerView.ViewHolder(itemView) {
    var progressBar = itemView.progress_bar
}

internal class ItemViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
    var txtName = itemView.txtName
    var txtLength = itemView.txtLength
}

internal class NothingHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
    var nothing = itemView.nothing
}

class CheckForUpdatesAdapter(recyclerView: RecyclerView, internal var activity: Activity, internal var items:MutableList<DevicesToUpdate?>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val VIEW_DEVICETYPE=0
    val VIEW_LOADINGTYPE=1

    internal var loadMore:LoadMore?=null
    internal var isLoading:Boolean=false
    internal var visibleThreshold=5
    internal var lastVisibleItem:Int=0
    internal var totalItemCount:Int=0

    init {
        val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
        recyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = linearLayoutManager.itemCount
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                if (!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold)
                    if (loadMore != null)
                        loadMore!!.onLoadMore()
                isLoading = true
            }
        }
        )
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_DEVICETYPE) {
            val view = LayoutInflater.from(activity)
                .inflate(R.layout.check_for_updates_devices_layout, parent, false)
            return ItemViewHolder(view)

        }
        else if (viewType == VIEW_LOADINGTYPE) {
            val view = LayoutInflater.from(activity)
                .inflate(R.layout.check_for_updates_loading_layout,parent, false)
            return LoadingViewHolder(view)
        }
        val view = LayoutInflater.from(activity).inflate(R.layout.nothing,parent, false)
        return NothingHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
            val item = items[position]
            holder.txtName.text = items[position]!!.name
            holder.txtLength.text = items[position]!!.length.toString()
        }
        else if (holder is LoadingViewHolder) {
            holder.progressBar.isIndeterminate=true
        }
    }

    fun setLoaded() {
        isLoading = false
    }

    fun setLoadMore(LoadMore:LoadMore) {
        this.loadMore = LoadMore
    }
}