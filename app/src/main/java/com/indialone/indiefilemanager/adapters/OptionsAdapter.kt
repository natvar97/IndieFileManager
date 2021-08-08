package com.indialone.indiefilemanager.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.indialone.indiefilemanager.R
import com.indialone.indiefilemanager.databinding.OptionLayoutBinding

class OptionsAdapter(
    private val activity: Activity,
    private val list: Array<String>
): BaseAdapter() {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = activity.layoutInflater.inflate(R.layout.option_layout, null)
        val tvOption = view.findViewById<TextView>(R.id.tv_option)
        val ivOption = view.findViewById<ImageView>(R.id.iv_option)

        tvOption.text = list[position]

        when(list[position]) {
            "Details" -> {
                ivOption.setImageResource(R.drawable.ic_details)
            }
            "Rename" -> {
                ivOption.setImageResource(R.drawable.ic_rename)
            }
            "Delete" -> {
                ivOption.setImageResource(R.drawable.ic_delete)
            }
            "Share" -> {
                ivOption.setImageResource(R.drawable.ic_share)
            }
        }
        return view

    }
}