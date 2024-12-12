package com.di.recyclerviewmontes

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.di.recyclerviewmontes.adapter.MonteAdapter
import com.di.recyclerviewmontes.databinding.ActivityMainBinding
import com.google.gson.Gson
import java.io.InputStream
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var adapter:MonteAdapter
    private lateinit var copyList: MutableList<Monte>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //Biding
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //FUnciones mias
        initRecyclerView()
        retrieveMonte()
        initSearch()
    }

    private fun initRecyclerView() {
        //A nuestro adpater le pasamos la lista mutable, función lamdda del click y función lambda del borrar
        adapter = MonteAdapter(
            monteList = getListFromJson(this),
            onClickListener = { monte -> onItemSelected(monte)},
            onDeleteListener = { position -> onDeleteItem(position)}
        )
        val manager = LinearLayoutManager(this)

        binding.rvMontes.layoutManager = manager
        //Asignamos el adapter a nuestro recyclerView
        binding.rvMontes.adapter = adapter

        //Añadimos el separador
        val decoration = DividerItemDecoration(this, manager.orientation)
        binding.rvMontes.addItemDecoration(decoration)

        binding.btnCrear.setOnClickListener {
            val intent = Intent(this, MonteForm::class.java)
            startActivity(intent)
        }
    }

    private fun onItemSelected(monte: Monte) {
        // Mostrar el enlace en un Toast
        Toast.makeText(this, monte.urlinfo, Toast.LENGTH_SHORT).show()

        // Usar un Handler para esperar un segundo y medio antes de abrir el enlace
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(monte.urlinfo)))
        }, 1500)
    }


    fun onCreateItem(){
        val monte=Monte("3","Sierra del Segura","Europa","20000","https://upload.wikimedia.org/wikipedia/commons/thumb/7/79/BANDERILLAS.jpg/320px-BANDERILLAS.jpg","https://es.wikipedia.org/wiki/Sierra_de_Segura");
        copyList.add(monte)
        //Notificamos que hemos borrado un elemento de la lista
        adapter.notifyItemInserted(copyList.size-1)
    }

    fun onDeleteItem(position:Int){
        copyList.removeAt(position)
        //Notificamos que hemos borrado un elemento de la lista
        adapter.notifyItemRemoved(position)
    }
    /**TRAER JSON*/
    fun getListFromJson(context: Context): List<Monte> {
        val json: String? = getJSONFromAssets(context, "montes.json")
        val monteList = Gson().fromJson(json, Array<Monte>::class.java).toMutableList()
        //Esta copia la utilizaremos para el SearchView posterio
        copyList = monteList

        return monteList
    }

    fun getJSONFromAssets(context: Context, file: String): String? {
        var json = "";
        val stream: InputStream = context.assets.open(file)
        val size: Int = stream.available()
        val buffer = ByteArray(size)
        stream.read(buffer)
        stream.close()

        json = String(buffer, Charset.defaultCharset())
        return json
    }

    private  fun initSearch(){
        binding.svMonte.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    var filteredList: MutableList<Monte> =
                        copyList.filter {
                            it.nombre.lowercase().contains(newText)
                        }.toMutableList()
                    binding.rvMontes.adapter = MonteAdapter(filteredList,
                        onClickListener = {monte: Monte ->  onItemSelected(monte)},
                        onDeleteListener = { position -> onDeleteItem(position)})
                }
                return false
            }
        })
    }

    fun retrieveMonte() {
        // Obtener datos del intent y agregar un nuevo Monte si hay datos
        val bundle = intent.extras
        bundle?.let {
            val name = it.getString("name")
            val continent = it.getString("continent")
            val height = it.getString("height")
            val photo = it.getString("photo")
            val urlInfo = it.getString("urlInfo")

            // Verificar que los datos no sean nulos o vacíos
            if (!name.isNullOrEmpty() && !continent.isNullOrEmpty() && !height.isNullOrEmpty() && !photo.isNullOrEmpty() && !urlInfo.isNullOrEmpty()) {
                val newMonte = Monte((copyList.size).toString(), name, continent, height, photo, urlInfo)
                copyList.add(newMonte)
                adapter.notifyItemInserted(copyList.size - 1)
            }
        }
    }

}