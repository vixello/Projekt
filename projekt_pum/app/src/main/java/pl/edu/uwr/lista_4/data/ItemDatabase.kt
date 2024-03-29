package pl.edu.uwr.projekt_pum.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pl.edu.uwr.projekt_pum.data.ItemDao
import pl.edu.uwr.projekt_pum.model.Item


@Database(entities = [Item::class], version = 1, exportSchema = false)

abstract class ItemDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao

    companion object{
        @Volatile private var INSTANCE: ItemDatabase? = null

        fun getDatabase(context: Context): ItemDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ItemDatabase::class.java,
                    "word_database_kotlin"
                ).build().also { INSTANCE = it }
                instance
            }
        }
    }
}