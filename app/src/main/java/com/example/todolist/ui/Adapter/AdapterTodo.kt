package com.example.todolist.ui.Adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.data.local.FileHelper
import com.example.todolist.data.model.Title

class AdapterTodo(val list: ArrayList<Title>) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_todo, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var listTodo = list[position]
        var fileHelper = FileHelper()
        holder.todo_title.text = listTodo.title

        holder.todo_title.setOnClickListener {
            AlertDialog.Builder(holder.itemView.context)
                .setTitle("Xác nhận")
                .setMessage("Bạn đã hoàn thành rồi chứ ?")
                .setPositiveButton("Yes") { dialog, which ->
                    list.removeAt(position)

                    fileHelper.writeData(list, holder.itemView.context)
                    notifyDataSetChanged()
                    dialog.dismiss()

                }
                .setNegativeButton("No") { dialog, which ->
                    dialog.dismiss()
                }
                .show()
        }


    }



}


class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val todo_title: TextView = itemView.findViewById(R.id.todo_title)

}