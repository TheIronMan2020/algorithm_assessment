import java.util.*;

public class ContactTracer {
    private List<Trace> traces;
    /**
     * Initialises an empty ContactTracer with no populated contact traces.
     */
    public ContactTracer() {
        traces = new ArrayList<>();
    }

    /**
     * Initialises the ContactTracer and populates the internal data structures
     * with the given list of contract traces.
     * 
     * @param traces to populate with
     * @require traces != null
     */
    public ContactTracer(List<Trace> traces) {
        this.traces = traces;
    }

    /**
     * Adds a new contact trace to 
     * 
     * If a contact trace involving the same two people at the exact same time is
     * already stored, do nothing.
     * 
     * @param trace to add
     * @require trace != null
     */
    public void addTrace(Trace trace) {
        this.traces.add(trace);
    }

    /**
     * Gets a list of times that person1 and person2 have come into direct 
     * contact (as per the tracing data).
     *
     * If the two people haven't come into contact before, an empty list is returned.
     * 
     * Otherwise the list should be sorted in ascending order.
     * 
     * @param person1 
     * @param person2
     * @return a list of contact times, in ascending order.
     * @require person1 != null && person2 != null
     */
    public List<Integer> getContactTimes(String person1, String person2) {
        List<Integer> timeList = new ArrayList<>();

        for (Trace trace : traces) {

            // if both names match given person1 and person2
            if (trace.getPerson1().equals(person1) && trace.getPerson2().equals(person2)) {
                timeList.add(trace.getTime());
            } else if (trace.getPerson2().equals(person1) && trace.getPerson1().equals(person2)) {
                timeList.add(trace.getTime());
            }
        }

        // sort time by ascending order
        Collections.sort(timeList);

        return timeList;
    }

    /**
     * Gets all the people that the given person has been in direct contact with
     * over the entire history of the tracing dataset.
     * 
     * @param person to list direct contacts of
     * @return set of the person's direct contacts
     */
    public Set<String> getContacts(String person) {
        Set<String> set = new HashSet<>();

        for (Trace trace : traces) {

            // if name matches given persons name
            if (trace.getPerson1().equals(person)) {
                set.add(trace.getPerson2());
            } else if (trace.getPerson2().equals(person)) {
                set.add(trace.getPerson1());
            }
        }
        
        return set;
    }

    /**
     * Gets all the people that the given person has been in direct contact with
     * at OR after the given timestamp (i.e. inclusive).
     * 
     * @param person to list direct contacts of
     * @param timestamp to filter contacts being at or after
     * @return set of the person's direct contacts at or after the timestamp
     */
    public Set<String> getContactsAfter(String person, int timestamp) {
        Set<String> totalContact;
        Set<String> set = new HashSet<>();
        List<Integer> timeList;

        Iterator iterator;
        String personTo;

        totalContact = getContacts(person);
        iterator = totalContact.iterator();

        while (iterator.hasNext()) {
            personTo = (String) iterator.next();
            timeList = getContactTimes(personTo, person);

            for (Integer time : timeList) {
                // if contacted after the given timestamp
                if (time >= timestamp)
                    set.add(personTo);
            }
        }

        return set;
    }

    /**
     * Initiates a contact trace starting with the given person, who
     * became contagious at timeOfContagion.
     * 
     * Note that the return set shouldn't include the original person the trace started from.
     * 
     * @param person to start contact tracing from
     * @param timeOfContagion the exact time person became contagious
     * @return set of people who may have contracted the disease, originating from person
     */
    public Set<String> contactTrace(String person, int timeOfContagion) {
        Set<String> set = new HashSet<>();

        // Track and update the earliest contagion time
        HashMap<String, Integer> conTime = new HashMap<>();

        // Initialize all contagion time to Integer.MAX_VALUE
        for (Trace trace : traces) {
            conTime.put(trace.getPerson1(), Integer.MAX_VALUE);
            conTime.put(trace.getPerson2(), Integer.MAX_VALUE);
        }

        // initial person and time of contagion
        conTime.put(person, timeOfContagion);

        // depth first search method
        helper(set, conTime, person, timeOfContagion);

        return set;
    }

    /**
     *  A helper function that recursively detects and updates person's
     *  contagious time
     *
     * @param set to start contact tracing from
     * @param conTime contains the time of each person getting contagious
     * @param person the person who will be checked
     * @param timeOfContagion the exact time person became contagious
     */
    private void helper(Set<String> set, HashMap<String, Integer> conTime, String person, int timeOfContagion) {
        String personTo;
        Iterator iterator;

        iterator = getContactsAfter(person, timeOfContagion).iterator();

        while (iterator.hasNext()) {
            personTo = (String) iterator.next();
            List<Integer> timeList = getContactTimes(personTo, person);

            for (Integer time : timeList) {

                // contact time >=(later) contagion time of person && potential contagion time of person to personTo
                // is earlier than the old contagion time of personTo
                if (time >= conTime.get(person) && conTime.get(personTo) > time + 60) {
                    conTime.put(personTo, time + 60);
                    set.add(personTo);
                    helper(set, conTime, personTo, time + 60);
                }
            }
        }
    }
}
