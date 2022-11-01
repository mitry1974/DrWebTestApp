package com.example.drwebtestapp.packagesInfo.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AppInfo(
    val label: String,
    val packageName: String,
    val version: String
) : Parcelable
