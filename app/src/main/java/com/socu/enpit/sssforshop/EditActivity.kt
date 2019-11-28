package com.socu.enpit.sssforshop

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.SoundPool
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.activity_request.*
import java.util.*
import kotlin.collections.ArrayList

class EditActivity : AppCompatActivity() {

    private val nadapter =  NewsAdapter(ArrayList(), this)
    private val madapter = MenuAdapter(ArrayList(), this)
    private lateinit var addse: SoundPool
    private var soundResId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setTitle(R.string.title_bar_edit)

        shopNameText.text = CloudDataManager.getShopName()

        recyclerViewNews.layoutManager = LinearLayoutManager(this)
        recyclerViewNews.adapter = nadapter
        recyclerViewMenu.layoutManager = LinearLayoutManager(this)
        recyclerViewMenu.adapter = madapter

        nadapter.removeAllItems()
        val list = CloudDataManager.getNewsDataList()
        nadapter.addItemList(list)

        requestButton.setOnClickListener {
            val intent = Intent(this, RequestActivity::class.java)
            startActivity(intent)
        }

        editNewsButton.setOnClickListener {
            addse.play(soundResId, 1.0f, 1.0f, 0, 0, 1.0f)
            if(newsContentsEditText != null && newsTitleEditText != null){
                val item = NewsData( newsTitleEditText.text.toString(), newsContentsEditText.text.toString(), DateFormat.format("yyyy/MM/dd kk:mm:ss", Calendar.getInstance()).toString())
                nadapter.addItem(item)
                newsTitleEditText.text.clear()
                newsContentsEditText.text.clear()
                CloudDataManager.addNewsData(item)
            }
        }

        editMenuButton.setOnClickListener {
            addse.play(soundResId, 1.0f, 1.0f, 0, 0, 1.0f)
            if(menuContentsEditText != null && menuTitleEditText != null){
                val item = MenuData(menuTitleEditText.text.toString(), menuContentsEditText.text.toString())
                madapter.addItem(item)
                menuTitleEditText.text.clear()
                menuContentsEditText.text.clear()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        addse = SoundPool(2, AudioManager.STREAM_ALARM, 0)
        soundResId = addse.load(this, R.raw.add_se, 1)
    }

    override fun onPause() {
        super.onPause()
        addse.release()
    }

    // メニューを表示させる処理
    // この関数をオーバーライドして「menu.xml」を指定することで表示される
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    // メニューを選択したときの動作をここに書く
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val itemId = item?.itemId
        when (itemId) {
            // メニューの「ログアウト」を押したとき
            R.id.menu_logout -> {
                // MainActivity（ログイン画面）に遷移する
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // キーボード隠すやつ
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editlayout.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        return super.dispatchTouchEvent(ev)
    }
}
