package es.upm.btb.helloworldkt

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import es.upm.btb.helloworldkt.databinding.ActivitySecondBinding


class SecondActivity : AppCompatActivity() {
    private val TAG = "btaSecondActivity"
    private lateinit var binding: ActivitySecondBinding
    private lateinit var adapter: BarAdapter
    private lateinit var latestLocation: Location

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = BarAdapter(this)
        binding.listview.adapter = adapter

        binding.listview.isClickable = true
        binding.listview.adapter = BarAdapter(this)

        binding.listview.setOnItemClickListener { parent, view, position, id ->
            Toast.makeText(this, "Clicked on ${BarManager.barList[position]}", Toast.LENGTH_SHORT)
                .show()
            BarManager.barList[position].isChecked = !BarManager.barList[position].isChecked

            if (BarManager.barList[position].isChecked) {
                view.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.light_green))
            } else {
                view.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.white))
            }

        }
    }
}
