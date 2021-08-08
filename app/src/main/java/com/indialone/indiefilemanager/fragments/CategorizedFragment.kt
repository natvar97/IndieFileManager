package com.indialone.indiefilemanager.fragments

import android.Manifest
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.format.Formatter
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.marginStart
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.indialone.indiefilemanager.FileOpener
import com.indialone.indiefilemanager.R
import com.indialone.indiefilemanager.adapters.FileAdapter
import com.indialone.indiefilemanager.adapters.OptionsAdapter
import com.indialone.indiefilemanager.databinding.*
import com.indialone.indiefilemanager.listeners.OnFileSelectedListener
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.File
import java.io.IOException
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.absoluteValue

class CategorizedFragment : Fragment(), OnFileSelectedListener {

    private lateinit var mBinding: FragmentCategorizedBinding
    private lateinit var fileList: ArrayList<File>
    private var storage: File? = null
    private lateinit var mFileAdapter: FileAdapter
    private var data = ""
    private val items = arrayOf("Details", "Rename", "Delete", "Share")
    private lateinit var path: File

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentCategorizedBinding.inflate(inflater, container, false)

        val bundle = this.arguments
        if (bundle!!.getString("fileType").equals("downloads")) {
            path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        } else {
            path = Environment.getExternalStorageDirectory()
        }

        runtimePermission()

