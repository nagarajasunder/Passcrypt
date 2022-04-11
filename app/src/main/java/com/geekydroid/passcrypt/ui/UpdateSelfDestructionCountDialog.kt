package com.geekydroid.passcrypt.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import com.geekydroid.passcrypt.R
import com.geekydroid.passcrypt.listeners.GenericOnClickListener

class UpdateSelfDestructionCountDialog(
    private var pickerValue: Int = 0,
    private val clickListener: GenericOnClickListener<Any>
) : DialogFragment() {

    private lateinit var numberPicker: NumberPicker
    private lateinit var button: Button
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.update_self_destruction_count_dialog, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        numberPicker = view.findViewById(R.id.number_picker)
        button = view.findViewById(R.id.btn_update)
        numberPicker.minValue = 3
        numberPicker.maxValue = 10
        numberPicker.displayedValues = arrayOf("3", "4", "5", "6", "7", "8", "9", "10")
        numberPicker.value = pickerValue

        numberPicker.setOnValueChangedListener { numberPicker, _, _ ->
            pickerValue = numberPicker.value
        }

        button.setOnClickListener {
            clickListener.onClick(pickerValue)
            dismiss()
        }
    }

}