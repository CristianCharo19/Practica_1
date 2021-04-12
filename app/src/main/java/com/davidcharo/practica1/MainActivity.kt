package com.davidcharo.practica1

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.davidcharo.practica1.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

private const val EMPTY = ""
private const val SPACE = " "

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private var users: MutableList<User> = mutableListOf()
    private var fechaNacimiento: String = ""
    private var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainBinding.saveButton.setOnClickListener {
            val name = mainBinding.nameEditText.text.toString()
            val email = mainBinding.emailEditText.text.toString()
            val password = mainBinding.passwordEditText.text.toString()
            val repPassword = mainBinding.repPasswordEditText.text.toString()
            val genre = if (mainBinding.femaleRadioButton.isChecked) getString(R.string.female) else getString(R.string.male)
            var hobbies = if (mainBinding.danceCheckBox.isChecked) getString(R.string.dance) else EMPTY
            hobbies = hobbies + SPACE + if (mainBinding.eatCheckBox.isChecked) getString(R.string.eat) else EMPTY
            hobbies = hobbies + SPACE + if (mainBinding.readCheckBox.isChecked) getString(R.string.read) else EMPTY
            hobbies = hobbies + SPACE + if (mainBinding.sportCheckBox.isChecked) getString(R.string.sport) else EMPTY
            val birthday = mainBinding.birthdayEditText.text.toString()
            val spCiudad = mainBinding.spCiudad.selectedItem.toString()
            cleanViews()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && repPassword.isNotEmpty()) {
                if (password == repPassword) {
                    saveUser(name, email, password, genre, hobbies, birthday, spCiudad)
                    mainBinding.repPasswordTextInputLayout.error = null
                } else {
                    mainBinding.repPasswordTextInputLayout.error = getString(R.string.password_error)
                }
            } else {
                Toast.makeText(this, getString(R.string.form_error), Toast.LENGTH_LONG).show()
                cleanViews()
            }
        }

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val format = "MM/dd/yyyy"
                val sdf = SimpleDateFormat(format, Locale.US)
                fechaNacimiento = sdf.format(cal.time).toString()
                mainBinding.birthdayEditText.setText(fechaNacimiento)
            }

        mainBinding.birthdayEditText.setOnClickListener {
            DatePickerDialog(
                this,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        mainBinding.spCiudad.selectedItem.toString()
    }

    private fun cleanViews() {
        with(mainBinding) {
            nameEditText.setText(EMPTY)
            emailEditText.setText(EMPTY)
            passwordEditText.setText(EMPTY)
            repPasswordEditText.setText(EMPTY)
            femaleRadioButton.isChecked = true
            danceCheckBox.isChecked = false
            eatCheckBox.isChecked = false
            sportCheckBox.isChecked = false
            readCheckBox.isChecked = false
            birthdayEditText.setText(EMPTY)
        }
    }

    private fun saveUser(name: String, email: String, password: String, genre: String, hobbies: String, birthday: String, spCiudad: String) {
        val newUser = User(name, email, password, genre, hobbies, birthday, spCiudad)
        users.add(newUser)
        printUserData()
    }

    private fun printUserData() {
        var info = ""
        for (user in users)
            info = info + "\n\n" + user.name + "\n" + user.email + "\n" + user.gnre + "\n" + user.hobbies + "\n" + user.birthday + "\n" + user.spCiudad
        mainBinding.infoTextView.text = info

    }
}