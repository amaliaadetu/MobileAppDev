package es.upm.btb.helloworldkt.persistence.room
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
@Dao
interface IBarDao {
    @Insert
    fun insertBar(barEntity: BarEntity)
    @Query("SELECT * FROM BarEntity")
    fun getAllBars(): List<BarEntity>
    @Query("SELECT COUNT(*) FROM BarEntity")
    fun getCount(): Int
    @Query("DELETE FROM BarEntity WHERE timestamp = :timestamp")
    fun deleteLocationByTimestamp(timestamp: Long)
}
