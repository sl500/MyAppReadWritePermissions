package com.example.myappreadwritepermissions
//package example.javatpoint.com.kotlinexternalstoragereadwrite

// import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.os.Environment
import java.io.*

import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val filepath = "MyFileStorage"
    private var myExternalFile: File? =null
    private val isExternalStorageReadOnly: Boolean get() {
        val extStorageState = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)
    }
    private val isExternalStorageAvailable: Boolean get() {
        val extStorageState = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED.equals(extStorageState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fileName = findViewById<EditText>(R.id.editTextFileName)
        val fileData = findViewById<EditText>(R.id.editTextFileData)
        val saveButton = findViewById<Button>(R.id.button_save)
        val viewButton = findViewById<Button>(R.id.button_view)

        saveButton.setOnClickListener(View.OnClickListener {
            this.myExternalFile = File(getExternalFilesDir(filepath), fileName.text.toString())
            try {
                val fileOutPutStream = FileOutputStream(myExternalFile)
                fileOutPutStream.write(fileData.text.toString().toByteArray())
                fileOutPutStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            Toast.makeText(applicationContext,"data save",Toast.LENGTH_SHORT).show()
        })
        viewButton.setOnClickListener(View.OnClickListener {
            this.myExternalFile = File(getExternalFilesDir(filepath), fileName.text.toString())

            val filename = fileName.text.toString()
            this.myExternalFile = File(getExternalFilesDir(filepath),filename)
            if(filename.toString()!=null && filename.trim()!=""){
                var fileInputStream =FileInputStream(myExternalFile)
                var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
                val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
                val stringBuilder: StringBuilder = StringBuilder()
                var text: String? = null
                while ({ text = bufferedReader.readLine(); text }() != null) {
                    stringBuilder.append(text)
                }
                fileInputStream.close()
                //Displaying data on EditText
                Toast.makeText(applicationContext,stringBuilder.toString(),Toast.LENGTH_SHORT).show()
            }
        })

        if (!isExternalStorageAvailable || isExternalStorageReadOnly) saveButton.isEnabled = false
    }
}
