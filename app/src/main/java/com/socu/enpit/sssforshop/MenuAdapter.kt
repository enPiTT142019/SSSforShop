package com.socu.enpit.sssforshop

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.news_item.view.*
import kotlinx.android.synthetic.main.request_item.view.*
import kotlinx.android.synthetic.main.request_item.view.contentsText
import kotlinx.android.synthetic.main.request_item.view.dateText
import kotlinx.android.synthetic.main.request_item.view.titleText
import java.io.IOException

class MenuAdapter(
    private val mItems:   ArrayList<MenuData>,
    private val mContext: Context
): RecyclerView.Adapter<MenuAdapter.ViewHolder>() {

    override fun getItemCount(): Int = mItems.size

    fun addItem(mitem: MenuData) {
        mItems.add(mitem)
        notifyDataSetChanged()
    }

    fun addItemList(list: List<MenuData>) {
        for (mitem in list) {
            addItem(mitem)
        }
    }

    fun removeAllItems() {
        mItems.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(mContext).inflate(
        R.layout.news_item, parent, false))

    private fun removeItem(position: Int) {
        mItems.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = mItems[position].title
        holder.contents.text = mItems[position].contents
        holder.date.text = mItems[position].date

        // reference: Y.A.M の 雑記帳: SimpleDateFormat ではなく android.text.format.DateFormat を使おう - http://bit.ly/2OybKLu
        //holder.tvCreatedAt.text = DateFormat.format("yyyy/MM/dd kk:mm:ss", mItems[position].mCreatedAt).toString()
       /* holder.btRemoveItem?.setOnClickListener(){
            removeItem(position)
        }*/
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val title:       TextView    = view.titleText
        val contents:  TextView    = view.contentsText
        val date: TextView = view.dateText
       // val btRemoveItem: Button? = view.deleteButton
    }
}