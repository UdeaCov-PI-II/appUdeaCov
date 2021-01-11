package co.edu.udea.udeacov.fragmentos.solicitud_permiso


import android.Manifest
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import co.edu.udea.udeacov.R
import co.edu.udea.udeacov.databinding.FragmentDeclaracionDeResponsabilidadBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DeclaracionResponsabilidadFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DeclaracionResponsabilidadFragment : Fragment() {
    private val STORAGE_PERMISSION_CODE: Int = 1000
    private lateinit var binding : FragmentDeclaracionDeResponsabilidadBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_declaracion_de_responsabilidad, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnVerDeclaracionDeCompromiso.setOnClickListener {
            //mostrar el Dialog
            val mAlertDialog = AlertDialog.Builder(this.view?.context)
                .setView(R.layout.layout_declaracion_compromiso)
                .setNegativeButton("Cancelar") { dialog: DialogInterface?, _: Int ->
                    dialog?.dismiss()
                }
                .setPositiveButton("Enviar") { _: DialogInterface?, _: Int ->
                    Log.d("Tag", "Prueba")
                }
            // Create the AlertDialog object and return it
            mAlertDialog.show()
        }
        binding.btnDescargar.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ContextCompat.checkSelfPermission(this.requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)==
                    PackageManager.PERMISSION_DENIED) {
                    //PERMISSION DENEGADO
                    //show popup for runtime permission
                    requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE )
                }
                else {
                    //permiso concedido
                    startDownloading()
                }
            }
            else {
                //sistema operativo es menor
                startDownloading()
            }
        }
    }

    private fun startDownloading() {
        var url = "http://www.udea.edu.co/wps/wcm/connect/udea/755c2d62-727a-48cc-ba24-5b775c4115d0/VA-TH-IN-02.pdf?MOD=AJPERES&CVID=ndpHhO7&attachment=true&id=1594938166306"
        val request = DownloadManager.Request(Uri.parse(url))
        //Permitir que ambas redes descarguen archivos
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setTitle("Instrucciones-bioseguridad-UdeA.pdf")
        request.setDescription("Instrucciones-bioseguridad-UdeA.pdf")
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "file.pdf")

        //obtener el servicio de descarga y poner en cola el archivo
        val manager = activity?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)
    }

    //método que solicita permiso al usuario para acceder a multimedia y archivos del celular
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode){
            STORAGE_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    // Fue aprobado el permiso desde la ventana emergente, puede realizar la descarga del archivo
                    startDownloading()
                }
                else {
                    //El permiso no fue abrobado
                    Toast.makeText(requireContext(), "Permiso Denegado", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}