package ru.glushko.dnkstockapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import ru.glushko.dnkstockapp.R
import ru.glushko.dnkstockapp.databinding.FragmentConsumablesBinding

class ConsumablesFragment : Fragment() {

    private lateinit var _consumablesFragmentBinding: FragmentConsumablesBinding
    private val _types = arrayListOf("Расходник", "Оборудование")
    private lateinit var _spinnerAdapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_consumables, container, false)
    }
    
}