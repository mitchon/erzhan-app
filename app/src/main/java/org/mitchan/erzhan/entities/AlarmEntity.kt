package org.mitchan.erzhan.entities

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.Update
import kotlinx.serialization.json.Json
import org.mitchan.erzhan.entities.AlarmEntity.Companion.toListItem
import org.mitchan.erzhan.entities.AlarmEntity.Companion.toModel
import org.mitchan.erzhan.models.AlarmListItemModel
import org.mitchan.erzhan.models.AlarmModel
import org.mitchan.erzhan.models.AlarmModel.Trait
import java.time.LocalTime
import java.util.UUID

@Entity(tableName = "alarms")
data class AlarmEntity(
    @PrimaryKey
    val id: UUID,
    val time: LocalTime,
    val enabled: Boolean,
    val trait: Trait
) {
    companion object {
        fun fromModel(model: AlarmModel): AlarmEntity {
            return AlarmEntity(
                id = model.id,
                time = model.time,
                enabled = model.enabled,
                trait = model.trait,
            )
        }

        fun AlarmEntity.toModel(): AlarmModel {
            return AlarmModel(
                id = this.id,
                time = this.time,
                enabled = this.enabled,
                trait = this.trait,
            )
        }

        fun AlarmEntity.toListItem(): AlarmListItemModel {
            return AlarmListItemModel(
                id = this.id,
                time = this.time,
                enabled = this.enabled,
                trait = this.trait,
            )
        }
    }
}

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

    @TypeConverter
    fun traitFromString(value: String?): Trait? {
        return value?.let { Json.decodeFromString(it) }
    }

    @TypeConverter
    fun traitToString(trait: Trait?): String? {
        return trait?.let { Json.encodeToString(it) }
    }
}

@Database(entities = [AlarmEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AlarmsDatabase: RoomDatabase() {
    abstract fun alarmDao(): AlarmDao
}

interface AlarmsRepository {
    fun getAll(): List<AlarmListItemModel>
    fun getById(id: UUID): AlarmModel?
    suspend fun insert(model: AlarmModel): AlarmModel
    suspend fun update(model: AlarmModel): AlarmModel
    suspend fun delete(id: UUID)
}

class AlarmsRepositoryImpl(
    private val dao: AlarmDao
): AlarmsRepository {
    override fun getAll(): List<AlarmListItemModel> = dao.findAll().map { it.toListItem() }

    override fun getById(id: UUID): AlarmModel? = dao.findById(id)?.toModel()

    private fun getByIdOrDie(id: UUID): AlarmModel = getById(id) ?: throw RuntimeException("alarm $id is not found")

    override suspend fun insert(model: AlarmModel): AlarmModel {
        dao.insert(AlarmEntity.fromModel(model))
        return getByIdOrDie(model.id)
    }

    override suspend fun update(model: AlarmModel): AlarmModel {
        dao.update(AlarmEntity.fromModel(model))
        return getByIdOrDie(model.id)
    }

    override suspend fun delete(id: UUID) = dao.delete(id)
}
