package org.mitchan.erzhan.domain.database.model.alarm

fun AlarmEntity.toModel(): Alarm {
    return this.value
}