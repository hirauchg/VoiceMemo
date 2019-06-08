package com.hirauchi.voicememo.ui

import com.hirauchi.voicememo.fragment.MainFragment
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.textView
import org.jetbrains.anko.verticalLayout

class MainFragmentUI : AnkoComponent<MainFragment> {
    override fun createView(ui: AnkoContext<MainFragment>) = with(ui) {
        verticalLayout {
            textView("Hello World!!")
        }
    }
}
