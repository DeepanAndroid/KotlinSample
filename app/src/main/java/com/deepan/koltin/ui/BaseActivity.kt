package com.deepan.koltin.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.deepan.koltin.R
import com.deepan.koltin.adapter.UserAdapter

open class BaseActivity : AppCompatActivity() {

    private var mActivityContext: AppCompatActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityContext = this
    }

    override fun onResume() {
        super.onResume()
        mActivityContext = this
    }

    fun showToast(mMsg: String) {
        Toast.makeText(mActivityContext, mMsg, Toast.LENGTH_SHORT).show();
    }

    fun sysout(mMsg: String) {
        System.out.println(mMsg)
        Log.e(getString(R.string.app_name), mMsg)
    }

}
