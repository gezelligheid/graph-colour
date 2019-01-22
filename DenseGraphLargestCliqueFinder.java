import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class implements a clique-finding algorithm that proceeds from larger
 * cliques candidates towards smaller. By construction, the very first clique
 * found is guaranteed to be the largest.
 *
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Jul 24, 2017)
 */
public class DenseGraphLargestCliqueFinder extends AbstractLargestCliqueFinder {
    /**
     * @param graph the graph
     * @return the first and by algorithmic nature largest clique possible
     */
    @Override
    public int[] computeLargestClique(Graph graph) {
        Objects.requireNonNull(graph, "The input graph is null.");
        checkGraphNotEmpty(graph);

        int[] nodes = getNodeArray(graph);
        BackwardIntCombinationIterator iterator =
                new BackwardIntCombinationIterator(nodes);
        List<Integer> clique = new ArrayList<>(graph.getSize());

        while (iterator.loadNextCombination(clique)) {
            if (isClique(graph, clique)) {
                System.out.println("clique size found: " + clique.size());
                break;
            }
        }

        return intListToIntArray(clique);
    }
}