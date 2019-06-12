package com.hirauchi.voicememo.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.hirauchi.voicememo.R
import com.hirauchi.voicememo.model.Memo
import com.hirauchi.voicememo.ui.MemoListAdapterUI
import io.realm.OrderedRealmCollection
import io.realm.RealmRecyclerViewAdapter
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange
import java.text.SimpleDateFormat
import java.util.*

class MemoListAdapter(val mContext: Context?, val mListener: SampleAdapterListener, val mMemoList: OrderedRealmCollection<Memo>?, val mAutoUpdate: Boolean)
    : RealmRecyclerViewAdapter<Memo, MemoListAdapter.ViewHolder>(mMemoList, mAutoUpdate) {

    interface SampleAdapterListener {
        fun onClickCheckBox(memo: Memo, isChecked: Boolean)
    }

    private val mUI = MemoListAdapterUI()
    private var mIsSelectAll = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(mUI.createView(AnkoContext.create(parent.context, parent)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mMemoList?.get(position)?.let {
            holder.dateTime.text = SimpleDateFormat(mContext?.getString(R.string.memo_list_date_format), Locale.US).format(it.dateTime)
            holder.content.text = it.content

            holder.checkBox.isChecked = mIsSelectAll
            holder.checkBox.onCheckedChange { _, isChecked ->
                mListener.onClickCheckBox(it, isChecked)
            }
        }
    }

    fun selectAll(isSelectAll: Boolean) {
        mIsSelectAll = isSelectAll
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateTime = mUI.mDateTime
        val content = mUI.mContent
        val checkBox = mUI.mCheckbox
    }
}