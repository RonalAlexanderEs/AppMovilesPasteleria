package com.example.reposteria_am.Cliente

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.reposteria_am.Constantes
import com.example.reposteria_am.R
import com.example.reposteria_am.databinding.ActivityRegistroClienteBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistroClienteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroClienteBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.btnRegistrarC.setOnClickListener{
            vaidarInformacion()
        }
    }

    private var nombres = ""
    private var email = ""
    private var password = ""
    private var cpassword = ""
    private fun vaidarInformacion() {
        nombres = binding.etNombresC.text.toString().trim()
        email = binding.etMail.text.toString().trim()
        password = binding.etPassword.text.toString().trim()
        cpassword = binding.etCPassword.text.toString().trim()

        if (nombres.isEmpty()) {
            binding.etNombresC.error = "Ingrese sus nombres"
            binding.etNombresC.requestFocus()
        } else if (email.isEmpty()) {
            binding.etMail.error = "Ingrese su correo"
            binding.etMail.requestFocus()
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etMail.error = "Correo no válido"
            binding.etMail.requestFocus()
        } else if (password.isEmpty()) {
            binding.etPassword.error = "Ingrese una contraseña"
            binding.etPassword.requestFocus()
        } else if (password.length < 6) {
            binding.etPassword.error = "La contraseña debe tener al menos 6 caracteres"
            binding.etPassword.requestFocus()
        } else if (cpassword.isEmpty()) {
            binding.etCPassword.error = "Confirme la contraseña"
            binding.etCPassword.requestFocus()
        } else if (password != cpassword) {
            binding.etCPassword.error = "Las contraseñas no coinciden"
            binding.etCPassword.requestFocus()
        } else {
            registraCliente()

            }
        }

    private fun registraCliente() {
        progressDialog.setMessage("creando cuenta")
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                insertarInfoBD()
            }
            .addOnFailureListener{e->
                Toast.makeText(this,"Fallo el registro debido a ${e.message}",Toast.LENGTH_SHORT).show()

            }
    }

    private fun insertarInfoBD() {
        progressDialog.setMessage("Guardando información")
        progressDialog.show()

        val uid = firebaseAuth.uid
        val nombresC = nombres
        val emailC = email
        val tiempoRegistro = Constantes().obtenerTiempoD()

        val datosCliente = HashMap<String, Any>()
        datosCliente["uid"] = "$uid"
        datosCliente["nombres"] = "$nombresC"
        datosCliente["tiempoRegistro"] = tiempoRegistro
        datosCliente["email"] = "$emailC"
        datosCliente["imagen"] = ""
        datosCliente["tipoUsuario"] = "cliente"


        val ref = FirebaseDatabase.getInstance().getReference("Usuarios")
        ref.child(uid!!)
            .setValue(datosCliente)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this@RegistroClienteActivity, MainActivityCliente::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }
    }

