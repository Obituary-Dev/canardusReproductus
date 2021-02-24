package levi.ackerman.canardus_reproductus


/**
 * @author Levi Ackerman
 * ducks will have parameters such as an id, a name, a description... all set up here
 */
class DuckModel (
        val id: String = "duck0",
        val name: String = "Mallard",
        val desc: String = "Small description",
        val imageUrl: String = "https://cdn.pixabay.com/photo/2018/10/14/23/13/mallard-3747770_960_720.jpg",
        val type: String = "Dabbling ducks",
        val latin: String = "Canardus Reproductus",
        val length: String = "",
        val weight: String = "",
        val egg: String = "",
        var liked: Boolean = false
)