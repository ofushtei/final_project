package com.example.final_project.models

import com.example.final_project.models.interfaces.ISensorModel
import com.example.final_project.models.interfaces.ISensorTypeModel
import com.google.gson.annotations.SerializedName

class SensorModel(
    @SerializedName(
        "id"
    )
    override val id: Int,
    @SerializedName(
        "name"
    )
    override val name: String,
    @SerializedName(
        "pin"
    )
    override val pin: Int,
    @SerializedName(
        "status"
    )
    override val status: Boolean,
    @SerializedName(
        "value"
    )
    override val value: Int,
    @SerializedName(
        "sensorTypeId"
    )
    override val sensorTypeId: Int,
    @SerializedName(
        "controllerId"
    )
    override val controllerId: Int,
    @SerializedName(
        "sensorType"
    )
    override val sensorType: SensorTypeModel
) : ISensorModel