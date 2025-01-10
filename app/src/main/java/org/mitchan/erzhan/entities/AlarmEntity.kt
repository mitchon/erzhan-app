package org.mitchan.erzhan.entities

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.Update
import org.mitchan.erzhan.models.AlarmListItemModel.Trait
import java.time.LocalTime
import java.util.UUID

@Entity(tableName = "alarms")
data class AlarmEntity(
    @PrimaryKey
    val id: UUID,
    val time: LocalTime,
    val enabled: Boolean,
//    val trait: Trait
)

@Dao
interface AlarmDao {
    @Query("select * from alarms")
    fun findAll(): List<AlarmEntity>

    @Query("select * from alarms where id = :id")
    fun findById(id: UUID): AlarmEntity?

    @Insert
    suspend fun insert(entity: AlarmEntity)

    @Update
    suspend fun update(entity: AlarmEntity)

    @Query("delete from alarms where id = :id")
    suspend fun delete(id: UUID)
}

class Converters {
    @TypeConverter
    fun timeFromString(value: String?): LocalTime? {
        return value?.let { LocalTime.parse(value) }
    }

    @TypeConverter
    fun timeToString(time: LocalTime?): String? {
        return time?.toString()
    }

    @TypeConverter
    fun uuidFromString(value: String?): UUID? {
        return value?.let { UUID.fromString(value) }
    }

    @TypeConverter
    fun uuidToString(uuid: UUID?): String? {
        return uuid?.toString()
    }
}

@Database(entities = [AlarmEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AlarmsDatabase: RoomDatabase() {
    abstract fun alarmDao(): AlarmDao

    companion object {
        @Volatile
        private var Instance: AlarmsDatabase? = null

        fun getDatabase(context: Context): AlarmsDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context.applicationContext, AlarmsDatabase::class.java, "alarms_db")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}

interface AlarmsRepository {
    fun getAll(): List<AlarmEntity>
    fun getById(id: UUID): AlarmEntity?
    suspend fun insert(alarm: AlarmEntity)
    suspend fun update(alarm: AlarmEntity)
    suspend fun delete(id: UUID)
}

class AlarmsRepositoryImpl(
    private val dao: AlarmDao
): AlarmsRepository {
    override fun getAll(): List<AlarmEntity> = dao.findAll()

    override fun getById(id: UUID): AlarmEntity? = dao.findById(id)

    override suspend fun insert(alarm: AlarmEntity) = dao.insert(alarm)

    override suspend fun update(alarm: AlarmEntity) = dao.update(alarm)

    override suspend fun delete(id: UUID) = dao.delete(id)
}
