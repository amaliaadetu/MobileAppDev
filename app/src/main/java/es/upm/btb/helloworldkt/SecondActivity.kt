package es.upm.btb.helloworldkt

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
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
                view.setBackgroundColor(
                    ContextCompat.getColor(
                        applicationContext,
                        R.color.light_green
                    )
                )
            } else {
                view.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.white))
            }

        }


        

        val navView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        navView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bars -> {
                    startActivity(Intent(this, SecondActivity::class.java))
                    true
                }
                R.id.map -> {
                    startActivity(Intent(this, OpenStreetMapActivity::class.java))
                    true
                }
                else -> false
            }
        }

//        binding.bottomNavigationView.setOnItemSelectedListener {
//            when (it.itemId) {
//                R.id.map -> startIntentMap()
//
//                else -> {
//
//                }
//
//            }
//            true
//        }

//        val barNav: Button = findViewById(R.id.map)
//        barNav.setOnClickListener {
//            if (latestLocation != null) {
//                val intent = Intent(this, OpenStreetMapActivity::class.java)
//                val bundle = Bundle()
//                bundle.putParcelable("location", latestLocation)
//                intent.putExtra("locationBundle", bundle)
//                startActivity(intent)
//            }else{
//                Log.e(TAG, "Location not set yet.")
//            }
//        }


    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.bottom_nav, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.bars -> {
//                startActivity(Intent(this, SecondActivity::class.java))
//                true
//            }
//            R.id.map -> {
//                startActivity(Intent(this, OpenStreetMapActivity::class.java))
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
//        fun startIntentMap(): Boolean {
//        val intent = Intent(this, OpenStreetMapActivity::class.java)
//                val bundle = Bundle()
//                bundle.putParcelable("location", latestLocation)
//                intent.putExtra("locationBundle", bundle)
//                startActivity(intent)
//
//            return true
//        }


}
