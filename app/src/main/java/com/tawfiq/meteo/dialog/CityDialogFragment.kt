package com.tawfiq.meteo.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class CityDialogFragment : DialogFragment(){

    interface CityDialogFragmentListener{
        fun onDialogPositive(cityName : String)
        fun onDialogNegatif()
    }

    var listener : CityDialogFragmentListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context)
        val input = EditText(context)

        with(input){
            inputType = InputType.TYPE_CLASS_TEXT
            hint = "Paris, Chennai...."
        }

        with(builder){
            setTitle("Ajouter une ville")
            setView(input)
            setPositiveButton("Ajouter",DialogInterface.OnClickListener{
                _, _ -> listener?.onDialogPositive(input.text.toString())
            })

            setNegativeButton("Supprimer",DialogInterface.OnClickListener{ dialog, _ ->
                dialog.cancel()
                listener?.onDialogNegatif()
            })
        }
        return builder.create()
    }
}
