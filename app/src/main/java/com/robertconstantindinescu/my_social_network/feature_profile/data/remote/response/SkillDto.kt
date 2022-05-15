package com.robertconstantindinescu.my_social_network.feature_profile.data.remote.response

import com.google.gson.annotations.SerializedName
import com.robertconstantindinescu.my_social_network.feature_profile.domain.model.Skill

data class SkillDto(
//    @SerializedName("_id") //in server side all id is called like that. so use the serielized name to map it
//    val id: String,
    val name: String,
    val imageUrl: String
){
    fun toSkill(): Skill{
        return Skill(
            name = name,
            imageUrl = imageUrl
        )
    }
}