package es.upm.btb.helloworldkt.persistence.room
import androidx.room.Database
import androidx.room.RoomDatabase
import es.upm.btb.helloworldkt.Bar

@Database(entities = [Bar::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun locationDao(): IBarDao
}