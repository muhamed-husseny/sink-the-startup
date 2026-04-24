import java.util.*;

public class GameHelper {
    private static final String ALPHABET = "abcdefg";
    private static final int GRID_LENGTH = 7;
    private static final int GRID_SIZE = 49;
    private static final int MAX_ATTEMPTS = 200;
    static final int HORIZONTAL_INCREMENT = 1;
    static final int VERTICAL_INCREMENT = GRID_LENGTH;

    private final int[] grid = new int[GRID_SIZE];
    private final Random random = new Random();
    private final Scanner scanner = new Scanner(System.in);

    public String getUserInput(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine().toLowerCase();
    }

    public List<String> placeStartup(int startupSize) {
        int[] startupCoords = new int[startupSize];
        int attempts = 0;
        boolean success = false;
        int increment = getIncrement();

        while (!success && attempts++ < MAX_ATTEMPTS) {
            int location = random.nextInt(GRID_SIZE);
            for (int i = 0; i < startupCoords.length; i++) {
                startupCoords[i] = location;
                location += increment;
            }
            if (startupFits(startupCoords, increment)) {
                success = coordsAvailable(startupCoords);
            }
        }
        savePositionToGrid(startupCoords);
        return convertCoordsToAlphaFormat(startupCoords);
    }

    private boolean startupFits(int[] startupCoords, int increment) {
        int finalLocation = startupCoords[startupCoords.length - 1];
        if (increment == HORIZONTAL_INCREMENT) {
            return calcRowFromIndex(startupCoords[0]) == calcRowFromIndex(finalLocation);
        } else {
            return finalLocation < GRID_SIZE;
        }
    }

    private boolean coordsAvailable(int[] startupCoords) {
        for (int coord : startupCoords) {
            if (grid[coord] != 0) {
                return false;
            }
        }
        return true;
    }

    private void savePositionToGrid(int[] startupCoords) {
        for (int index : startupCoords) {
            grid[index] = 1;
        }
    }

    private List<String> convertCoordsToAlphaFormat(int[] startupCoords) {
        List<String> alphaCells = new ArrayList<>();
        for (int index : startupCoords) {
            alphaCells.add(getAlphaCoordsFromIndex(index));
        }
        return alphaCells;
    }

    private String getAlphaCoordsFromIndex(int index) {
        int row = calcRowFromIndex(index);
        int column = index % GRID_LENGTH;
        String letter = ALPHABET.substring(row, row + 1);
        return letter + column;
    }

    private int calcRowFromIndex(int index) {
        return index / GRID_LENGTH;
    }

    private int getIncrement() {
        return random.nextBoolean() ? HORIZONTAL_INCREMENT : VERTICAL_INCREMENT;
    }
}