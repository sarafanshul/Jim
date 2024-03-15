package com.projectdelta.jim.data.model.entity

import androidx.compose.runtime.Immutable
import com.projectdelta.jim.util.BaseId

@Immutable
abstract class BaseDBModel {
    abstract var id : BaseId
}
