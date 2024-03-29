package com.socu.enpit.sssforshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_request.*

class RequestActivity : AppCompatActivity() {

    private val adapter =  RequestAdapter(ArrayList(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)

        setTitle(R.string.title_bar_request)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        adapter.removeAllItems()
        val requestDataList = CloudDataManager.getRequestDataList()
        for (request in requestDataList) adapter.addItem(request)

        returnEditScreenButton.setOnClickListener {finish()}

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
            R.id.menu_reload -> {
                adapter.removeAllItems()
                val requestDataList = CloudDataManager.getRequestDataList()
                for (request in requestDataList) adapter.addItem(request)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
