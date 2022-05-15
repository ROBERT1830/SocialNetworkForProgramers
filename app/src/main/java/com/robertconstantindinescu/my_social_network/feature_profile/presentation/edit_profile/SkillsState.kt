package com.robertconstantindinescu.my_social_network.feature_profile.presentation.edit_profile

import com.robertconstantindinescu.my_social_network.feature_profile.domain.model.Skill

data class SkillsState(
    val skills: List<Skill> = emptyList(),
    val selectedSkills: List<Skill> = emptyList()

)
