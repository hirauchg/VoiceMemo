package com.hirauchi.voicememo.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.hirauchi.voicememo.R
import com.hirauchi.voicememo.fragment.MemoListFragment

class MemoListActivity : BaseActivity() {

    private val mMemoListFragment = MemoListFragment()
    private var mIsSelectAll = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction().replace(R.id.base_container, mMemoListFragment).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_memo_list, menu)

        mIsSelectAll = !mIsSelectAll
        if (mIsSelectAll) {
            menu?.findItem(R.id.menu_select)?.title = getString(R.string.memo_list_menu_select_clear)
        } else {
            menu?.findItem(R.id.menu_select)?.title = getString(R.string.memo_list_menu_select_all)
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_select -> {
                mMemoListFragment.selectAll(!mIsSelectAll)
                invalidateOptionsMenu()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}