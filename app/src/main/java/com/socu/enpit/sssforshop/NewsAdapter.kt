package com.socu.enpit.sssforshop

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.DateFormat

class NewsAdapter ( private val mItems: ArrayList<Item>, private val mContext: Context):RecyclerView.Adapter<ItwmAdapter.ViewHolder>() {
    override fun getItemCount(): Int = mItems.size
    fun addItem(item: Item) {
        mItems.add(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
            = ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item, parent, false))

    private fun removedItem(position:Int) {
        mItems.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvName.text = mItems[position].mName
        holder.tvCreatedAt.text = DateFormat.format("yyyy/MM/dd kk:mm:ss", mItems[position].mCreatedAt).toString()
        holder.btRemoveItem.setOnClickListener {
            removedItem(position)
        }
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvName:     TextView = view.tv_created_at
        val btRemoveItem: ImageButton = view.bt_remove_item
    }
}