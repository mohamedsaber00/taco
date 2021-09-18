package com.candybytes.taco.ui.util

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.candybytes.taco.BuildConfig
import java.io.File

/*
* A helper class to create a file and return it's uri
* */

class ImageUtils {

    companion object{
         fun getTmpFileUri(context: Context): Uri {
            val tmpFile = File.createTempFile("tmp_image_file", ".png").apply {
                createNewFile()
                deleteOnExit()
            }

            return FileProvider.getUriForFile(
                context,
                "${BuildConfig.APPLICATION_ID}.provider",
                tmpFile
            )
        }
    }
}