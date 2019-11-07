package com.socu.enpit.sssforshop

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionSet
import android.view.Gravity
import android.view.Window
import kotlinx.android.synthetic.main.activity_request.*
import kotlinx.android.synthetic.main.activity_sub.*

class RequestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // アニメーションさせるために必要な手順らしい
        // ロリポップ以上のバージョンにしか対応していないらしいのでそのif文を付けた
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        }

        setContentView(R.layout.activity_request)

        // SubActivity起動後（ログイン後）すぐは空の画面が表示されるので、EditFragmentの画面を表示させる
        this.supportFragmentManager.beginTransaction().replace(R.id.container, EditFragment()).commit()

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
}
