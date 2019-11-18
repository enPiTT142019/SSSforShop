package com.socu.enpit.sssforshop

import android.app.Application
import io.realm.Realm

class EditNewsApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}