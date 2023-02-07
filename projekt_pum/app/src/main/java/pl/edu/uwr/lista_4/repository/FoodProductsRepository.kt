package pl.edu.uwr.projekt_pum.repository

import pl.edu.uwr.projekt_pum.api.RetrofitInstance

class FoodProductsRepository {
    suspend fun getCountriesCapital(title: String) = RetrofitInstance.api.getCountriesCapital(title)
    suspend fun getCountriesFlags() = RetrofitInstance.api.getCountriesFlags()
    suspend fun getFoodById(title: String) = RetrofitInstance.api.getFoodById(title)

//    val readAllData: LiveData<List<FoodProductsResponseItem>> = RestDao.getAllCountries()

}