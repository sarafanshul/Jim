package com.projectdelta.jim.data.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.projectdelta.jim.util.BaseId
import com.projectdelta.jim.util.Constants.Table.DEFAULT_VALUE_STR
import com.projectdelta.jim.util.Constants.Table.EXERCISE_TABLE
import java.io.Serializable
import kotlin.random.Random

@Keep
@Entity(tableName = EXERCISE_TABLE)
data class Exercise(

    @PrimaryKey(autoGenerate = false)
    override val id : BaseId = Random.nextInt(),

    @[SerializedName("name") ColumnInfo(name = "name")]
    val name: String = DEFAULT_VALUE_STR,

    @[SerializedName("force") ColumnInfo(name = "force")]
    val force: String? = DEFAULT_VALUE_STR,

    @[SerializedName("level") ColumnInfo(name = "level")]
    val level: String? = DEFAULT_VALUE_STR,

    @[SerializedName("mechanic") ColumnInfo(name = "mechanic")]
    val mechanic: String? = DEFAULT_VALUE_STR,

    @[SerializedName("equipment") ColumnInfo(name = "equipment")]
    val equipment: String? = DEFAULT_VALUE_STR,

    @[SerializedName("primaryMuscles") ColumnInfo(name = "primaryMuscles")]
    val primaryMuscles: List<String> = listOf(),

    @[SerializedName("secondaryMuscles") ColumnInfo(name = "secondaryMuscles")]
    val secondaryMuscles: List<String> = listOf(),

    @[SerializedName("instructions") ColumnInfo(name = "instructions")]
    val instructions: List<String> = listOf(),

    @[SerializedName("category") ColumnInfo(name = "category")]
    val category: String? = DEFAULT_VALUE_STR

) : BaseModel(), Serializable
