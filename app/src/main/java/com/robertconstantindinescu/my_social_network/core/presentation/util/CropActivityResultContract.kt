package com.robertconstantindinescu.my_social_network.core.presentation.util

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.RestrictionsManager.RESULT_ERROR
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContract
import com.robertconstantindinescu.my_social_network.core.domain.util.getFileName
import com.yalantis.ucrop.UCrop
import java.io.File


/**
 * The contract get a uri and return an uri
 */
class CropActivityResultContract(
): ActivityResultContract<Uri, Uri?>() { //here we pass a uri when launch the contract and return a uri when the user pick an image.
 //the input comes when we launch the result launcer
    override fun createIntent(context: Context, input: Uri): Intent {
     //This method creates new Intent builder and sets both source and destination image URIs.
        return UCrop.of(
            input,
            Uri.fromFile(
                File(
                    context.cacheDir, //absolute path where the files are stored in the device.
                    context.contentResolver.getFileName(input) //pathname of the file
                )
            )
        )
            .withAspectRatio(16f, 9f)
            .getIntent(context)
    }

    //when press the ok in the cropper.
    override fun parseResult(resultCode: Int, intent: Intent?): Uri? {
        if (intent == null){
            println("INTENT IS NULL")
            return null
        }

        if (resultCode == RESULT_ERROR){
            val error = UCrop.getError(intent)
            error?.printStackTrace()
        }
        return UCrop.getOutput(intent?: return null) //get the cropped image uri from the intent of image picker  and return it

    }
}