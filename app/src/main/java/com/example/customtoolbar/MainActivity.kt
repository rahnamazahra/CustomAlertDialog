package com.example.customtoolbar

import android.content.Context
import android.content.DialogInterface
import android.content.res.Configuration
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.customtoolbar.databinding.ActivityMainBinding
import java.util.*


private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
         loadLocale()
        /********************************************/
        val dialog = AlertDialog.Builder(this)
        val layoutDialog = layoutInflater.inflate(R.layout.layout_message, null)
        val name = layoutDialog.findViewById<EditText>(R.id.name)
        val email = layoutDialog.findViewById<EditText>(R.id.email)
        val message = layoutDialog.findViewById<EditText>(R.id.message)

        dialog.setView(layoutDialog)
        dialog.setCancelable(false)

        dialog.setPositiveButton("ثبت نظرات") { _: DialogInterface, _: Int -> }
        dialog.setNegativeButton("بیخیال") { _: DialogInterface, _: Int -> }

        val customDialog = dialog.create()
        customDialog.show()

        customDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            if (name.text.isNotEmpty() && email.text.isNotEmpty() && message.text.isNotEmpty()) {
                Toast.makeText(this, "اطلاعات با موفقیت درج شد", Toast.LENGTH_SHORT).show()
                customDialog.dismiss()
            } else {
                Toast.makeText(this, "هیچ فیلدی نباید خالی باشد", Toast.LENGTH_SHORT).show()
            }

        }

        customDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener {
            customDialog.dismiss()
        }
        /****************************************************/
        val listlanguage=resources.getStringArray(R.array.list_Language)

        val adapter= ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1,listlanguage)
        binding.spinner.adapter=adapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item=parent?.getItemAtPosition(position).toString()
                setLocate(item)
            }

        }
        /*********************************************/

    }

    private fun setLocate(item: String) {
         val locale=Locale(item)
        Toast.makeText(this,locale.toString(),Toast.LENGTH_SHORT).show()
        Locale.setDefault(locale)
        val configuration=Configuration()

        configuration.setLocale(locale)

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N){
            applicationContext.createConfigurationContext(configuration)
        } else {
            resources.updateConfiguration(configuration,baseContext.resources.displayMetrics)
        }


        val editor=getSharedPreferences("setting", Context.MODE_PRIVATE).edit()
        editor.putString("Language",item)
        editor.apply()
    }
    private fun loadLocale() {
       val sharedPreferences=getSharedPreferences("setting", 0)
        if (sharedPreferences.contains("Language")) {
            val language = sharedPreferences.getString("Language", null).toString()
            setLocate(language)
            Toast.makeText(this,language,Toast.LENGTH_SHORT).show()

        }
    }

}

