package co.edu.udea.udeacov

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_registrarse.setOnClickListener {
            val intent: Intent = Intent(this, preingreso::class.java)
            startActivity(intent)
        }
    }



}