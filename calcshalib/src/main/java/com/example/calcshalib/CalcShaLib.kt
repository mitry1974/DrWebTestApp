package com.example.calcshalib

class CalcShaLib {

    external fun calculate(fileName: String): String

    companion object {
        // Used to load the 'calcshalib' library on application startup.
        init {
            System.loadLibrary("calcshalib")
        }
    }
}