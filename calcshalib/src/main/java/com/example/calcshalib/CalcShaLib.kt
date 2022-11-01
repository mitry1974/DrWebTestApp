package com.example.calcshalib

class CalcShaLib {

    /**
     * A native method that is implemented by the 'calcshalib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String
    external fun calculate(packageName: String): String

    companion object {
        // Used to load the 'calcshalib' library on application startup.
        init {
            System.loadLibrary("calcshalib")
        }
    }
}