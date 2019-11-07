package com.socu.enpit.sssforshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.Settings.Global.putString
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.core.content.edit
import kotlinx.android.synthetic.main.activity_edit.*

class EditActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)



        requestButton.setOnClickListener {
            val intent = Intent(this, RequestActivity::class.java)
            startActivity(intent)
        }

        editNewsButton.setOnClickListener {
            if(newsEditText != null){
                newsText.text = newsEditText.text.toString()
            }
        }

        editMenuButton.setOnClickListener {
            if(menuEditText != null){
                menuText.text = menuEditText.text.toString()
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
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
