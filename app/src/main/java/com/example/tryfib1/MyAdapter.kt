package com.example.tryfib1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val userList : ArrayList<User>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item,parent,false)
        return  MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = userList[position]

        holder.name.text = currentitem.name
        holder.bloodgroup.text = currentitem.bloodgroup
        holder.phone.text = currentitem.phone
    }

    override fun getItemCount(): Int {
        return  userList.size
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.RV_UserName)
        val bloodgroup : TextView = itemView.findViewById(R.id.RV_UserBloodGroup)
        var phone : TextView = itemView.findViewById(R.id.RV_UserPhone)
    }
}