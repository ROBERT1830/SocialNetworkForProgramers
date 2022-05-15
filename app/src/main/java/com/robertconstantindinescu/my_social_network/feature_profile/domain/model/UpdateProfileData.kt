package com.robertconstantindinescu.my_social_network.feature_profile.domain.model

import com.robertconstantindinescu.my_social_network.feature_profile.domain.model.Skill


/**
 * This will be used as a request object to make a requeast to the server.
 */
data class UpdateProfileData(
    //we allways update the whole profile. The data will be filled by default but if we update a name
    //we wil save that instead. if not save again the default value
    val userName: String,
    val bio: String,
    val githubUrl: String,
    val instagramUrl: String,
    val linkedInUrl: String,
    val skills: List<Skill>,
   // val profileImageChanged: Boolean = false

)