package com.robertconstantindinescu.my_social_network.feature_post.data.repository

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.core.net.toFile
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.gson.Gson
import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.core.domain.models.Post
import com.robertconstantindinescu.my_social_network.core.domain.util.getFileName
import com.robertconstantindinescu.my_social_network.core.util.Constants
import com.robertconstantindinescu.my_social_network.core.util.Resource
import com.robertconstantindinescu.my_social_network.core.util.SimpleResource
import com.robertconstantindinescu.my_social_network.core.util.UiText
import com.robertconstantindinescu.my_social_network.feature_post.data.data_source.remote.PostApi
import com.robertconstantindinescu.my_social_network.feature_post.data.paging.PostSource
import com.robertconstantindinescu.my_social_network.feature_post.domain.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import robertconstantin.example.data.requests.CreatePostRequest
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException

class PostRepositoryImpl(
    private val api: PostApi,
    private val gson: Gson,
    private val appContext: Context
): PostRepository {

//    override suspend fun getPostForFollows(page: Int, pageSize: Int): Resource<List<Post>> {
//
//        return try {
//            val posts = api.getPostForFollows(page, pageSize)
//            Resource.Success(posts)
//            //couldnt send the data or coudl not receive. Some kind of time out
//        } catch (e: IOException) {
//            Resource.Error(
//                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
//            )
//
//
//            //exception for verything that doesnt start with the 2 xx (200 ok)
//        } catch (e: HttpException) {
//            Resource.Error(
//                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
//            )
//        }
//    }

    //when call this variable then we will get a flow of an object called PaginData of type Post.
    override val posts: Flow<PagingData<Post>>
        get() = Pager(PagingConfig(pageSize = Constants.PAGE_SIZE_POSTS)) {
            PostSource(api)
        }.flow //important to return a flow by .flow This makes the data to be a flow.


    override suspend fun createPost(
        description: String,
        imageUri: Uri
    ): SimpleResource {

        val request = CreatePostRequest(description)


        //read the file from that uri. We need to create a file from the content of the uri whoch is the image.
        //val file = imageUri.toFile() //creates a file objects taht contains the info of tahth uri
        //user --> wil close the bufferedReader automatically
        val file = withContext(Dispatchers.IO){
            //
            appContext.contentResolver.openFileDescriptor(imageUri, "r")?.let { fd ->
                val inputStream = FileInputStream(fd.fileDescriptor)
                //that one will contain the image
                val file = File(
                    appContext.cacheDir,
                    appContext.contentResolver.getFileName(imageUri)
                )
                val outputStream = FileOutputStream(file)
                inputStream.copyTo(outputStream)
                file
            }
        } ?: //if there are no bytes means that the file that contains the image cant be ofund
            return Resource.Error(
                uiText = UiText.StringResource(R.string.error_file_not_found),
                data = null
            )


        return try {
            val response = api.createPost(
                /*Here in the formDataPart we need json data as value. I mean
                * the description in form of  JSON because we are in multipart
                * request. So we need acces to GSON to convert it
                *
                * For the image, we have */
                postData = MultipartBody.Part
                    .createFormData(
                        name = "post_data",
                        value = gson.toJson(request)
                    ),
                postImage = MultipartBody.Part
                    .createFormData(
                        name =  "post_image",
                        filename = file.name,
                        body = file.asRequestBody()
                    )
            )
            if (response.successful) {
                Resource.Success(Unit) //no data
            } else {

                response.message?.let { msg ->
                    Resource.Error(UiText.DynamicString(msg))
                } ?: Resource.Error(UiText.StringResource(R.string.error_unknown))

            }
        } catch (e: IOException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.error_couldnt_reach_server)
            )


            //exception for verything that doesnt start with the 2 xx (200 ok)
        } catch (e: HttpException) {
            Resource.Error(
                uiText = UiText.StringResource(R.string.oops_something_went_wrong)
            )
        }
    }
}

























