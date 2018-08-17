package com.deepan.koltin.ui

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import com.deepan.koltin.BR
import com.deepan.koltin.R
import com.deepan.koltin.adapter.UserAdapter
import com.deepan.koltin.database.DbHandlerThread
import com.deepan.koltin.database.UserDataBase
import com.deepan.koltin.database.model.UserData
import com.deepan.koltin.databinding.UiHomeScreenBinding
import com.deepan.koltin.model.UserModel
import com.deepan.koltin.recyclerutils.SwipeDeleteMenu
import kotlinx.android.synthetic.main.ui_home_screen.*

class HomeScreen : BaseActivity(), View.OnClickListener {

    private val mUserList: ArrayList<UserModel> = ArrayList()

    private var mUserDataBase: UserDataBase? = null
    private lateinit var mDbHandlerThread: DbHandlerThread
    private val mUiHandler = Handler()
    private var start: Int = 1
    private var end: Int = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: UiHomeScreenBinding = DataBindingUtil.setContentView(this, R.layout.ui_home_screen)
        binding.setVariable(BR.listener, this)
        initView()

    }

    private fun initView() {
        mDbHandlerThread = DbHandlerThread("userDataThread", HandlerThread.MAX_PRIORITY)
        mDbHandlerThread.start()

        mUserDataBase = UserDataBase.getInstance(applicationContext)
        this.userListView.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        val swipeHandler = object : SwipeDeleteMenu(applicationContext) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
                val pos = viewHolder!!.adapterPosition
                deleteSingleUserData(mUserList[pos].UserName)
                val adapter: UserAdapter = userListView.adapter as UserAdapter
                adapter.removeAt(pos)

            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(userListView)
        getFromUserData()
    }

    override fun onDestroy() {
        UserDataBase.destroyInstance()
        mDbHandlerThread.quit()
        super.onDestroy()
    }

    private fun addUserList() {
        mUserList.clear()
        for (i in start..end) {
            mUserList += UserModel("Deepan_" + i.toString())
        }
        insertToUserData(mUserList)
    }

    private fun insertToUserData(mUserList: ArrayList<UserModel>) {
        for (item in mUserList) {
            val userData = UserData(null, item.UserName)
            val task = Runnable { mUserDataBase?.userDataAccess()?.insert(userData) }
            mDbHandlerThread.postTask(task)
        }
        getFromUserData()
    }

    private fun getFromUserData() {
        val task = Runnable {
            val localData = mUserDataBase?.userDataAccess()?.getAll()
            mUiHandler.post {
                if (localData == null || localData.isEmpty()) {
                    addUserList()
                } else {
                    mUserList.clear()
                    for (item in localData) {
                        val userData = UserModel(item.username)
                        mUserList += userData
                        setAdapter()
                    }
                }
            }
        }
        mDbHandlerThread.postTask(task)
    }

    private fun deleteAllUserData() {
        val task = Runnable {
            mUserDataBase?.userDataAccess()?.deleteAll()
            val userData = mUserDataBase?.userDataAccess()?.getAll()
            mUiHandler.post {
                if (userData == null || userData.isEmpty()) {
                    showToast("All records are cleared")
                    mUserList.clear()
                    setAdapter()
                } else {
                    sysout("Something went wrong while clear local DB")
                }
            }
        }
        mDbHandlerThread.postTask(task)
    }

    private fun deleteSingleUserData(username: String) {
        val task = Runnable {
            mUserDataBase?.userDataAccess()?.deleteSingleItem(username)
        }
        mDbHandlerThread.postTask(task)
    }

    private fun setAdapter() {
        if (userListView.adapter == null) {
            userListView.adapter = UserAdapter(applicationContext, mUserList)
        } else {
            userListView.adapter.notifyDataSetChanged()
        }
        end = mUserList.size
        userListView.scrollToPosition(mUserList.size - 1)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.add_user_btn -> {
                start = end + 1
                end += 5
                addUserList()
            }
            R.id.delete_btn -> {
                start = 1
                end = 5
                deleteAllUserData()
            }
        }
    }
}

