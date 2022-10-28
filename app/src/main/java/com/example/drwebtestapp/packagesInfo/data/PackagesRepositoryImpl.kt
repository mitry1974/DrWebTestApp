package com.example.drwebtestapp.packagesInfo.data

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.GET_META_DATA
import com.example.drwebtestapp.packagesInfo.data.model.AppInfo
import com.example.drwebtestapp.packagesInfo.domain.PackagesRepository
import java.security.MessageDigest


object PackagesRepositoryImpl : PackagesRepository {

    override suspend fun getPackagesList(pm: PackageManager): List<AppInfo> {
        val intent = Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }
        val appsInfo = pm.queryIntentActivities(intent, GET_META_DATA).map {
            AppInfo(
                label = it.loadLabel(pm).toString(),
                packageName = it.activityInfo.packageName,
                signs = getPackageSha(it.activityInfo.packageName, pm),
                version = getPackageVersion(it.activityInfo.packageName, pm)
            )
        }
        return appsInfo
    }

    private fun getPackageVersion(packageName: String, pm: PackageManager): String {
        return pm.getPackageInfo(packageName, 0).versionName
    }

    private fun getPackageSha(packageName: String, pm: PackageManager): List<String> {
        val sig =
            pm.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES).signingInfo
        return if (sig.hasMultipleSigners()) {
            sig.apkContentsSigners.map {
                val digest = MessageDigest.getInstance("SHA")
                digest.update(it.toByteArray())
                bytesToHex(digest.digest())
            }
        } else {
            sig.signingCertificateHistory.map {
                val digest = MessageDigest.getInstance("SHA")
                digest.update(it.toByteArray())
                bytesToHex(digest.digest())
            }
        }
    }

    private fun bytesToHex(bytes: ByteArray): String {
        return bytes.joinToString("") { it.toString(radix = 16).padStart(2, '0') }
    }

}