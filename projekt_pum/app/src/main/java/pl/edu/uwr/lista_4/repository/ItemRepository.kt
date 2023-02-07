package pl.edu.uwr.projekt_pum.repository

import androidx.lifecycle.LiveData
import pl.edu.uwr.projekt_pum.data.ItemDatabase
import pl.edu.uwr.projekt_pum.data.ItemDao
import pl.edu.uwr.projekt_pum.model.Item

class ItemRepository (private val itemDao: ItemDao) {
    val readAllData: LiveData<List<Item>> = itemDao.readAllData()
    suspend fun addItem(item: Item){
        itemDao.addItem(item)
    }

    fun getItem(id: Int): LiveData<Item>{
        return itemDao.getItem(id)
    }

    suspend fun getDate(date: String): List<Item>{
        return itemDao.getDate(date)
    }
    suspend fun updateItem(item: Item){
        itemDao.updateItem(item)
    }

    suspend fun deleteItem(item: Item){
        itemDao.deleteItem(item)
    }

    suspend fun deleteAllItems(){
        itemDao.deleteAllItems()
    }

    fun searchItem(query: String): LiveData<List<Item>>{
        return itemDao.search(query)
    }
}