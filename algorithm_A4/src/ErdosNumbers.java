import java.util.*;

public class ErdosNumbers {

    /**
     *  A class representing each author
     */
    private class Person {
        /** Name of the author */
        private String name;
        /** All papers the author wrote */
        private HashSet<String> articles;
        /** All authors the author has published papers with */
        private HashSet<String> coAuthors;
        /** Times of collaborations with other authors */
        private HashMap<String, Double> totalCollaboration;

        Person(String name) {
            this.name = name;
            this.articles = new HashSet<>();
            this.coAuthors = new HashSet<>();
            this.totalCollaboration = new HashMap<>();
        }

        private void addCollabration(String article, String author) {
            if (author != null) {
                this.coAuthors.add(author);
                totalCollaboration.put(author, totalCollaboration.getOrDefault(author, (double) 0) + 1);
            }
            this.articles.add(article);
        }

        private Set<String> getPapers() { return this.articles;}

        private Set<String> getCollaborators() { return this.coAuthors; }

        private HashMap<String, Double> getTotalCollaborations() { return this.totalCollaboration; }
    }

    /**
     * String representing Paul Erdos's name to check against
     */
    public static final String ERDOS = "Paul Erdös";
    /** A list storing all the papers */
    private List<String> papers;
    /** A hash map that store paper name as key, authors as values */
    private HashMap<String, List<String>> paperCollections;
    /** A hash map that store author name as key, Person instance as value */
    private HashMap<String,Person> personCollections;

    /**
     * Initialises the class with a list of papers and authors.
     *
     * Each element in 'papers' corresponds to a String of the form:
     * 
     * [paper name]:[author1][|author2[|...]]]
     *
     * Note that for this constructor and the below methods, authors and papers
     * are unique (i.e. there can't be multiple authors or papers with the exact same name or title).
     * 
     * @param papers List of papers and their authors
     */
    public ErdosNumbers(List<String> papers) {
        this.papers = papers;
        this.personCollections = new HashMap<>();
        this.paperCollections = new HashMap<>();
        String paperName;
        // store all authors of a paper
        String[] authors;
        String[] info;
        ArrayList<String> paperAuthors;

        for (int i = 0; i < this.papers.size(); i++) {
            info = papers.get(i).split(":");
            paperName = info[0];
            authors = info[1].split("\\|");

            // if the paper has only one author
            if (authors.length == 1) {
                if (!personCollections.containsKey(authors[0])) {
                    personCollections.put(authors[0], new Person(authors[0]));
                }
                personCollections.get(authors[0]).addCollabration(paperName, null);
            } else {
                // the paper has multiple authors
                for (int j = 0; j < authors.length - 1; j++) {
                    for (int k = j + 1; k < authors.length; k++) {
                        addBiCollabration(authors[j], authors[k], paperName);
                    }
                }
            }

            paperAuthors = new ArrayList<>(Arrays.asList(authors));

            // Store all authors name of this paper
            paperCollections.put(paperName, new ArrayList<>(paperAuthors));
        }

    }

    /**
     * An utility function that adds collaboration between two authors
     *
     * @param authorA name of the author who wrote paper with authorB
     * @param authorB name of the author who wrote paper with authorA
     * @param article paper name they they wrote together
     */
    private void addBiCollabration(String authorA, String authorB, String article) {
        if (!personCollections.containsKey(authorA)) {
            personCollections.put(authorA, new Person(authorA));
        }
        if (!personCollections.containsKey(authorB)) {
            personCollections.put(authorB, new Person(authorB));
        }
        personCollections.get(authorA).addCollabration(article, authorB);
        personCollections.get(authorB).addCollabration(article, authorA);
    }

    /**
     * Gets all the unique papers the author has written (either solely or
     * as a co-author).
     * 
     * @param author to get the papers for.
     * @return the unique set of papers this author has written.
     */
    public Set<String> getPapers(String author) {
        return personCollections.get(author).getPapers();
    }

    /**
     * Gets all the unique co-authors the author has written a paper with.
     *
     * @param author to get collaborators for
     * @return the unique co-authors the author has written with.
     */
    public Set<String> getCollaborators(String author) {
       return personCollections.get(author).getCollaborators();
    }

    /**
     * Checks if Erdos is connected to all other author's given as input to
     * the class constructor.
     * 
     * In other words, does every author in the dataset have an Erdos number?
     * 
     * @return the connectivity of Erdos to all other authors.
     */
    public boolean isErdosConnectedToAll() {
        String s;
        LinkedList<String> queue = new LinkedList<>();
        // store the visit status of all vertices
        HashMap<String, Boolean> visited = new HashMap<>();

        // mark all vertices as unvisited
        for (Map.Entry entry : personCollections.entrySet()) {
            visited.put((String) entry.getKey(), false);
        }

        // mark source as visited
        visited.put(ERDOS, true);
        queue.add(ERDOS);

        // use BFS to visit vertices from source
        while (queue.size() != 0) {
            s = queue.poll();

            Iterator iterator = personCollections.get(s).getCollaborators().iterator();
            while (iterator.hasNext())
            {
                String next = (String) iterator.next();
                if (!visited.get(next))
                {
                    visited.put(next, true);
                    queue.add(next);
                }
            }
        }

        // if there someone who hasn't been visited
        for (Map.Entry entry : visited.entrySet()) {
            if ((Boolean) entry.getValue() == false) {
                return false;
            }
        }

        return true;
    }

