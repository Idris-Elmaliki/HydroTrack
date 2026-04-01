package com.example.water_logging_app.ui.signUpPage.viewModels.derived.signUp.validate

import com.example.water_logging_app.preferenceData.domain.modelData.UserPreferenceData

/*
* This class contains all the logic for validating the user data!
*
* These processes are used when the user is trying to go from screen to screen.
*/
class UserValidator(private val user : UserPreferenceData) {
     fun validateUserData() : List<String> {
        val errorList = mutableListOf<String>()
        
        if(user.age.isEmpty() || user.age.toIntOrNull() == 0) {
            errorList.add("Age is invalid.")
        }
        if (user.gender == null) {
            errorList.add("Gender is Empty.")
        }
        if(user.unitOfMeasurement == null) {
            errorList.add("User hasn't chosen a unit system.")
        }
        if(user.height == 0.0f) {
            errorList.add("A height hasn't been added.")
        }
        if(user.weight == 0.0f) {
            errorList.add("A weight hasn't been added.")
        }
        
        return errorList.toList()
     }

    fun validateActivityData() : String {
        if(user.activityLevel == null) {
            return "Activity Level hasn't been Chosen."
        }

        return ""
    }

    fun validateUserProfile() : List<String> {
        val errorList = mutableListOf<String>()

        if (user.firstName.isBlank()) {
            errorList.add("First Name is Empty.")
        }
        if (user.lastName.isBlank()) {
            errorList.add("Last Name is Empty.")
        }
        if (user.userName.isBlank()) {
            errorList.add("User Name is Empty.")
        }

        return errorList.toList()
    }
}