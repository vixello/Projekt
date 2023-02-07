package pl.edu.uwr.projekt_pum

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.edu.uwr.projekt_pum.data.ItemDatabase
import pl.edu.uwr.projekt_pum.model.Item
import pl.edu.uwr.projekt_pum.repository.ItemRepository

class ItemViewModel(application: Application) : AndroidViewModel(application) {
    val readAllData: LiveData<List<Item>>
    private val repository: ItemRepository

    init {
        val itemDao = ItemDatabase.getDatabase(application).itemDao()
        repository = ItemRepository(itemDao)
        readAllData = repository.readAllData
    }
//Dodajmy metody odczytujące dane z bazy i zwracające LiveData
    fun getItem(id: Int): LiveData<Item>{
        return repository.getItem(id)
    }

    fun searchItem(query: String): LiveData<List<Item>>{
        return repository.searchItem(query)
    }

// metody zapisujące dane do bazy
    fun updateItem(item: Item){
        viewModelScope.launch {
            repository.updateItem(item)
        }
    }
    fun deleteItem(item: Item){
        viewModelScope.launch {
            repository.deleteItem(item)
        }
    }

    fun deleteAll(){
        viewModelScope.launch {
            repository.deleteAllItems()
        }
    }

    fun addItem(item: Item){
        viewModelScope.launch {
            repository.addItem(item)
        }
    }
}