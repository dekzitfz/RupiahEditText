package id.adiandrea.rupiahedittext

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatEditText
import java.text.NumberFormat
import java.util.Locale
import java.util.Objects

class RupiahEditText : AppCompatEditText {
    private val editText = this
    private var cursorPos = 0
    private var valueBefore = ""
    private var valueAfter = ""
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
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                Log.d("beforeTextChanged", "cursor position: ${editText.selectionStart}\nedittext length: ${editText.length()}")
                valueBefore = editText.text.toString()
                cursorPos = editText.selectionStart
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                Log.d("RupiahEditText onTextChanged", "start: $start\nbefore: $before\ncount: $count")
                editText.removeTextChangedListener(this)
                editText.setText(validateValue(s.toString()))
//                editText.setSelection(editText.length())

                editText.addTextChangedListener(this)
            }

            override fun afterTextChanged(s: Editable) {
                valueAfter = editText.text.toString()

                if(!isDeleting()){
                    //case for inputing numbers
                    if(cursorPos != (editText.length()-1)){
                        //last cursor pos is on the very left or middle text
                        if(cursorPos == 0){
                            //last cursor pos is on the very left
                            if(editText.text.toString()[0] == '0'){
                                //inputed 0 on the left
                                editText.setSelection(editText.length())
                            }else{
                                editText.setSelection(1)
                            }
                        }else{
                            //last cursor pos is on the middle text
                            if(!editText.text.toString().contains(".")){
                                //no dot delimiter
                                if(editText.length() == 1){
                                    editText.setSelection(editText.length())
                                }else{
                                    editText.setSelection(2)
                                }
                            }else{
                                /***
                                 * there is more than 3 digits
                                 * Logic:
                                 *
                                 * 1|34 cursor pos: 1
                                 * 1.2|34 cursor pos: 3
                                 *
                                 * 13|4 cursor pos: 2
                                 * 1.32|4 cursor pos: 4
                                 *
                                 * 9|99.999 cursor pos: 1
                                 * 9.2|99.999 cursor pos: 3
                                 *
                                 * 999.99|9 cursor pos: 6
                                 * 9.999.92|9 cursor pos: 8
                                 */
                                editText.setSelection(cursorPos + 2)
                            }
                        }
                    }else{
                        //last cursor pos is on the very right
                        editText.setSelection(editText.length())
                    }
                }else{
                    //case for deleting numbers
                    if(cursorPos != (editText.length()-1)){
                        //last cursor pos is on the very left or middle text
                        if(cursorPos == 0){
                            //last cursor pos is on the very left
                            editText.setSelection(0)
                        }else{
                            //last cursor pos is on the middle text
                            if(!valueBefore.contains(".")){
                                //no dot delimiter (max 3 digits)
                                /**
                                 * no dot delimiter (max 3 digits)
                                 * logic:
                                 *
                                 * 1|2 cursor pos: 1
                                 * |2 cursor pos: 0
                                 *
                                 * 12|3 cursor pos: 2
                                 * 1|3 cursor pos: 1
                                 */
                                editText.setSelection(cursorPos - 1)
                            }else{
                                /**
                                 * more than 3 digits
                                 * logic:
                                 *
                                 * 1.234| cursor pos: 5
                                 * 123| cursor pos: 3
                                 *
                                 * 1.23|4 cursor pos: 4
                                 * 12|4 cursor pos: 2
                                 *
                                 * 99.999.9|99 cursor pos: 8
                                 * 9.999.9|99 cursor pos: 7
                                 *
                                 * 1.234.5|67.890 cursor pos: 7
                                 * 123.4|67.890 cursor pos: 5
                                 */
                                if((cursorPos-2) == editText.length()){
                                    //deleting from very right
                                    editText.setSelection(editText.length())
                                }else{
                                    if(valueAfter.none { it == '.' }){
                                        /**
                                         * 1|.234 cursor pos: 1
                                         * 234 cursor pos: 0
                                         *
                                         * 1.2|34 cursor pos: 3
                                         * 1|34 cursor pos: 1
                                         *
                                         * 1.23|4 cursor pos: 4
                                         * 124 cursoir pos: 2
                                         */
                                        if(cursorPos == 1){
                                            editText.setSelection(cursorPos - 1)
                                        }else{
                                            editText.setSelection(cursorPos - 2)
                                        }
                                    }else{
                                        if(isDelimiterChanged()){
                                            editText.setSelection(cursorPos - 2)
                                        }else{
                                            editText.setSelection(cursorPos - 1)
                                        }
                                    }
                                }
                            }
                        }
                    }else{
                        //last cursor pos is on the very right
                        editText.setSelection(editText.length())
                    }
                }


                Log.d("afterTextChanged", "cursor position: ${editText.selectionStart}\n" +
                        "edittext length: ${editText.length()}")
            }
        })
    }

    private fun isDelimiterChanged(): Boolean {
        val delimiterCountBefore = valueBefore.filter { it == '.' }.length
        val delimiterCountAfter = valueAfter.filter { it == '.' }.length
        return delimiterCountBefore != delimiterCountAfter
    }

    private fun isDeleting(): Boolean = valueAfter.length < valueBefore.length

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