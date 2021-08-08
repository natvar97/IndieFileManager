package com.indialone.indiefilemanager.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.indialone.indiefilemanager.databinding.FragmentAboutBinding
import com.indialone.indiefilemanager.databinding.FragmentHomeBinding

class AboutFragment : Fragment() {

    private lateinit var mBinding: FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentAboutBinding.inflate(inflater, container, false)
        return mBinding.root
    }

}