package ru.glushko.dnkstockapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import ru.glushko.dnkstockapp.R
import ru.glushko.dnkstockapp.databinding.FragmentManagementBinding
import ru.glushko.dnkstockapp.presentation.viewutils.tabAdapters.ManagementTabAdapter

class ManagementFragment : Fragment() {

    private lateinit var _managementFragmentBinding: FragmentManagementBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _managementFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_management, container, false)
        val pagerAdapter = ManagementTabAdapter(this)
        _managementFragmentBinding.viewPager2.adapter = pagerAdapter

        TabLayoutMediator(
            _managementFragmentBinding.tabLayout,
            _managementFragmentBinding.viewPager2
        ) { tab, position ->
            val tabNames = listOf("Склад", "Люди", "Архив", "Отчеты")
            tab.text = tabNames[position]
        }.attach()

        return _managementFragmentBinding.root
    }

}