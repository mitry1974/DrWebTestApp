package com.example.drwebtestapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.drwebtestapp.packagesInfo.core.ToolbarActions
import com.example.drwebtestapp.packagesInfo.presentation.packagesScreen.PackagesFragment
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity(), ToolbarActions {

    private val toolbar: MaterialToolbar by lazy { findViewById(R.id.toolbar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(com.google.android.material.R.id.container, PackagesFragment.newInstance())
                .commitNow()
        }
    }

    override fun setToolbarTitle(toolbarTitle: String) {
        toolbar.title = toolbarTitle
    }

    override fun setToolbarBackButtonAction(action: (() -> Unit)?) {
        toolbar.setNavigationOnClickListener {
            action?.invoke()
        }
    }
}