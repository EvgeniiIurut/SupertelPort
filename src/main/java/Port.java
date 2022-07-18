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

    //converts indexes to int[][] type
    public int[][] getIndexes() {
        int[][] ints = new int[indexes.length][];
        for (int i = 0; i < indexes.length; i++) {
            ints[i] = getSequenceNumbers(indexes[i]);
        }
        return ints;
    }

    private int[] getSequenceNumbers(String index) {
        String[] strings = index.split(",");
        List<Integer> list = new ArrayList<>();
        for (String string : strings) {
            if (string.contains("-")) {
                String[] numbersSeparatedByMinus = string.split("-");
                int leftBorder = Integer.parseInt(numbersSeparatedByMinus[0]);
                int rightBorder = Integer.parseInt(numbersSeparatedByMinus[1]);
                for (int j = leftBorder; j <= rightBorder; j++) {
                    list.add(j);
                }
            } else list.add(Integer.parseInt(string));
        }
        return list.stream().mapToInt(i -> i).toArray();
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

    //generates non-repeating sequences by indexes numbers
    public int[][] getCombineSequences() {
        List<int[]> input = Arrays.asList(getIndexes());
        combineMethod(input, new int[input.size()], 0);
        int[][] arr = new int[combineIndexes.size()][];
        Arrays.setAll(arr, i -> combineIndexes.get(i));
        return arr;
    }
}
