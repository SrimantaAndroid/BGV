package com.gespl.bgv

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.gespl.bgv.utils.Alert
import com.gespl.bgv.utils.AppConstant
import com.gespl.bgv.utils.SheardParam
import com.gespl.bgv.utils.SheardPreference

class LogInActivity:AppCompatActivity() {
    var sp_selectloginid: Spinner?=null
    var et_password: EditText?=null
    var btn_next: Button?=null
    var select_name:String?=""
    var select_position:Int?=0

    var items = arrayOf("Select your login id","SURAJIT GIRI", "SANJU BERA", "BAPPA HATI", "SOUGATA PRADHAN", "GOUTAM DAS","ANUP SEKHAR","RAKTIM DASMAHAPATRA",
        "SAMIR METTA","DEBASISH SAMANTA","DEBASISH (DEBU)")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        SheardPreference.init(this)
        sp_selectloginid = findViewById(R.id.sp_selectloginid)
        et_password = findViewById(R.id.et_password)
        btn_next = findViewById(R.id.btn_next1)
        val adapter = ArrayAdapter(this, R.layout.liginid_layout, items)
        sp_selectloginid!!.adapter = adapter

        sp_selectloginid!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                select_name = items[position]
                select_position = position
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                select_position = 0
                select_name=""
            }
        }
        btn_next!!.setOnClickListener {
            val pass=et_password!!.text.toString()
            if (select_position!! >0) {
                if (!pass.equals("")){
                    if(select_name.equals("SURAJIT GIRI")){
                        if(pass.equals(AppConstant.pass_sur)){
                                 SheardPreference.setSomeStringValue(this,SheardParam.loginuserid!!,AppConstant.sur)
                                 SheardPreference.setSomeStringValue(this,SheardParam.log_stncode!!,AppConstant.stn_code_sur)
                                   SheardPreference.setSomeStringValue(this,SheardParam.comp_id!!,AppConstant.Comp_id_sur)


                            val intent=Intent(this,MainActivity::class.java)
                                   startActivity(intent)
                            finish()
                            }else
                            Alert.showalert(this, "Password name doesn't match with UserId")

                    }else  if(select_name.equals("SANJU BERA")){

                        if(pass.equals(AppConstant.pass_sanju)){
                            SheardPreference.setSomeStringValue(this,SheardParam.loginuserid!!,AppConstant.sanju)
                            SheardPreference.setSomeStringValue(this,SheardParam.log_stncode!!,AppConstant.stn_code_sanju)
                            SheardPreference.setSomeStringValue(this,SheardParam.comp_id!!,AppConstant.Comp_id_sanju)


                            val intent=Intent(this,MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else
                            Alert.showalert(this, "Password name doesn't match with UserId")
                    }
                   else if(select_name.equals("BAPPA HATI")){
                        if(pass.equals(AppConstant.pass_bappa)){
                            SheardPreference.setSomeStringValue(this,SheardParam.loginuserid!!,AppConstant.bappa)
                            SheardPreference.setSomeStringValue(this,SheardParam.log_stncode!!,AppConstant.stn_code_bappa)
                            SheardPreference.setSomeStringValue(this,SheardParam.comp_id!!,AppConstant.Comp_id_bappa)


                            val intent=Intent(this,MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else
                            Alert.showalert(this, "Password name doesn't match with UserId")

                    }else  if(select_name.equals("SOUGATA PRADHAN")){
                        if(pass.equals(AppConstant.pass_sou)){
                            SheardPreference.setSomeStringValue(this,SheardParam.loginuserid!!,AppConstant.sou)
                            SheardPreference.setSomeStringValue(this,SheardParam.log_stncode!!,AppConstant.stn_code_sou)
                            SheardPreference.setSomeStringValue(this,SheardParam.comp_id!!,AppConstant.Comp_id_sou)


                            val intent=Intent(this,MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else
                            Alert.showalert(this, "Password name doesn't match with UserId")
                    }
                    else if(select_name.equals("GOUTAM DAS")){
                        if(pass.equals(AppConstant.pass_gou)){
                            SheardPreference.setSomeStringValue(this,SheardParam.loginuserid!!,AppConstant.gou)
                            SheardPreference.setSomeStringValue(this,SheardParam.log_stncode!!,AppConstant.stn_code_gou)
                            SheardPreference.setSomeStringValue(this,SheardParam.comp_id!!,AppConstant.Comp_id_gou)



                            val intent=Intent(this,MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else
                            Alert.showalert(this, "Password name doesn't match with UserId")

                    }else  if(select_name.equals("ANUP SEKHAR")){
                        if(pass.equals(AppConstant.pass_anup)){
                            SheardPreference.setSomeStringValue(this,SheardParam.loginuserid!!,AppConstant.anup)
                            SheardPreference.setSomeStringValue(this,SheardParam.log_stncode!!,AppConstant.stn_code_anup)
                            SheardPreference.setSomeStringValue(this,SheardParam.comp_id!!,AppConstant.Comp_id_anup)



                            val intent=Intent(this,MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else
                            Alert.showalert(this, "Password name doesn't match with UserId")
                    }
                    else if(select_name.equals("RAKTIM DASMAHAPATRA")){
                        if(pass.equals(AppConstant.pass_raktim)){
                            SheardPreference.setSomeStringValue(this,SheardParam.loginuserid!!,AppConstant.raktim)
                            SheardPreference.setSomeStringValue(this,SheardParam.log_stncode!!,AppConstant.stn_code_raktim)
                            SheardPreference.setSomeStringValue(this,SheardParam.comp_id!!,AppConstant.Comp_id_raktim)



                            val intent=Intent(this,MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else
                            Alert.showalert(this, "Password name doesn't match with UserId")

                    }else  if(select_name.equals("SAMIR METTA")){

                        if(pass.equals(AppConstant.pass_samir)){
                            SheardPreference.setSomeStringValue(this,SheardParam.loginuserid!!,AppConstant.samir)
                            SheardPreference.setSomeStringValue(this,SheardParam.log_stncode!!,AppConstant.stn_code_samir)
                            SheardPreference.setSomeStringValue(this,SheardParam.comp_id!!,AppConstant.Comp_id_samir)



                            val intent=Intent(this,MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else
                            Alert.showalert(this, "Password name doesn't match with UserId")
                    }
                    else if(select_name.equals("DEBASISH SAMANTA")){
                        if(pass.equals(AppConstant.pass_debas)){
                            SheardPreference.setSomeStringValue(this,SheardParam.loginuserid!!,AppConstant.debas)
                            SheardPreference.setSomeStringValue(this,SheardParam.log_stncode!!,AppConstant.stn_code_debas)
                            SheardPreference.setSomeStringValue(this,SheardParam.comp_id!!,AppConstant.Comp_id_debas)



                            val intent=Intent(this,MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else
                            Alert.showalert(this, "Password name doesn't match with UserId")

                    }else  if(select_name.equals("DEBASISH (DEBU)")){
                        if(pass.equals(AppConstant.pass_debu)){
                            SheardPreference.setSomeStringValue(this,SheardParam.loginuserid!!,AppConstant.debu)
                            SheardPreference.setSomeStringValue(this,SheardParam.log_stncode!!,AppConstant.stn_code_debu)
                            SheardPreference.setSomeStringValue(this,SheardParam.comp_id!!,AppConstant.Comp_id_debu)



                            val intent=Intent(this,MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }else
                            Alert.showalert(this, "Password name doesn't match with UserId")
                    }


                }else
                    Alert.showalert(this, "Please enter password")

            } else {
                Alert.showalert(this, "Please select your login-id")
            }
        }
    }
}