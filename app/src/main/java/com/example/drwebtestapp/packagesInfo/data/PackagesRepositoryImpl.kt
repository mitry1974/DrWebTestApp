package com.example.drwebtestapp.packagesInfo.data

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.GET_META_DATA
import com.example.drwebtestapp.packagesInfo.data.model.AppInfo
import com.example.drwebtestapp.packagesInfo.domain.PackagesRepository


object PackagesRepositoryImpl : PackagesRepository {

    override suspend fun getPackagesList(pm: PackageManager): List<AppInfo> {
        val intent = Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }
        val appsInfo = pm.queryIntentActivities(intent, GET_META_DATA).map {
            AppInfo(
                label = it.loadLabel(pm).toString(),
                packageName = it.activityInfo.packageName,
                version = pm.getPackageInfo(it.activityInfo.packageName, 0).versionName
            )
        }
        return appsInfo
    }
}