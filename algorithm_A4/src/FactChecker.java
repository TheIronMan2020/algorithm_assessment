import java.util.*;

public class FactChecker {
    /**
     * Checks if a list of facts is internally consistent. 
     * That is, can they all hold true at the same time?
     * Or are two (or potentially more) facts logically incompatible?
     * 
     * @param facts list of facts to check consistency of
     * @return true if all the facts are internally consistent, otherwise false.
     */
    public static boolean areFactsConsistent(List<Fact> facts) {
        // Store the abstract facts graph relation
        HashMap<String, List<String>> graph = new HashMap<>();
        // Store status to check if vertex is visited
        HashMap<String, Boolean> visited = new HashMap<>();
        // Store status to check if vertex is currently in stack
        HashMap<String, Boolean> recStack = new HashMap<>();
        // Store the fact type by key of personA+personB
        HashMap<String, Fact.FactType> typeStore = new HashMap<>();
        // Store the edge of the traversal
        Stack<Fact.FactType> stack = new Stack<>();
        Fact fact;
        String personA, personB;
        Iterator iterator = facts.iterator();

        while (iterator.hasNext()) {
            fact = (Fact) iterator.next();
            personA = fact.getPersonA();
            personB = fact.getPersonB();

            // Mark all vertices as unvisited and not in stack
            visited.put(personA, false);
            recStack.put(personA, false);
            visited.put(personB , false);
            recStack.put(personB , false);

            // Check simple inconsistency
            if (fact.getType() == Fact.FactType.TYPE_ONE) {
                /** */
                typeStore.put(personA+personB, Fact.FactType.TYPE_ONE);
                // if personB hasn't been added
                if (!graph.containsKey(personB)) {
                    new FactChecker().addEdge(graph, personA, personB);
                // personB doesn't point to (->) personA
                } else if (!graph.get(personB).contains(personA)) {
                    new FactChecker().addEdge(graph, personA, personB);
                } else {
                    return false;
                }
            } else {
                // personB -> personA or personA -> personB
                if (graph.getOrDefault(personB, new ArrayList<>()).contains(personA) ||
                        graph.getOrDefault(personA, new ArrayList<>()).contains(personB)) {
                    return false;
                } else {
                    new FactChecker().addEdge(graph, personA, personB);
                    new FactChecker().addEdge(graph, personB, personA);

                    typeStore.put(personA+personB, Fact.FactType.TYPE_TWO);
                }
            }
        }

        // Check if all vertices have been visited from Erdos
        for (Map.Entry entry : graph.entrySet()) {
            // ### is dummy placeholder
            if ( new FactChecker().isCyclicUtil(typeStore, stack, graph, (String) entry.getKey(), visited, recStack, null))
                return false;
        }

        return true;
    }

    /**
     * An utility function that adds an directed edge from personA to personB
     *
     * @param graph Store the abstract facts graph relation
     * @param personA To be added into graph
     * @param personB To be added into graph
     */
    private void addEdge(HashMap<String, List<String>> graph, String personA, String personB) {
        List<String> list;
        list = new ArrayList<>(graph.getOrDefault(personA, new ArrayList<>()));
        list.add(personB);
        graph.put(personA, list);
    }

    /**
     *
     *
     * @param graph Store the abstract facts graph relation
     * @param name Name of current author
     * @param visited Store status to check if vertex is visited
     * @param recStack Store status to check if vertex is currently in stack
     * @param last The name of the person which points to current person
     */
    private boolean isCyclicUtil(HashMap<String, Fact.FactType> typeStore, Stack<Fact.FactType> stack, HashMap<String, List<String>> graph, String name, HashMap<String, Boolean> visited, HashMap<String, Boolean> recStack, String last) {
        // Add fact type as edge
        if (last != null) {
            if (typeStore.getOrDefault(last+name, null) == Fact.FactType.TYPE_ONE ||
                    typeStore.getOrDefault(name+last, null) == Fact.FactType.TYPE_ONE)
                stack.push(Fact.FactType.TYPE_ONE);
            else
                stack.push(Fact.FactType.TYPE_TWO);
        }


        // if current person is in stack, then circle detected
        if (recStack.get(name)) {
            int type1_counter = 0;
            int type2_counter = 0;
            Boolean twoConsecutive = false;
            int next;

            for (int i = 0; i < stack.size(); i ++) {
                if (stack.get(i) == Fact.FactType.TYPE_ONE) {
                    type1_counter ++;
                } else {
                    type2_counter ++;
                    if ( i < stack.size() - 1)
                        next = i + 1;
                    else
                        next = 0;

                    // check if exists two consecutive TYPE_TWO Fact
                    if (stack.get(next) == Fact.FactType.TYPE_TWO) {
                        twoConsecutive = true;
                    }
                }
            }

            // if circle consists of only TYPE_ONE facts
            if (type1_counter == stack.size())
                return true;

            // Check for TYPE_TWO
            // if numbers of TYPE_TWO = 1, then invalid
            // if numbers of TYPE_TWO > 1, if no two consecutive TYPE_TWO
            // edges, then invalid
            if (type2_counter > 1 && !twoConsecutive || type2_counter == 1)
                return true;

            return false;
        }

        // if visited, no need to visit again
        if (visited.get(name))
            return false;

        // mark as visited
        visited.put(name, true);
        // mark as in Stack
        recStack.put(name, true);

        List<String> children = graph.getOrDefault(name, new ArrayList<>());

        for (String child : children) {
            // Current child is the last, meaning parallel circle with length 1
            if (child.equals(last))
                continue;
            if (isCyclicUtil(typeStore, stack, graph, child, visited, recStack, name)) {
                return true;
            }
        }

        // Pop the person from stack if all of its nodes are visited
        recStack.put(name, false);

        if (!stack.isEmpty()) {
            stack.pop();
        }

        return false;
    }

}
