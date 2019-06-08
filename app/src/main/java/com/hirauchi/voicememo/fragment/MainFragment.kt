package com.hirauchi.voicememo.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hirauchi.voicememo.ui.MainFragmentUI
import org.jetbrains.anko.AnkoContext

class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return MainFragmentUI().createView(AnkoContext.create(inflater.context, this, false))
    }
}
