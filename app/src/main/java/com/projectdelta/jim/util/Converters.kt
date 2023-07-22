package com.projectdelta.jim.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.projectdelta.jim.data.model.Workout
import com.projectdelta.jim.data.model.WorkoutSet

object Converters {
    private val m_Gson = Gson() // cached gson for type conversion

    @TypeConverter
    fun fromStringToArrayList(value: String): ArrayList<String> {
        val listType = object : TypeToken<ArrayList<String>>() {}.type
        return m_Gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayListToString(list: ArrayList<String>): String {
        return m_Gson.toJson(list)
    }


    @TypeConverter
    fun fromStringToList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return m_Gson.fromJson(value, listType)
    }

    @TypeConverter
    fun fromListToString(list: List<String>): String {
        return m_Gson.toJson(list)
    }


    @TypeConverter
    fun fromWorkoutListToString(list: List<Workout>): String {
        return m_Gson.toJson(list)
    }

    @TypeConverter
    fun fromStringToWorkoutList(value: String): List<Workout> {
        val listType = object : TypeToken<List<Workout>>() {}.type
        return m_Gson.fromJson(value, listType)
    }


    @TypeConverter
    fun fromWorkoutSetListToString(list: List<WorkoutSet>): String {
        return m_Gson.toJson(list)
    }

    @TypeConverter
    fun fromStringToWorkoutSetList(value: String): List<WorkoutSet> {
        val listType = object : TypeToken<List<WorkoutSet>>() {}.type
        return m_Gson.fromJson(value, listType)
    }


}

