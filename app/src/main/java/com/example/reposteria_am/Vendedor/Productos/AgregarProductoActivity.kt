package com.example.reposteria_am.Vendedor.Productos

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.reposteria_am.Adaptadores.AdaptadorImagenSeleccionada
import com.example.reposteria_am.Constantes
import com.example.reposteria_am.Modelos.ModeloImagenSeleccionada
import com.example.reposteria_am.R
import com.example.reposteria_am.databinding.ActivityAgregarProductoBinding
import com.github.dhaval2404.imagepicker.ImagePicker

class AgregarProductoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAgregarProductoBinding
    private var imageUri: Uri?=null

    private lateinit var imagenSelecArrayList : ArrayList<ModeloImagenSeleccionada>
    private lateinit var adaptadorImagenSel : AdaptadorImagenSeleccionada

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imagenSelecArrayList = ArrayList()

        binding.imgAgregarProducto.setOnClickListener{
            seleccionarImg()
        }

        cargarImagenes()

    }

    private fun cargarImagenes() {
        adaptadorImagenSel = AdaptadorImagenSeleccionada(this, imagenSelecArrayList )
        binding.RVImagenesProducto.adapter = adaptadorImagenSel
    }

    private fun seleccionarImg(){
        ImagePicker.with(this)
            .crop()
            .compress(1024)
            .maxResultSize(1080,1080)
            .createIntent { intent ->
                resultadoImg.launch(intent)

            }
    }

    private val resultadoImg=
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){resultado->
            if(resultado.resultCode == Activity.RESULT_OK){
                val data = resultado.data
                imageUri = data!!.data
                val tiempo = "${Constantes().obtenerTiempoD()}"

                val modeloImgSel = ModeloImagenSeleccionada(tiempo,imageUri,null,false)
                imagenSelecArrayList.add(modeloImgSel)
                cargarImagenes()
            }else{
                Toast.makeText(this,"Accion cancelada",Toast.LENGTH_SHORT).show()
            }
        }
}
