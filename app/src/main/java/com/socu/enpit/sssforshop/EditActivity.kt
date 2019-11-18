package com.socu.enpit.sssforshop

import android.app.Activity
import android.content.AbstractThreadedSyncAdapter
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.core.content.edit
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editNews = pref.getString("NEWS","")
        val editMenu = pref.getString("MENU","")
        val shopName: String = CloudDataManager.getShopName()

        shopNameText.setText(shopName)
        newsText.setText(editNews)
        menuText.setText(editMenu)

        requestButton.setOnClickListener {
            val intent = Intent(this, RequestActivity::class.java)
            startActivity(intent)
        }

        editNewsButton.setOnClickListener {
            if(newsEditText != null){
                newsText.text = newsEditText.text.toString()
                val newsStrings: CharSequence = newsText.text
                val pref = PreferenceManager.getDefaultSharedPreferences(this)
                pref.edit{
                    putString("NEWS",newsText.text.toString())
                    putString("NEWS",newsEditText.text.toString())
                }
            }
        }

        editMenuButton.setOnClickListener {
            if(menuEditText != null){
                menuText.text = menuEditText.text.toString()
                val menuStrings: CharSequence = menuText.text
                menuEditText.hint = "商品情報を入力してください"
                val pref = PreferenceManager.getDefaultSharedPreferences(this)
                pref.edit{
                    putString("MENU",menuText.text.toString())
                    putString("NEWS",menuEditText.text.toString())
                }
            }
        }
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
