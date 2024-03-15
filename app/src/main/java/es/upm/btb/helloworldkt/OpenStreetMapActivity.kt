package es.upm.btb.helloworldkt

import android.graphics.drawable.Drawable
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.*


class OpenStreetMapActivity : AppCompatActivity() {
    private val TAG = "btaOpenStreetMapActivity"
    private lateinit var map: MapView

    var newList: MutableList<Bar> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_street_map)

        Log.d(TAG, "onCreate: The activity is being created.");

        val bundle = intent.getBundleExtra("locationBundle")
        val location: Location? = bundle?.getParcelable("location")

        if (location != null) {
            Log.i(
                TAG,
                "onCreate: Location[" + location.altitude + "][" + location.latitude + "][" + location.longitude + "]["
            )

            Configuration.getInstance()
                .load(applicationContext, getSharedPreferences("osm", MODE_PRIVATE))

            map = findViewById(R.id.map)
            map.setTileSource(TileSourceFactory.MAPNIK)

            map.controller.setZoom(15.0)

            var startPoint = GeoPoint(location.latitude, location.longitude)
            map.controller.setCenter(startPoint)

            addMarker(startPoint, "My current location")

            addBarMarkers()
            Log.i(TAG, "before quickestPath")

            val visited: IntArray = IntArray(BarManager.barList.size) { 0 }

            quickestPath(startPoint, BarManager.barList, visited)

            addMarkersAndRoute(map)
            for (places in newList)
                Log.i(TAG,places.name)

            map.controller.setZoom(18.0)
        };
    }

    fun addBarMarkers() {
        for (bar in BarManager.barList) {
            val marker = Marker(map)
            marker.position = bar.location
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            map.overlays.add(marker)
            marker.title = "${bar.name} \n rating: ${bar.rating} \n price:  ${bar.price}"

            try {
                val inputStream = assets.open("${bar.id}.jpg")
                val image = Drawable.createFromStream(inputStream, null)
                marker.image = image
                inputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            map.overlays.add(marker)
        }
        map.invalidate()
    }

    fun addMarkersAndRoute(mapView: MapView) {
        val route = Polyline()

        // Filters the selected bars
        var selectedBars : List<Bar> = BarManager.barList.filter { it.isChecked }

        val locationList: List<GeoPoint> = selectedBars.map { it.location } //i made a list of the coords
        val locationsNames: List<String> = selectedBars.map { it.name }

        route.setPoints(locationList)
        route.color = ContextCompat.getColor(this, R.color.teal_700)
        mapView.overlays.add(route)

        for (location in locationList) {
            val marker = Marker(mapView)
            marker.position = location
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            val locationIndex = locationList.indexOf(location)
            marker.title =
                "Marker at ${locationsNames[locationIndex]} ${location.latitude}, ${location.longitude}"
            marker.icon =
                ContextCompat.getDrawable(this, org.osmdroid.library.R.drawable.ic_menu_compass)
            mapView.overlays.add(marker)
        }

        mapView.invalidate()
    }

    fun calculateDistance(
        point1: GeoPoint,
        point2: GeoPoint
    ): Double {
        val earthRadius = 6371 // Earth radius in kilometers

        val dLat = Math.toRadians(point2.latitude - point1.latitude)
        val dLon = Math.toRadians(point2.longitude - point2.longitude)

        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(point1.latitude)) * cos(Math.toRadians(point2.latitude)) *
                sin(dLon / 2) * sin(dLon / 2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return earthRadius * c
    }

    fun containsOnlyOnes(array: IntArray): Boolean {
        for (element in array) {
            if (element != 1) {
                return false
            }
        }
        return true
    }

    private fun quickestPath(startPoint: GeoPoint, placesList: List<Bar>, visited: IntArray){
        Log.i(TAG, "todo bien")

        var n: Int = 0
        var closestPlace: Bar = placesList[0]
        var min: Double = 9999.0
        for (i in placesList.indices) {
            if (visited[i] == 0) {
                val current: Double = calculateDistance(startPoint, placesList[i].location)
                if (current < min) {
                    min = current
                    closestPlace = placesList[i]
                    n = i;
                }
            }

        }
        visited[n] = 1;
        newList.add(closestPlace)

        if(containsOnlyOnes(visited) == false)
            quickestPath(closestPlace.location, placesList, visited)

    }
    private fun createObjects() {
        val input = InputStreamReader(assets.open("bars.csv"));
        val reader = BufferedReader(input)

        reader.forEachLine { line ->
            val c = line.split(",")
            if (c.size >= 2) {
                val id: Int = c[0].toInt()
                val location: GeoPoint = GeoPoint(c[2].toDouble(), c[3].toDouble())
                val rating: Float = c[6].toFloat()
                BarManager.barList.add(
                    Bar(id, c[1], location, c[4], c[5], rating, c[7], false)
                )
//                Log.i(TAG, barList.toString())
            } else {
                // Handle incomplete or malformed lines
                println("Skipping malformed line: $line")
            }
        }
    }

    private fun addMarker(point: GeoPoint, title: String) {
        val marker = Marker(map)
        marker.position = point
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.title = title
        map.overlays.add(marker)
        map.invalidate() // Reload map
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }
}
