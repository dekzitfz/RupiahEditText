package id.adiandrea.rupiahedittext

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import id.adiandrea.rupiahinput.R
import kotlinx.android.synthetic.main.layout_tes.*

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_tes)

        btn.setOnClickListener {
            Toast.makeText(this, rupiah.value.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}