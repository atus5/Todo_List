package com.example.todolist.data.local

import android.content.Context
import android.widget.Toast
import com.example.todolist.data.model.Title
import java.io.FileInputStream
import java.io.FileOutputStream

import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class FileHelper {
    val FILE_NAME = "listinfo.dat"
    fun writeData(item: ArrayList<Title>, context: Context) {
        val fos: FileOutputStream = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)
        val oos = ObjectOutputStream(fos)
        oos.writeObject(item)
        oos.close()
    }


    fun readData(context: Context): ArrayList<Title> {
        var itemList = ArrayList<Title>()
        try {

            val fis: FileInputStream = context.openFileInput(FILE_NAME)
            val ois = ObjectInputStream(fis)
            itemList = ois.readObject() as ArrayList<Title>
        } catch (e: Exception) {

            itemList = ArrayList()
        }
        return itemList
    }

}