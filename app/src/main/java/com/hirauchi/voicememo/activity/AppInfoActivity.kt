package com.hirauchi.voicememo.activity

import android.os.Bundle
import com.hirauchi.voicememo.R
import com.hirauchi.voicememo.fragment.AppInfoFragment

class AppInfoActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction().replace(R.id.base_container, AppInfoFragment()).commit()
    }

}