# Add project specific ProGuard rules here.
-keepattributes *Annotation*
-keep class androidx.** { *; }
-keep class com.google.android.material.** { *; }

# Room
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**

# Kotlin
-keep class kotlin.** { *; }
-keep class kotlinx.** { *; }
