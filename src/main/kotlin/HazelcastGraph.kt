package graph

class HazelcastGraph(private val hazelcast: com.hazelcast.core.HazelcastInstance) : Graph {
    private val edges = hazelcast.getMultiMap<Node, Edge>("graph")

    override fun addEdge(from: Node, edge: Edge) {
        edges.put(from, edge)
    }

    override fun getEdges(from: Node): Collection<Edge> = edges.get(from)
}
