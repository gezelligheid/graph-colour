import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This clique finder starts to search trivial cliques of one node. The 
 * algorithm caches the largest tentative clique of size {@code k}. If at some
 * point it cannot find a clique of size {@code k + 1}, it returns the cached
 * clique. Needless to say, this is algorithm is best applied to sparse graphs.
 *
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Jul 24, 2017)
 */
public final class SparseGraphLargestCliqueFinder
        extends AbstractLargestCliqueFinder {

    @Override
    public int[] computeLargestClique(Graph graph) {
        Objects.requireNonNull(graph, "The input graph is null.");
        checkGraphNotEmpty(graph);

        int[] nodes = getNodeArray(graph);
        List<Integer> clique = new ArrayList<>(graph.getSize());
        List<Integer> bestClique = new ArrayList<>(graph.getSize());
        ForwardIntCombinationIterator iterator =
                new ForwardIntCombinationIterator(nodes);

        while (iterator.loadNextCombination(clique)) {
            if (iterator.combinationSize() > clique.size() + 1) {
                break;
            }

            if (isClique(graph, clique) && bestClique.size() < clique.size()) {
                bestClique.clear();
                bestClique.addAll(clique);
                if (clique.size() > 4)
                    System.out.println("NEW BEST LOWER BOUND = " + clique.size());
            }
        }

        return intListToIntArray(bestClique);
    }
}