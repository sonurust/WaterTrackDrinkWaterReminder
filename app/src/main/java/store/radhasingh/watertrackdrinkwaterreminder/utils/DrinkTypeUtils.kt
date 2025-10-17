package store.radhasingh.watertrackdrinkwaterreminder.utils

import store.radhasingh.watertrackdrinkwaterreminder.R

object DrinkTypeUtils {
    
    data class DrinkType(
        val name: String,
        val imageResId: Int,
        val defaultVolume: Int
    )
    
    val availableDrinkTypes = listOf(
        DrinkType("Water", R.drawable.sparkling_water_soda, 250),
        DrinkType("Mango Bubble Tea", R.drawable.mango_pineapple_bubble_tea, 300),
        DrinkType("Milk Bubble Tea", R.drawable.milk_bubble_tea, 300),
        DrinkType("Orange Juice", R.drawable.orange_mango_juice, 200),
        DrinkType("Peach Juice", R.drawable.orange_peach_juice, 200),
        DrinkType("Cantaloupe Juice", R.drawable.peach_cantaloupe_juice, 200),
        DrinkType("Sparkling Water", R.drawable.sparkling_water_soda, 250),
        DrinkType("Soda", R.drawable.sparkling_water_soda, 300),
        DrinkType("Strawberry Bubble Tea", R.drawable.strawberry_bubble_tea_foam, 300),
        DrinkType("Strawberry Juice", R.drawable.strawberry_watermelon_juice, 200),
        DrinkType("Watermelon Juice", R.drawable.strawberry_watermelon_juice, 200),
        DrinkType("Watermelon Bubble Tea", R.drawable.watermelon_strawberry_bubble_tea, 300),
        DrinkType("Tea", R.drawable.milk_bubble_tea, 200),
        DrinkType("Coffee", R.drawable.milk_bubble_tea, 200),
        DrinkType("Milk", R.drawable.milk_bubble_tea, 250)
    )
    
    fun getDrinkTypeByName(name: String): DrinkType? {
        return availableDrinkTypes.find { it.name.equals(name, ignoreCase = true) }
    }
    
    fun getImageResIdForDrinkType(drinkType: String): Int {
        return getDrinkTypeByName(drinkType)?.imageResId ?: R.drawable.sparkling_water_soda
    }
    
    fun getDefaultVolumeForDrinkType(drinkType: String): Int {
        return getDrinkTypeByName(drinkType)?.defaultVolume ?: 250
    }
}
