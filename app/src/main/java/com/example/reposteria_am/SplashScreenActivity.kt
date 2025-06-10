package com.example.reposteria_am

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.example.reposteria_am.Cliente.MainActivityCliente
import com.example.reposteria_am.Vendedor.MainActivityVendedor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        firebaseAuth = FirebaseAuth.getInstance()

        verBienvenida()
    }

    private fun verBienvenida() {
        object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // No es necesario hacer nada por cada tick
            }

            override fun onFinish() {
                comprobarTipoUsuario()
            }
        }.start()
    }

    private fun comprobarTipoUsuario() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null) {
            startActivity(Intent(this, SeleccionarTipoActivity::class.java))
            finishAffinity()
        } else {
            val reference = FirebaseDatabase.getInstance().getReference("Usuarios")
            reference.child(firebaseUser.uid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val tipoU = snapshot.child("tipoUsuario").value as? String
                        when (tipoU) {
                            "vendedor" -> {
                                startActivity(Intent(this@SplashScreenActivity, MainActivityVendedor::class.java))
                                finishAffinity()
                            }
                            "cliente" -> {
                                startActivity(Intent(this@SplashScreenActivity, MainActivityCliente::class.java))
                                finishAffinity()
                            }
                            else -> {
                                // Si el tipoUsuario no es válido, redirigir a la pantalla de selección
                                startActivity(Intent(this@SplashScreenActivity, SeleccionarTipoActivity::class.java))
                                finishAffinity()
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Manejamos el error de lectura
                        startActivity(Intent(this@SplashScreenActivity, SeleccionarTipoActivity::class.java))
                        finishAffinity()
                    }
                })
        }
    }
}
