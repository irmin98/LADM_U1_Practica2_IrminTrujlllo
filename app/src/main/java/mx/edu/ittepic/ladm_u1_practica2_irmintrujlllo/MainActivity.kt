package mx.edu.ittepic.ladm_u1_practica2_irmintrujlllo

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        permisos()
        //BOTON PARA GUARDAR
      button.setOnClickListener {

          if(radioButton4.isChecked){
            //GUARDARA EN MEMORIA INTERNA
              mensaje("SE GUARDO EN INTERNA")

              guardarArchivoInterno()
          }else if (radioButton5.isChecked){
              //GUARDARA EN MEMORIA SD
              mensaje("SE GUARDO EN EXTERNA")

              guardarArchivoSD()
          }

      }

        //BOTON PARA LEER
        button2.setOnClickListener {

            if(radioButton4.isChecked){
                //LEER EN MEMORIA INTERNA
                mensaje(editText5.text.toString())

                leerArchivoInterno()

            }else if (radioButton5.isChecked){
                //LEER EN MEMORIA SD
                mensaje(editText5.text.toString())
                leerArchivoSD()

            }
        }

    }

    fun permisos(){
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_DENIED){

                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    ,0)

            }else{
                mensaje("Permisos Ya Otorgados")
            }

        }

    fun mensaje(m:String){
        AlertDialog.Builder(this)
            .setTitle("Atencion")
            .setMessage(m)
            .setPositiveButton("OK"){d,i->}
            .show()
    }

    fun leerArchivoSD(){
        if(noSD()){
            mensaje("NO HAY MEMORIA EXTERNA")
        }
        try {

            var rutaSD = Environment.getExternalStorageDirectory()
            var datosArchivo = File(rutaSD.absolutePath,editText5.text.toString()+".txt")

            var flujoEntrada= BufferedReader(InputStreamReader(FileInputStream(datosArchivo)))
            var data = flujoEntrada.readLine()

           editText2.setText(data).toString()
            flujoEntrada.close()

        }catch (error: IOException){
            mensaje(error.message.toString())
        }

    }

    fun guardarArchivoSD(){

        if(noSD()){
            mensaje("NO HAY MEMORIA EXTERNA")
            return
        }

        try{

            var rutaSD= Environment.getExternalStorageDirectory()
            var datosArchivo = File(rutaSD.absolutePath,editText5.text.toString()+".txt")

            var flujoSalida = OutputStreamWriter(FileOutputStream(datosArchivo))
            var data =editText2.text.toString()

            flujoSalida.write(data)
            flujoSalida.flush()
            flujoSalida.close()
            mensaje("SE GUARDO CORRECTAMENTE")
            editText2.setText("")

        }catch (error : IOException){
            mensaje(error.message.toString())
        }


    }

    fun noSD(): Boolean{

        var estado= Environment.getExternalStorageState()
        if(estado != Environment.MEDIA_MOUNTED){
            return true
        }
        return false
    }

    fun guardarArchivoInterno(){

        try{
            var flujoSalida = OutputStreamWriter(openFileOutput(editText5.text.toString()+".txt", Context.MODE_PRIVATE))
            var data =editText2.text.toString()

            flujoSalida.write(data)
            flujoSalida.flush()
            flujoSalida.close()
            mensaje("SE GUARDÓ CORRECTAMENTE")
            editText2.setText("")

        }catch (error : IOException){
            mensaje(error.message.toString())
        }
    }

    fun leerArchivoInterno(){
        try {
            var flujoEntrada= BufferedReader(InputStreamReader(openFileInput(editText5.text.toString()+".txt")))
            var data = flujoEntrada.readLine()

            editText2.setText(data).toString()


            flujoEntrada.close()

        }catch (error: IOException){
            mensaje(error.message.toString())
        }
    }




}//class
