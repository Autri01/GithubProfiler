package com.example.github_user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_user.view.*
import kotlinx.coroutines.NonDisposableHandle.parent
import java.util.*

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewAdapter>() {

    private var data: List<User> = ArrayList()
    var onItemClick:((login:String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewAdapter {
        return UserViewAdapter(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_user, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: UserViewAdapter, position: Int) =
        holder.bind(data[position])

    fun swapData(data: List<User>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class UserViewAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: User) = with(itemView) {
            textView.text=item.name
            textView2.text=item.login
            Picasso.get().load(item.avatarUrl).into(imageView)
            setOnClickListener {
                onItemClick?.invoke(item.login)
            }
        }
    }
}