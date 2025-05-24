package com.example.reposteria_am.Cliente

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.reposteria_am.Cliente.Bottom_Nav_Fragments_Cliente.FragmentMisOrdenesC
import com.example.reposteria_am.Cliente.Bottom_Nav_Fragments_Cliente.FragmentTiendaC
import com.example.reposteria_am.Cliente.Nav_Fragments_Cliente.FragmentInicioC
import com.example.reposteria_am.Cliente.Nav_Fragments_Cliente.FragmentMiPerfilC
import com.example.reposteria_am.R
import com.example.reposteria_am.databinding.ActivityMainClienteBinding
import com.example.reposteria_am.databinding.ActivityMainVendedorBinding
import com.google.android.material.navigation.NavigationView



class MainActivityCliente : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding : ActivityMainClienteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        binding.NavigationView.setNavigationItemSelectedListener (this)

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        replaceFragment(FragmentInicioC())


    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.navFragment,fragment)
            .commit()

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.op_inicio_c -> {
                replaceFragment(FragmentInicioC())
            }
            R.id.op_mi_perfil_c -> {
                replaceFragment(FragmentMiPerfilC())
            }
            R.id.op_cerrar_sesion_c -> {
                Toast.makeText(applicationContext, "Has cerrado sesion", Toast.LENGTH_SHORT).show()
            }
            R.id.op_tienda_C -> {
                replaceFragment(FragmentTiendaC())
            }
            R.id.op_mis_ordenes_c -> {
                replaceFragment(FragmentMisOrdenesC())
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

}