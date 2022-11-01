package com.example.drwebtestapp.packagesInfo.presentation.packageDetailsScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.calcshalib.CalcShaLib
import com.example.drwebtestapp.R
import com.example.drwebtestapp.databinding.FragmentPackageDetailsBinding
import com.example.drwebtestapp.packagesInfo.core.ToolbarActions
import com.example.drwebtestapp.packagesInfo.data.model.AppInfo
import com.example.drwebtestapp.packagesInfo.presentation.packagesScreen.PackagesFragment


class PackageDetailsFragment : Fragment() {
    companion object {
        const val APP_INFO_ARG = "appInfoArg"

        fun newInstance(appInfo: AppInfo) = PackageDetailsFragment().apply {
            arguments = bundleOf(APP_INFO_ARG to appInfo)
        }
    }

    private val appInfo: AppInfo by lazy { requireArguments().getParcelable(APP_INFO_ARG)!! }

    private var _binding: FragmentPackageDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPackageDetailsBinding.inflate(inflater, container, false)
        initToolbar()
        initListeners()
        return binding.root
    }

    private fun initListeners() {
        binding.launch.setOnClickListener {
            requireActivity()
                .packageManager
                .getLaunchIntentForPackage(appInfo.packageName)?.let {
                    startActivity(it)
                }
        }
    }

    private fun initToolbar() {
        with(requireActivity() as ToolbarActions) {
            setToolbarTitle(getString(R.string.package_details_fragment_name))
            setToolbarBackButtonAction {
                parentFragmentManager.beginTransaction().replace(
                    com.google.android.material.R.id.container,
                    PackagesFragment.newInstance()
                ).commitNow()
            }
        }
    }

    private fun fillUi() {
        with(binding) {
            val lib = CalcShaLib()
            appIcon.setImageDrawable(requireActivity().packageManager.getApplicationIcon(appInfo.packageName))
            appLabel.text = appInfo.label
            appPackageName.text = appInfo.packageName
            appVersion.text = appInfo.version
            appPackageSign.text = lib.calculate(appInfo.packageName)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fillUi()
    }
}