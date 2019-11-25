package com.example.final_project.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.example.final_project.R
import com.example.final_project.api.helpers.DeviceHelper
import com.example.final_project.api.interfaces.DeviceInterface
import com.example.final_project.core.MainApplication
import com.example.final_project.models.DeviceModel
import com.example.final_project.models.DeviceTypeModel
import com.example.final_project.models.ErrorModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_device_settings.*
import kotlinx.android.synthetic.main.fragment_device_settings.progressBar
import kotlinx.android.synthetic.main.fragment_device_settings.root_layout

class DeviceSettingsFragment :
    Fragment(),
    DeviceInterface.DeviceGetListener,
    DeviceInterface.DeviceSaveListener,
    DeviceInterface.DeviceDeleteListener {

    companion object {
        private const val CONTROLLERID =
            "controllerId"
        private const val DEVICEID =
            "deviceId"

        fun newInstance(
            caughtControllerId: Int,
            caughtDeviceId: Int
        ): DeviceSettingsFragment {
            val args =
                Bundle()
            args.putSerializable(
                CONTROLLERID,
                caughtControllerId
            )
            args.putSerializable(
                DEVICEID,
                caughtDeviceId
            )
            val fragment =
                DeviceSettingsFragment()
            fragment.arguments =
                args
            return fragment
        }
    }

    override fun onDeviceSaveResponseSuccess() {
        activity?.finish()
    }

    override fun onDeviceSaveResponseFailure(
        errorModel: ErrorModel
    ) {
        val message: String =
            when {
                errorModel.message.isEmpty() -> "An error occupied. Try again"
                else -> errorModel.message
            }
        Snackbar.make(
            root_layout,
            message,
            Snackbar.LENGTH_SHORT
        )
            .show()
    }

    override fun onDeviceSaveCancelled() {
        Snackbar.make(
            root_layout,
            "Error while saving device. Try again",
            Snackbar.LENGTH_SHORT
        )
            .show()
    }

    override fun onDeviceSaveFailure() {
        Snackbar.make(
            root_layout,
            "Check your connection to the Internet",
            Snackbar.LENGTH_SHORT
        )
            .show()
    }

    override fun onDeviceGetResponseSuccess(
        device: DeviceModel
    ) {
        progressBar.visibility =
            View.GONE
        deviceNameEditText.setText(
            device.name
        )
        when {
            device.pin != 0 -> {
                devicePinEditText.isEnabled =
                    true
                devicePinTextInput.isEnabled =
                    true
                devicePinEditText.setText(
                    device.pin.toString()
                )
                rgConnection.check(
                    R.id.rbPin
                )
                deviceMACEditText.isEnabled =
                    false
                deviceMACTextInput.isEnabled =
                    false
            }
            else -> {
                deviceMACEditText.isEnabled =
                    true
                deviceMACTextInput.isEnabled =
                    true
                deviceMACEditText.setText(
                    device.mac
                )
                rgConnection.check(
                    R.id.rbMAC
                )
                devicePinEditText.isEnabled =
                    false
                devicePinTextInput.isEnabled =
                    false
            }
        }
        deviceSwitch.isChecked =
            device.status
    }

    override fun onDeviceGetResponseFailure(
        errorModel: ErrorModel
    ) {
        val message: String =
            when {
                errorModel.message.isEmpty() -> "An error occupied. Try again"
                else -> errorModel.message
            }
        Snackbar.make(
            root_layout,
            message,
            Snackbar.LENGTH_SHORT
        )
            .show()
    }

    override fun onDeviceGetCancelled() {
        Snackbar.make(
            root_layout,
            "Error while getting device. Try again",
            Snackbar.LENGTH_SHORT
        )
            .show()
    }

    override fun onDeviceGetFailure() {
        Snackbar.make(
            root_layout,
            "Check your connection to the Internet",
            Snackbar.LENGTH_SHORT
        )
            .show()
    }

    override fun onDeviceDeleteResponseSuccess() {
        //go to the list
        activity?.finish()
    }

    override fun onDeviceDeleteResponseFailure(
        errorModel: ErrorModel
    ) {
        val message: String =
            when {
                errorModel.message.isEmpty() -> "An error occupied. Try again"
                else -> errorModel.message
            }
        Snackbar.make(
            root_layout,
            message,
            Snackbar.LENGTH_SHORT
        )
            .show()
    }

    override fun onDeviceDeleteCancelled() {
        Snackbar.make(
            root_layout,
            "Error while deleting device. Try again",
            Snackbar.LENGTH_SHORT
        )
            .show()
    }

    override fun onDeviceDeleteFailure() {
        Snackbar.make(
            root_layout,
            "Check your connection to the Internet",
            Snackbar.LENGTH_SHORT
        )
            .show()
    }

    private val deviceTypes: ArrayList<DeviceTypeModel> =
        ArrayList()
    private val deviceTypesList: ArrayList<String> =
        ArrayList()
    private var deviceTypeId: Int =
        0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View =
            inflater.inflate(
                R.layout.fragment_device_settings,
                container,
                false
            )
        val bundle =
            this.arguments
        val controllerId =
            bundle?.getInt(
                "controllerId",
                0
            )
        val deviceId =
            bundle?.getInt(
                "deviceId",
                0
            )
        val app =
            context?.applicationContext as MainApplication
        val progressBar =
            view.findViewById<ProgressBar>(
                R.id.progressBar
            )
        val deviceHelper =
            DeviceHelper(
                app.getApi()
            )
        val PinTIL =
            view.findViewById<TextInputLayout>(
                R.id.devicePinTextInput
            )
        val PinET =
            view.findViewById<TextInputEditText>(
                R.id.devicePinEditText
            )
        val MACTIL =
            view.findViewById<TextInputLayout>(
                R.id.deviceMACTextInput
            )
        val MACET =
            view.findViewById<TextInputEditText>(
                R.id.deviceMACEditText
            )
        val saveButton =
            view.findViewById<MaterialButton>(
                R.id.buttonSave
            )
        val deleteButton =
            view.findViewById<MaterialButton>(
                R.id.buttonDelete
            )
        val backButton =
            view.findViewById<MaterialButton>(
                R.id.buttonBack
            )
        val rgConnection =
            view.findViewById<RadioGroup>(
                R.id.rgConnection
            )
        val rbPin: RadioButton =
            view.findViewById(
                R.id.rbPin
            )
        val rbMAC: RadioButton =
            view.findViewById(
                R.id.rbMAC
            )
        val rgTypes =
            view.findViewById<RadioGroup>(
                R.id.rgTypes
            )
        val rbSwitch =
            view.findViewById<RadioButton>(
                R.id.rbSwitch
            )
        val rbLamp =
            view.findViewById<RadioButton>(
                R.id.rbLamp
            )
        val rbLED =
            view.findViewById<RadioButton>(
                R.id.rbLED
            )

        if (deviceId == 0) {
            deleteButton.visibility =
                View.INVISIBLE
            progressBar.visibility =
                View.GONE
            MACTIL.isEnabled =
                false
            MACET.isEnabled =
                false
        } else {
            deviceHelper.getDevice(
                deviceId!!,
                this
            )
        }

        rgConnection.setOnCheckedChangeListener { _, checkedId ->
            when (view.findViewById<RadioButton>(
                checkedId
            )) {
                rbPin -> {
                    PinTIL.isEnabled =
                        true
                    PinET.isEnabled =
                        true
                    rgConnection.check(
                        R.id.rbPin
                    )
                    MACTIL.isEnabled =
                        false
                    MACET.isEnabled =
                        false
                }
                else -> {
                    PinTIL.isEnabled =
                        false
                    PinET.isEnabled =
                        false
                    rgConnection.check(
                        R.id.rbMAC
                    )
                    MACTIL.isEnabled =
                        true
                    MACET.isEnabled =
                        true
                }
            }
        }

        rgTypes.setOnCheckedChangeListener { _, checkedId ->
            val rButton: RadioButton =
                view.findViewById(
                    checkedId
                )
            deviceTypeId =
                when (rButton) {
                    rbSwitch -> 1
                    rbLamp -> 2
                    rbLED -> 3
                    else -> 1
                }
        }

//        val array: Array = deviceTypesList.toTypedArray()
//        val adapter = ArrayAdapter<String>(view.context, R.layout.device_type_row, array)
//        val dropdown: MaterialAutoCompleteTextView = view.findViewById(R.id.exposedDropdown)
//        val dropdown: AutoCompleteTextView = view.findViewById(R.id.exposedDropdown)
//        dropdown.setAdapter(adapter)
//        dropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//
//            override fun onNothingSelected(
//                parent: AdapterView<*>?
//            ) {
//                Snackbar.make(root_layout, "NothingSelected", Snackbar.LENGTH_SHORT).show()
//            }
//
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                deviceTypeId = deviceTypes[position].id
//                Snackbar.make(root_layout, deviceTypeId, Snackbar.LENGTH_SHORT).show()
//            }
//
//        }

        val typesList: ArrayList<String> =
            ArrayList()
        for (item in deviceTypes) {
            typesList.add(
                item.typeName
            )
        }
//        val adapter =
//            ArrayAdapter<String>(
//                context!!,
//                R.layout.device_type_row,
//                typesList
//            )
//        val ddlist =
//            view.findViewById<AutoCompleteTextView>(
//                R.id.exposedDropdown
//            )
//        ddlist.setAdapter(
//            adapter
//        )


        saveButton.setOnClickListener {
            if (validateAll()) {
                val pin: Int
                val mac: String?
                if (rgConnection.checkedRadioButtonId == R.id.rbPin) {
                    pin =
                        devicePinEditText.text.toString()
                            .toInt()
                    mac =
                        null
                } else {
                    pin =
                        0
                    mac =
                        deviceMACEditText.text.toString()
                }
                progressBar.visibility =
                    View.VISIBLE
                val deviceData =
                    DeviceModel(
                        deviceId,
                        deviceNameEditText.text.toString(),
                        pin,
                        mac,
                        deviceSwitch.isChecked,
                        controllerId!!,
                        deviceTypeId,
                        null
                    )
                if (deviceId == 0) {
                    deviceHelper.addDevice(
                        deviceData,
                        this
                    )
                } else {
                    deviceHelper.editDevice(
                        deviceData,
                        this
                    )
                }
            }
        }

        backButton.setOnClickListener {
            activity?.finish()
        }

        deleteButton.setOnClickListener {
            deviceHelper.deleteDevice(
                deviceId,
                this
            )
        }

        return view
    }

    private fun validateAll(): Boolean =
        validateName() && validateConnection()

    private fun validateName(): Boolean =
        when {
            deviceNameEditText.text.toString().isEmpty() -> {
                deviceNameTextInput.error =
                    "Name field shouldn't be empty"
                false
            }
            else -> true
        }

    private fun validateConnection(): Boolean =
        when {
            rbPin.isChecked -> {
                when {
                    devicePinEditText.text.toString().isEmpty() -> {
                        devicePinTextInput.error =
                            "Pin field shouldn't be empty"
                        false
                    }
                    !devicePinEditText.text.toString().trim().matches(
                        Regex(
                            "^[1-9]\\d*\$"
                        )
                    ) -> {
                        devicePinTextInput.error =
                            "Pin should greater than 0"
                        true
                    }
                    else -> true
                }
            }
            rbMAC.isChecked -> {
                when {
                    deviceMACEditText.text.toString().isEmpty() -> {
                        deviceMACTextInput.error =
                            "MAC Address field shouldn't be empty"
                        false
                    }
                    deviceMACEditText.text.toString().trim().length != 12 -> {
                        deviceMACTextInput.error =
                            "MAC Address should be 12 character long"
                        false
                    }
                    else -> true
                }
            }
            else -> false
        }

}