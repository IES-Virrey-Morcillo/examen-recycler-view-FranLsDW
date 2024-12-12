package com.di.recyclerviewmontes

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.di.recyclerviewmontes.databinding.ActivityMonteFormBinding

class MonteForm : AppCompatActivity() {

    private lateinit var binding: ActivityMonteFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMonteFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ajuste para los márgenes y el recubrimiento de la interfaz
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurar el listener para el botón de envío
        binding.btnSubmit.setOnClickListener {
            guardarDatos()
        }
    }

    private fun guardarDatos() {
        // Obtener los datos de los campos de texto usando binding
        val name = binding.etNombre.text.toString().trim()
        val continent = binding.etContinente.text.toString().trim()  // Añadido el campo de continente
        val height = binding.etAltura.text.toString().trim()  // Añadido el campo de altura
        val photo = binding.etFoto.text.toString().trim()  // Añadido el campo de foto
        val urlInfo = binding.etURLInfo.text.toString().trim()

        // Validación de los campos
        if (name.isNotEmpty() && continent.isNotEmpty() && height.isNotEmpty() && photo.isNotEmpty() && urlInfo.isNotEmpty()) {
            // Crear un bundle para pasar los datos
            val bundle = Bundle()
            bundle.putString("name", name)
            bundle.putString("continent", continent)  // Continent
            bundle.putString("height", height)  // Height
            bundle.putString("photo", photo)  // Photo
            bundle.putString("urlInfo", urlInfo)  // URL

            // Crear un intent y pasar los datos a MainActivity
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
        } else {
            // Mostrar un mensaje de error si algún campo está vacío
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
        }
    }
}
