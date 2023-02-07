package pl.edu.uwr.projekt_pum.data

import androidx.lifecycle.LiveData
import androidx.room.*
import pl.edu.uwr.projekt_pum.model.Item

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addItem(item: Item) //dodaj element

    @Query("Select * FROM item_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Item>>// czytaj wszystkie elementy

    @Query("SELECT * FROM item_table WHERE id = :id")
    fun getItem(id: Int): LiveData<Item>// czytaj element o zadanym id

    @Query("SELECT * FROM item_table WHERE date LIKE :date")
    suspend fun getDate(date: String):List<Item>// czytaj element o zadanym id

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateItem(item: Item) // aktualizuj element

    @Delete
    suspend fun deleteItem(item: Item) // usuń element

    @Query("DELETE FROM item_table")
    suspend fun deleteAllItems() // usuń wszystkie elemen

    @Query("SELECT * FROM item_table WHERE name LIKE :query")
    fun search(query: String): LiveData<List<Item>> // wyszukaj element o zadanej nazwie
}