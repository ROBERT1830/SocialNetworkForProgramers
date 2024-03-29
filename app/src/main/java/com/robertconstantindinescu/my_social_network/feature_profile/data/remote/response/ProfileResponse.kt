package com.robertconstantindinescu.my_social_network.feature_profile.data.remote.response

import com.robertconstantindinescu.my_social_network.feature_profile.domain.model.Profile

/**
 * This reposnse could be used to display the initial data for current user profile.
 */
data class ProfileResponse(
    val userId: String,
    val username: String,
    val bio: String,
    /**Maybe this is not a good way to do represent the number of those because
     * to get that we will have to perform 3 queries. Because profile is something we visit frequently
     * iS BETTER TO HAVE FIXED VALUES IN OUR USER DOCUMENT. And whenever we follow
     * someone or post something new. Modify the count for the corresponding user**/
    val followerCount: Int,
    val followingCount: Int,
    val postCount: Int,
    /****/
    val profilePictureUrl: String,
    val bannerUrl: String?,
    val topSkills: List<SkillDto>,
    val gitHubUrl: String?,
    val instagramUrl: String?,
    val linkedInUrl: String?,
    //if we are following the user or not. When you se a profile you have the button there.
    val isOwnProfile: Boolean, //if the profile is from the current user that makes the request. We have to distinguish between current user profile or an other profile the current user clicked in.
    val isFollowing: Boolean

    /*We want to be able to paginate the response for getting the post. but we dont want to paginate
    * the other information we have above. So we will create a separate response.
    * So to take th post and put htem below user info we just return a list of Post. Because this datacass
    * hass all the info needed.
    *
    *  */
) {
    fun toProfile(): Profile {
        return Profile(
            userId = userId,
            username = username,
            bio = bio,
            followingCount = followingCount,
            followerCount = followerCount,
            postCount = postCount,
            profilePictureUrl = profilePictureUrl,
            bannerUrl = bannerUrl,
            topSkills = topSkills.map {
                it.toSkill()
            },
            gitHubUrl = gitHubUrl,
            instagramUrl = instagramUrl,
            linkedInUrl = linkedInUrl,
            isOwnProfile = isOwnProfile,
            isFollowing = isFollowing
        )
    }
}
