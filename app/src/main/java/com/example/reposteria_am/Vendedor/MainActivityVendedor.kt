package com.example.reposteria_am.Vendedor

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.reposteria_am.R
import com.example.reposteria_am.SeleccionarTipoActivity
import com.example.reposteria_am.Vendedor.Bottom_Nav_Fragments_Vendedor.FragmentMisProductosV
import com.example.reposteria_am.Vendedor.Bottom_Nav_Fragments_Vendedor.FragmentOrdenesV
import com.example.reposteria_am.Vendedor.Nav_Fragments_Vendedor.FragmentCategoriasV
import com.example.reposteria_am.Vendedor.Nav_Fragments_Vendedor.FragmentInicioV
import com.example.reposteria_am.Vendedor.Nav_Fragments_Vendedor.FragmentMiTiendaV
import com.example.reposteria_am.Vendedor.Nav_Fragments_Vendedor.FragmentReseniasV
import com.example.reposteria_am.databinding.ActivityMainVendedorBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.Firebase

import com.google.firebase.auth.FirebaseAuth

class MainActivityVendedor : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainVendedorBinding
    private var firebaseAuth : FirebaseAuth?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainVendedorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        firebaseAuth = FirebaseAuth.getInstance()
        comprobarSesion()

        binding.NavigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        replaceFragment(FragmentInicioV())
        binding.NavigationView.setCheckedItem(R.id.op_inicio_v)

    }

    private fun cerrarSesion(){
        firebaseAuth!!.signOut()
        startActivity(Intent(applicationContext , SeleccionarTipoActivity::class.java))
        finish()
        Toast.makeText(applicationContext, "Has cerrado sesion",Toast.LENGTH_SHORT).show()
    }

    private fun comprobarSesion() {
        /*Si el usuario no ha iniciado sesion*/
        if(firebaseAuth!!.currentUser==null){
            startActivity(Intent(applicationContext,SeleccionarTipoActivity::class.java))
            Toast.makeText(applicationContext,"Vendedor no logeado",Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(applicationContext,"Usuario en linea",Toast.LENGTH_SHORT).show()
        }
    }


    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.navFragment,fragment)
            .commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.op_inicio_v->{
                replaceFragment(FragmentInicioV())
            }
            R.id.op_mi_tienda_v->{
                replaceFragment(FragmentMiTiendaV())
            }
            R.id.op_categorias_v->{
                replaceFragment(FragmentCategoriasV())
            }
            R.id.op_resenia_v->{
                replaceFragment(FragmentReseniasV())
            }
            R.id.op_cerrar_sesion_v->{
                cerrarSesion()
            }
            R.id.op_mis_productos_v->{
                replaceFragment(FragmentMisProductosV())
            }
            R.id.op_mis_ordenes_v->{
                replaceFragment(FragmentOrdenesV())
            }

        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
