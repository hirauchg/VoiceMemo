package com.hirauchi.voicememo.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hirauchi.voicememo.R
import com.hirauchi.voicememo.adapter.MemoListAdapter
import com.hirauchi.voicememo.model.Memo
import com.hirauchi.voicememo.ui.MemoListFragmentUI
import io.realm.OrderedRealmCollection
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.where
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.yesButton

class MemoListFragment : Fragment(), MemoListAdapter.SampleAdapterListener {

    private val mUI = MemoListFragmentUI()
    private var mMemoListMap = hashMapOf<Memo, Boolean>()

    private lateinit var mMemoList: OrderedRealmCollection<Memo>
    private lateinit var mRealm: Realm
    private lateinit var mAdapter: MemoListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return mUI.createView(AnkoContext.create(inflater.context, this, false))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRealm = Realm.getDefaultInstance()

        mMemoList = getMemoList()
        mAdapter = MemoListAdapter(context, this, mMemoList, true)
        mUI.mRecyclerView.adapter = mAdapter

        mUI.mDeleteButton.onClick {
            alert {
                title = getString(R.string.memo_list_delete_title)
                message = getString(R.string.memo_list_delete_message)

                yesButton {
                    deleteMemo()
                }
            }.show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mRealm.close()
    }

    private fun getMemoList(): OrderedRealmCollection<Memo> {
        lateinit var memoList: OrderedRealmCollection<Memo>

        try {
            mRealm.executeTransaction {
                memoList = mRealm.where<Memo>().findAll().sort("dateTime", Sort.DESCENDING)
            }

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
                    val _memo = mRealm.where<Memo>().equalTo("id",memo.id).findFirst()
                    if (isChecked) _memo?.deleteFromRealm()
                }
            }
            toast(R.string.memo_list_delete_success)
        } catch (ex: Exception) {
            ex.printStackTrace()
            toast(R.string.memo_list_delete_error)
        }

        mMemoList = getMemoList()
        mUI.mDeleteButton.visibility = View.GONE
    }

    fun selectAll(isSelectAll: Boolean) {
        mAdapter.selectAll(isSelectAll)
        for ((memo, isChecked) in mMemoListMap) {
            mMemoListMap.put(memo, isSelectAll)
        }

        if (isSelectAll) {
            mUI.mDeleteButton.visibility = View.VISIBLE
        } else {
            mUI.mDeleteButton.visibility = View.GONE
        }
    }

    override fun onClickCheckBox(memo: Memo, isChecked: Boolean) {
        mMemoListMap.set(memo, isChecked)

        mUI.mDeleteButton.visibility = View.GONE
        for ((_, _isChecked) in mMemoListMap) {
            if (_isChecked) mUI.mDeleteButton.visibility = View.VISIBLE
        }
    }
}