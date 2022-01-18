package ru.glushko.dnkstockapp.presentation.fragments.management

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.glushko.dnkstockapp.R
import ru.glushko.dnkstockapp.databinding.FragmentAddOrEditStaffBinding
import ru.glushko.dnkstockapp.databinding.FragmentStaffManagementBinding
import ru.glushko.dnkstockapp.domain.model.Staff
import ru.glushko.dnkstockapp.presentation.viewmodels.ManagementViewModel
import ru.glushko.dnkstockapp.presentation.viewutils.recyclerAdapters.management.staff.StaffRecyclerAdapter

class StaffManagementFragment : Fragment() {
    private lateinit var _staffManagementFragmentBinding: FragmentStaffManagementBinding
    private lateinit var _addOrEditStaffBinding: FragmentAddOrEditStaffBinding

    private val _managementViewModel by viewModel<ManagementViewModel>()
    private var _staffItemRecyclerAdapter = StaffRecyclerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _staffManagementFragmentBinding =
            FragmentStaffManagementBinding.inflate(inflater, container, false)

        setupRecyclerView()

        setupToolbarFunctional()

        return _staffManagementFragmentBinding.root
    }

    private fun setupToolbarFunctional() {
        _staffManagementFragmentBinding.addButton.setOnClickListener {
            showAddStaffDialog()
        }
    }

    private fun setupRecyclerView() {
        _managementViewModel.allStaff.observe(viewLifecycleOwner, { staffList ->
            _staffItemRecyclerAdapter.submitList(staffList)
        })

        _staffManagementFragmentBinding.recyclerView.adapter = _staffItemRecyclerAdapter

        setupOnActionButtonClick(_staffItemRecyclerAdapter)
    }

    private fun setupOnActionButtonClick(staffItemRecyclerAdapter: StaffRecyclerAdapter) {
        staffItemRecyclerAdapter.onPopupButtonClickListener = { staffElement, view ->
            showPopupMenu(
                staffElement = staffElement,
                view = view,
                menuRes = R.menu.action_popup_menu
            )
        }
    }

    private fun showPopupMenu(view: View, staffElement: Staff, @MenuRes menuRes: Int) {
        val popupMenu = PopupMenu(view.context, view)
        popupMenu.menuInflater.inflate(menuRes, popupMenu.menu)
        //TODO: Намутить свап записей.
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete_action -> {
                    _managementViewModel.deleteStaffFromDatabase(staff = staffElement)
                }
                R.id.edit_action -> {
                    showEditStaffDialog(staffElement = staffElement)
                }
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    private fun showAddStaffDialog() {
        _addOrEditStaffBinding =
            FragmentAddOrEditStaffBinding.inflate(
                LayoutInflater.from(requireContext()),
                null,
                false
            )

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Добавить новую запись") //Добавление заголовка.
            .setView(_addOrEditStaffBinding.root) //Присвоение View полученного ранее.
            .setPositiveButton("Добавить") { _, _ ->
                _managementViewModel.addStaffToDatabase(
                    surname = _addOrEditStaffBinding.surnameEditText.text.toString(),
                    name = _addOrEditStaffBinding.nameEditText.text.toString(),
                    lastname = _addOrEditStaffBinding.lastnameEditText.text.toString()
                )

                _managementViewModel.transactionStatus.observe(viewLifecycleOwner, { status ->
                    Toast.makeText(requireContext(), status, Toast.LENGTH_SHORT).show()
                })
            }
            .setNeutralButton("Отмена") { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun showEditStaffDialog(staffElement: Staff) {
        _addOrEditStaffBinding =
            FragmentAddOrEditStaffBinding.inflate(
                LayoutInflater.from(requireContext()),
                null,
                false
            )

        with(_addOrEditStaffBinding) {
            surnameEditText.setText(staffElement.surname)
            nameEditText.setText(staffElement.name)
            lastnameEditText.setText(staffElement.lastname)
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Редактировать запись") //Добавление заголовка.
            .setView(_addOrEditStaffBinding.root) //Присвоение View полученного ранее.
            .setPositiveButton("Готово") { _, _ ->
                _managementViewModel.updateStaffInDatabase(
                    id = staffElement.id,
                    surname = _addOrEditStaffBinding.surnameEditText.text.toString(),
                    name = _addOrEditStaffBinding.nameEditText.text.toString(),
                    lastname = _addOrEditStaffBinding.lastnameEditText.text.toString()
                )

                _managementViewModel.transactionStatus.observe(viewLifecycleOwner, { status ->
                    Toast.makeText(requireContext(), status, Toast.LENGTH_SHORT).show()
                })
            }
            .setNeutralButton("Отмена") { dialog, _ -> dialog.cancel() }
            .show()
    }
}