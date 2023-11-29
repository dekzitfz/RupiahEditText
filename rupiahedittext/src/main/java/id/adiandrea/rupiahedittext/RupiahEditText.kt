package id.adiandrea.rupiahedittext

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import java.text.NumberFormat
import java.util.Locale
import java.util.Objects

class RupiahEditText : AppCompatEditText {
    private val editText = this

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    val value: Long
        get() = Objects.requireNonNull(editText.text).toString().replace(".", "").toLong()

    private fun init() {
        editText.inputType = InputType.TYPE_CLASS_NUMBER
        editText.setText("0")
        editText.setRawInputType(InputType.TYPE_CLASS_NUMBER)
        editText.filters = arrayOf<InputFilter>(LengthFilter(19))
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                editText.removeTextChangedListener(this)
                editText.setText(validateValue(s.toString()))
                editText.setSelection(editText.length())
                editText.addTextChangedListener(this)
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    fun validateValue(inputed: String): String {
        return if (inputed != "") {
            if (inputed[0] == '0') {
                //angka pertama 0
                if (inputed.length > 1) {
                    //panjang angka lebih dari 1
                    if (inputed[0] == '0' && inputed[1] != '0') {
                        //angka pertama 0 dan angka kedua tidak 0, hilangkan angka 0 dikiri
                        formatCurrency(
                            StringBuilder(inputed).deleteCharAt(0).toString().toLong()
                        )
                    } else if (inputed[0] != '0') {
                        //angka pertama tidak 0
                        formatCurrency(inputed.toLong())
                    } else {
                        //angka kedua 0
                        "0"
                    }
                } else {
                    //panjang angka adalah 1
                    formatCurrency(inputed.toLong())
                }
            } else {
                //angka pertama tidak 0
                val cleanString = inputed.replace(".", "")
                formatCurrency(cleanString.toLong())
            }
        } else {
            "0"
        }
    }

    private fun formatCurrency(amount: Long): String {
        val format = NumberFormat.getNumberInstance(Locale.GERMANY)
        return format.format(amount)
    }
}