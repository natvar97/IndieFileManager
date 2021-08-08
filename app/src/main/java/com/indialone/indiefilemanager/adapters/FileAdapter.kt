package com.indialone.indiefilemanager.adapters

import android.content.Context
import android.text.format.Formatter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.indialone.indiefilemanager.R
import com.indialone.indiefilemanager.databinding.FileItemBinding
import com.indialone.indiefilemanager.listeners.OnFileSelectedListener
import java.io.File
import java.util.*

class FileAdapter(
    private val context: Context,
    private val list: ArrayList<File>,
    private val listener: OnFileSelectedListener
) : RecyclerView.Adapter<FileAdapter.FileViewHolder>() {
    class FileViewHolder(itemView: FileItemBinding) : RecyclerView.ViewHolder(itemView.root) {
        val fileName = itemView.tvFileName
        val fileSize = itemView.tvFileSize
        val ivFileType = itemView.ivFileType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val view = FileItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FileViewHolder(view)
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        holder.fileName.text = list[position].name
        holder.fileName.isSelected = true
        var items = 0
        if (list[position].isDirectory) {
            val files = list[position].listFiles()
            if (files != null) {
                for (file in files) {
                    if (!file.isHidden) {
                        items += 1
                    }
                }
                holder.fileSize.text = "$items files"
            }
        } else {
            holder.fileSize.text = Formatter.formatShortFileSize(context, list[position].length())
        }

        if (list[position].name.lowercase(Locale.getDefault()).endsWith(".jpeg")) {
            holder.ivFileType.setImageResource(R.drawable.ic_image)
        } else if (list[position].name.lowercase(Locale.getDefault()).endsWith(".jpg")) {
            holder.ivFileType.setImageResource(R.drawable.ic_image)
        } else if (list[position].name.lowercase(Locale.getDefault()).endsWith(".png")) {
            holder.ivFileType.setImageResource(R.drawable.ic_image)
        } else if (list[position].name.lowercase(Locale.getDefault()).endsWith(".pdf")) {
            holder.ivFileType.setImageResource(R.drawable.ic_pdf)
        } else if (list[position].name.lowercase(Locale.getDefault()).endsWith(".doc")) {
            holder.ivFileType.setImageResource(R.drawable.ic_docs)
        } else if (list[position].name.lowercase(Locale.getDefault()).endsWith(".mp3")) {
            holder.ivFileType.setImageResource(R.drawable.ic_music)
        } else if (list[position].name.lowercase(Locale.getDefault()).endsWith(".wav")) {
            holder.ivFileType.setImageResource(R.drawable.ic_music)
        } else if (list[position].name.lowercase(Locale.getDefault()).endsWith(".mp4")) {
            holder.ivFileType.setImageResource(R.drawable.ic_play)
        } else if (list[position].name.lowercase(Locale.getDefault()).endsWith(".apk")) {
            holder.ivFileType.setImageResource(R.drawable.ic_android)
        } else if (list[position].name.lowercase(Locale.getDefault()).endsWith(".txt")) {
            holder.ivFileType.setImageResource(R.drawable.icon_txt)
        } else if (list[position].name.lowercase(Locale.getDefault()).endsWith(".ppt")) {
            holder.ivFileType.setImageResource(R.drawable.ppt)
        } else if (list[position].name.lowercase(Locale.getDefault()).endsWith(".docx")) {
            holder.ivFileType.setImageResource(R.drawable.doc)
        } else if (list[position].name.lowercase(Locale.getDefault()).endsWith(".xls")) {
            holder.ivFileType.setImageResource(R.drawable.excel)
        } else if (list[position].name.lowercase(Locale.getDefault()).endsWith(".xlsx")) {
            holder.ivFileType.setImageResource(R.drawable.excel)
        } else if (list[position].name.lowercase(Locale.getDefault()).endsWith(".exe")) {
            holder.ivFileType.setImageResource(R.drawable.exe)
        } else if (list[position].name.lowercase(Locale.getDefault()).endsWith(".zip")) {
            holder.ivFileType.setImageResource(R.drawable.zip)
        } else {
            holder.ivFileType.setImageResource(R.drawable.ic_folder)
        }

        holder.itemView.setOnClickListener {
            listener.onFileClicked(list[position])
        }

        holder.itemView.setOnLongClickListener {
            listener.onFileLongClicked(list[position], position)
            return@setOnLongClickListener true
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}