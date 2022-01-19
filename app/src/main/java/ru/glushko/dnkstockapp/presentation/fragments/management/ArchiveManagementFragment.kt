package ru.glushko.dnkstockapp.presentation.fragments.management

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.glushko.dnkstockapp.R
import ru.glushko.dnkstockapp.databinding.*
import ru.glushko.dnkstockapp.domain.model.ArchiveItem
import ru.glushko.dnkstockapp.presentation.viewmodels.ManagementViewModel
import ru.glushko.dnkstockapp.presentation.viewutils.recyclerAdapters.management.archive.ArchiveItemRecyclerAdapter

class ArchiveManagementFragment : Fragment() {

    private lateinit var _archiveManagementFragmentBinding: FragmentArchiveManagementBinding
    private lateinit var _archiveItemInfoFragmentBinding: FragmentArchiveItemInfoBinding //Намутить новый фрагмент

    private val _managementViewModel by viewModel<ManagementViewModel>()
    private var _archiveItemRecyclerAdapter = ArchiveItemRecyclerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _archiveManagementFragmentBinding = FragmentArchiveManagementBinding.inflate(inflater, container, false)

        setupRecyclerView()

        return  _archiveManagementFragmentBinding.root
    }

    override fun onStart() {
        _managementViewModel.allArchiveItems.observe(viewLifecycleOwner, { archiveItemsList ->
            _archiveItemRecyclerAdapter.submitList(archiveItemsList)
        })
        super.onStart()
    }

    private fun setupRecyclerView() {
        _archiveManagementFragmentBinding.recyclerView.adapter = _archiveItemRecyclerAdapter

        setupOnActionButtonClick(_archiveItemRecyclerAdapter)
        setupOnHolderViewClick(_archiveItemRecyclerAdapter)
    }

    private fun setupOnHolderViewClick(archiveItemRecyclerAdapter: ArchiveItemRecyclerAdapter) {
        archiveItemRecyclerAdapter.onHolderViewClickListener = { archiveItem ->
            showInfoAboutItemRecordDialog(archiveItem)
        }
    }

    private fun setupOnActionButtonClick(archiveItemRecyclerAdapter: ArchiveItemRecyclerAdapter) {
        archiveItemRecyclerAdapter.onPopupButtonClickListener = { archiveItem, view ->
            showPopupMenu(
                archiveItemElement = archiveItem,
                view = view,
                menuRes = R.menu.archive_action_popup_menu
            )
        }
    }

    private fun showPopupMenu(view: View, archiveItemElement: ArchiveItem, @MenuRes menuRes: Int) {
        val popupMenu = PopupMenu(view.context, view)
        popupMenu.menuInflater.inflate(menuRes, popupMenu.menu)
        //TODO: Намутить свап записей.
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete_action -> {
                    _managementViewModel.deleteArchiveItemFromDatabase(archiveItem = archiveItemElement)
                }
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    private fun showInfoAboutItemRecordDialog(archiveItem: ArchiveItem) {
        _archiveItemInfoFragmentBinding =
            FragmentArchiveItemInfoBinding.inflate(LayoutInflater.from(requireContext()), null, false)

        with(_archiveItemInfoFragmentBinding) {
            infoNameItem.text = archiveItem.name
            infoCountItem.text = archiveItem.count + " шт."
            infoDateItem.text = archiveItem.date //Сделать дату сдачи
            infoUserItem.text = archiveItem.user
            if(archiveItem.type == "consumables")
                infoTypeItem.text = "Расходник"
            else
                infoTypeItem.text = "Оборудование"
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Информация о сданном") //Добавление заголовка.
            .setView(_archiveItemInfoFragmentBinding.root) //Присвоение View полученного ранее.
            .setNegativeButton( "Закрыть") { dialog, _ -> dialog.cancel() }
            .show()
    }
}