package com.example.final_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.view.Gravity
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import com.example.final_project.models.SignUpDataModel
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        if (supportActionBar != null) supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        buttonSU.isEnabled = false
        buttonSU.isClickable = false

        usernameEditText.setOnFocusChangeListener { _, b -> validateUsername(b) }
        emailEditText.setOnFocusChangeListener { _, b -> validateEmail(b) }
        passwordEditText.setOnFocusChangeListener { _, b -> validatePassword(b) }
        confPasswordEditText.setOnFocusChangeListener { _, b -> validateConfPassword(b) }
        nameEditText.setOnFocusChangeListener { _, b -> validateName(b) }
        surnameEditText.setOnFocusChangeListener { _, b -> validateSurname(b) }

        buttonSU.setOnClickListener {
            //do final check
            //
            val checkData = SignUpDataModel(usernameEditText.text.toString(), emailEditText.text.toString(), passwordEditText.text.toString(), nameEditText.text.toString(), surnameEditText.text.toString())
            //send to server
            Toast.makeText(this@SignUpActivity, checkData.username + " " + checkData.email + " " + checkData.password + " " + checkData.name + " " + checkData.surname, Toast.LENGTH_LONG).show()
        }

        buttonBack.setOnClickListener {
            val signInIntent = Intent(this@SignUpActivity, SignInActivity::class.java)
            startActivity(signInIntent)
        }
    }

    //Enable "Sign Up" button
    private fun enableButton() {
        buttonSU.isEnabled = true
        buttonSU.isClickable = true
    }

    //Disable "Sign Up" button
    private fun disableButton() {
        buttonSU.isEnabled = false
        buttonSU.isClickable = false
    }

    //Validations of EditText fields
    //kind of OK
    private fun validateUsername(b: Boolean) {
        if (usernameEditText.text.isNullOrEmpty() && !b) {
            Toast.makeText(this@SignUpActivity, "Username field shouldn't be empty", Toast.LENGTH_LONG).show()
            disableButton()
        } else if (usernameEditText.text.isNotEmpty() && !b && usernameEditText.text.toString().length < 3) {
            Toast.makeText(this@SignUpActivity, "Username must be at least 3 characters long", Toast.LENGTH_LONG).show()
            disableButton()
        } else {
            //confirm the data is OK somehow
            enableButton()
        }
    }

    //TODO implement email validation via email pattern (check https://www.tutorialspoint.com/how-to-check-email-address-validation-in-android-on-edit-text)
    private fun validateEmail(b: Boolean) {
        if (emailEditText.text.isNullOrEmpty() && !b) {
            Toast.makeText(this@SignUpActivity, "Email field shouldn't be empty", Toast.LENGTH_LONG).show()
        }
    }

    //TODO
    private fun validatePassword(b: Boolean) {
        if (passwordEditText.text.isNullOrEmpty() && !b) {
            Toast.makeText(this@SignUpActivity, "Password field shouldn't be empty", Toast.LENGTH_LONG).show()
            disableButton()
        } else if (passwordEditText.text.isNotEmpty() && !b && passwordEditText.text.toString().length < 6) {
            Toast.makeText(this@SignUpActivity, "Password must be at least 6 characters long", Toast.LENGTH_LONG).show()
            disableButton()
        } else {
            enableButton()
        }
    }

    //TODO
    private fun validateConfPassword(b: Boolean) {
        if (confPasswordEditText.text.isNullOrEmpty() && !b) {
            Toast.makeText(this@SignUpActivity, "Confirm Password field shouldn't be empty", Toast.LENGTH_LONG).show()
            disableButton()
        } else if (confPasswordEditText.text.toString() != passwordEditText.text.toString()) {
            Toast.makeText(this@SignUpActivity, "Passwords should match", Toast.LENGTH_LONG).show()
            disableButton()
        } else {
            enableButton()
        }
    }

    //TODO
    private fun validateName(b: Boolean) {
        if (nameEditText.text.isNullOrEmpty() && !b) {
            Toast.makeText(this@SignUpActivity, "Name field shouldn't be empty", Toast.LENGTH_LONG).show()
            disableButton()
        } else if (nameEditText.text.isNotEmpty() && !b && nameEditText.text.toString().length < 2) {
            Toast.makeText(this@SignUpActivity, "Name must be at least 2 characters long", Toast.LENGTH_LONG).show()
            disableButton()
        } else {
            //confirm the data is OK somehow
            enableButton()
        }
    }

    //TODO
    private fun validateSurname(b: Boolean) {
        if (surnameEditText.text.isNullOrEmpty() && !b) {
            Toast.makeText(this@SignUpActivity, "Surname field shouldn't be empty", Toast.LENGTH_LONG).show()
            disableButton()
        } else if (surnameEditText.text.isNotEmpty() && !b && surnameEditText.text.toString().length < 2) {
            Toast.makeText(this@SignUpActivity, "Surname must be at least 2 characters long", Toast.LENGTH_LONG).show()
            disableButton()
        } else {
            enableButton()
        }
    }
}
