package org.mitchan.erzhan.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import java.util.UUID

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