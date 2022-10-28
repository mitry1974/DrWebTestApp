package com.example.drwebtestapp.packagesInfo.presentation.packagesScreen

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.drwebtestapp.R
import com.example.drwebtestapp.databinding.FragmentsPackagesBinding
import com.example.drwebtestapp.packagesInfo.core.ToolbarActions
import com.example.drwebtestapp.packagesInfo.domain.PackagesRepository
import com.example.drwebtestapp.packagesInfo.presentation.packageDetailsScreen.PackageDetailsFragment

class PackagesFragment private constructor() : Fragment() {
    companion object {
        fun newInstance() = PackagesFragment()

        const val APP_COLUMN_WIDTH = 80.0F
    }

    private val vm: PackagesViewModel by viewModels {
        PackagesViewModel.provideFactory(
            PackagesRepository.newInstance(), this
        )
    }

    private var _binding: FragmentsPackagesBinding? = null
    private val binding get() = _binding!!

    private val adapter = ApplicationsInfoAdapter(::onAppClick)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentsPackagesBinding.inflate(inflater, container, false)
        initToolbar()
        observeViewModel()
        return binding.root
    }

    private fun initToolbar() {
        with(requireActivity() as ToolbarActions) {
            setToolbarTitle(getString(R.string.packages_fragment_name))
            setToolbarBackButtonAction {
                requireActivity().finishAndRemoveTask()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerViewAdapter()
        vm.getApplicationsInfo(requireActivity().packageManager)
    }

    private fun initRecyclerViewAdapter() {

        val colCount = calculateNoOfColumns()
        with(binding) {
            packages.adapter = adapter
            packages.layoutManager = GridLayoutManager(requireContext(), colCount)
        }
    }

    private fun observeViewModel() {
        vm.appsInfo.observe(viewLifecycleOwner) {
            adapter.setItems(it.map { appInfo ->
                AppInfoUiModel(
                    label = appInfo.label,
                    packageName = appInfo.packageName,
                    icon = requireActivity().packageManager.getApplicationIcon(appInfo.packageName)
                )
            })
        }
    }

    private fun onAppClick(packageName: String) {
        val appInfo = vm.appsInfo.value?.find { it.packageName == packageName }
        appInfo?.let {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(
                    com.google.android.material.R.id.container,
                    PackageDetailsFragment.newInstance(it)
                )
                .commit()
        }
    }

    private fun calculateNoOfColumns(
    ): Int {
        val displayMetrics: DisplayMetrics = requireContext().resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        return (screenWidthDp / APP_COLUMN_WIDTH + 0.5).toInt()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
