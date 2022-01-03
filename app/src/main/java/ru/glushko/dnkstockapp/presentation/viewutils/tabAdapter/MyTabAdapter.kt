package ru.glushko.dnkstockapp.presentation.viewutils.tabAdapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.glushko.dnkstockapp.presentation.fragment.ConsumablesFragment
import ru.glushko.dnkstockapp.presentation.fragment.HardwareFragment

class MyTabAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2;
    }

    override fun createFragment(position: Int): Fragment =
        when (position) {
        0 -> ConsumablesFragment()
        else -> HardwareFragment()
    }

}