package co.edu.udea.udeacov.activities.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.edu.udea.udeacov.R
import co.edu.udea.udeacov.activities.ListaSolicitudes
import co.edu.udea.udeacov.activities.SolicitudDeUnPermiso
import co.edu.udea.udeacov.activities.porteria.PersonalDeSeguridad
import co.edu.udea.udeacov.activities.preingreso
import co.edu.udea.udeacov.databinding.ActivityMainBinding
import co.edu.udea.udeacov.network.response.AuthResponseDto


class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by lazy {
        ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnIngresar.setOnClickListener {
            viewModel.signIn(binding.emailEditText.text.toString(),binding.passwordEditText.text.toString())
        }

        binding.btnRegistrarse.setOnClickListener {
            val intent= Intent(this, preingreso::class.java)
            startActivity(intent)
        }

        binding.buttonBorrarDespues.setOnClickListener {
            val intent= Intent(this, ListaSolicitudes::class.java)
            startActivity(intent)
        }

        viewModel.authResponse.observe(this, Observer {
            it?.let{
                saveUserInfoInPreferencesFile(it)
                assignDeviceTokenToLoggedUser(it.userId)

               var role = it.roles[0]

                if (role == "ROLE_USER") {
                    val intent= Intent(this, SolicitudDeUnPermiso::class.java)
                    startActivity(intent)
                    viewModel.signInIsCompleted()
                }else if (role == "ROLE_SECURITY") {
                    val intent= Intent(this, PersonalDeSeguridad::class.java)
                    startActivity(intent)
                    viewModel.signInIsCompleted()
                }else{
                    val intent= Intent(this, ListaSolicitudes::class.java)
                    startActivity(intent)
                    viewModel.signInIsCompleted()
                }

            }
        })

        viewModel.responseError.observe(this, Observer{
            it?.let{
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                viewModel.showErrorIsCompleted()
            }
        })
    }

    private fun assignDeviceTokenToLoggedUser(userId : String) {
        val sharedPref =
            this.getSharedPreferences(getString(R.string.user_settings_file), Context.MODE_PRIVATE)
        val token = sharedPref.getString(getString(R.string.user_device_token),null)
        token?.let{
            viewModel.sendTokenToServer(it,userId)
        }
    }

    private fun saveUserInfoInPreferencesFile(it: AuthResponseDto) {
        val sharedPref = this.getSharedPreferences(getString(R.string.user_settings_file), Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString(getString(R.string.user_id), it.userId)
            putString(getString(R.string.user_role), it.roles[0])
            putString(getString(R.string.user_token), it.token)
            apply()
        }
    }


}