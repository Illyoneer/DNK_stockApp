package ru.glushko.dnkstockapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.glushko.dnkstockapp.R
import ru.glushko.dnkstockapp.databinding.FragmentAddOrEditItemBinding
import ru.glushko.dnkstockapp.databinding.FragmentReviewBinding
import ru.glushko.dnkstockapp.presentation.viewmodels.AddOrEditItemViewModel
import ru.glushko.dnkstockapp.presentation.viewmodels.MainViewModel
import ru.glushko.dnkstockapp.presentation.viewutils.Status
import ru.glushko.dnkstockapp.presentation.viewutils.tabAdapter.ReviewTabAdapter

class ReviewFragment : Fragment() {

    private lateinit var _reviewFragmentBinding: FragmentReviewBinding
    private val _mainViewModel by viewModel<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _reviewFragmentBinding = FragmentReviewBinding.inflate(inflater, container, false)
        val pagerAdapter = ReviewTabAdapter(this)
        _reviewFragmentBinding.viewPager2.adapter = pagerAdapter

        TabLayoutMediator(_reviewFragmentBinding.tabLayout,_reviewFragmentBinding.viewPager2, ){
            tab, position ->
            val tabNames = listOf("Расходники", "Оборудование")
            tab.text = tabNames[position]
        }.attach()

        _reviewFragmentBinding.addButton.setOnClickListener{
            if(_reviewFragmentBinding.viewPager2.currentItem == 0)
                showAddConsumablesItemRecordDialog()
            else
                showAddHardwareItemRecordDialog()
        }

        return _reviewFragmentBinding.root
    }

    private fun showAddConsumablesItemRecordDialog() {
        val binding: FragmentAddOrEditItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()), R.layout.fragment_add_or_edit_item,
            null, false
        )
        val addItemFragmentViewModel =
            ViewModelProvider(this)[AddOrEditItemViewModel::class.java]

        binding.addItemVM = addItemFragmentViewModel

        AlertDialog.Builder(requireContext(), R.style.MyAlertDialogRoundedTheme)
            .setTitle("Добавить новую запись") //Добавление заголовка.
            .setView(binding.root) //Присвоение View полученного ранее.
            .setPositiveButton("Добавить") { _, _ ->
                _mainViewModel.addItemToDatabase(
                    binding.itemNameEditText.text.toString(),
                    binding.itemCountEditText.text.toString(),
                    binding.itemDateEditText.text.toString(),
                    binding.itemUserEditText.text.toString(),
                    "consumables"
                )

                _mainViewModel.getStateAddItemLiveData().observe(viewLifecycleOwner, {
                    when (it!!) {
                        Status.ERROR -> Snackbar.make(
                            _reviewFragmentBinding.root, "Введите все данные.",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        Status.SUCCESS -> Snackbar.make(
                            _reviewFragmentBinding.root, "Запись успешно добавлена!",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                })
            }.setNegativeButton("Отмена") { dialog, _ -> dialog.cancel() }.show()
    }

    private fun showAddHardwareItemRecordDialog() {
        val binding: FragmentAddOrEditItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()), R.layout.fragment_add_or_edit_item,
            null, false
        )
        val addItemFragmentViewModel =
            ViewModelProvider(this)[AddOrEditItemViewModel::class.java]

        binding.addItemVM = addItemFragmentViewModel

        AlertDialog.Builder(requireContext(), R.style.MyAlertDialogRoundedTheme)
            .setTitle("Добавить новую запись") //Добавление заголовка.
            .setView(binding.root) //Присвоение View полученного ранее.
            .setPositiveButton("Добавить") { _, _ ->
                _mainViewModel.addItemToDatabase(
                    binding.itemNameEditText.text.toString(),
                    binding.itemCountEditText.text.toString(),
                    binding.itemDateEditText.text.toString(),
                    binding.itemUserEditText.text.toString(),
                    "hardware"
                )

                _mainViewModel.getStateAddItemLiveData().observe(viewLifecycleOwner, {
                    when (it!!) {
                        Status.ERROR -> Snackbar.make(
                            _reviewFragmentBinding.root, "Введите все данные.",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        Status.SUCCESS -> Snackbar.make(
                            _reviewFragmentBinding.root, "Запись успешно добавлена!",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                })
            }.setNegativeButton("Отмена") { dialog, _ -> dialog.cancel() }.show()
    }
}