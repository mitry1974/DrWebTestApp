package com.example.drwebtestapp.packagesInfo.core

interface ToolbarActions {
    fun setToolbarTitle(toolbarTitle: String)
    fun setToolbarBackButtonAction(action: (() -> Unit)?)
}