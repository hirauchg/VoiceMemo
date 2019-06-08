package com.hirauchi.voicememo.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.hirauchi.voicememo.R
import com.hirauchi.voicememo.ui.AppInfoFragmentUI
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.browse
import org.jetbrains.anko.support.v4.toast

class AppInfoFragment : Fragment() {

    private val mUI = AppInfoFragmentUI()

    lateinit var mContext: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return mUI.createView(AnkoContext.create(inflater.context, this, false))
    }

    override fun onAttach(context: Context){
        mContext = context
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val version = mContext.packageManager.getPackageInfo(mContext.packageName, 0).versionName

        val listView = mUI.mListView
        listView.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> browse("https://hirauchi-genta.com/privacy")
                1 -> activity?.startActivity<OssLicensesMenuActivity>("title" to mContext.getString(R.string.app_info_oss))
                2 -> toast(version)
            }
        }

        val itemList = listOf(
            mContext.getString(R.string.app_info_privacy),
            mContext.getString(R.string.app_info_oss),
            mContext.getString(R.string.app_info_version))
        val adapter = ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, itemList)
        listView.adapter = adapter

        super.onViewCreated(view, savedInstanceState)
    }
}