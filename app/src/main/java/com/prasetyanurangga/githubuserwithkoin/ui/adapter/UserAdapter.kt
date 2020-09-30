package com.prasetyanurangga.githubuserwithkoin.ui.adapter

import android.content.Context
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.prasetyanurangga.githubuserwithkoin.R
import com.prasetyanurangga.githubuserwithkoin.data.model.UserModel
import com.squareup.picasso.Picasso

class UserAdapter(private var userList: MutableList<UserModel>, private var context : Context): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.UserViewHolder {
        return UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_user, parent, false))
    }

    fun updateUser(newUser: MutableList<UserModel>?)
    {
        userList = newUser!!
    }

    fun resetUser()
    {
        userList.clear()
    }

    fun addUser(newUser: MutableList<UserModel>?)
    {
        userList.let { list1 -> newUser?.let(list1::addAll) }
    }

    fun <UserModel> merge(first: List<UserModel>?, second: List<UserModel>?): List<UserModel> {
        val list: MutableList<UserModel> = ArrayList()
        list.addAll(first!!)
        list.addAll(second!!)
        return list
    }

    override fun getItemCount(): Int {
        return userList?.size ?: 0
    }

    override fun onBindViewHolder(holder: UserAdapter.UserViewHolder, position: Int) {
        holder.bindItem(userList[position], context = context)
    }

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private  val userName: TextView = view.findViewById(R.id.user_name_txt)
        private  val UserUrl: TextView = view.findViewById(R.id.user_url_txt)
        private  val UserImg: ImageView = view.findViewById(R.id.user_img)
        fun bindItem(items: UserModel, context: Context){
            userName.text = items.name
            UserUrl.text = items.url
            Picasso.with(context).load(items.avatar).into(UserImg)
        }
    }

}


