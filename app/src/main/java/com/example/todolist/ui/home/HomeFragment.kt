package com.example.todolist.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.data.local.FileHelper
import com.example.todolist.data.model.Title
import com.example.todolist.databinding.FragmentHomeBinding
import com.example.todolist.ui.Adapter.AdapterTodo


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    var fileHelper = FileHelper()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var list = arrayListOf<Title>()

        list = fileHelper.readData(requireContext())
        val adapter = AdapterTodo(list)

        binding.btnAdd.setOnClickListener {
            val text = binding.edtAdd.text.toString().trim()
            if (text.isEmpty()) {
                Toast.makeText(requireContext(), "⚠️ Nhập gì đó đi ku", Toast.LENGTH_SHORT).show()

            }
            list.add(0, Title(text))              // ➕ Thêm lên đầu danh sách
            adapter.notifyItemInserted(0)         // 🎯 Báo adapter
            binding.recyclerView.scrollToPosition(0)      // 📜 Cuộn lên đầu
            fileHelper.writeData(list, requireContext())

            binding.edtAdd.text.clear()                   // 🧹 Xóa ô nhập
        }


        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter


    }


}