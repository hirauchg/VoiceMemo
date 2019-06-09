package com.hirauchi.voicememo

import android.app.Application
import io.realm.Realm

class VoiceMemoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}