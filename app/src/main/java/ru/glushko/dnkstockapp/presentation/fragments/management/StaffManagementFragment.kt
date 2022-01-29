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
    private lateinit var staffFBinding: FragmentAddOrEditStaffBinding

    private val _managementViewModel by viewModel<ManagementViewModel>()
    private var _staffItemRecyclerAdapter = StaffRecyclerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _staffManagementFragmentBinding =
            FragmentStaffManagementBinding.inflate(inflater, container, false)

        setupRecyclerView()
        setupToolbarFunctional()

        return _staffManagementFragmentBinding.root
    }

    override fun onResume() {
        _managementViewModel.allStaff.observe(viewLifecycleOwner) { staffList ->
            _staffItemRecyclerAdapter.submitList(staffList)
            if (staffList.isNullOrEmpty())
                showEmptyAttentionDialog()
        }
        super.onResume()
    }

    private fun setupToolbarFunctional() {
        _staffManagementFragmentBinding.addButton.setOnClickListener {
            showAddStaffDialog()
        }
    }

    private fun showEmptyAttentionDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Внимание!")
            .setMessage("Для начала работы вам необходимо добавить хотя бы одного человека.")
            .setPositiveButton("Добавить") { _, _ -> showAddStaffDialog() }
            .setNeutralButton("Отмена") { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun setupRecyclerView() {
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
        staffFBinding =
            FragmentAddOrEditStaffBinding.inflate(
                LayoutInflater.from(requireContext()),
                null,
                false
            )

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Добавить новую запись") //Добавление заголовка.
            .setView(staffFBinding.root) //Присвоение View полученного ранее.
            .setPositiveButton("Добавить") { _, _ ->
                val surname = staffFBinding.surnameEditText.text.toString().trim()
                val name = staffFBinding.nameEditText.text.toString().trim()
                val lastname = staffFBinding.lastnameEditText.text.toString().trim()

                _managementViewModel.addStaffToDatabase(
                    surname = surname,
                    name = name,
                    lastname = lastname
                )

                _managementViewModel.transactionStatus.observe(viewLifecycleOwner) { status ->
                    Toast.makeText(requireContext(), status, Toast.LENGTH_SHORT).show()
                }
            }
            .setNeutralButton("Отмена") { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun showEditStaffDialog(staffElement: Staff) {
        staffFBinding =
            FragmentAddOrEditStaffBinding.inflate(
                LayoutInflater.from(requireContext()),
                null,
                false
            )

        with(staffFBinding) {
            surnameEditText.setText(staffElement.surname)
            nameEditText.setText(staffElement.name)
            lastnameEditText.setText(staffElement.lastname)
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Редактировать запись") //Добавление заголовка.
            .setView(staffFBinding.root) //Присвоение View полученного ранее.
            .setPositiveButton("Готово") { _, _ ->
                val surname = staffFBinding.surnameEditText.text.toString().trim()
                val name = staffFBinding.nameEditText.text.toString().trim()
                val lastname = staffFBinding.lastnameEditText.text.toString().trim()

                _managementViewModel.updateStaffInDatabase(
                    id = staffElement.id,
                    surname = surname,
                    name = name,
                    lastname = lastname
                )

                _managementViewModel.transactionStatus.observe(viewLifecycleOwner) { status ->
                    Toast.makeText(requireContext(), status, Toast.LENGTH_SHORT).show()
                }
            }
            .setNeutralButton("Отмена") { dialog, _ -> dialog.cancel() }
            .show()
    }
}