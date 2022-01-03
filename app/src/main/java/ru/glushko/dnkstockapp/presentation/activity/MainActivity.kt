package ru.glushko.dnkstockapp.presentation.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.glushko.dnkstockapp.R
import ru.glushko.dnkstockapp.databinding.ActivityMainBinding
import ru.glushko.dnkstockapp.databinding.FragmentAddOrEditItemBinding
import ru.glushko.dnkstockapp.presentation.viewmodels.AddOrEditItemViewModel
import ru.glushko.dnkstockapp.presentation.viewmodels.MainViewModel
import ru.glushko.dnkstockapp.presentation.viewutils.Status


class MainActivity : AppCompatActivity() {

    private lateinit var _mainActivityBinding: ActivityMainBinding
    private val _mainViewModel by viewModel<MainViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        _mainActivityBinding.bottomNavView.setupWithNavController(navController)

        _mainActivityBinding.bottomNavView.background = null

        _mainActivityBinding.addItemRecordButton.setOnClickListener{
            if(navController.currentDestination?.label.contentEquals("ConsumablesFragment"))
                showAddConsumablesItemRecordDialog()
            else
                showAddHardwareItemRecordDialog()
        }
    }

    private fun showAddConsumablesItemRecordDialog() {
        val binding: FragmentAddOrEditItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this), R.layout.fragment_add_or_edit_item,
            null, false
        )
        val addItemFragmentViewModel =
            ViewModelProvider(this)[AddOrEditItemViewModel::class.java]

        binding.addItemVM = addItemFragmentViewModel

        AlertDialog.Builder(this, R.style.MyAlertDialogRoundedTheme)
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

                _mainViewModel.getStateAddItemLiveData().observe(this, {
                    when (it!!) {
                        Status.ERROR -> Snackbar.make(
                            _mainActivityBinding.root, "Введите все данные.",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        Status.SUCCESS -> Snackbar.make(
                            _mainActivityBinding.root, "Запись успешно добавлена!",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                })
            }.setNegativeButton("Отмена") { dialog, _ -> dialog.cancel() }.show()
    }

    private fun showAddHardwareItemRecordDialog() {
        val binding: FragmentAddOrEditItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(this), R.layout.fragment_add_or_edit_item,
            null, false
        )
        val addItemFragmentViewModel =
            ViewModelProvider(this)[AddOrEditItemViewModel::class.java]

        binding.addItemVM = addItemFragmentViewModel

        AlertDialog.Builder(this, R.style.MyAlertDialogRoundedTheme)
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

                _mainViewModel.getStateAddItemLiveData().observe(this, {
                    when (it!!) {
                        Status.ERROR -> Snackbar.make(
                            _mainActivityBinding.root, "Введите все данные.",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        Status.SUCCESS -> Snackbar.make(
                            _mainActivityBinding.root, "Запись успешно добавлена!",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                })
            }.setNegativeButton("Отмена") { dialog, _ -> dialog.cancel() }.show()
    }
}