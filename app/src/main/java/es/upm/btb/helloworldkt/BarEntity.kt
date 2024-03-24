package es.upm.btb.helloworldkt.persistence.room
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.osmdroid.util.GeoPoint

@Entity
data class BarEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val location: GeoPoint,
    val price: String,
    val rating: Float,
    val imageUrl: String,
    var isChecked: Boolean
)