        return mBinding.root
    }

    private fun runtimePermission() {
        Dexter.withContext(context)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                    displayFiles()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissionRequest: MutableList<PermissionRequest>?,
                    permissionToken: PermissionToken?
                ) {
                    permissionToken!!.continuePermissionRequest()
                }

            }).check()
    }

    private fun findFiles(file: File): ArrayList<File> {
        val arrayList = ArrayList<File>()

        val files = file.listFiles()
        if (files != null) {
            for (singleFile in files) {
                if (singleFile.isDirectory && !singleFile.isHidden) {
                    arrayList.addAll(findFiles(singleFile))
                } else {
                    when (arguments?.getString("fileType")) {
                        "image" -> {
                            if (singleFile.name.lowercase(Locale.getDefault()).endsWith(".jpeg")
                                || singleFile.name.lowercase(Locale.getDefault()).endsWith(".jpg")
                                || singleFile.name.lowercase(Locale.getDefault()).endsWith(".png")
                            ) {
                                arrayList.add(singleFile)
                            }
                        }
                        "video" -> {
                            if (singleFile.name.lowercase(Locale.getDefault())
                                    .endsWith(".mkv") || singleFile.name.lowercase(Locale.getDefault())
                                    .endsWith(".mp4")
                            ) {
                                arrayList.add(singleFile)
                            }
                        }
                        "music" -> {
                            if (singleFile.name.lowercase(Locale.getDefault()).endsWith(".mp3")
                                || singleFile.name.lowercase(Locale.getDefault()).endsWith(".wav")
                            ) {
                                arrayList.add(singleFile)
                            }
                        }
                        "docs" -> {
                            if (singleFile.name.lowercase(Locale.getDefault()).endsWith(".doc")
                                || singleFile.name.lowercase(Locale.getDefault()).endsWith(".txt")
                                || singleFile.name.lowercase(Locale.getDefault()).endsWith(".ppt")
                                || singleFile.name.lowercase(Locale.getDefault()).endsWith(".zip")
                                || singleFile.name.lowercase(Locale.getDefault()).endsWith(".exe")
                                || singleFile.name.lowercase(Locale.getDefault()).endsWith(".xls")
                                || singleFile.name.lowercase(Locale.getDefault()).endsWith(".xlsx")
                                || singleFile.name.lowercase(Locale.getDefault()).endsWith(".docx")
                            ) {
                                arrayList.add(singleFile)
                            }
                        }
                        "apk" -> {
                            if (singleFile.name.lowercase(Locale.getDefault()).endsWith(".apk")) {
                                arrayList.add(singleFile)
                            }
                        }
                        "download" -> {
                            if (singleFile.name.lowercase(Locale.getDefault()).endsWith(".jpeg")
                                || singleFile.name.lowercase(Locale.getDefault()).endsWith(".jpg")
                                || singleFile.name.lowercase(Locale.getDefault()).endsWith(".png")
                                || singleFile.name.lowercase(Locale.getDefault()).endsWith(".mp3")
                                || singleFile.name.lowercase(Locale.getDefault()).endsWith(".wav")
                                || singleFile.name.lowercase(Locale.getDefault()).endsWith(".mp4")
                                || singleFile.name.lowercase(Locale.getDefault()).endsWith(".pdf")
                                || singleFile.name.lowercase(Locale.getDefault()).endsWith(".doc")
                                || singleFile.name.lowercase(Locale.getDefault()).endsWith(".apk")
                                || singleFile.name.lowercase(Locale.getDefault()).endsWith(".mkv")
                                || singleFile.name.lowercase(Locale.getDefault()).endsWith(".txt")
                                || singleFile.name.lowercase(Locale.getDefault()).endsWith(".ppt")
                                || singleFile.name.lowercase(Locale.getDefault()).endsWith(".zip")
                                || singleFile.name.lowercase(Locale.getDefault()).endsWith(".exe")
                                || singleFile.name.lowercase(Locale.getDefault()).endsWith(".xls")
                                || singleFile.name.lowercase(Locale.getDefault()).endsWith(".xlsx")
                                || singleFile.name.lowercase(Locale.getDefault()).endsWith(".docx")
                            ) {
                                arrayList.add(singleFile)
                            }
                        }
                    }
                }
            }

        }
        return arrayList
    }

    private fun displayFiles() {
        fileList = ArrayList()
        fileList.addAll(findFiles(path))
        mFileAdapter = FileAdapter(requireContext(), fileList, this)
        mBinding.rvInternal.apply {
            this.setHasFixedSize(true)
            this.layoutManager = GridLayoutManager(context, 2)
            this.adapter = mFileAdapter
        }

    }

    override fun onFileClicked(file: File) {
        if (file.isDirectory) {
            val bundle = Bundle()
            bundle.putString("path", file.absolutePath)
            val fragment = CategorizedFragment()
            fragment.arguments = bundle
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragment_container,
                    fragment
                )
                .addToBackStack(null)
                .commit()
        } else {
            try {
                FileOpener.openFile(requireContext(), file)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onFileLongClicked(file: File, position: Int) {
        val optionDialog = Dialog(requireContext())
        val binding = OptionDialogBinding.inflate(layoutInflater)
        optionDialog.setContentView(binding.root)
        optionDialog.setTitle("Select Options")
        val optionAdapter = OptionsAdapter(requireActivity(), items)
        binding.list.adapter = optionAdapter
        optionDialog.show()

        binding.list.setOnItemClickListener { parent, _, i, _ ->
            val selectedItem = parent.getItemAtPosition(i).toString()

            when (selectedItem) {
                "Details" -> {
                    val detailsDialog: AlertDialog.Builder = AlertDialog.Builder(requireContext())
                    detailsDialog.setTitle("Details")
                    val details = TextView(requireContext())
                    details.setPadding(50)
                    details.gravity = Gravity.START
                    detailsDialog.setView(details)
                    val lastModified = Date(file.lastModified())
                    val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                    val formattedDate = formatter.format(lastModified)
                    details.setText(
                        "File Name: ${file.name}\nSize: ${
                            Formatter.formatShortFileSize(
                                requireContext(),
                                file.length()
                            )
                        }\nFile path: ${file.absolutePath}\nLast Modified At: $formattedDate"
                    )
                    detailsDialog.setPositiveButton("ok") { dialog, which ->
                        dialog!!.cancel()
                    }
                    val alertDialog_details = detailsDialog.create()
                    alertDialog_details.show()
                }

                "Rename" -> {
                    val renameDialog = AlertDialog.Builder(requireContext())
                    renameDialog.setTitle("Rename File")
                    val etName = EditText(requireContext())
                    etName.setTextColor(resources.getColor(R.color.black))
                    renameDialog.setView(etName)
                    renameDialog.setPositiveButton("Ok") { dialog, which ->
                        val newName = etName.text.toString()
                        val extension =
                            file.absolutePath.substring(file.absolutePath.lastIndexOf("."))
                        val current = File(file.absolutePath)
                        val destinationFile =
                            File(file.absolutePath.replace(file.name, newName) + extension)

                        if (current.renameTo(destinationFile)) {
                            fileList.set(position, destinationFile)
                            mFileAdapter.notifyItemChanged(position)
                            Toast.makeText(requireContext(), "Renamed", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Could not renamed file",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    renameDialog.setNegativeButton(
                        "Cancel"
                    ) { dialog, which ->
                        dialog!!.cancel()
                    }

                    val alertDialogRename = renameDialog.create()
                    alertDialogRename.show()
                }

                "Delete" -> {
                    val deleteDialogBuilder = AlertDialog.Builder(requireContext())
                    deleteDialogBuilder.setTitle("Delete ${file.name}?")
                    deleteDialogBuilder.setPositiveButton("Yes") { dialog, _ ->
                        file.delete()
                        fileList.removeAt(position)
                        mFileAdapter.notifyItemRemoved(position)
                        Toast.makeText(
                            requireContext(),
                            "${file.name} is deleted...",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    deleteDialogBuilder.setNegativeButton("No") { dialog, _ ->
                        dialog.cancel()
                    }
                    val deleteDialog = deleteDialogBuilder.create()
                    deleteDialog.show()
                }

                "Share" -> {
                    val fileName = file.name
                    val intent = Intent()
                    Log.e("file", "$file")
                    intent.action = Intent.ACTION_SEND
                    intent.type = "image/jpeg"
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file))
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    startActivity(Intent.createChooser(intent, "Share $fileName"))
                }

            }

        }

    }


}