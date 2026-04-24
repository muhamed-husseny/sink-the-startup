import java.util.ArrayList;
import java.util.List;

public class StartupBust {
    private static final int STARTUP_SIZE = 3;

    private final GameHelper helper = new GameHelper();
    private final List<Startup> startups = new ArrayList<>();
    private int numOfGuesses = 0;


    private void setUpGame() {
        String[] startupNames = {"poniez", "hacqi", "cabista"};

        System.out.println("Your goal is to sink " + startupNames.length + " Startups.");
        System.out.println(String.join(", ", startupNames));
        System.out.println("Try to sink them all in the fewest number of guesses.");

        for (String name : startupNames) {
            Startup startup = new Startup();
            startup.setName(name);
            startup.setLocationCells(helper.placeStartup(STARTUP_SIZE));
            startups.add(startup);
        }
    }

    private void startPlaying(){
       while (!startups.isEmpty()){
           String userGuess = helper.getUserInput("Enter a guess");
           checkUserGuess(userGuess);
       }
       finishGame();
    }

    private void checkUserGuess(String userGuess){
         numOfGuesses++;
         String result = "miss";

         for(Startup startupToTest: startups){
             result = startupToTest.checkYourself(userGuess);

             if(result.equals("hit")){
                 break;
             }
             if(result.equals("kill")){
                 startups.remove(startupToTest);
                 break;
             }
         }
         System.out.println(result);
    }

    private void finishGame(){
        System.out.println("All Startups are dead! Your stock is now worthless");
        if (numOfGuesses <= 18) {
            System.out.println("It only took you " + numOfGuesses + " guesses.");
            System.out.println("You got out before your options sank.");
        } else {
            System.out.println("Took you long enough. " + numOfGuesses + " guesses.");
            System.out.println("Fish are dancing with your options");
        }
    }

    public static void main(String[] args) {
        StartupBust game = new StartupBust();
        game.setUpGame();
        game.startPlaying();
    }
}

