package com.elephantgroup.one.ui.home

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class TabFragment : Fragment() {
    private var mTitle: String? = "Default"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (arguments != null) {
            mTitle = arguments!!.getString("title")
        }

        val textView = TextView(activity)
        textView.textSize = 16f
        textView.setPadding(0, 0, 0, 20)
        textView.setBackgroundColor(Color.parseColor("#ffffffff"))
        textView.gravity = Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
        textView.text = mTitle
        return textView
    }
}
