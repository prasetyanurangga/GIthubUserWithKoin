package com.prasetyanurangga.githubuserwithkoin.ui.view

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.*
import com.prasetyanurangga.githubuserwithkoin.R
import com.prasetyanurangga.githubuserwithkoin.data.model.UserModel
import com.prasetyanurangga.githubuserwithkoin.ui.adapter.UserAdapter
import com.prasetyanurangga.githubuserwithkoin.ui.viewmodel.UserViewModel
import com.prasetyanurangga.githubuserwithkoin.util.Status
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private var userAdapter : UserAdapter? = null
    private lateinit var rvUser : RecyclerView
    private lateinit var edSearch : EditText

    lateinit var progressDialog: ProgressDialog

    private val userViewModel : UserViewModel by viewModel()

    private var visibleThreshold: Int = 20
    private var currentPage: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()

        progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setCancelable(false)
        progressDialog.setMessage("Please Wait ...")


        rvUser.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)


                if(dy > 0)
                {

                    if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
                        currentPage += visibleThreshold;
                        loadMore("a", currentPage, visibleThreshold)
                    }
                }
            }
        })

        edSearch.setOnEditorActionListener(TextView.OnEditorActionListener { textView, actionId, keyEvent ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                onUpdateList(textView.text.toString() ?: "")
                true
            } else {
                false
            }
        })

        edSearch.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.isNullOrEmpty())
                {
                    userAdapter?.resetUser()
                    userAdapter?.notifyDataSetChanged()
                }
            }

        })


    }

    fun Context.hideKeyboard() {
        val view = this@MainActivity.currentFocus
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }



    private fun loadMore(q: String, page: Int, perPage: Int)
    {
            userViewModel.getUser(q, page, perPage).observe(this, Observer {
                resource ->
            if (!isDestroyed)
            {
                when (resource.status) {
                    Status.SUCCESS -> {
                        hideKeyboard()
                        resource.data?.let { list ->
                            addList(list)
                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                }

            }
        }
            )
    }

    private fun showList(userList: List<UserModel>?)
    {
        if(userAdapter == null && userList != null)
        {
            rvUser.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            userAdapter = UserAdapter(userList as MutableList<UserModel>, this@MainActivity)
            rvUser.adapter = userAdapter
        }
        else
        {
            userAdapter?.updateUser(userList as MutableList<UserModel>)
            userAdapter?.notifyDataSetChanged()
        }
    }

    private fun addList(userList: List<UserModel>?)
    {
        userAdapter?.addUser(userList as MutableList<UserModel>)
        userAdapter?.notifyDataSetChanged()
    }

    private fun init()
    {
        rvUser = findViewById(R.id.rview_user)
        edSearch = findViewById(R.id.ed_search)
    }


    fun onUpdateList(q: String)
    {
        currentPage = 0;
        progressDialog.show()
        userViewModel.getUser(q, currentPage, visibleThreshold).observe(this, Observer {
                resource ->
            if (!isDestroyed && q.isNotEmpty())
            {
                progressDialog.hide()
                when (resource.status) {
                    Status.SUCCESS -> {
                        hideKeyboard()
                        resource.data?.let { list ->
                            showList(list)
                        }
                    }
                    Status.ERROR -> {
                        Toast.makeText(this, resource.message, Toast.LENGTH_LONG).show()
                    }
                }


            }
            else
            {
                showList(null)
            }
        })

    }




}