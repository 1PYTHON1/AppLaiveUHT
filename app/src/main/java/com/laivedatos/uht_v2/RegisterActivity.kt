package com.laivedatos.uht_v2

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONArray
import org.json.JSONObject




class RegisterActivity : AppCompatActivity(){


    var LOG_TAG = "JosiId"
    var SPREAD_SHEET_ID = "1ET9bgx6dpuYncs6_8qRZ9lgLS39Hw49rWLCU2EfmGI4"
    var TABLE_USERS = "users"


    //IMPORTANTE: estas URL tienes que cambiarlas por las de tus propios scripts
    // para obtener valores de la hoja de calculo
    //var sheetInJsonURL = "https://script.google.com/macros/s/AKfycbzyFF5oMHkxJty7qhXSkbphjdXMoMh5jUPunOsJ9VvWWGoiRSjm/exec?spreadsheetId=$SPREAD_SHEET_ID&sheet=" //
    // para enviar valores de la hoja de calculo
    val addRowURL = "https://script.google.com/macros/s/AKfycbzyFF5oMHkxJty7qhXSkbphjdXMoMh5jUPunOsJ9VvWWGoiRSjm/exec"


    private val db = FirebaseFirestore.getInstance()
    var varMaquinaGlobal=""
    var varEventoGlobal=""
    var varEmailGlobal =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        val bundle : Bundle?= intent.extras//////recibir datos del anterior activity
        val email : String? =bundle?.getString("email") ///enviar datos a la funcion botones
        varEmailGlobal= email.toString()

        seleccionDeSpinner(email?:"")
        botones()

    }

    private fun seleccionDeSpinner(email:String){

        title = "INGRESAR DATOS"

        ////////////MAQUINAS//////////////
        var listaMaquinas = findViewById<Spinner>(R.id.maquinasSpinner)
        //var maquinas = listOf("LLENADORA","KISTER")     /////AGREGAMOS LOS DATOS A LISTA DE LAS MAQUINAS
        var maquinas = resources.getStringArray(R.array.OPCIONES)
        val adaptador_maquinas = ArrayAdapter(this,android.R.layout.simple_spinner_item,maquinas)
        listaMaquinas.adapter = adaptador_maquinas




        ////////// TIPO DE EVENTO///////
        var listaEventos = findViewById<Spinner>(R.id.eventoSpinner)
        //var maquinas = listOf("LLENADORA","KISTER")     /////AGREGAMOS LOS DATOS A LISTA DE LAS MAQUINAS
        var eventos = resources.getStringArray(R.array.EVENTOS)
        val adaptador_evento = ArrayAdapter(this,android.R.layout.simple_spinner_item,eventos)
        listaEventos.adapter= adaptador_evento


        listaMaquinas.onItemSelectedListener= object:
                AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                var maquina_selecionada = adaptador_maquinas.getItem(position)
                varMaquinaGlobal = maquina_selecionada.toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }



        listaEventos.onItemSelectedListener= object:
                AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
            {
                var evento_selecionada = adaptador_evento.getItem(position)
                varEventoGlobal = evento_selecionada.toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        /*
        saveButton.setOnClickListener {

            db.collection("User").document(email).set(
                hashMapOf("Fecha" to fechaTextView.text.toString(),
                        "Maquina" to varMaquinaGlobal,
                        "Tipo de evento" to varEventoGlobal,
                        "Descripcion" to descripcionEditText.text.toString(),
                        "Accion Tecnica" to accionEditText.text.toString(),
                        "Hora de Inicio" to horaInicioTextView.text.toString(),
                        "Hora de Finalizacion" to horaFinalTextView.text.toString())
            )
            onBackPressed()
        }*/

    }

    private fun botones()
    {
        cancelButton.setOnClickListener {
            onBackPressed()
        }

        fechaButton.setOnClickListener{
            showDatePickerDialog() }

        horaIniciobutton.setOnClickListener {
            showTimePickerDialog()
        }

        horaFinalButton.setOnClickListener {
            showTime2PickerDialog()

        }

        saveButton.setOnClickListener {

            db.collection("User").document(varEmailGlobal).set(
                    hashMapOf("Fecha" to fechaTextView.text.toString(),
                            "Maquina" to varMaquinaGlobal,
                            "Tipo de evento" to varEventoGlobal,
                            "Descripcion" to descripcionEditText.text.toString(),
                            "Accion Tecnica" to accionEditText.text.toString(),
                            "Hora de Inicio" to horaInicioTextView.text.toString(),
                            "Hora de Finalizacion" to horaFinalTextView.text.toString())
            )
            addUsers()
            onBackPressed()
        }


    }

    ///////////// ingreso de hora final /////////////////
    private fun showTime2PickerDialog() {
        val timePicker=TimePickerFragment{minute,hour -> onTime2Selected(minute,hour)}
        timePicker.show(supportFragmentManager,"time picker")
    }

    @SuppressLint("SetTextI18n")
    fun onTime2Selected(minute: Int, hour: Int) {
        horaFinalTextView.text = " $hour : $minute "

    }
    ///////////// ingreso de hora final /////////////////



    ///////////// ingreso de hora inicial/////////////////
    private fun showTimePickerDialog() {
        val timePicker=TimePickerFragment{minute,hour -> onTimeSelected(minute,hour)}
        timePicker.show(supportFragmentManager,"time picker")

    }

    @SuppressLint("SetTextI18n")
    fun onTimeSelected(minute: Int, hour: Int) {
        horaInicioTextView.text = " $hour : $minute "

    }
    ///////////// ingreso de hora inicial/////////////////



    ////////////// ingreso de fecha/////////////////////////////
    private fun showDatePickerDialog(){
        val datePicker = DatePickerFragment{day, month, year ->  onDateSelected(day, month, year)}
        datePicker.show(supportFragmentManager,"date picker")

    }
    @SuppressLint("SetTextI18n")
    fun onDateSelected(day:Int, month:Int, year:Int){
        val mes = month+1
        fechaTextView.text = " $day / $mes / $year" ///// visualizamos la fecha en el texView
    }
    ////////////// ingreso de fecha/////////////////////////////



    private fun addUsers(){

        /* Montamos un JSON como este:
         * {"spreadsheet_id":"1jBtXZdoxIYJlEAnJ8YbQ3NbUmPrBFqgtSbmMHMIQMck", "sheet": "users", "rows":[["4", "Juan", "juan@gmail.com"], ["5", "Maria", "maria@gmail.com"]]}
         */

        val jsonObject = JSONObject()
        jsonObject.put("spreadsheet_id", SPREAD_SHEET_ID)
        jsonObject.put("sheet", TABLE_USERS)

        val rowsArray = JSONArray()

        val row1 = JSONArray()
        row1.put(fechaTextView.text.toString())
        row1.put(varMaquinaGlobal)
        row1.put(varEventoGlobal)
        row1.put(descripcionEditText.text.toString())
        row1.put(accionEditText.text.toString())
        row1.put(horaInicioTextView.text.toString())
        row1.put(horaFinalTextView.text.toString())

        rowsArray.put(row1)


        jsonObject.put("rows", rowsArray)

        val queue = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest( addRowURL, jsonObject,
                { response ->
                    Log.i(LOG_TAG, "Response is: $response")
                    Toast.makeText(this, "Evento Agregado con Exito", Toast.LENGTH_LONG).show()

                    //Refrescamos la lista de usuarios
                    //getUsers()
                },
                { error ->
                    error.printStackTrace()
                    Log.e(LOG_TAG, "That didn't work!")
                }
        )
        queue.add(jsonObjectRequest)


    }

}

