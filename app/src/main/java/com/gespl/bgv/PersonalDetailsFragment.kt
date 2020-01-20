package com.gespl.bgv


import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.gespl.bgv.utils.Alert
import com.gespl.bgv.utils.AppConstant
import com.gespl.bgv.utils.SheardParam
import com.gespl.bgv.utils.SheardPreference
import java.util.prefs.Preferences
import android.widget.DatePicker
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Handler
import java.util.*
import kotlin.collections.ArrayList

import java.text.SimpleDateFormat


class PersonalDetailsFragment : Fragment() {
   var sp_dlyear:Spinner?=null
    var sp_dlstate:Spinner?=null
    var et_compid:EditText?=null
    var et_stn_code:EditText?=null
    var et_associatefull:EditText?=null
   // var til_assofathename:EditText?=null
    var et_assfather:EditText?=null
    var et_roomdoor:EditText?=null
    var et_associateflore:EditText?=null
    var et_associateaddress:EditText?=null
    var et_associatestreetname:EditText?=null
    var et_associatearaename:EditText?=null
    var et_associatepin:EditText?=null
    var et_associatevillage:EditText?=null
    var et_associatesate:EditText?=null
    var et_associatelandmark:EditText?=null
    var et_associatevmobile1:EditText?=null
    var et_associatevmobile2:EditText?=null
    var et_associatedlnumber:EditText?=null
    var et_associatedlpan:EditText?=null
    var et_associatedvoterid:EditText?=null
    var tv_dateofbirth:TextView?=null
    var sp_validuptoyear:Spinner?=null
    var btn_next:Button?=null
    var btn_clear:Button?=null
    var tv_issuedyaer:TextView?=null
    var tv_validyear:TextView?=null
    var tvissuedstate:TextView?=null


    var years=ArrayList<String>()
    var vaildyears=ArrayList<String>()
    var state_name:String?=""
    var _year:String?=""
    var validyeat:String?=""
    var bod:String?=""
    var states= arrayOf("Select State","Andra Pradesh","Arunachal Pradesh" ,
           "Assam", "Bihar", "Chhattisgarh", "Goa", "Gujarat", "Haryana", "Himachal Pradesh",
            "Jammu and Kashmir", "Jharkhand", "Karnataka", "Kerala", "Madya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Kohima", "Orissa", "Punjab",
            "Rajasthan", "Sikkim", "Tamil Nadu", "Telagana", "Tripura", "Uttaranchal", "Uttar Pradesh", "West Bengal")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view:View=LayoutInflater.from(activity).inflate(R.layout.fragment_personal_details,null)

        viewbinds(view)
        setvalue();

        years.add("Issued year")
        vaildyears.add("valid upto")
        for (i in 1980 until 2021){
            years.add(i.toString())
        }

        for (i in 1980 until 2041){
            vaildyears.add(i.toString())
        }
        val adapter = ArrayAdapter(activity!!, R.layout.liginid_layout, years.toArray())
        sp_dlyear!!.adapter = adapter!!

        val validadapter = ArrayAdapter(activity!!, R.layout.liginid_layout, vaildyears.toArray())
        sp_validuptoyear!!.adapter = validadapter!!


        val stateadapter = ArrayAdapter(activity!!, R.layout.liginid_layout, states)
        sp_dlstate!!.adapter = stateadapter

