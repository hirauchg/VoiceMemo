package com.hirauchi.voicememo.ui

import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.*
import com.hirauchi.voicememo.R
import com.hirauchi.voicememo.fragment.MainFragment
import org.jetbrains.anko.*

class MainFragmentUI : AnkoComponent<MainFragment> {

    lateinit var mText: EditText
    lateinit var mRecordButton: ImageButton
    lateinit var mRecordText: TextView
    lateinit var mSaveWrapper: RelativeLayout
    lateinit var mSaveButton: ImageButton
    lateinit var mDeleteWrapper: RelativeLayout
    lateinit var mDeleteButton: ImageButton

    override fun createView(ui: AnkoContext<MainFragment>) = with(ui) {
        relativeLayout {
            mText = editText {
                visibility = View.GONE
                maxLines = 15
            }.lparams(matchParent, wrapContent) {
                topMargin = dip(16)
                horizontalMargin = dip(16)
                alignParentTop()
            }

            linearLayout {
                mSaveWrapper = relativeLayout {
                    visibility = View.GONE

                    mSaveButton = imageButton(R.drawable.ic_save) {
                        padding = dip(24)
                        scaleType = ImageView.ScaleType.FIT_CENTER
                        background = ContextCompat.getDrawable(ctx, R.drawable.radius_button)
                        elevation = dip(4).toFloat()
                    }.lparams(dip(72), dip(72)) {
                        centerHorizontally()
                    }

                    textView(R.string.main_btn_save) {
                        textSize = 16F
                    }.lparams {
                        centerHorizontally()
                        alignParentBottom()
                    }
                }.lparams(0, matchParent, 2F)

                relativeLayout {
                    mRecordButton = imageButton(R.drawable.ic_mic) {
                        padding = dip(24)
                        scaleType = ImageView.ScaleType.FIT_CENTER
                        background = ContextCompat.getDrawable(ctx, R.drawable.radius_button)
                        elevation = dip(4).toFloat()
                    }.lparams(dip(72), dip(72)) {
                        centerHorizontally()
                    }

                    mRecordText = textView(R.string.main_btn_record) {
                        textSize = 16F
                    }.lparams {
                        centerHorizontally()
                        alignParentBottom()
                    }
                }.lparams(0, matchParent, 1F)

                mDeleteWrapper = relativeLayout {
                    visibility = View.GONE

                    mDeleteButton = imageButton(R.drawable.ic_delete) {
                        padding = dip(24)
                        scaleType = ImageView.ScaleType.FIT_CENTER
                        background = ContextCompat.getDrawable(ctx, R.drawable.radius_button)
                        elevation = dip(4).toFloat()
                    }.lparams(dip(72), dip(72)) {
                        centerHorizontally()
                    }

                    textView(R.string.main_btn_delete) {
                        textSize = 16F
                    }.lparams {
                        centerHorizontally()
                        alignParentBottom()
                    }
                }.lparams(0, matchParent, 2F)
            }.lparams(matchParent, dip(100)) {
                alignParentBottom()
                bottomMargin = dip(100)
            }
        }
    }
}
