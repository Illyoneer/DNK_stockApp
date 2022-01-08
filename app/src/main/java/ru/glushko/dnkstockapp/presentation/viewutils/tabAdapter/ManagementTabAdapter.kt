package ru.glushko.dnkstockapp.presentation.viewutils.tabAdapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.glushko.dnkstockapp.presentation.fragment.management.ArchiveManagementFragment
import ru.glushko.dnkstockapp.presentation.fragment.management.ReportsManagementFragment
import ru.glushko.dnkstockapp.presentation.fragment.management.StaffManagementFragment
import ru.glushko.dnkstockapp.presentation.fragment.management.StockManagementFragment

class ManagementTabAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> StockManagementFragment()
            1 -> StaffManagementFragment()
            2 -> ArchiveManagementFragment()
            else -> ReportsManagementFragment()
        }

}