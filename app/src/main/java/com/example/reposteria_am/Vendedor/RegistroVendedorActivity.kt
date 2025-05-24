package com.example.reposteria_am.Vendedor

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.reposteria_am.R

class RegistroVendedorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Establece el layout XML para esta actividad
        setContentView(R.layout.activity_registro_vendedor)
    }
}
