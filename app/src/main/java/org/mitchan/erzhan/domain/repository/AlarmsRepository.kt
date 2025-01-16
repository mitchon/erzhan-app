package org.mitchan.erzhan.domain.repository

import android.util.Log
import org.mitchan.erzhan.domain.database.model.alarm.Alarm
import org.mitchan.erzhan.domain.database.model.alarm.AlarmEntity
import org.mitchan.erzhan.domain.database.model.alarm.AlarmsDao
import org.mitchan.erzhan.domain.database.model.alarm.toModel
import org.mitchan.erzhan.domain.singleton.RoomSingleton
import java.util.UUID

interface AlarmsRepository {
    fun getAll(): List<Alarm>
    fun getById(id: UUID): Alarm?
    suspend fun insert(model: Alarm): Alarm
    suspend fun update(model: Alarm): Alarm
    suspend fun delete(id: UUID)
}

class AlarmsRepositoryImpl: AlarmsRepository {
    private val dao: AlarmsDao = RoomSingleton.getInstanceUnsafe().alarms()

    override fun getAll(): List<Alarm> = dao.findAll().map { it.toModel() }

    override fun getById(id: UUID): Alarm? = dao.findById(id)?.toModel()

    private fun getByIdOrDie(id: UUID): Alarm = getById(id) ?: throw RuntimeException("alarm $id is not found")

    override suspend fun insert(model: Alarm): Alarm {
        val id = model.id ?: UUID.randomUUID()
        dao.insert(AlarmEntity(id, model))
        return getByIdOrDie(id)
    }

    override suspend fun update(model: Alarm): Alarm {
        return try {
            dao.update(AlarmEntity(model.id!!, model))
            getByIdOrDie(model.id)
        } catch (e: NullPointerException) {
            Log.e("AlarmsRepositoryImpl", "Updating alarm with no id")
            throw e
        }
    }

    override suspend fun delete(id: UUID) = dao.delete(id)
}
