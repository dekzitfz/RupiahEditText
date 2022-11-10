package id.adiandrea.rupiahedittext

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import id.adiandrea.rupiahinput.databinding.LayoutTesBinding

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = LayoutTesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btn.setOnClickListener {
            Toast.makeText(this, binding.rupiah.value.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}