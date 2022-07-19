import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static java.lang.Integer.parseInt;
import static java.util.Arrays.stream;
import static java.util.function.Predicate.not;

public class Port {
    private String[] indexes;
    private List<int[]> combineIndexes = new ArrayList<>();
    final Pattern indexPattern = Pattern.compile("\\d+-\\d+|\\d+");

    public Port(String[] indexes) {
        this.indexes = indexes;
        stream(indexes).filter(not(indexPattern.asMatchPredicate())).findFirst().ifPresent(incorrectIndex -> {
            throw new IllegalArgumentException("Invalid indexes. Please write correct data. Wrong element: " + incorrectIndex);
        });
    }

    public int[][] getProcessedIndexes() {
        int[][] resultArray = new int[indexes.length][];
        for (int i = 0; i < indexes.length; i++) {
            resultArray[i] = getSequenceNumbers(indexes[i]);
        }
        return resultArray;
    }

    private int[] getSequenceNumbers(String indexes) {
        return stream(indexes.split(",")).map(index -> {
            final boolean indexIsRange = index.contains("-");
            if (indexIsRange) {
                final String[] range = index.split("-");
                final int leftBoundInclusive = parseInt(range[0]);
                final int rightBoundExclusive = parseInt(range[1]) + 1;
                return IntStream.range(leftBoundInclusive, rightBoundExclusive).toArray();
            } else {
                return new int[]{parseInt(index)};
            }
        }).flatMapToInt(Arrays::stream).toArray();
    }

    private void combineMethod(List<int[]> input, int[] current, int k) {
        if (k == input.size()) {
            combineIndexes.add(current.clone());
        } else {
            for (int j = 0; j < input.get(k).length; j++) {
                current[k] = input.get(k)[j];
                combineMethod(input, current, k + 1);
            }
        }
    }

    /**
     * Generates non-repeating sequences by indexes numbers
     *
     * @return array of non-repeating sequences
     */
    public int[][] getCombineSequences() {
        List<int[]> input = Arrays.asList(getProcessedIndexes());
        combineMethod(input, new int[input.size()], 0);
        int[][] resultArray = new int[combineIndexes.size()][];
        Arrays.setAll(resultArray, i -> combineIndexes.get(i));
        return resultArray;
    }
}
