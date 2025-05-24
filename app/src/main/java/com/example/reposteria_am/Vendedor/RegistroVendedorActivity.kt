package com.example.reposteria_am.Vendedor

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.reposteria_am.Constantes
import com.example.reposteria_am.R
import com.example.reposteria_am.databinding.ActivityRegistroVendedorBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistroVendedorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroVendedorBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroVendedorBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Espere por favor")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.btnRegistrarV.setOnClickListener {
            validarInformacion()
        }
    }
    private var nombres= ""
    private var email= ""
    private var password =""
    private var cpassword =""

    private fun validarInformacion() {
        nombres =binding.etNombresV.text.toString().trim()
        email =binding.etMailV.text.toString().trim()
        password  =binding.etPassword.text.toString().trim()
        cpassword= binding.etCPassword.text.toString().trim()

        if(nombres.isEmpty()){
            binding.etNombresV.error = "Ingrese sus nombres"
            binding.etNombresV.requestFocus()
        }else if(email.isEmpty()){
            binding.etMailV.error = "Ingrese su email"
            binding.etMailV.requestFocus()
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.etMailV.error = "Email no valido"
                binding.etMailV.requestFocus()
        }else if(password.isEmpty()){
                binding.etPassword.error = "Ingrese su contraseña"
                binding.etPassword.requestFocus()
        }else if(password.length <=6){
            binding.etPassword.error = "Necesita 6 o mas caracteres"
            binding.etPassword.requestFocus()
        }else if(cpassword.isEmpty()){
            binding.etCPassword.error = "Confirme la contraseña"
            binding.etCPassword.requestFocus()
        }else if(password!=cpassword){
            binding.etCPassword.error = "La contraseña no coincide"
            binding.etCPassword.requestFocus()
        }else {
            RegistrarVendedor()
        }

        }

    private fun RegistrarVendedor() {
       progressDialog.setMessage("Creando cuenta")
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                insertartInfoBD()

            }
            .addOnFailureListener{ e->
                Toast.makeText(this,"Fallo el registro debado a ${e.message}",Toast.LENGTH_SHORT).show()

            }

        

    }

    private fun insertartInfoBD() {
        progressDialog.setMessage("Guardando informacion...")

        val uidBD = firebaseAuth.uid
        val nombreBD = nombres
        val emailBD = email
        val tipoUsuario = "vendedor"
        val tiempoBD = Constantes().obtenerTiempoD()

        val datosVendedor = HashMap<String ,Any>()

        datosVendedor["uid"]= "$uidBD"
        datosVendedor["nombres"]= "$nombreBD"
        datosVendedor["tiempoUsuario"]= "vendedor"
        datosVendedor["tiempo_registro"]= tiempoBD

        val reference=FirebaseDatabase.getInstance().getReference("Usuarios")
        reference.child(uidBD!!)
            .setValue(datosVendedor)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this,MainActivityVendedor::class.java))
                finish()

            }
            .addOnFailureListener{e->
                progressDialog.dismiss()
                Toast.makeText(this,"Fallo en el registro en BD debido a ${e.message}",Toast.LENGTH_SHORT).show()
            }



    }
}

