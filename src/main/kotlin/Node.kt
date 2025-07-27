package graph

import java.io.Serializable

sealed class Node(open val id: String, val type: String) : Serializable {
    data class User(override val id: String) : Node(id, "User")
    data class Item(override val id: String) : Node(id, "Item")
}
