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
 *
 * we creatye  an intent and the intent should be fired of onece we pick a picutre. but it also gets
 * fired off if we go back and not select an image. That should not happen.
 *
 *
 */
class CropActivityResultContract(
    private val aspectRatioX: Float,
    private val aspectRatioY: Float
): ActivityResultContract<Uri, Uri?>() { //here we pass a uri when launch the contract and return a uri when the user pick an image.
 //the input comes when we launch the result launcer
    override fun createIntent(context: Context, input: Uri): Intent {
     //This method creates new Intent builder and sets both source and destination image URIs.
        return UCrop.of(
            /**
             * if we didnt choose a uri because we dont pick an image, will be null
             * and that wil lbe send to the Ucrop library. And because id doesnt accept a nullable uri
             * we somehow we have to retunr an other intent. So this will bne the intne
             * tha twe send and crop the UcxRop library is the input is no null and opens the cropp libarry with the image
             * But if we dont havew an uri, we dont want to crop something. so we dont have a uri
             * then we don want to crop something so we instead we have to choose a different intent
             * but if we retiurn an empty intent it doesnt have an ewmpty activiirty that can be launched and it
             * just crashes because it says activity cant be found.
             */
            input,
            Uri.fromFile(
                File(
                    context.cacheDir, //absolute path where the files are stored in the device.
                    context.contentResolver.getFileName(input) //pathname of the file
                )
            )
        )
            .withAspectRatio(aspectRatioX, aspectRatioY)
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