package ru.glushko.dnkstockapp.presentation.viewutils.tabAdapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.glushko.dnkstockapp.presentation.fragments.review.ConsumablesFragment
import ru.glushko.dnkstockapp.presentation.fragments.review.HardwareFragment

class ReviewTabAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> ConsumablesFragment()
            else -> HardwareFragment()
        }

}