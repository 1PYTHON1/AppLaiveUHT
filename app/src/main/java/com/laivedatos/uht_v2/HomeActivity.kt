package com.laivedatos.uht_v2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_main.*
enum class  ProviderType{
    BASIC
}

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        //setup
        val bundle : Bundle?= intent.extras  ////RECIBIR DATOS DE OTRO ACTIVITY
        val email : String? =bundle?.getString("email")
        val provider : String?= bundle?.getString("provider")
        setup(email?:"",provider?:"")
    }
    private fun setup(email:String,provider:String)
    {
        title = "INICIO"
        ////////// boton de salida//////////
        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }

        //////// boton de agragar evento///////
        eventoButton.setOnClickListener{
            val homeIntent : Intent = Intent(this, RegisterActivity::class.java).apply {
                putExtra("email",email)
            }
            startActivity(homeIntent)
        }

        revisarButton.setOnClickListener {
            val homeIntent : Intent = Intent(this, VisorExcelActivity::class.java)
            startActivity(homeIntent)
        }
    }

}