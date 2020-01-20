package com.gespl.bgv


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.gespl.bgv.utils.SheardParam
import com.gespl.bgv.utils.SheardPreference

class ViewFragment : Fragment() {
    var tv_compid_val: TextView?=null
    var tv_station_val: TextView?=null
    var tv_fullname_val: TextView?=null
    var tv_father_val: TextView?=null
    var tv_dob_val: TextView?=null
    var tv_address_val: TextView?=null
    var tv_mob_val: TextView?=null
    var tv_dl_val: TextView?=null
    var tv_pan_val: TextView?=null
    var tv_voter_val: TextView?=null
    var btn_edit:TextView?=null
    var btn_send:TextView?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View=LayoutInflater.from(activity).inflate(R.layout.fragment_view,null);
        viewbinds(view)

        btn_edit!!.setOnClickListener {
           // val action=ViewFragmentDirections.actionViewFragmentToPersonaldetails()
            findNavController().popBackStack()
        }
        btn_send!!.setOnClickListener {

            val action1=ViewFragmentDirections.actionViewFragmentToImageFragment()
            findNavController().navigate(action1);
        }
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setvalue()
    }

    private fun setvalue() {
        val fulladdfress=SheardPreference.getSomeStringValue(activity!!,SheardParam.address!!)+","+SheardPreference.getSomeStringValue(activity!!,SheardParam.dist!!)+
                ","+SheardPreference.getSomeStringValue(activity!!,SheardParam.area!!)+","+SheardPreference.getSomeStringValue(activity!!,SheardParam.dist!!)+
                ","+SheardPreference.getSomeStringValue(activity!!,SheardParam.state!!)+","+SheardPreference.getSomeStringValue(activity!!,SheardParam.pin!!)
        tv_compid_val!!.setText(SheardPreference.getSomeStringValue(activity!!,SheardParam.comp_id!!))
        tv_station_val!!.setText(SheardPreference.getSomeStringValue(activity!!,SheardParam.log_stncode!!))
        tv_fullname_val!!.setText(SheardPreference.getSomeStringValue(activity!!,SheardParam.assoc_full_name!!))
        tv_father_val!!.setText(SheardPreference.getSomeStringValue(activity!!,SheardParam.assoc_fathername!!))
        tv_dob_val!!.setText(SheardPreference.getSomeStringValue(activity!!,SheardParam.dob!!))
        tv_address_val!!.setText(fulladdfress)
        tv_mob_val!!.setText(SheardPreference.getSomeStringValue(activity!!,SheardParam.mob1!!))
        tv_dl_val!!.setText(SheardPreference.getSomeStringValue(activity!!,SheardParam.dl!!))
        tv_pan_val!!.setText(SheardPreference.getSomeStringValue(activity!!,SheardParam.pan!!))
        tv_voter_val!!.setText(SheardPreference.getSomeStringValue(activity!!,SheardParam.v_id!!))

    }

    private fun viewbinds(view: View) {
        tv_compid_val=view.findViewById(R.id.tv_compid_val)
        tv_station_val=view.findViewById(R.id.tv_station_val)
        tv_fullname_val=view.findViewById(R.id.tv_fullname_val)
        tv_father_val=view.findViewById(R.id.tv_father_val)
        tv_dob_val=view.findViewById(R.id.tv_dob_val)
        tv_address_val=view.findViewById(R.id.tv_address_val)
        tv_mob_val=view.findViewById(R.id.tv_mob_val)
        tv_dl_val=view.findViewById(R.id.tv_dl_val)
        tv_pan_val=view.findViewById(R.id.tv_pan_val)
        tv_voter_val=view.findViewById(R.id.tv_voter_val)
        btn_edit=view.findViewById(R.id.btn_edit)
        btn_send=view.findViewById(R.id.btn_send)

    }


}
