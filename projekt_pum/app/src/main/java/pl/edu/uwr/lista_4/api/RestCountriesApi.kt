package pl.edu.uwr.projekt_pum.api

import pl.edu.uwr.projekt_pum.data.FoodProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RestCountriesApi {
    @Headers(
        "X-RapidAPI-Key: d830d39c19msh32794cd4789a1b2p10d35fjsn5b252d559351",
        "X-RapidAPI-Host: recipe-by-api-ninjas.p.rapidapi.com'Cache-Control: max-age=640000"
    )
    @GET("https://recipe-by-api-ninjas.p.rapidapi.com/v1/recipe?")
    suspend fun getCountriesCapital(@Query("query") title: String) : Response<FoodProductsResponse>
    @Headers(
        "X-RapidAPI-Key: d830d39c19msh32794cd4789a1b2p10d35fjsn5b252d559351",
        "X-RapidAPI-Host: recipe-by-api-ninjas.p.rapidapi.com'Cache-Control: max-age=640000"
    )
    @GET("https://recipe-by-api-ninjas.p.rapidapi.com/v1/recipe?")
    suspend fun getFoodById(@Query("query") title: String) : Response<FoodProductsResponse>
//    @GET("v2/all?fields=name,flags")
//    suspend fun getCountriesFlags(@Query("flags") id: String) : Response<FoodProductsResponse>

    @GET("v2/all?fields=name,capital,flags")
    suspend fun getCountriesFlags() : Response<FoodProductsResponse>
}
