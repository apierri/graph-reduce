package graph

import com.hazelcast.core.Hazelcast
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

class GraphTest {
    companion object {
        private val instance = Hazelcast.newHazelcastInstance()
        private val graph = HazelcastGraph(instance)

        @JvmStatic
        @AfterAll
        fun teardown() {
            instance.shutdown()
        }
    }

    @Test
    fun `add and retrieve edges`() {
        val user = Node.User("u1")
        val item = Node.Item("i1")
        graph.addEdge(user, Edge.Viewed(item))
        graph.addEdge(user, Edge.Bought(item))

        val edges = graph.getEdges(user).sortedBy { it.type }
        assertEquals(2, edges.size)
        assertEquals("Bought", edges[0].type)
        assertEquals(0.4, edges[0].weight)
        assertEquals("Viewed", edges[1].type)
        assertEquals(0.1, edges[1].weight)
    }
}
