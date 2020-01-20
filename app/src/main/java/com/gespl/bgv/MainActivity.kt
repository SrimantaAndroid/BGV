package com.gespl.bgv

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import jxl.Workbook
import jxl.WorkbookSettings
import jxl.write.Label
import jxl.write.WritableWorkbook
import jxl.write.WriteException
import jxl.write.biff.RowsExceededException
import java.io.File
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class MainActivity:AppCompatActivity() {
    var exelfile:File?=null
    var permissions = arrayOf<String>(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host)
        checkPermissions()
    }

    private fun checkPermissions(): Boolean {
        var result: Int
        val listPermissionsNeeded=ArrayList<String>()
        for (p in permissions) {
            result = ContextCompat.checkSelfPermission(this, p)
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p)
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toTypedArray(), 100)
            return false
        }
        return true
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        if (requestCode == 100) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
            return
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
            val a = 25
            workbook = Workbook.createWorkbook(file, wbSettings)
            //workbook.createSheet("Report", 0);
            val sheet = workbook.createSheet("First Sheet", 0)
            //  val label = Label(0, 2, "SECOND")
            val label0 = Label(0, 0, "Name")
            val label1 = Label(0, 1, "Avijit Giri")
            val label3 = Label(1, 0, "Age")
            val label4 = Label(1, 1, a.toString())
            val label5= Label(2,0,"DoB")
            val label6= Label(2,1,"12/12/12")
            val label7= Label(3,0,"c/o")
            val label8= Label(3,1,"G.  Giri")
            val label9= Label(4,0,"Address1 ")
            val label10= Label(4,1,"Ramchandrapur")
            val label11= Label(5,0,"Address 2")
            val label12= Label(5,1,"Burari hut")
            val label13= Label(6,0,"p.s")
            val label14= Label(6,1,"Kolaghat")
            val label15= Label(7,0,"Dist")
            val label16= Label(7,1,"Purba medinipur")
            val label17= Label(8,0,"Pin")
            val label18= Label(8,1,"721137")
            val label19= Label(9,0,"Mob")
            val label20= Label(9,1,"9800638989")
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



            //createExcel(excelSheet);
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

    }
}