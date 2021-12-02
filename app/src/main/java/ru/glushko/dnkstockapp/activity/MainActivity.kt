package ru.glushko.dnkstockapp.activity

import android.content.ClipData
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import ru.glushko.dnkstockapp.R
import ru.glushko.dnkstockapp.databinding.ActivityMainBinding
import ru.glushko.dnkstockapp.databinding.FragmentAddItemBinding
import ru.glushko.dnkstockapp.databinding.RecyclerItemBinding
import ru.glushko.dnkstockapp.model.Item
import ru.glushko.dnkstockapp.utils.Status
import ru.glushko.dnkstockapp.utils.recyclerAdapter.ItemRecyclerAdapter
import ru.glushko.dnkstockapp.viewmodels.AddItemFragmentViewModel
import ru.glushko.dnkstockapp.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var _mainActivityBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        _mainActivityBinding.mainVM = MainViewModel.instance

        setupRecyclerView()

        _mainActivityBinding.addItemRecordButton.setOnClickListener {
            showAddItemRecordDialog()
        }
    }

    private fun setupRecyclerView() {
        val itemRecyclerAdapter = ItemRecyclerAdapter()

        MainViewModel.instance.getItemsLiveData().observe(this, {
            itemRecyclerAdapter.items = it
        })

        _mainActivityBinding.recyclerView.adapter = itemRecyclerAdapter

        setupOnHolderViewClick(itemRecyclerAdapter)
        setupOnActionButtonClick(itemRecyclerAdapter)
    }

    private fun setupOnHolderViewClick(itemRecyclerAdapter: ItemRecyclerAdapter) {
        itemRecyclerAdapter.onHolderViewClickListener = {
            Log.i("item", it.toString())
        }
    }

    private fun setupOnActionButtonClick(itemRecyclerAdapter: ItemRecyclerAdapter) {
        itemRecyclerAdapter.onPopupButtonClickListener = {
            MainViewModel.instance.deleteItemFromDatabase(it)
            Log.i("item", "Deleted Item: $it")
        }
    }

    private fun showAddItemRecordDialog() {
        val binding: FragmentAddItemBinding = DataBindingUtil.inflate(
            LayoutInflater
                .from(this), R.layout.fragment_add_item, null, false
        )
        val addItemFragmentViewModel =
            ViewModelProvider(this).get(AddItemFragmentViewModel::class.java)
        binding.addItemVM = addItemFragmentViewModel

        AlertDialog.Builder(this)
            .setTitle("Добавить новую запись") //Добавление заголовка.
            .setView(binding.root) //Присвоение View полученного ранее.
            .setPositiveButton("Добавить") { _, _ ->
                MainViewModel.instance.addItemToDatabase(
                    binding.itemNameEditText.text.toString(),
                    binding.itemCountEditText.text.toString(),
                    binding.itemDateEditText.text.toString(),
                    binding.itemUserEditText.text.toString()
                )

                MainViewModel.instance.getStateAddItemLiveData().observe(this, {
                    when (it!!) {
                        Status.ERROR -> Snackbar.make(
                            _mainActivityBinding.root,
                            "Введите все данные.",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        Status.SUCCESS -> Snackbar.make(
                            _mainActivityBinding.root,
                            "Данные успешно добавлены!",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                })
            }.show()
    }


}