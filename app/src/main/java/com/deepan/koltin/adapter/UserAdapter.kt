package com.deepan.koltin.adapter

import android.content.Context
import com.deepan.koltin.R
import com.deepan.koltin.model.UserModel
import com.deepan.koltin.recyclerutils.RecyclerBaseAdapter

class UserAdapter(private val mContext: Context,
                  private val mUsersList: ArrayList<UserModel>) : RecyclerBaseAdapter() {

    override fun getLayoutIdForPosition(position: Int) = R.layout.adap_home_screen

    override fun getViewModel(position: Int) = mUsersList[position]

    override fun getItemCount() = mUsersList.size

    fun removeAt(position: Int) {
        mUsersList.removeAt(position)
        notifyItemRemoved(position)
    }

}