        btn_clear!!.setOnClickListener {
            val alertDialog: AlertDialog.Builder= AlertDialog.Builder(activity!!)
            alertDialog.setTitle(activity!!.resources.getString(R.string.app_name))
            alertDialog.setMessage("Are you want to clear all data?")

            alertDialog.setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which ->
                    claerallfeild()
                })
            alertDialog.setNegativeButton("No", DialogInterface.OnClickListener{ dialog, which ->
                dialog.dismiss()
            })
            val dialog = alertDialog.create()
            // Display the alert dialog on interface
            dialog.show()

        }

        btn_next!!.setOnClickListener {

            val alertDialog: AlertDialog.Builder= AlertDialog.Builder(activity!!)
            alertDialog.setTitle(activity!!.resources.getString(R.string.app_name))
            alertDialog.setMessage("Are you want to Save?")

            alertDialog.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                   checkblank()

                })
            alertDialog.setNegativeButton("No", DialogInterface.OnClickListener{ dialog, which ->
                dialog.dismiss()
            })
            val dialog = alertDialog.create()

            dialog.show()

        }

        tv_dateofbirth!!.setOnClickListener {
               selectdateofbith()
        }

        sp_dlyear!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                _year = years.get(position)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        sp_dlstate!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                state_name = states[position]

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        sp_validuptoyear!!.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                validyeat=vaildyears.get(p2)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!bod.equals("")){
            tv_dateofbirth!!.setText(bod)
        }
    }
    private fun selectdateofbith() {
        val c = Calendar.getInstance()
        val mYear = c.get(Calendar.YEAR)
        val mMonth = c.get(Calendar.MONTH)
        val mDay = c.get(Calendar.DAY_OF_MONTH)


        val datePickerDialog = DatePickerDialog(activity!!,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                val sdf = SimpleDateFormat("dd-MMM-yyyy")
                c.set(year, monthOfYear, dayOfMonth)
                val dateString = sdf.format(c.getTime())
                tv_dateofbirth!!.setText(dateString)
                bod=dateString
            }, mYear, mMonth, mDay
        )
        datePickerDialog.show()
    }

    private fun checkblank() {
        if(!et_associatefull!!.text.toString().equals("")){
            if (!et_assfather!!.text.toString().equals("")){
                if (!et_associateaddress!!.text.toString().equals("")) {
                    if (!et_associatearaename!!.text.toString().equals("")) {
                        if (!et_associatepin!!.text.toString().equals("")) {
                            if (!et_associatevillage!!.text.toString().equals("")) {
                                if (!et_associatesate!!.text.toString().equals("")) {
                                    if (!et_associatelandmark!!.text.toString().equals("")) {
                                        if (!et_associatevmobile1!!.text.toString().equals("")) {
                                            if (!_year.equals("")) {
                                                if (!validyeat.equals("")) {
                                                    if (!et_associatedvoterid!!.text.toString().equals("")) {
                                                           savevalue()
                                                    }else
                                                        Alert.showalert(activity!!,"Please Enter Your Voter-Id.")

                                                }else
                                                    Alert.showalert(activity!!,"Please Select DL valid year.")
                                            }else
                                                Alert.showalert(activity!!,"Please Select Dl Issued year.")
                                        }else
                                            Alert.showalert(activity!!,"Please Enter Mob no.")
                                    }else
                                        Alert.showalert(activity!!,"Please Enter Landmark name")
                                }else
                                    Alert.showalert(activity!!,"Please Enter State name")
                            }
                            else
                                Alert.showalert(activity!!,"Please Enter village name")
                        }else
                            Alert.showalert(activity!!,"Please Enter pin.")
                    }
                    else
                        Alert.showalert(activity!!,"Please Enter area name.")
                }
                else
                    Alert.showalert(activity!!,"Please Enter Address")
            }else
                Alert.showalert(activity!!,"Please Enter father name.")
        }else
            Alert.showalert(activity!!,"Please Enter full name.")
    }

    private fun savevalue() {
          SheardPreference.setSomeStringValue(activity!!,SheardParam.assoc_full_name,et_associatefull!!.text.toString())
          SheardPreference.setSomeStringValue(activity!!,SheardParam.assoc_fathername,et_assfather!!.text.toString())
          SheardPreference.setSomeStringValue(activity!!,SheardParam.flat,et_roomdoor!!.text.toString())
        SheardPreference.setSomeStringValue(activity!!,SheardParam.dob,tv_dateofbirth!!.text.toString())
          SheardPreference.setSomeStringValue(activity!!,SheardParam.floor,et_associateflore!!.text.toString())
       // SheardPreference.setSomeStringValue(activity!!,SheardParam.building,et_associateaddress)
        SheardPreference.setSomeStringValue(activity!!,SheardParam.address,et_associateaddress!!.text.toString())
        SheardPreference.setSomeStringValue(activity!!,SheardParam.street,et_associatestreetname!!.text.toString())
        SheardPreference.setSomeStringValue(activity!!,SheardParam.area,et_associatearaename!!.text.toString())
        SheardPreference.setSomeStringValue(activity!!,SheardParam.pin,et_associatepin!!.text.toString())
        SheardPreference.setSomeStringValue(activity!!,SheardParam.dist,et_associatevillage!!.text.toString())
        SheardPreference.setSomeStringValue(activity!!,SheardParam.state,et_associatesate!!.text.toString())
        SheardPreference.setSomeStringValue(activity!!,SheardParam.landmark,et_associatelandmark!!.text.toString())
        SheardPreference.setSomeStringValue(activity!!,SheardParam.mob1,et_associatevmobile1!!.text.toString())
        SheardPreference.setSomeStringValue(activity!!,SheardParam.mob2,et_associatevmobile2!!.text.toString())
        SheardPreference.setSomeStringValue(activity!!,SheardParam.dl,et_associatedlnumber!!.text.toString())
        SheardPreference.setSomeStringValue(activity!!,SheardParam.dlyear,_year!!)
        SheardPreference.setSomeStringValue(activity!!,SheardParam.dlvalidyear,validyeat!!)
        SheardPreference.setSomeStringValue(activity!!,SheardParam.dlstate,state_name!!)
        SheardPreference.setSomeStringValue(activity!!,SheardParam.pan,et_associatedlpan!!.text.toString())
        SheardPreference.setSomeStringValue(activity!!,SheardParam.v_id,et_associatedvoterid!!.text.toString())


      //  android.os.Handler().postDelayed(Runnable {
            val action=PersonalDetailsFragmentDirections.actionPersonaldetailsToViewFragment()
            findNavController().navigate(action)

     //   },3000)





    }

    private fun claerallfeild() {
        et_associatefull!!.setText("")
        et_assfather!!.setText("")
        et_associateflore!!.setText("")
        et_associateaddress!!.setText("")
        et_associatestreetname!!.setText("")
        et_associatearaename!!.setText("")
        et_associatevillage!!.setText("")
        et_associatepin!!.setText("")
        et_associatesate!!.setText("")
        et_associatelandmark!!.setText("")
        et_associatevmobile1!!.setText("")
        et_associatevmobile2!!.setText("")
        et_associatedlnumber!!.setText("")
        et_associatedlpan!!.setText("")
        et_associatedvoterid!!.setText("")
        et_roomdoor!!.setText("")


    }

    private fun setvalue() {
        et_stn_code!!.setText(SheardPreference.getSomeStringValue(activity!!, SheardParam.log_stncode!!))
        et_compid!!.setText(SheardPreference.getSomeStringValue(activity!!,SheardParam.comp_id!!))
        et_stn_code!!.isEnabled=false
        et_compid!!.isEnabled=false

    }

    private fun viewbinds(view: View) {
        sp_dlyear=view.findViewById(R.id.sp_dlyear)
        sp_dlstate=view.findViewById(R.id.sp_dlstate)
        et_compid=view.findViewById(R.id.et_compid)
        et_stn_code=view.findViewById(R.id.et_stn_code)
        et_associatefull=view.findViewById(R.id.et_associatefull)
        et_assfather=view.findViewById(R.id.et_assfather)
        et_associateflore=view.findViewById(R.id.et_associateflore)
        et_associateaddress=view.findViewById(R.id.et_associateaddress)
        et_associatestreetname=view.findViewById(R.id.et_associatestreetname)
        et_associatearaename=view.findViewById(R.id.et_associatearaename)
        et_associatevillage=view.findViewById(R.id.et_associatevillage)
        et_associatepin=view.findViewById(R.id.et_associatepin)
        et_associatesate=view.findViewById(R.id.et_associatesate)
        et_associatelandmark=view.findViewById(R.id.et_associatelandmark)
        et_associatevmobile1=view.findViewById(R.id.et_associatevmobile1)
        et_associatevmobile2=view.findViewById(R.id.et_associatevmobile2)
        et_associatedlnumber=view.findViewById(R.id.et_associatedlnumber)
        et_associatedlpan=view.findViewById(R.id.et_associatedlpan)
        et_associatedvoterid=view.findViewById(R.id.et_associatedvoterid)
        et_roomdoor=view.findViewById(R.id.et_roomdoor)
        btn_clear=view.findViewById(R.id.btn_clear)
        btn_next=view.findViewById(R.id.btn_next)
        tv_dateofbirth=view.findViewById(R.id.tv_dateofbirth)
        sp_validuptoyear=view.findViewById(R.id.sp_validuptoyear)

        tvissuedstate=view.findViewById(R.id.tvissuedstate)
        tv_issuedyaer=view.findViewById(R.id.tv_issuedyaer)
        tv_validyear=view.findViewById(R.id.tv_validyear)
    }


}
