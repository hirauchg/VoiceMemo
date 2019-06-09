package com.hirauchi.voicememo.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hirauchi.voicememo.adapter.MemoListAdapter
import com.hirauchi.voicememo.model.Memo
import com.hirauchi.voicememo.ui.MemoListFragmentUI
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.where
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.sdk27.coroutines.onClick

class MemoListFragment : Fragment(), MemoListAdapter.SampleAdapterListener {

    private val mUI = MemoListFragmentUI()
    private var mMemoListMap = hashMapOf<Memo, Boolean>()

    private lateinit var mRealm: Realm
    private lateinit var mAdapter: MemoListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return mUI.createView(AnkoContext.create(inflater.context, this, false))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRealm = Realm.getDefaultInstance()

        mAdapter = MemoListAdapter(context!!, this)
        mUI.mRecyclerView.adapter = mAdapter

        mAdapter.setMemoList(getMemoList())

        mUI.mDelete.onClick {
            deleteMemo()
        }
    }

    private fun getMemoList(): List<Memo> {
        var memoList = listOf<Memo>()
        try {
            memoList = mRealm.where<Memo>().findAll().sort("dateTime", Sort.DESCENDING)

            mMemoListMap.clear()
            for (memo in memoList) {
                mMemoListMap.put(memo, false)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return memoList
    }

    private fun deleteMemo() {
        try {
            mRealm.executeTransaction {
                for ((memo, isChecked) in mMemoListMap) {
                    if (isChecked) memo.deleteFromRealm()
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        mAdapter.setMemoList(getMemoList())
    }

    fun selectAll(isSelectAll: Boolean) {
        mAdapter.selectAll(isSelectAll)
        for ((memo, isChecked) in mMemoListMap) {
            mMemoListMap.put(memo, isSelectAll)
        }
    }

    override fun onClickCheckBox(memo: Memo, isChecked: Boolean) {
        mMemoListMap.set(memo, isChecked)
    }
}