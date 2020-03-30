package com.project.base.model.local.converter

import androidx.room.TypeConverter
import java.util.*

/*
room 에서 Date 타입을 자동으로 저장할 수 있는 long 타입으로 전환 시켜주는 converter
 */
class DateConverter {
    @TypeConverter
    fun toDate(value: Long): Date = Date(value)

    @TypeConverter
    fun toLong(date: Date): Long = date.time
}