package com.hirauchi.voicememo.ui

import android.support.v4.content.ContextCompat
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.hirauchi.voicememo.R
import org.jetbrains.anko.*

class MemoListAdapterUI : AnkoComponent<ViewGroup> {

    lateinit var mDateTime: TextView
    lateinit var mContent: TextView
    lateinit var mCheckbox: CheckBox

    override fun createView(ui: AnkoContext<ViewGroup>) = with(ui) {
        verticalLayout {
            lparams(width = matchParent)

            linearLayout {
                verticalLayout {
                    mContent = textView {
                        textSize = 16F
                    }

                    mDateTime = textView {
                        textSize = 12F
                        textColor = ContextCompat.getColor(ctx, R.color.memoListTime)
                    }
                }.lparams(width = 0, height = wrapContent, weight = 1F) {
                    verticalMargin = dip(6)
                    leftMargin = dip(12)
                }

                relativeLayout {
                    mCheckbox = checkBox().lparams {
                        centerInParent()
                    }
                }.lparams(width = 0, height = matchParent, weight = 0.2F)
            }

            view {
                backgroundColor = ContextCompat.getColor(ctx, R.color.memoListDivider)
            }.lparams(width = matchParent, height = dip(1))
        }
    }
}