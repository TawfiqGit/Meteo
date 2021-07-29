package com.tawfiq.meteo.ui.weather.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.DialogFragment
import com.tawfiq.meteo.R

class CityDialogFragment : DialogFragment(){

    interface CityDialogFragmentListener{
        fun onDialogPositive(cityName : String)
        fun onDialogNegatif()
    }

    var listener : CityDialogFragmentListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context)
        val view = layoutInflater.inflate(R.layout.custom_dialog_add_city, null) as View
        val autoCompleteTextView  = view.findViewById(R.id.autoCompleteAddCity) as AutoCompleteTextView
        val listcity: Array<out String> = resources.getStringArray(R.array.list_city)

        with(builder){
            setView(view)
            ArrayAdapter(context, android.R.layout.simple_list_item_1, listcity)
                .also { adapter -> autoCompleteTextView.setAdapter(adapter) } //also specifie avec la valeur comme argument et le renvoie

            setTitle(getString(R.string.dialog_ajouter_une_ville))

            setPositiveButton(getString(R.string.dialog_ajouter)) { _, _ ->
                if (autoCompleteTextView.text.isNotEmpty())
                    listener?.onDialogPositive(autoCompleteTextView.text.toString())
            }
            setNegativeButton(getString(R.string.dialog_annuler)) { dialog, _ ->
                dialog.cancel()
                listener?.onDialogNegatif()
            }
        }
        return builder.create()
    }
}
