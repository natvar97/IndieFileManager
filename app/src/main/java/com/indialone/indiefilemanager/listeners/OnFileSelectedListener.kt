package com.indialone.indiefilemanager.listeners

import java.io.File
import java.text.FieldPosition

interface OnFileSelectedListener {

    fun onFileClicked(file: File)

    fun onFileLongClicked(file: File, position: Int)

}