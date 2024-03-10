package es.upm.btb.helloworldkt

import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.upm.btb.helloworldkt.databinding.ActivityMainBinding
import es.upm.btb.helloworldkt.databinding.ActivitySecondBinding
import java.io.IOException


class SecondActivity : AppCompatActivity() {
    private val TAG = "btaSecondActivity"
    private lateinit var binding : ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.listview.isClickable = true
        binding.listview.adapter = BarAdapter(this)
//        binding.listview.setOnItemClickListener { parent, view, position, id ->

//        }
//        val value = intent.getStringExtra("KEY")
//
//        Log.d(TAG, "onCreate: The activity is being created.");
//
//        val bundle = intent.getBundleExtra("locationBundle")
//        val location: Location? = bundle?.getParcelable("location")
//
//        if (location != null) {
//            Log.i(TAG, "onCreate: Location["+location.altitude+"]["+location.latitude+"]["+location.longitude+"][")
//        };
//
//        val buttonNext: Button = findViewById(R.id.secondNextButton)
//        buttonNext.setOnClickListener {
//            val intent = Intent(this, ThirdActivity::class.java)
//            startActivity(intent)
//        }
//
//        val buttonPrevious: Button = findViewById(R.id.secondPreviousButton)
//        buttonPrevious.setOnClickListener {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }

    }
}