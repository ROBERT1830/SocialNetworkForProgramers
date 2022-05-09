package com.robertconstantindinescu.my_social_network.core.domain.util

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns

//to get the filename of an uri
fun ContentResolver.getFileName(uri: Uri): String{
    val returnCursor = query(uri, null, null, null, null) ?: return ""
    val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    returnCursor.moveToFirst()
    val fileName = returnCursor.getString(nameIndex)
    returnCursor.close()
    return fileName
}