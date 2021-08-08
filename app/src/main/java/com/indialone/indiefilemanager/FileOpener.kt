package com.indialone.indiefilemanager

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import kotlin.jvm.Throws

object FileOpener {

    @Throws(IOException::class)
    fun openFile(context: Context, file: File) {
        val selectedFile = file
        val uri = FileProvider.getUriForFile(
            context,
            context.applicationContext.packageName + ".provider",
            file
        )

        val intent = Intent(Intent.ACTION_VIEW)

        if (uri.toString().contains(".doc")) {
            intent.setDataAndType(uri, "application/msword")
        } else if (uri.toString().contains(".xls") || uri.toString().contains(".xlsx")) {
            intent.setDataAndType(uri, "application/msexcel")
        } else if (uri.toString().contains(".ppt")) {
            intent.setDataAndType(uri, "application/mspowerpoint")
        } else if (uri.toString().contains(".pdf")) {
            intent.setDataAndType(uri, "application/pdf")
        } else if (uri.toString().contains(".mp3") || uri.toString().contains(".wav")) {
            intent.setDataAndType(uri, "audio/x-wav")
        } else if (uri.toString().contains(".jpeg") || uri.toString().contains(".jpg") || uri.toString().contains(".png")) {
            intent.setDataAndType(uri, "image/jpeg")
        } else if (uri.toString().contains(".mp4") || uri.toString().contains(".mkv")) {
            intent.setDataAndType(uri, "video/*")
        } else {
            intent.setDataAndType(uri, "*/*")
        }

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        context.startActivity(intent)

    }

}