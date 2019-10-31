package com.socu.enpit.sssforshop

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.*
import android.view.Window
import kotlinx.android.synthetic.main.activity_sub.*
import android.view.Gravity
import kotlinx.android.synthetic.main.fragment_request.*
 class SubActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // アニメーションさせるために必要な手順らしい
        // ロリポップ以上のバージョンにしか対応していないらしいのでそのif文を付けた
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        }

        setContentView(R.layout.activity_sub)

        // SubActivity起動後（ログイン後）すぐは空の画面が表示されるので、EditFragmentの画面を表示させる
        this.supportFragmentManager.beginTransaction().replace(R.id.requestContainer, EditFragment()).commit()

        // 「店舗情報の編集」ボタンを押したときの処理
        editButton.setOnClickListener {
            // EditFragmentクラスを用意
            val fragment = EditFragment()
            // ロリポップ以上のバージョンならアニメーションさせる
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // スライドアニメーションを用意。移動方向は「START」（左から右）
                val slide = Slide()
                slide.slideEdge = Gravity.START
                val set = TransitionSet()
                set.addTransition(slide)
                // 新しい画面として入ってくるFragmentにアニメーションを設定する
                fragment.enterTransition = set
                // 元の画面のFragmentにアニメーションを設定する
                fragment.exitTransition = set
            }
            // EditFragmentを表示させる
            this.supportFragmentManager.beginTransaction().replace(R.id.requestContainer, fragment).commit()
        }

        // 「お客様のご意見」ボタンを押したときの処理
        requestButton.setOnClickListener {
            val fragment = RequestFragment()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val slide = Slide()
                slide.slideEdge = Gravity.END
                val set = TransitionSet()
                set.addTransition(slide)
                fragment.enterTransition = set
                fragment.exitTransition = set
            }
            this.supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
        }

        // 「ログアウト」ボタンを押したときの処理
        logoutButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
