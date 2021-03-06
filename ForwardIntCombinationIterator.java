/**
 * Child class of the AbstractIntegerCombinationIterator that is used for a sparse graph largest clique finder
 * <p>
 * most credits go to:
 *
 * @author Rodion "rodde" Efremov
 * * @version 1.6 (Jul 24, 2017)
 */
public final class ForwardIntCombinationIterator
        extends AbstractIntegerCombinationIterator {

    public ForwardIntCombinationIterator(int[] allInts) {
        super(allInts);
        this.currentCombinationSize = 1;
    }

    @Override
    protected boolean done() {
        return currentCombinationSize == allInts.length + 1;
    }

    @Override
    protected void updateIndices() {
        if (indices[currentCombinationSize - 1] < indices.length - 1) {
            indices[currentCombinationSize - 1]++;
            return;
        }

        for (int i = currentCombinationSize - 2; i >= 0; --i) {
            if (indices[i] < indices[i + 1] - 1) {
                indices[i] ++;

                for (int j = i + 1; j < currentCombinationSize; ++j) {
                    indices[j] = indices[j - 1] + 1;
                }

                return;
            }
        }

        ++currentCombinationSize;

        if (currentCombinationSize <= allInts.length) {
            for (int i = 0; i < currentCombinationSize; ++i) {
                indices[i] = i;
            }
        }
    }
}