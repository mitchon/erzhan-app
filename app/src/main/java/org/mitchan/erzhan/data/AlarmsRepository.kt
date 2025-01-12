package org.mitchan.erzhan.data

import org.mitchan.erzhan.data.AlarmEntity.Companion.toListItem
import org.mitchan.erzhan.data.AlarmEntity.Companion.toModel
import org.mitchan.erzhan.ui.alarm.AlarmModel
import org.mitchan.erzhan.ui.alarmslist.AlarmListItemModel
import java.util.UUID

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
