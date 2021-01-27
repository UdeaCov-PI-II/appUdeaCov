package co.edu.udea.udeacov.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import co.edu.udea.udeacov.R
import co.edu.udea.udeacov.fragmentos.lista_solicitudes.Ingreso

class  ListaSolicitudes : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_solicitudes)

    }


}