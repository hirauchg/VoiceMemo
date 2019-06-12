package com.hirauchi.voicememo.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hirauchi.voicememo.R
import com.hirauchi.voicememo.model.Memo
import com.hirauchi.voicememo.ui.MainFragmentUI
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.yesButton
import java.util.*

class MainFragment : Fragment() {

    private val mUI = MainFragmentUI()
    private val REQUEST_CODE = 100

    private lateinit var mRealm: Realm

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return mUI.createView(AnkoContext.create(inflater.context, this, false))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRealm = Realm.getDefaultInstance()

        mUI.apply {
            mRecordButton.onClick {
                startRecord()
            }

            mSaveButton.onClick {
                if (!mUI.mText.text.trim().isEmpty()) {
                    saveRecord()
                } else {
                    deleteRecord()
                }
            }

            mDeleteButton.onClick {
                alert(R.string.main_delete_message) {
                    yesButton {
                        deleteRecord()
                    }
                }.show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mRealm.close()
    }

    private fun startRecord() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.JAPAN.toString())
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 100)
        }

        startActivityForResult(intent, REQUEST_CODE)
    }

    private fun saveRecord() {
        try {
            mRealm.executeTransaction {
                val maxId = mRealm.where<Memo>().max("id")
                val targetId = (maxId?.toLong() ?: 0) + 1
                val memo = mRealm.createObject<Memo>(targetId)
                memo.content = mUI.mText.text.toString()
                memo.dateTime = System.currentTimeMillis()
                toast(R.string.main_save_success)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            toast(R.string.main_save_error)
        }

        mUI.mText.text.clear()
        changeView(false)
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
        mUI.apply {
            if (isVisible) {
                mText.visibility = View.VISIBLE
                mSaveWrapper.visibility = View.VISIBLE
                mDeleteWrapper.visibility = View.VISIBLE
                mRecordText.text = getString(R.string.main_btn_continue)
            } else {
                mText.visibility = View.GONE
                mSaveWrapper.visibility = View.GONE
                mDeleteWrapper.visibility = View.GONE
                mRecordText.text = getString(R.string.main_btn_record)
            }
        }
    }
}
