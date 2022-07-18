import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PortTest {
    final String[] strings = new String[]{"1-2", "3-4", "5-6"};
    final String[] wrongStrings = new String[]{"1- 2", "3-4", "5-6wrong"};
    final Port port = new Port(strings);
    final int[][] result1 = new int[][]{{1, 2}, {3, 4}, {5, 6}};
    final int[][] result2 = new int[][]{{1, 3, 5}, {1, 3, 6}, {1, 4, 5}, {1, 4, 6}, {2, 3, 5}, {2, 3, 6}, {2, 4, 5}, {2, 4, 6}};

    @Test
    void should_throw_exception_for_invalid_input_indexes_data() {
        var exception = assertThrows(IllegalArgumentException.class,
                () -> new Port(wrongStrings));
        assertEquals(exception.getMessage(), "Invalid indexes. Please write correct data");
    }

    @Test
    void should_return_indexes_array() {
        assertArrayEquals(port.getIndexes(), result1);
    }

    @Test
    void should_return_non_repeating_sequences_array_by_indexes() {
        assertArrayEquals(port.getCombineSequences(), result2);
    }
}
