package ru.glushko.dnkstockapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.glushko.dnkstockapp.R
import ru.glushko.dnkstockapp.presentation.viewmodels.HardwareViewModel

class HardwareFragment : Fragment() {

    companion object {
        fun newInstance() = HardwareFragment()
    }

    private lateinit var viewModel: HardwareViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.hardware_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HardwareViewModel::class.java)
        // TODO: Use the ViewModel
    }

}