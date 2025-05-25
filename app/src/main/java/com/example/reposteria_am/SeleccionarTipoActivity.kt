package com.example.reposteria_am

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.reposteria_am.Cliente.LoginClienteActivity
import com.example.reposteria_am.Vendedor.LoginVendedorActivity
import com.example.reposteria_am.databinding.ActivitySeleccionarTipoBinding

class SeleccionarTipoActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySeleccionarTipoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySeleccionarTipoBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.tipoVendedor.setOnClickListener{
            startActivity(Intent(this@SeleccionarTipoActivity,LoginVendedorActivity::class.java))
        }

        binding.tipoCliente.setOnClickListener{
            startActivity(Intent(this@SeleccionarTipoActivity,LoginClienteActivity::class.java))
        }

    }
}