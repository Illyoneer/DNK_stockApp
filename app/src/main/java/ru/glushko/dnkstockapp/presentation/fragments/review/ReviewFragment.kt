package ru.glushko.dnkstockapp.presentation.fragments.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import ru.glushko.dnkstockapp.databinding.FragmentReviewBinding
import ru.glushko.dnkstockapp.presentation.viewutils.tabAdapters.ReviewTabAdapter


class ReviewFragment: Fragment() {

    private lateinit var _reviewFragmentBinding: FragmentReviewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _reviewFragmentBinding = FragmentReviewBinding.inflate(inflater, container, false)

        val pagerAdapter = ReviewTabAdapter(this)
        _reviewFragmentBinding.viewPager2.adapter = pagerAdapter

        TabLayoutMediator(
            _reviewFragmentBinding.tabLayout,
            _reviewFragmentBinding.viewPager2
        ) { tab, position ->
            val tabNames = listOf("Расходники", "Оборудование")
            tab.text = tabNames[position]
        }.attach()

        return _reviewFragmentBinding.root
    }
}