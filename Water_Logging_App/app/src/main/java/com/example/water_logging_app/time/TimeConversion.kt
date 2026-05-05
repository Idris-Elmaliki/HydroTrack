package com.example.water_logging_app.time

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


/*
* This object uses the java.time library from quick time conversions for all aspects of the app
*   (Ui, ViewModel, Repository)
*
* Note: Each function is used in a specific part of the app.
*   If it's for the UI, @Composable is included.
*   For ViewModels and Repositories, check the last letter in the func name!
*
*   (V = ViewModel, R = Repository, D = DataStore)
*/
object TimeConversion {
    fun getLocalDateFromLocalDateTimeV(
        date : LocalDateTime
    ) : LocalDate {
        val localDate = date.toLocalDate()

        return localDate
    }

    fun getStringFromLocalDateTimeR(
        date : LocalDateTime
    ) : String {
        val string = date.toString()

        return string
    }

    fun getLocalDateTimeFromStringR(
        date : String
    ) : LocalDateTime {
        val localDateTime = LocalDateTime.parse(date)

        return localDateTime
    }

    fun getStringFromLocalTimeD(
        date : LocalTime
    ) : String {
        val string = date.toString()

        return string
    }

    fun getLocalTimeFromStringD(
        date : String
    ) : LocalTime {
        val localTime = LocalTime.parse(date)

        return localTime
    }

    fun getStringFromLocalDateV(
        date: LocalDate
    ): String {
        val string = date.toString()

        return string
    }
}