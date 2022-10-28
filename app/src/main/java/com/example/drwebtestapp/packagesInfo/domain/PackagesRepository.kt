package com.example.drwebtestapp.packagesInfo.domain

import android.content.pm.PackageManager
import com.example.drwebtestapp.packagesInfo.data.PackagesRepositoryImpl
import com.example.drwebtestapp.packagesInfo.data.model.AppInfo

interface PackagesRepository {

    companion object {
        fun newInstance() = PackagesRepositoryImpl
    }

    suspend fun getPackagesList(pm: PackageManager): List<AppInfo>
}