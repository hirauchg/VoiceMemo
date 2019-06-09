package com.hirauchi.voicememo.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hirauchi.voicememo.ui.MainFragmentUI
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.util.*

class MainFragment : Fragment() {

    private val mUI = MainFragmentUI()
    private val REQUEST_CODE = 100

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return mUI.createView(AnkoContext.create(inflater.context, this, false))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mUI.apply {
            mRecordButton.onClick {
                startRecord()
            }

            mSaveButton.onClick {
                saveRecord()
            }

            mDeleteButton.onClick {
                deleteRecord()
            }
        }
    }

    private fun startRecord() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.JAPAN.toString())
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 100)
        }

        startActivityForResult(intent, REQUEST_CODE)
    }

    private fun saveRecord() {
        mUI.mText.text.clear()
        changeView(false)

        // TODO
    }

    private fun deleteRecord() {
        mUI.mText.text.clear()
        changeView(false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val candidates = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (candidates.isNotEmpty()) {
                changeView(true)

                var text = mUI.mText.text.toString()
                if (text.trim().isNotEmpty()) {
                    text += "\n"
                }
                text += candidates.first()
                mUI.mText.setText(text)
            }
        }
    }

    private fun changeView(isVisible: Boolean) {
        if (isVisible) {
            mUI.mText.visibility = View.VISIBLE
            mUI.mSaveWrapper.visibility = View.VISIBLE
            mUI.mDeleteWrapper.visibility = View.VISIBLE
        } else {
            mUI.mText.visibility = View.GONE
            mUI.mSaveWrapper.visibility = View.GONE
            mUI.mDeleteWrapper.visibility = View.GONE
        }
    }
}
