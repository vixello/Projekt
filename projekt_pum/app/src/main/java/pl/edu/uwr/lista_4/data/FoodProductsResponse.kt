package pl.edu.uwr.projekt_pum.data

import androidx.room.Entity
import androidx.room.PrimaryKey


class FoodProductsResponse : ArrayList<FoodProductsResponseItem>()

//data class RestCount9riesResponse(
//    val countries: List<FoodProductsResponseItem>
//)
@Entity(tableName = "country")
data class FoodProductsResponseItem(
    @PrimaryKey
    val title: String,
    val ingredients: String,
    val servings: String,
    val instructions: String

)
//"title": "Stracciatella (Italian Wedding Soup)",
//"ingredients": "1 lb Fresh spinach, washed and chopped|1 Egg|1 c Parmesan cheese, * see note|Salt, to taste|Pepper, to taste",
//"servings": "6 Servings",
//"instructions"
//data class FoodRecipes(
//
//)
data class Flags(
    val png: String,
    val svg: String
)

data class FoodRecipes(
    val count: Int,
    val next: String,
    val previous: String,
    val results: List<Result>
)

data class Result(
    val cooking_instructions: Any,
    val date_added: String,
    val date_updated: String,
    val description: String,
    val featured_image: String,
    val ingredients: List<String>,
    val long_date_added: Int,
    val long_date_updated: Int,
    val pk: Int,
    val publisher: String,
    val rating: Int,
    val source_url: String,
    val title: String
)

