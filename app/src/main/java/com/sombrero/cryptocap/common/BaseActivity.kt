package com.sombrero.cryptocap.common

import android.support.v7.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    abstract fun getFragmentContainerId(): Int
}