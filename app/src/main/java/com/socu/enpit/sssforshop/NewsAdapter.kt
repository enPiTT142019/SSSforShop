package com.socu.enpit.sssforshop

import android.content.Context
import android.media.AudioManager
import android.media.SoundPool
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.news_item.view.*
import kotlinx.android.synthetic.main.request_item.view.contentsText
import kotlinx.android.synthetic.main.request_item.view.dateText
import kotlinx.android.synthetic.main.request_item.view.titleText

class NewsAdapter(
    private val mItems:   ArrayList<NewsData>,
    private val mContext: Context
): RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun getItemCount(): Int = mItems.size
    /*private lateinit var removese: SoundPool
    private var soundResId = 1*/

    fun addItem(nitem: NewsData) {
        mItems.add(nitem)
        notifyDataSetChanged()
    }

    fun addItemList(list: List<NewsData>) {
        for (nitem in list) {
            addItem(nitem)
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
        /*holder.btRemoveItem?.setOnClickListener(){
            removeItem(position)
        }*/
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val title:       TextView    = view.titleText
        val contents:  TextView    = view.contentsText
        val date: TextView = view.dateText
       // val btRemoveItem: Button? = view.deleteButton
    }

    /*override fun onResume() {
        super.onResume()
        removese = SoundPool(2, AudioManager.STREAM_ALARM, 0)
        soundResId = removese.load(this, R.raw.remove_se, 1)
    }

    override fun onPause() {
        super.onPause()
        removese.release()
    }*/
}