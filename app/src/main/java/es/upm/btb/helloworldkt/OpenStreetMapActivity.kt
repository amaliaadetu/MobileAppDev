package es.upm.btb.helloworldkt

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


class OpenStreetMapActivity : AppCompatActivity() {
    private val TAG = "btaOpenStreetMapActivity"
    private lateinit var map: MapView
    private val barList: MutableList<Bar> = mutableListOf()

    val gymkhanaCoords = listOf(
        GeoPoint(40.38779608214728, -3.627687914352839), // Tennis
        GeoPoint(40.38788595319803, -3.627048250272035), // Futsal outdoors
        GeoPoint(40.3887315224542, -3.628643539758645), // Fashion and design
        GeoPoint(40.38926842612264, -3.630067893975619), // Topos
        GeoPoint(40.38956358584258, -3.629046081389352), // Teleco
        GeoPoint(40.38992125672989, -3.6281366497769714), // ETSISI
        GeoPoint(40.39037466191718, -3.6270256763598447), // Library
        GeoPoint(40.389855884803005, -3.626782180787362) // CITSEM
    )
    val gymkhanaNames = listOf(
        "Tennis",
        "Futsal outdoors",
        "Fashion and design school",
        "Topography school",
        "Telecommunications school",
        "ETSISI",
        "Library",
        "CITSEM"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_street_map)

        Log.d(TAG, "onCreate: The activity is being created.");

        createObjects();

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

            val startPoint = GeoPoint(location.latitude, location.longitude)
            //val startPoint = GeoPoint(40.416775, -3.703790) in case you want to test it mannualy
            map.controller.setCenter(startPoint)
            map.controller.setCenter(startPoint)

            addMarker(barList[0].location, "100 Montatidos")

            addMarker(startPoint, "My current location")
            addMarkersAndRoute(map, gymkhanaCoords, gymkhanaNames)

            addMarker(startPoint, "My current location")
            addMarkers(map, gymkhanaCoords, gymkhanaNames)
            map.controller.setZoom(18.0)
        };
    }

    fun addMarkers(
        mapView: MapView,
        locationsCoords: List<GeoPoint>,
        locationsNames: List<String>
    ) {

        for (location in locationsCoords) {
            val marker = Marker(mapView)
            marker.position = location
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            mapView.overlays.add(marker)

            marker.title =
                "Marker at ${locationsNames.get(locationsCoords.indexOf(location))} ${location.latitude}, ${location.longitude}"
            mapView.overlays.add(marker)
        }
        mapView.invalidate() // Refresh the map to display the new markers
    }

    fun addMarkersAndRoute(
        mapView: MapView,
        locationsCoords: List<GeoPoint>,
        locationsNames: List<String>
    ) {
        if (locationsCoords.size != locationsNames.size) {
            Log.e(
                "addMarkersAndRoute",
                "Locations and names lists must have the same number of items."
            )
            return
        }

        val route = Polyline()
        route.setPoints(locationsCoords)
        route.color = ContextCompat.getColor(this, R.color.teal_700)
        mapView.overlays.add(route)

        for (location in locationsCoords) {
            val marker = Marker(mapView)
            marker.position = location
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            val locationIndex = locationsCoords.indexOf(location)
            marker.title =
                "Marker at ${locationsNames[locationIndex]} ${location.latitude}, ${location.longitude}"
            marker.icon =
                ContextCompat.getDrawable(this, org.osmdroid.library.R.drawable.ic_menu_compass)
            mapView.overlays.add(marker)
        }

        mapView.invalidate()
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
                barList.add(
                    Bar(id, c[1], location, c[4], c[5], rating, false)
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

data class Bar(
    val id: Int,
    val name: String,
    val location: GeoPoint,
    val description: String,
    val price: String,
    val rating: Float,
    val isChecked: Boolean
)
