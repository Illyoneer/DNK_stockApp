package ru.glushko.dnkstockapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.glushko.dnkstockapp.R
import ru.glushko.dnkstockapp.databinding.FragmentAddOrEditItemBinding
import ru.glushko.dnkstockapp.databinding.FragmentReviewBinding
import ru.glushko.dnkstockapp.presentation.viewmodels.ReviewViewModel
import ru.glushko.dnkstockapp.presentation.viewutils.Status
import ru.glushko.dnkstockapp.presentation.viewutils.tabAdapters.ReviewTabAdapter

class ReviewFragment : Fragment() {

    private lateinit var _reviewFragmentBinding: FragmentReviewBinding
    private val _mainViewModel by viewModel<ReviewViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _reviewFragmentBinding = FragmentReviewBinding.inflate(inflater, container, false)
        val pagerAdapter = ReviewTabAdapter(this)
        _reviewFragmentBinding.viewPager2.adapter = pagerAdapter

        TabLayoutMediator(_reviewFragmentBinding.tabLayout, _reviewFragmentBinding.viewPager2){
            tab, position ->
            val tabNames = listOf("Расходники", "Оборудование")
            tab.text = tabNames[position]
        }.attach()

        setupToolbarButtons()

        return _reviewFragmentBinding.root
    }

    private fun setupToolbarButtons() {
        _reviewFragmentBinding.addButton.setOnClickListener {
            if (_reviewFragmentBinding.viewPager2.currentItem == 0)
                showAddConsumablesItemRecordDialog()
            else
                showAddHardwareItemRecordDialog()
        }

        _reviewFragmentBinding.sortButton.setOnClickListener {
            showSortPopupMenu(it, R.menu.sort_popup_menu)
        }
    }

    private fun showAddConsumablesItemRecordDialog() {
        val binding: FragmentAddOrEditItemBinding = FragmentAddOrEditItemBinding.inflate(
            LayoutInflater.from(requireContext()), null, false)

        AlertDialog.Builder(requireContext(), R.style.MyAlertDialogRoundedTheme)
            .setTitle("Добавить новую запись") //Добавление заголовка.
            .setView(binding.root) //Присвоение View полученного ранее.
            .setPositiveButton("Добавить") { _, _ ->
                _mainViewModel.addItemToDatabase(
                    name = binding.itemNameEditText.text.toString(),
                    count = binding.itemCountEditText.text.toString(),
                    date = binding.itemDateEditText.text.toString(),
                    user = binding.itemUserEditText.text.toString(),
                    type = "consumables"
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
        val binding: FragmentAddOrEditItemBinding = FragmentAddOrEditItemBinding.inflate(
            LayoutInflater.from(requireContext()), null, false)

        AlertDialog.Builder(requireContext(), R.style.MyAlertDialogRoundedTheme)
            .setTitle("Добавить новую запись") //Добавление заголовка.
            .setView(binding.root) //Присвоение View полученного ранее.
            .setPositiveButton("Добавить") { _, _ ->
                _mainViewModel.addItemToDatabase(
                    name = binding.itemNameEditText.text.toString(),
                    count = binding.itemCountEditText.text.toString(),
                    date = binding.itemDateEditText.text.toString(),
                    user = binding.itemUserEditText.text.toString(),
                    type = "hardware"
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

    private fun showSortPopupMenu(view: View, @MenuRes menuRes: Int) {
        val popupMenu = PopupMenu(view.context, view)
        popupMenu.menuInflater.inflate(menuRes, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.sort_by_date -> {
                    Toast.makeText(requireContext(), "Сортировка по дате", Toast.LENGTH_SHORT).show()
                }
                R.id.sort_by_item_name -> {
                    Toast.makeText(requireContext(), "Сортировка по названию", Toast.LENGTH_SHORT).show()
                }
                R.id.sort_by_surname -> {
                    Toast.makeText(requireContext(), "Сортировка по фамилии", Toast.LENGTH_SHORT).show()
                }
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }
}