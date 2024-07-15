package com.example.hostEase.Screens.BottomNavScreens.LostOrFound

data class LostFoundItem(
    val id : String = "",
    val userId : String = "",
    val name : String = "",
    val model : String = "",
    val itemDetails : String = "",
    val contactDetails : String = "",
    val imgUrl : String = "",
    val category : String = "",
    val subCategory : String = ""
)

data class LFCategory(
    val name : String = "",
    val subCategories: List<String>
)


/*
sealed class LFcategories(
    val name: String,
    val subCategories : List<String>
)
{
    data object Electronics : LFcategories(name = "Electronics",
        subCategories = listOf("Mobile Phone","Headphones","Earphones","Charger","Wearable Devices","Laptop","Other"))

    data object Stationary : LFcategories(name = "Stationary",
        subCategories = listOf("Calculator","Lab Record","Note Book", "Other"))

    data object General : LFcategories(name = "General",
        subCategories = listOf("Umbrella","Shoes","Keys","WaterBottle","Other")
    )

    data object Accessories : LFcategories(name = "Accessories", subCategories = listOf("Jewellery", "Wallet","BackPack", "Other"))

    data object Cycle : LFcategories(name = "Cycle", subCategories = emptyList())

    data object Clothing : LFcategories(name = "Clothing", subCategories = emptyList())
    data object Other : LFcategories(name = "Other", subCategories = emptyList())
}

 */