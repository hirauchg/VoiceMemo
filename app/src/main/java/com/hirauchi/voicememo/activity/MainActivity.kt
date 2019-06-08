package com.hirauchi.voicememo.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.hirauchi.voicememo.R
import com.hirauchi.voicememo.fragment.MainFragment
import org.jetbrains.anko.startActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportFragmentManager.beginTransaction().replace(R.id.base_container, MainFragment()).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_app_info -> startActivity<AppInfoActivity>()
        }
        return super.onOptionsItemSelected(item)
    }
}
