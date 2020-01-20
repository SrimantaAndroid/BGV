package com.gespl.bgv


import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import com.gespl.bgv.utils.SheardParam
import com.gespl.bgv.utils.SheardPreference
import jxl.Workbook
import jxl.WorkbookSettings
import jxl.write.Label
import jxl.write.WritableWorkbook
import jxl.write.WriteException
import jxl.write.biff.RowsExceededException
import kotlinx.android.synthetic.main.fragment_image.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class ImageFragment : Fragment() {
    val CAMERA_IMAGE_REQUEST:Int=222
    val REQUEST_CODE = 100
    var myPath: File?=null
    var exelfile:File?=null

    private val neededPermissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val  view:View=LayoutInflater.from(activity).inflate(R.layout.fragment_image,null)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_name_val.setText(SheardPreference.getSomeStringValue(activity!!,SheardParam.assoc_full_name))
        tv_station_val.setText(SheardPreference.getSomeStringValue(activity!!,SheardParam.log_stncode!!))
        take_image.setOnClickListener {
            takephato()
        }
        btn_new.setOnClickListener {
            val intent=Intent(activity!!,MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
         //   val action=ImageFragmentDirections.actionImageFragmentToPersonaldetails()
            //findNavController().navigate(action)
        }
        btn_send1.setOnClickListener {
            createExcelSheet()
            //sendemail()

        }
        btn_exit.setOnClickListener {
            val alertDialog: AlertDialog.Builder= AlertDialog.Builder(activity!!)
            alertDialog.setTitle(activity!!.resources.getString(R.string.app_name))
            alertDialog.setMessage("Are you Exit From App")

            alertDialog.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                activity!!.finish()

            })
            alertDialog.setNegativeButton("No", DialogInterface.OnClickListener{ dialog, which ->
                dialog.dismiss()
            })
            val dialog = alertDialog.create()

            dialog.show()
        }

    }

    private fun sendemail() {
        val mailsuject="BGV_GESPL_"+SheardPreference.getSomeStringValue(activity!!,SheardParam.log_stncode!!)+" -  "+SheardPreference.getSomeStringValue(activity!!,SheardParam.assoc_full_name!!)
       val  mailtext="Name: "+SheardPreference.getSomeStringValue(activity!!,SheardParam.assoc_full_name!!)+"\n"+
              "Comp_ID:" +SheardPreference.getSomeStringValue(activity!!,SheardParam.comp_id!!)+"\n"+
               "Station_Code: " +SheardPreference.getSomeStringValue(activity!!,SheardParam.log_stncode)+"\n"+
               "Mobile no: "+ SheardPreference.getSomeStringValue(activity!!,SheardParam.mob1)+"\n"+
               "Verify by: "+SheardPreference.getSomeStringValue(activity!!,SheardParam.loginuserid!!)
       // val   photoURI: Uri = FileProvider.getUriForFile(activity!!, context!!.getApplicationContext().getPackageName() + ".provider", myPath!!);
        val i = Intent(Intent.ACTION_SEND)
        // i.type = "image/png"
        i.setType("*/*");
        i.putExtra(Intent.EXTRA_EMAIL, arrayOf("giriexpressservice@giriexpressservice.com"))
        i.putExtra(Intent.EXTRA_SUBJECT,mailsuject )
        i.putExtra(Intent.EXTRA_TEXT, mailtext)
      //  i.putExtra(Intent.EXTRA_STREAM, photoURI)//pngFile
        i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        var packageManager:PackageManager=activity!!.packageManager
        var matches: MutableList<ResolveInfo> =packageManager.queryIntentActivities(i,0)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val apkURI = FileProvider.getUriForFile(activity!!, "com.gespl.bgv.provider", exelfile!!)
           // val fileURI = FileProvider.getUriForFile(activity!!, "$packageName.fileprovider", yourFile)
            // intent.setType("application/excel")
            i.putExtra(Intent.EXTRA_STREAM,apkURI)

        } else {
            // intent.setType("application/excel")
            i.putExtra(Intent.EXTRA_STREAM,Uri.fromFile(exelfile!!))
        }
        var best: ResolveInfo?=null;
        for (  info : ResolveInfo in matches)
            if (info.activityInfo.packageName.endsWith(".gm") ||
                info.activityInfo.name.toLowerCase().contains("gmail"))
                best = info;
        if (best != null)
            i.setClassName(best.activityInfo.packageName, best.activityInfo.name);

        startActivityForResult(i,400);

    }

    private fun takephato() {

        val result: Boolean = checkPermission()
        if (result){
            opencamera()
        }
    }
    private fun opencamera() {
        // Creating folders for Image
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_IMAGE_REQUEST)
        // val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        //takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(File(imageFolderPath, imageName)))
        // startActivityForResult(takePictureIntent, CAMERA_IMAGE_REQUEST )

    }
    private fun showPermissionAlert(permissions: Array<String>) {
        val alertBuilder = AlertDialog.Builder(activity!!)
        alertBuilder.setCancelable(true)
        alertBuilder.setTitle(resources.getString(R.string.app_name))
        alertBuilder.setMessage("Permession")
        alertBuilder.setPositiveButton(android.R.string.yes,
            DialogInterface.OnClickListener { dialog, which -> requestPermissions(permissions,REQUEST_CODE) })
        val alert = alertBuilder.create()
        alert.show()
    }
    private fun checkPermission(): Boolean {
        val currentAPIVersion = Build.VERSION.SDK_INT
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            val permissionsNotGranted = ArrayList<String>()

            for (permission in neededPermissions) {
                if (ContextCompat.checkSelfPermission(activity!!, permission) != PackageManager.PERMISSION_GRANTED) {
                    permissionsNotGranted.add(permission)
                }
            }
            if (permissionsNotGranted.size> 0) {
                var shouldShowAlert = false
                for (permission in permissionsNotGranted) {
                    shouldShowAlert = ActivityCompat.shouldShowRequestPermissionRationale(activity!!, permission)
                }
                if (shouldShowAlert) {
                    showPermissionAlert(permissionsNotGranted.toArray(arrayOfNulls<String>(permissionsNotGranted.size)))
                } else {
                    requestPermissions(permissionsNotGranted.toArray(arrayOfNulls<String>(permissionsNotGranted.size)),REQUEST_CODE)
                }
                return false
            }
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE -> {
                for (result in grantResults) {
                    if (result == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(activity, "Permession Denied", Toast.LENGTH_LONG).show()
                        return
                    }
                }

                opencamera()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === CAMERA_IMAGE_REQUEST) {
            val image = data!!.getExtras()!!.get("data") as Bitmap
            img.visibility=View.VISIBLE
            take_image.visibility=View.GONE
            ll.visibility=View.VISIBLE
            img.setImageBitmap(image)

            val imageFolderPath = Environment.getExternalStorageDirectory().toString() + "/GESPL"
            // Generating file name
            //  val imageName =+ ".jpg"
            val imageName = ((Math.random() * ((100 - 5) + 1))).toString()+".jpg"
            val imagesFolder = File(imageFolderPath,imageName)
            //  imagesFolder.mkdirs()
            val outputFile = File(Environment.getExternalStorageDirectory(), imageName)
            val extr = (Environment.getExternalStorageDirectory().toString() + File.separator + "GESPL")
            myPath = File(outputFile, imageName)
            myPath=outputFile
            var fos: FileOutputStream? = null
            try {
                fos = FileOutputStream(outputFile)
                image.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.flush()
                fos.close()
                MediaStore.Images.Media.insertImage(context!!.getContentResolver(), image, myPath!!.path, imageName)
            } catch (e: FileNotFoundException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            } catch (e: Exception) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }

        }
    }

    private fun createExcelSheet() {
        val Fnamexls = "excelSheet" + System.currentTimeMillis() + ".xls"
        val sdCard = Environment.getExternalStorageDirectory()
        val directory = File(sdCard.absolutePath + "/newfolder")
        directory.mkdirs()
        val file = File(directory, Fnamexls)
        exelfile=file
        val wbSettings = WorkbookSettings()
        wbSettings.locale = Locale("en", "EN")

        val workbook: WritableWorkbook
        try {

            workbook = Workbook.createWorkbook(file, wbSettings)
            //workbook.createSheet("Report", 0);
            val sheet = workbook.createSheet("First Sheet", 0)
            //  val label = Label(0, 2, "SECOND")
            val label0 = Label(0, 0, "COMP_ID")
            val label1 = Label(0, 1, SheardPreference.getSomeStringValue(activity!!,SheardParam.comp_id!!))
            val label3 = Label(1, 0, "STATION_CODE")
            val label4 = Label(1, 1, SheardPreference.getSomeStringValue(activity!!,SheardParam.log_stncode!!))
            val label5= Label(2,0,"Full Name")
            val label6= Label(2,1,SheardPreference.getSomeStringValue(activity!!,SheardParam.assoc_full_name!!))
            val label7= Label(3,0,"Father's Name")
            val label8= Label(3,1,SheardPreference.getSomeStringValue(activity!!,SheardParam.assoc_fathername!!))
            val label9= Label(4,0,"Date-OF_Birth")
            val label10= Label(4,1,SheardPreference.getSomeStringValue(activity!!,SheardParam.dob!!))
            val label11= Label(5,0,"Flat/Romm")
            val label12= Label(5,1,SheardPreference.getSomeStringValue(activity!!,SheardParam.flat!!))
            val label13= Label(6,0,"Floor")
            val label14= Label(6,1,SheardPreference.getSomeStringValue(activity!!,SheardParam.floor!!))
            val label15= Label(7,0,"Building/House &Address")
            val label16= Label(7,1,SheardPreference.getSomeStringValue(activity!!,SheardParam.address!!))
            val label17= Label(8,0,"Street name")
            val label18= Label(8,1,SheardPreference.getSomeStringValue(activity!!,SheardParam.street!!))
            val label19= Label(9,0,"Area Name")
            val label20= Label(9,1,SheardPreference.getSomeStringValue(activity!!,SheardParam.area!!))
            val label21= Label(10,0,"Pin Code")
            val label22= Label(10,1,SheardPreference.getSomeStringValue(activity!!,SheardParam.pin!!))
            val label23= Label(11,0,"District/Village/town/city")
            val label24= Label(11,1,SheardPreference.getSomeStringValue(activity!!,SheardParam.dist!!))
            val label25= Label(12,0,"State")
            val label26= Label(12,1,SheardPreference.getSomeStringValue(activity!!,SheardParam.state!!))
            val label27= Label(13,0,"Landmark")
            val label28= Label(13,1,SheardPreference.getSomeStringValue(activity!!,SheardParam.landmark!!))
            val label29= Label(14,0,"Mobile 1")
            val label30= Label(14,1,SheardPreference.getSomeStringValue(activity!!,SheardParam.mob1!!).toString())
            val label31= Label(15,0,"Mobile 2")
            val label32= Label(15,1,SheardPreference.getSomeStringValue(activity!!,SheardParam.mob2!!).toString())
            val label33= Label(16,0,"DL number")
            val label34= Label(16,1,SheardPreference.getSomeStringValue(activity!!,SheardParam.dl!!))
            val label35= Label(17,0,"DL year of issue")
            val label36= Label(17,1,SheardPreference.getSomeStringValue(activity!!,SheardParam.dlyear!!))
            val label37= Label(18,0,"DL Valid Year")
            val label38= Label(18,1,SheardPreference.getSomeStringValue(activity!!,SheardParam.dlvalidyear!!))
            val label39= Label(19,0,"DL issue state")
            val label40= Label(19,1,SheardPreference.getSomeStringValue(activity!!,SheardParam.dlstate!!))
            val label41= Label(20,0,"PAN")
            val label42= Label(20,1,SheardPreference.getSomeStringValue(activity!!,SheardParam.pan!!))
            val label43= Label(21,0,"Voter Id")
            val label44= Label(21,1,SheardPreference.getSomeStringValue(activity!!,SheardParam.v_id!!))
            try {
                // sheet.addCell(label)
                sheet.addCell(label1)
                sheet.addCell(label0)
                sheet.addCell(label4)
                sheet.addCell(label3)
                sheet.addCell(label5)
                sheet.addCell(label6)
                sheet.addCell(label7)
                sheet.addCell(label8)
                sheet.addCell(label9)
                sheet.addCell(label10)
                sheet.addCell(label11)
                sheet.addCell(label12)
                sheet.addCell(label13)
                sheet.addCell(label14)
                sheet.addCell(label15)
                sheet.addCell(label16)
                sheet.addCell(label17)
                sheet.addCell(label18)
                sheet.addCell(label19)
                sheet.addCell(label20)
                sheet.addCell(label21)
                sheet.addCell(label22)
                sheet.addCell(label23)
                sheet.addCell(label24)
                sheet.addCell(label25)
                sheet.addCell(label26)
                sheet.addCell(label27)
                sheet.addCell(label28)
               // sheet.addCell(label29)
                sheet.addCell(label29)
                sheet.addCell(label30)
                sheet.addCell(label31)
                sheet.addCell(label32)
                sheet.addCell(label33)
                sheet.addCell(label34)
                sheet.addCell(label35)
                sheet.addCell(label36)
                sheet.addCell(label37)
                sheet.addCell(label38)
                sheet.addCell(label39)
                sheet.addCell(label40)
                sheet.addCell(label41)
                sheet.addCell(label42)
                sheet.addCell(label43)
                sheet.addCell(label44)

            } catch (e: RowsExceededException) {
                e.printStackTrace()
            } catch (e: WriteException) {
                e.printStackTrace()
            }
            workbook.write()
            try {
                workbook.close()
            } catch (e: WriteException) {
                e.printStackTrace()
            }
            //val file = File(file.absolutePath)

            sendemail()

            //createExcel(excelSheet);
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

    }
}
