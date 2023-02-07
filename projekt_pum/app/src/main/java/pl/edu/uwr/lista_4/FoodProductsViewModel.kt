package pl.edu.uwr.projekt_pum

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.edu.uwr.projekt_pum.data.ItemDatabase
import pl.edu.uwr.projekt_pum.data.FoodProductsResponse
import pl.edu.uwr.projekt_pum.model.Item
import pl.edu.uwr.projekt_pum.repository.ItemRepository
import pl.edu.uwr.projekt_pum.repository.FoodProductsRepository
import pl.edu.uwr.projekt_pum.util.Resource
import retrofit2.Response


class FoodProductsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository=  FoodProductsRepository()
    private val _capitallist: MutableLiveData<Resource<FoodProductsResponse>> = MutableLiveData()
    private val _flaglist: MutableLiveData<Resource<FoodProductsResponse>> = MutableLiveData()
    private var food: MutableLiveData<List<Item>> = MutableLiveData()
    private val _meal: MutableLiveData<Resource<FoodProductsResponse>> = MutableLiveData()

    val restCountriesCapitalList: LiveData<Resource<FoodProductsResponse>>
        get() = _capitallist
    val restCountriesFlagsList: LiveData<Resource<FoodProductsResponse>>
        get() = _flaglist
    val foodList: LiveData<List<Item>>
        get() = food
    val meal: LiveData<Resource<FoodProductsResponse>>
        get() = _meal
//    init {
//        repository = FoodProductsRepository()
//    }

    private fun handleRestCountriesResponse(response: Response<FoodProductsResponse>)
            : Resource<FoodProductsResponse> {
        if (response.isSuccessful)
            response.body()?.let { return Resource.Success(it) }
        return Resource.Error(response.message())

    }


    fun getCapitalList(title: String) = viewModelScope.launch {
        _capitallist.postValue(Resource.Loading())
        val response = repository.getCountriesCapital(title)
        println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+response)
        _capitallist.postValue(handleRestCountriesResponse(response))
    }

    fun getFlagList() = viewModelScope.launch {
        _flaglist.postValue(Resource.Loading())
        val response = repository.getCountriesFlags()
        _flaglist.postValue(handleRestCountriesResponse(response))
    }

    fun getMealById(title: String) = viewModelScope.launch {
        _meal.postValue(Resource.Loading())
        val response = repository.getFoodById(title)
        println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"+response)

        _meal.postValue(handleRestCountriesResponse(response))
    }

    val readAllData: LiveData<List<Item>>
    private val repository2: ItemRepository

    init {
        val itemDao = ItemDatabase.getDatabase(application).itemDao()
        repository2 = ItemRepository(itemDao)
        readAllData = repository2.readAllData
    }
    //Dodajmy metody odczytujące dane z bazy i zwracające LiveData
    fun getItem(id: Int): LiveData<Item>{
        return repository2.getItem(id)
    }

    fun getTheDate(date: String) =   viewModelScope.launch {
        repository2.getDate(date)
    }
    fun getDate(date: String) : List<Item>? {
        viewModelScope.launch {

            val response = repository2.getDate(date)
            println(response)
            food.value = response
//            println("DASDASFASASDASDASDASDASD " + food.postValue(response))

        }
        return food.value
    }

//    fun getDate(date: String) : LiveData<List<Item>>? {
//        viewModelScope.launch {
//            val response = repository2.getDate(date)
//            food.postValue(response)
//        }
//        return food.value
//    }

    fun searchItem(query: String): LiveData<List<Item>>{
        return repository2.searchItem(query)
    }

    // metody zapisujące dane do bazy
    fun updateItem(item: Item){
        viewModelScope.launch {
            repository2.updateItem(item)
        }
    }
    fun deleteItem(item: Item){
        viewModelScope.launch {
            repository2.deleteItem(item)
        }
    }

    fun deleteAll(){
        viewModelScope.launch {
            repository2.deleteAllItems()
        }
    }

    fun addItem(item: Item){
        viewModelScope.launch {
            repository2.addItem(item)
        }
    }

}
