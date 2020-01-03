package id.adiandrea.rupiahedittext

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import java.text.NumberFormat
import java.util.*


class RupiahEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatEditText(context, attrs, defStyleAttr) {

    var editText: RupiahEditText = this

    fun init() {
        this.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                editText.removeTextChangedListener(this)
                if(s.toString().isNotEmpty()){
                    if(s.toString().substring(0,1) == "0"){
                        //angka pertama 0
                        if(s.toString().length > 1){
                            //panjang angka lebih dari 1
                            if(s.toString().substring(0,1) == "0" && s.toString().substring(1,2) != "0"){
                                //angka pertama 0 dan angka kedua tidak 0, hilangkan angka 0 dikiri
                                editText.setText(formatCurrency(StringBuilder(s.toString()).deleteCharAt(0).toString().toLong()))
                            }else if(s.toString().substring(0,1) != "0"){
                                //angka pertama tidak 0
                                editText.setText(formatCurrency(s.toString().toLong()))
                            }else{
                                //angka kedua 0
                                editText.setText("0")
                            }
                        }else{
                            //panjang angka adalah 1
                            editText.setText(formatCurrency(s.toString().toLong()))
                        }
                    }else{
                        //angka pertama tidak 0
                        val cleanString = s.toString().replace(".", "")
                        editText.setText(formatCurrency(cleanString.toLong()))
                    }
                }else{
                    editText.setText("0")
                }

                editText.text?.length?.let { editText.setSelection(it) }
                editText.addTextChangedListener(this)
            }

        })
    }

    private fun formatCurrency(amount: Long): String {
        val format = NumberFormat.getNumberInstance(Locale.GERMANY)
        return format.format(amount)
    }

}