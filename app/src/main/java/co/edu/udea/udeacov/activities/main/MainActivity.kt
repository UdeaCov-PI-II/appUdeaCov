package co.edu.udea.udeacov.activities.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.edu.udea.udeacov.R
import co.edu.udea.udeacov.activities.SolicitudDeUnPermiso
import co.edu.udea.udeacov.activities.preingreso
import co.edu.udea.udeacov.databinding.ActivityMainBinding


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

        viewModel.authResponse.observe(this, Observer {
            it?.let{
                val intent= Intent(this, SolicitudDeUnPermiso::class.java)
                startActivity(intent)
                viewModel.signInIsCompleted()
            }
        })

        viewModel.responseError.observe(this, Observer{
            it?.let{
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                viewModel.showErrorIsCompleted()
            }
        })
    }



}