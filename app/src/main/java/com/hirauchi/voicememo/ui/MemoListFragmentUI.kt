package com.hirauchi.voicememo.ui

import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import com.hirauchi.voicememo.R
import com.hirauchi.voicememo.fragment.MemoListFragment
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class MemoListFragmentUI : AnkoComponent<MemoListFragment> {

    lateinit var mRecyclerView: RecyclerView
    lateinit var mDeleteButton: ImageButton

    override fun createView(ui: AnkoContext<MemoListFragment>) = with(ui) {
        relativeLayout {
            mRecyclerView = recyclerView {
                layoutManager = LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
            }.lparams(matchParent, wrapContent) {
                bottomMargin = dip(100)
            }

            mDeleteButton = imageButton(R.drawable.ic_delete) {
                visibility = View.GONE
                padding = dip(24)
                scaleType = ImageView.ScaleType.FIT_CENTER
                background = ContextCompat.getDrawable(ctx, R.drawable.radius_button)
                elevation = dip(6).toFloat()
            }.lparams(dip(72), dip(72)) {
                alignParentBottom()
                leftMargin = dip(16)
                bottomMargin = dip(16)
            }
        }
    }
}
