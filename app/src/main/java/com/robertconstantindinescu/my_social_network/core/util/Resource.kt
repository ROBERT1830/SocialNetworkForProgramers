package com.robertconstantindinescu.my_social_network.core.util

import androidx.annotation.StringRes
import java.security.MessageDigest

//make an alias of that sealed class that is called SimpleResource in those cases
//where the response only will be the BasicApiResponse with succesful or message.
//So for cleaner code we use that alias that indicates a simple version of the Resource we get.
typealias SimpleResource = Resource<Unit>

/**
 * With this Resource class the use case doesn't know from where the Error or Success came from
 */
sealed class Resource<T>(val data:T?= null, val uiText: UiText? = null){

    class Success<T>(data: T?): Resource<T>(data)
    //@StringRes --> indicates that is a string resource id
    class Error<T>(uiText: UiText?, data: T? = null): Resource<T>(data, uiText)

}
