package com.laivedatos.uht_v2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(){
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

        seleccion(email?:"")
        botones()

    }

    private fun seleccion(email:String){

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
            onBackPressed()
        }

    }

    ///////////// ingreso de hora final /////////////////
    private fun showTime2PickerDialog() {
        val timePicker=TimePickerFragment{minute,hour -> onTime2Selected(minute,hour)}
        timePicker.show(supportFragmentManager,"time picker")
    }

    fun onTime2Selected(minute: Int, hour: Int) {
        horaFinalTextView.setText(" $hour : $minute ")

    }
    ///////////// ingreso de hora final /////////////////



    ///////////// ingreso de hora inicial/////////////////
    private fun showTimePickerDialog() {
        val timePicker=TimePickerFragment{minute,hour -> onTimeSelected(minute,hour)}
        timePicker.show(supportFragmentManager,"time picker")

    }

    fun onTimeSelected(minute: Int, hour: Int) {
        horaInicioTextView.setText(" $hour : $minute ")

    }
    ///////////// ingreso de hora inicial/////////////////



    ////////////// ingreso de fecha/////////////////////////////
    private fun showDatePickerDialog(){
        val datePicker = DatePickerFragment{day, month, year ->  onDateSelected(day, month, year)}
        datePicker.show(supportFragmentManager,"date picker")

    }
    fun onDateSelected(day:Int, month:Int, year:Int){
        val mes = month+1
        fechaTextView.setText(" $day / $mes/ $year") ///// visualizamos la fecha en el texView
    }
    ////////////// ingreso de fecha/////////////////////////////
}