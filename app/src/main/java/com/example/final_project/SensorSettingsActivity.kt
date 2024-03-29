package com.example.final_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.final_project.api.helpers.SensorHelper
import com.example.final_project.api.interfaces.SensorInterface
import com.example.final_project.core.MainApplication
import com.example.final_project.models.ErrorModel
import com.example.final_project.models.SensorModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_sensor_settings.*


class SensorSettingsActivity :
    AppCompatActivity(),
    SensorInterface.SensorGetListener,
    SensorInterface.SensorSaveListener,
    SensorInterface.SensorDeleteListener {

    override fun onSensorSaveResponseSuccess() {
        finish()
    }

    override fun onSensorSaveResponseFailure(
        errorModel: ErrorModel?
    ) {
        val errorMessage =
            when (errorModel) {
                null -> "Server error"
                else -> errorModel.message
            }
        progressBar.visibility =
            View.GONE
        Snackbar.make(
            root_layout,
            errorMessage,
            Snackbar.LENGTH_SHORT
        )
            .show()
    }

    override fun onSensorSaveCancelled() {
        progressBar.visibility =
            View.GONE
        Snackbar.make(
            root_layout,
            "Saving cancelled. Try again",
            Snackbar.LENGTH_SHORT
        )
            .show()
    }

    override fun onSensorSaveFailure() {
        progressBar.visibility =
            View.GONE
        Snackbar.make(
            root_layout,
            "Check your connection to the Internet",
            Snackbar.LENGTH_SHORT
        )
            .show()
    }

    override fun onSensorDeleteResponseSuccess() {
        finish()
    }

    override fun onSensorDeleteResponseFailure(
        errorModel: ErrorModel?
    ) {
        val errorMessage =
            when (errorModel) {
                null -> "Server error"
                else -> errorModel.message
            }
        progressBar.visibility =
            View.GONE
        Snackbar.make(
            root_layout,
            errorMessage,
            Snackbar.LENGTH_SHORT
        )
            .show()
    }

    override fun onSensorDeleteCancelled() {
        progressBar.visibility =
            View.GONE
        Snackbar.make(
            root_layout,
            "Deleting cancelled. Try again",
            Snackbar.LENGTH_SHORT
        )
            .show()
    }

    override fun onSensorDeleteFailure() {
        progressBar.visibility =
            View.GONE
        Snackbar.make(
            root_layout,
            "Check your connection to the Internet",
            Snackbar.LENGTH_SHORT
        )
            .show()
    }

    override fun onSensorGetResponseSuccess(
        sensor: SensorModel
    ) {
        progressBar.visibility =
            View.GONE
        sensorNameEditText.setText(
            sensor.name
        )
        sensorPinEditText.setText(
            sensor.pin.toString()
        )
        sensorTypeId =
            sensor.sensorTypeId
        when (sensorTypeId) {
            1 -> rgTypes.check(
                R.id.rbLight
            )
            2 -> rgTypes.check(
                R.id.rbTemperature
            )
            else -> rgTypes.check(
                R.id.rbSubmersion
            )
        }
        sensorSwitch.isChecked =
            sensor.status
    }

    override fun onSensorGetResponseFailure(
        errorModel: ErrorModel?
    ) {
        val errorMessage =
            when (errorModel) {
                null -> "Server error"
                else -> errorModel.message
            }
        progressBar.visibility =
            View.GONE
        Snackbar.make(
            root_layout,
            errorMessage,
            Snackbar.LENGTH_SHORT
        )
            .show()
    }

    override fun onSensorGetCancelled() {
        progressBar.visibility =
            View.GONE
        Snackbar.make(
            root_layout,
            "Something went wrong. Try again",
            Snackbar.LENGTH_SHORT
        )
            .show()
    }

    override fun onSensorGetFailure() {
        progressBar.visibility =
            View.GONE
        Snackbar.make(
            root_layout,
            "Check your connection to the Internet",
            Snackbar.LENGTH_SHORT
        )
            .show()
    }

    private var sensorTypeId: Int =
        1

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(
            savedInstanceState
        )
        setContentView(
            R.layout.activity_sensor_settings
        )
        val app: MainApplication =
            application as MainApplication
        val sensorHelper =
            SensorHelper(
                app.getApi()
            )
        val controllerId =
            intent.getIntExtra(
                "controllerId",
                0
            )
        val sensorId =
            intent.getIntExtra(
                "sensorId",
                0
            )
        val isAdmin =
            intent.getBooleanExtra(
                "isAdmin",
                false
            )
        if (sensorId == 0) {
            buttonDelete.visibility =
                View.INVISIBLE
            progressBar.visibility =
                View.GONE
        } else {
            sensorHelper.getSensor(
                sensorId,
                this
            )
        }
        rgTypes.setOnCheckedChangeListener { _, checkedId ->
            val rButton: RadioButton =
                findViewById(
                    checkedId
                )
            sensorTypeId =
                when (rButton) {
                    rbLight -> 1
                    rbTemperature -> 2
                    else -> 3
                }

        }
        buttonSave.setOnClickListener {
            if (validateAll()) {
                progressBar.visibility =
                    View.VISIBLE
                val sensorData =
                    SensorModel(
                        sensorId,
                        sensorNameEditText.text.toString(),
                        sensorPinEditText.text.toString().toInt(),
                        sensorSwitch.isChecked,
                        0.0,
                        sensorTypeId,
                        controllerId
                    )
                if (sensorId == 0) {
                    sensorHelper.addSensor(
                        sensorData,
                        this
                    )
                } else {
                    sensorHelper.editSensor(
                        sensorData,
                        this
                    )
                }
            }
        }
        buttonBack.setOnClickListener {
            finish()
        }
        buttonDelete.setOnClickListener {
            sensorHelper.deleteSensor(
                sensorId,
                this
            )
        }
        if (!isAdmin && sensorId != 0) {
            val changesSnackbar =
                Snackbar.make(
                    root_layout,
                    "Any changes won't be saved",
                    Snackbar.LENGTH_INDEFINITE
                )
            changesSnackbar.setAction(
                "OK, got it!"
            ) { changesSnackbar.dismiss() }
            changesSnackbar.show()
            buttonDelete.visibility =
                View.GONE
            buttonSave.visibility =
                View.GONE
        }
    }

    private fun validateAll(): Boolean {
        sensorNameTextInput.error =
            null
        sensorPinTextInput.error =
            null
        return validateName() && validatePin()
    }

    private fun validateName(): Boolean =
        when {
            sensorNameEditText.text.toString().isEmpty() -> {
                sensorNameTextInput.error =
                    "Name field shouldn't be empty"
                false
            }
            else -> {
                sensorNameTextInput.error =
                    null
                true
            }
        }

    private fun validatePin(): Boolean =
        when {
            sensorPinEditText.text.toString().isEmpty() -> {
                sensorPinTextInput.error =
                    "Pin field shouldn't be empty"
                false
            }
            else -> {
                sensorPinTextInput.error =
                    null
                true
            }
        }

}
