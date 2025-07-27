package graph

import java.io.Serializable
import java.time.Duration
import java.time.Instant
import kotlin.math.exp
import kotlin.math.ln

sealed class Node(open val id: String, val type: String) : Serializable {
    data class User(override val id: String) : Node(id, "User")
    data class Item(override val id: String) : Node(id, "Item")
}

sealed class Edge(
    open val to: Node,
    val type: String
) : Serializable {

    /** Weight of the edge. */
    abstract val weight: Double

    data class Viewed(override val to: Node, val timestamp: Instant = Instant.now()) :
        Edge(to, "Viewed") {
        override val weight: Double
            get() {
                val days = Duration.between(timestamp, Instant.now()).toDays().toDouble()
                return (0.1 - TERMINAL_WEIGHT) * exp(-DECAY_K * days)
            }
    }

    data class Bought(override val to: Node, val timestamp: Instant = Instant.now()) :
        Edge(to, "Bought") {
        override val weight: Double
            get() {
                val days = Duration.between(timestamp, Instant.now()).toDays().toDouble()
                return (0.4 - TERMINAL_WEIGHT) * exp(-DECAY_K * days)
            }
    }

    companion object {
        private const val TERMINAL_WEIGHT = 0.0
        private val DECAY_K: Double = ln(100.0) / 30.0
    }
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
