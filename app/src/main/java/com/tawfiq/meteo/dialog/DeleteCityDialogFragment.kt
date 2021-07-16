package com.tawfiq.meteo.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.tawfiq.meteo.R

class DeleteCityDialogFragment : DialogFragment(){

    interface DeleteCityDialogFragmentListener{
        fun onDialogPositive()
        fun onDialogNegatif()
    }

    var listener : DeleteCityDialogFragmentListener? = null
    lateinit var cityName : String

    companion object{
        const val EXTRA_CITY_FRAGMENT = "com.tawfiq.meteo.extra.EXTRA_CITY_NAME"

        fun newInstance(city :String) : DeleteCityDialogFragment{
            val fragment = DeleteCityDialogFragment()
            fragment.arguments = Bundle().apply {
                putString(EXTRA_CITY_FRAGMENT,city)
            }
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cityName = requireArguments().getString(EXTRA_CITY_FRAGMENT).toString()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context)

        with(builder){
            setTitle("Voulez vous vraiment supprimer $cityName ?")

            setPositiveButton(getString(R.string.dialog_supprimer)) { _, _ ->
                listener?.onDialogPositive()}

            setNegativeButton(getString(R.string.dialog_annuler)) { dialog, _ ->
                dialog.cancel()
                listener?.onDialogNegatif()
            }
        }
        return builder.create()
    }
}