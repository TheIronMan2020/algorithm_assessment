import static org.junit.Assert.*;

import java.util.List;
import org.junit.Test;

public class FactCheckerTest {
    
    @Test
    public void testSimpleInconsistent() {
        List<Fact> facts = List.of(
                new Fact(Fact.FactType.TYPE_ONE, "a", "b"),
                new Fact(Fact.FactType.TYPE_TWO, "a", "b")
        );


        assertFalse(FactChecker.areFactsConsistent(facts));
    }

    @Test
    public void testSimpleConsistent() {
        List<Fact> facts = List.of(
                new Fact(Fact.FactType.TYPE_ONE, "a", "b"),
                new Fact(Fact.FactType.TYPE_ONE, "b", "c")
        );

        assertTrue(FactChecker.areFactsConsistent(facts));
    }
    
    @Test
    public void testConsistent1() {
        List<Fact> facts = List.of(
                new Fact(Fact.FactType.TYPE_ONE, "Anna", "Kenton"),
                new Fact(Fact.FactType.TYPE_TWO, "Kenton", "Katya"),
                new Fact(Fact.FactType.TYPE_TWO, "Katya", "Sanni"),
                new Fact(Fact.FactType.TYPE_ONE, "Sanni", "Matt"),
                new Fact(Fact.FactType.TYPE_TWO, "Matt", "Max")
        );
        
        assertTrue(FactChecker.areFactsConsistent(facts));
    }

    @Test
    public void testInconsistent1() {
        List<Fact> facts = List.of(
                new Fact(Fact.FactType.TYPE_ONE, "Anna", "Kenton"),
                new Fact(Fact.FactType.TYPE_TWO, "Kenton", "Katya"),
                new Fact(Fact.FactType.TYPE_TWO, "Katya", "Sanni"),
                new Fact(Fact.FactType.TYPE_ONE, "Sanni", "Matt"),
                new Fact(Fact.FactType.TYPE_TWO, "Matt", "Max"),
                new Fact(Fact.FactType.TYPE_ONE, "Max", "Sanni")
        );

        assertFalse(FactChecker.areFactsConsistent(facts));
    }

    @Test
    public void t() {
        List<Fact> facts = List.of(
                new Fact(Fact.FactType.TYPE_ONE, "a", "b"),
                new Fact(Fact.FactType.TYPE_ONE, "b", "d"),
                new Fact(Fact.FactType.TYPE_TWO, "d", "c"),
                new Fact(Fact.FactType.TYPE_TWO, "a", "c")
        );

        assertTrue(FactChecker.areFactsConsistent(facts));
    }

    @Test
    public void t2() {
        List<Fact> facts = List.of(
                new Fact(Fact.FactType.TYPE_ONE, "a", "b"),
                new Fact(Fact.FactType.TYPE_TWO, "b", "c"),
                new Fact(Fact.FactType.TYPE_ONE, "c", "d"),
                new Fact(Fact.FactType.TYPE_TWO, "d", "a")
        );

        assertFalse(FactChecker.areFactsConsistent(facts));
    }
}