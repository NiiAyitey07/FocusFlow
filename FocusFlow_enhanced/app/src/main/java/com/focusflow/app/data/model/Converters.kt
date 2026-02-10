package com.focusflow.app.data.model

import androidx.room.TypeConverter
import java.util.Date

/**
 * Room Type Converters for Date and Enums
 */
class Converters {
    
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }
    
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
    
    @TypeConverter
    fun fromDuration(value: Duration?): String? {
        return value?.name
    }
    
    @TypeConverter
    fun toDuration(value: String?): Duration? {
        return value?.let { Duration.valueOf(it) }
    }
}
