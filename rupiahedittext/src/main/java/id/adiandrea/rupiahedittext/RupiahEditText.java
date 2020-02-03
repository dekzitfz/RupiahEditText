package id.adiandrea.rupiahedittext;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class RupiahEditText extends AppCompatEditText {

    private RupiahEditText editText = RupiahEditText.this;

    public RupiahEditText(Context context) {
        super(context);
        init();
    }

    public RupiahEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RupiahEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public long getValue(){
        return Long.parseLong(Objects.requireNonNull(editText.getText()).toString().replace(".", ""));
    }

    public void init(){
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setText("0");
        editText.setRawInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(19)});

        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editText.removeTextChangedListener(this);
                if(!s.toString().equals("")){
                    if(s.toString().substring(0, 1).equals("0")){
                        //angka pertama 0
                        if(s.toString().length() > 1){
                            //panjang angka lebih dari 1
                            if(s.toString().substring(0, 1).equals("0") && s.toString().substring(1,2) != "0"){
                                //angka pertama 0 dan angka kedua tidak 0, hilangkan angka 0 dikiri
                                editText.setText(formatCurrency(Long.parseLong(new StringBuilder(s.toString()).deleteCharAt(0).toString())));
                            }else if(!s.toString().substring(0, 1).equals("0")){
                                //angka pertama tidak 0
                                editText.setText(formatCurrency(Long.parseLong(s.toString())));
                            }else{
                                //angka kedua 0
                                editText.setText("0");
                            }
                        }else{
                            //panjang angka adalah 1
                            editText.setText(formatCurrency(Long.parseLong(s.toString())));
                        }
                    }else{
                        //angka pertama tidak 0
                        String cleanString = s.toString().replace(".", "");
                        editText.setText(formatCurrency(Long.parseLong(cleanString)));
                    }
                }else{
                    editText.setText("0");
                }

                editText.setSelection(editText.length());
                editText.addTextChangedListener(this);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    String validateValue(String inputed){
        if(!inputed.equals("")){
            if(inputed.substring(0, 1).equals("0")){
                //angka pertama 0
                if(inputed.length() > 1){
                    //panjang angka lebih dari 1
                    if(inputed.substring(0, 1).equals("0") && !inputed.substring(1, 2).equals("0")){
                        //angka pertama 0 dan angka kedua tidak 0, hilangkan angka 0 dikiri
                        return formatCurrency(Long.parseLong(new StringBuilder(inputed).deleteCharAt(0).toString()));
                    }else if(!inputed.substring(0, 1).equals("0")){
                        //angka pertama tidak 0
                        return formatCurrency(Long.parseLong(inputed));
                    }else{
                        //angka kedua 0
                        return "0";
                    }
                }else{
                    //panjang angka adalah 1
                    return formatCurrency(Long.parseLong(inputed));
                }
            }else{
                //angka pertama tidak 0
                String cleanString = inputed.replace(".", "");
                return formatCurrency(Long.parseLong(cleanString));
            }
        }else{
            return "0";
        }
    }

    private String formatCurrency(Long amount) {
        NumberFormat format = NumberFormat.getNumberInstance(Locale.GERMANY);
        return format.format(amount);
    }
}
