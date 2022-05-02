package com.robertconstantindinescu.my_social_network.core.data.dto.response
//this is for sending validation messages to client
data class BasicApiResponse(

    val successful: Boolean,
    //because if there is no error no message is needed
    val message: String? = null,

)