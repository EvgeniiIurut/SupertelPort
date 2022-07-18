import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Port {
    private String[] indexes;
    private List<int[]> combineIndexes = new ArrayList<>();

    public Port(String[] indexes) {
        this.indexes = indexes;
        Arrays.stream(indexes).filter(s -> !s.matches("[0-9]+-[0-9]+|[0-9]+")).forEach(s -> {
            throw new IllegalArgumentException("Invalid indexes. Please write correct data");
        });
    }

    public int[][] getProcessedIndexes() {
        int[][] resultArray = new int[indexes.length][];
        for (int i = 0; i < indexes.length; i++) {
            resultArray[i] = getSequenceNumbers(indexes[i]);
        }
        return resultArray;
    }

    private int[] getSequenceNumbers(String index) {
        String[] strings = index.split(",");
        List<Integer> result = new ArrayList<>();
        for (String string : strings) {
            if (string.contains("-")) {
                String[] numbersSeparatedByMinus = string.split("-");
                int leftBorder = Integer.parseInt(numbersSeparatedByMinus[0]);
                int rightBorder = Integer.parseInt(numbersSeparatedByMinus[1]);
                for (int j = leftBorder; j <= rightBorder; j++) {
                    result.add(j);
                }
            } else result.add(Integer.parseInt(string));
        }
        return result.stream().mapToInt(i -> i).toArray();
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
