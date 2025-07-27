package graph

import java.io.Serializable

sealed class Node(open val id: String, val type: String) : Serializable {
    data class User(override val id: String) : Node(id, "User")
    data class Item(override val id: String) : Node(id, "Item")
}

sealed class Edge(open val to: Node, val type: String, val weight: Double) : Serializable {
    data class Viewed(override val to: Node) : Edge(to, "Viewed", 0.1)
    data class Bought(override val to: Node) : Edge(to, "Bought", 0.4)
}

interface Graph {
    fun addEdge(from: Node, edge: Edge)
    fun getEdges(from: Node): Collection<Edge>
}

class HazelcastGraph(private val hazelcast: com.hazelcast.core.HazelcastInstance) : Graph {
    private val edges = hazelcast.getMultiMap<Node, Edge>("graph")

    override fun addEdge(from: Node, edge: Edge) {
        edges.put(from, edge)
    }

    override fun getEdges(from: Node): Collection<Edge> = edges.get(from)
}
