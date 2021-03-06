package layout

// Model class representing structure of items in database
class AnimalModel (
    val name: String = "",
    val age: Int = 0,
    val breed: String = "",
    val sex: String = "",
    val species: String = "",
    val dogfriendly: Boolean = false,
    val catfriendly: Boolean = false,
    val vaccinated: Boolean = false,
    val sterilized: Boolean = false,
    val uri: String = ""
)