    /**
     * Calculate the Erdos number of an author. 
     * 
     * This is defined as the length of the shortest path on a graph of paper 
     * collaborations (as explained in the assignment specification).
     * 
     * If the author isn't connected to Erdos (and in other words, doesn't have
     * a defined Erdos number), returns Integer.MAX_VALUE.
     * 
     * Note: Erdos himself has an Erdos number of 0.
     * 
     * @param author to calculate the Erdos number of
     * @return authors' Erdos number or otherwise Integer.MAX_VALUE
     */
    public int calculateErdosNumber(String author) {
        if (author.equals(ERDOS)) return 0;

        String s;
        int distance = -1;
        // store the visit status of all vertices
        HashMap<String, Boolean> visited = new HashMap<>();
        // Store vertices of current level
        LinkedList<String> queue = new LinkedList<>();

        // mark all vertices as unvisited
        for (Map.Entry entry : personCollections.entrySet()) {
            visited.put((String) entry.getKey(), false);
        }

        visited.put(ERDOS, true);
        queue.add(ERDOS);

        boolean found = false;

        // use BFS to visit target author
        while (queue.size() != 0 && !found) {
            // reach next level
            distance ++;
            // size of current level
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                if (found) continue;
                s = queue.poll();

                Iterator iterator = personCollections.get(s).getCollaborators().iterator();

                while (iterator.hasNext() && !found)
                {
                    String next = (String) iterator.next();
                    // target author found
                    if (next.equals(author)) {
                        found = true;
                        distance +=1;
                    }
                    if (!visited.get(next))
                    {
                        visited.put(next, true);
                        queue.add(next);
                    }
                }
            }
        }

        return found ? distance : Integer.MAX_VALUE;
    }

    /**
     * Gets the average Erdos number of all the authors on a paper.
     * If a paper has just a single author, this is just the author's Erdos number.
     *
     * Note: Erdos himself has an Erdos number of 0.
     *
     * @param paper to calculate it for
     * @return average Erdos number of paper's authors
     */
    public double averageErdosNumber(String paper) {
        List<String> authors;
        // total erdos number of all authors of paper
        double totalErdosNumber = 0;

        // if paper has only one author
        if (paperCollections.get(paper).size() == 1) {
            return calculateErdosNumber(paperCollections.get(paper).get(0));
        } else {
            authors = paperCollections.get(paper);
            for (String author : authors) {
                totalErdosNumber += calculateErdosNumber(author);
            }
        }

        return totalErdosNumber / authors.size();
    }

    /**
     * Calculates the "weighted Erdos number" of an author.
     * 
     * If the author isn't connected to Erdos (and in other words, doesn't have
     * an Erdos number), returns Double.MAX_VALUE.
     *
     * Note: Erdos himself has a weighted Erdos number of 0.
     * 
     * @param author to calculate it for
     * @return author's weighted Erdos number
     */
    public double calculateWeightedErdosNumber(String author) {
        if (calculateErdosNumber(author) == Double.MAX_VALUE) {
            return Double.MAX_VALUE;
        }

        String u;
        String v;
        Person person_u;
        HashMap<String, Boolean> visited = new HashMap<>();
        // Store distance from Erdos to all others
        HashMap<String, Double> dist = new HashMap<>();

        // mark all vertices as Double.MAX_VALUE, mark all vertices as unvisited
        for (Map.Entry entry : personCollections.entrySet()) {
            dist.put((String) entry.getKey(), Double.MAX_VALUE);
            visited.put((String) entry.getKey(), false);
        }

        // mark distance from source to itself as 0
        dist.put(ERDOS, (double) 0);

        for (int i = 0; i < personCollections.size() - 1; i ++) {
            // vertex u with the minimum distance that has not been unvisited
            u = minDistance(dist, visited);
            // mark as visited
            visited.put(u, true);

            for (Map.Entry entry : personCollections.entrySet()) {
                v = (String) entry.getKey();
                person_u = personCollections.get(u);

                // v hasn't been visited && v is a co-author of u &&
                // u has been reached from source && distance from u to v less than
                // current distance from source to v
                if (!visited.get(v) &&
                        person_u.getCollaborators().contains(v) &&
                        dist.get(u) != Double.MAX_VALUE &&
                        dist.get(u) + (1 / person_u.getTotalCollaborations().get(v)) < dist.get(v)) {

                    // update distance from source to v
                    dist.put(v, dist.get(u) +  (1 / person_u.getTotalCollaborations().get(v)));
                }
            }
        }

        return dist.get(author);
    }

    /**
     * An function to find the person with the minimum distance and
     * yet has not been visited
     *
     * @param dist Store distance from Erdos to all others
     * @param visited Store the visit status of all vertices
     * @return name of the unvisited author with minimum distance
     */
    private String minDistance(HashMap<String, Double> dist, HashMap<String, Boolean> visited) {
        // Initialize min value
        double min = Double.MAX_VALUE;
        // Initialize dummy placeholder, name of the author with minimum distance
        String minName = "John Doe";
        String v;

        for (Map.Entry entry : personCollections.entrySet()) {
            v = (String) entry.getKey();

            // if has not been visited && distance less than min
            if (!visited.get(v) && dist.get(v) <= min) {
                min = dist.get(v);
                minName = v;
            }
        }

        return minName;
    }
}
