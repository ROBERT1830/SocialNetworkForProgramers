package com.robertconstantindinescu.my_social_network.feature_profile.domain.model

data class Profile(
    val userId: String,
    val username: String,
    val bio: String,
    val followerCount: Int,
    val followingCount: Int,
    val postCount: Int,
    val profilePictureUrl: String,
    val bannerUrl: String,
    val topSkillUrls: List<String>,
    val gitHubUrl:String?,
    val instagramUrl: String?,
    val linkedInUrl: String?,
    val isOwnProfile: Boolean, //if the profile is from the current user that makes the request. We have to distinguish between current user profile or an other profile the current user clicked in.
    val isFollowing: Boolean

)
