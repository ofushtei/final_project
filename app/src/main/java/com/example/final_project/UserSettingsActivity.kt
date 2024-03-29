package com.example.final_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.final_project.api.helpers.ControllerAccessHelper
import com.example.final_project.api.interfaces.ControllerAccessInterface
import com.example.final_project.core.MainApplication
import com.example.final_project.models.ErrorModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_user_settings.*

class UserSettingsActivity :
    AppCompatActivity(),
    ControllerAccessInterface.DeleteUserFromListListener {

    override fun onDeleteUserFromListResponseSuccess() {
        finish()
    }

    override fun onDeleteUserFromListResponseFailure(
        errorModel: ErrorModel?
    ) {
        progressBar.visibility =
            View.GONE
        val errorMessage =
            when (errorModel) {
                null -> "Server error"
                else -> errorModel.message
            }
        Snackbar.make(
            root_layout,
            errorMessage,
            Snackbar.LENGTH_SHORT
        )
            .show()
    }

    override fun onDeleteUserFromListCancelled() {
        progressBar.visibility =
            View.GONE
        Snackbar.make(
            root_layout,
            "Something went wrong. Try again",
            Snackbar.LENGTH_SHORT
        )
            .show()
    }

    override fun onDeleteUserFromListFailure() {
        progressBar.visibility =
            View.GONE
        Snackbar.make(
            root_layout,
            "Check your connection to the internet",
            Snackbar.LENGTH_SHORT
        )
            .show()
    }

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(
            savedInstanceState
        )
        setContentView(
            R.layout.activity_user_settings
        )

        progressBar.visibility =
            View.GONE
        val id =
            intent.getIntExtra(
                "id",
                0
            )
        val username =
            intent.getStringExtra(
                "userUserName"
            )
        val name =
            intent.getStringExtra(
                "userName"
            )
        val surname =
            intent.getStringExtra(
                "userSurname"
            )
        val email =
            intent.getStringExtra(
                "userEmail"
            )
        val isAdmin =
            intent.getBooleanExtra(
                "isAdmin",
                false
            )
        userUserNameTextView.text =
            username
        nameEditText.setText(
            name
        )
        nameEditText.keyListener =
            null
        surnameEditText.setText(
            surname
        )
        surnameEditText.keyListener =
            null
        emailEditText.setText(
            email
        )
        emailEditText.keyListener =
            null
        if (!isAdmin) {
            buttonDelete.visibility =
                View.GONE
        }
        val app =
            application as MainApplication
        val controllerAccessHelper =
            ControllerAccessHelper(
                app.getApi()
            )
        buttonDelete.setOnClickListener {
            progressBar.visibility =
                View.VISIBLE
            controllerAccessHelper.deleteUser(
                id,
                this
            )
        }
        buttonBack.setOnClickListener {
            finish()
        }

    }
}
