package com.hirauchi.voicememo.ui

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import com.hirauchi.voicememo.fragment.MemoListFragment
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class MemoListFragmentUI : AnkoComponent<MemoListFragment> {

    lateinit var mRecyclerView: RecyclerView
    lateinit var mDelete: Button

    override fun createView(ui: AnkoContext<MemoListFragment>) = with(ui) {
        verticalLayout {
            mRecyclerView = recyclerView {
                layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
            }

            mDelete = button("削除")
        }
    }
}
