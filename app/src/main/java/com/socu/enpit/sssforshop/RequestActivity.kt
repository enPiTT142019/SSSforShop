package com.socu.enpit.sssforshop

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionSet
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.fragment_request.*

class RequestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request)

        // SubActivity起動後（ログイン後）すぐは空の画面が表示されるので、EditFragmentの画面を表示させる
        this.supportFragmentManager.beginTransaction().replace(R.id.container, YoubouFragment()).commit()

        YoubouButton.setOnClickListener {
            //val currentFragment = this.supportFragmentManager.findFragmentById(R.id.container)
            val nextFragment = YoubouFragment()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val slide = Slide()
                //slide.slideEdge = Gravity.START
                val set = TransitionSet()
                set.addTransition(slide)
                nextFragment.enterTransition = set
                nextFragment.exitTransition = set
            }
            this.supportFragmentManager.beginTransaction().replace(R.id.container, nextFragment).commit()
        }

        KaizenButton.setOnClickListener {
            val fragment = KaizenFragment()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val slide = Slide()
                //slide.slideEdge = if(this.supportFragmentManager.findFragmentById(R.id.container) is YoubouFragment) Gravity.END else Gravity.START
                //slide.slideEdge = Gravity.START
                val set = TransitionSet()
                set.addTransition(slide)
                fragment.enterTransition = set
                fragment.exitTransition = set
            }
            this.supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
        }

        KansouButton.setOnClickListener {
            val fragment = KansouFragment()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val slide = Slide()
                //slide.slideEdge = Gravity.END
                val set = TransitionSet()
                set.addTransition(slide)
                fragment.enterTransition = set
                fragment.exitTransition = set
            }
            this.supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
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
