package co.edu.udea.udeacov.utils

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore


class FileUtils {
    companion object{
        fun getFileName(uri: Uri, context: Context): String? { // Obtain a cursor with information regarding this uri
            var cursor: Cursor? = null
            return try {
                val proj =
                    arrayOf(MediaStore.Images.Media.DATA)
                cursor = context.contentResolver.query(uri, proj, null, null, null)
                val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                cursor.moveToFirst()
                cursor.getString(column_index)
            } finally {
                cursor?.close()
            }
        }
    }
}