package com.robertconstantindinescu.my_social_network.feature_profile.domain.use_case

import com.robertconstantindinescu.my_social_network.R
import com.robertconstantindinescu.my_social_network.core.util.Resource
import com.robertconstantindinescu.my_social_network.core.util.UiText
import com.robertconstantindinescu.my_social_network.feature_profile.domain.model.Skill
import com.robertconstantindinescu.my_social_network.feature_profile.domain.util.ProfileConstants.MAX_SELECTED_SKILL_COUNT

class SetSkillSelectedUseCase {

    /**
     * We can return an error in case we selected more than 3
     */
    operator fun invoke(
        selectedSkills: List<Skill>,
        skillToToggle: Skill

    ): Resource<List<Skill>>{
        val skillInList = selectedSkills.find { it.name == skillToToggle.name }
        //check if the skill is already in the list
        if (selectedSkills.any { it.name == skillToToggle.name }){

            if (skillInList != null){
                //remove from the selectedSkills list
                return Resource.Success(selectedSkills -  skillToToggle) //cant do it only using this line. Because they are different instances the one tht we pass as selected and the one from the list.
            }


        }
        return if (selectedSkills.size >= MAX_SELECTED_SKILL_COUNT){
            Resource.Error(uiText = UiText.StringResource(R.string.error_max_skills_selected))
        } else {
            Resource.Success(selectedSkills + skillToToggle)
        }
    }
}