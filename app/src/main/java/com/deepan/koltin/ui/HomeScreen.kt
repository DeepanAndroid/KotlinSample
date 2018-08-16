package com.deepan.koltin.ui

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.deepan.koltin.R
import com.deepan.koltin.adapter.UserAdapter
import com.deepan.koltin.model.UserModel
import com.deepan.koltin.recyclerutils.SwipeDeleteMenu
import kotlinx.android.synthetic.main.ui_home_screen.*

class HomeScreen : BaseActivity() {

    private val mUserList: ArrayList<UserModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ViewDataBinding = DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.ui_home_screen)
        initView()

    }

    private fun initView() {
        userListView.layoutManager = LinearLayoutManager(this)
        val swipeHandler = object : SwipeDeleteMenu(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                val adapter: UserAdapter = userListView.adapter as UserAdapter
                adapter.removeAt(viewHolder!!.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(userListView)
        addUserList()

    }

    private fun addUserList() {
        try {
            for (i in 1..100) {
                mUserList += UserModel("Deepan_" + i.toString())
            }
            setAdapter()
        } catch (e: Exception) {
            sysout("Deepan_" + e.message)
        }

    }

    private fun setAdapter() {
        try {
            if (userListView.adapter == null) {
                userListView.adapter = UserAdapter(applicationContext, mUserList)
            } else {
                userListView.adapter.notifyDataSetChanged();
            }

        } catch (e: Exception) {
            sysout("Deepan_" + e.message)
        }

    }
}
