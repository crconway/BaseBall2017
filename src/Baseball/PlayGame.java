package Baseball;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by crc07 on 4/29/17.
 */
public class PlayGame extends Utils implements Constants {


    static int innings = 9;             //Starting number of Innings
    int inning;                         //Current inning
    int batter;                         //Current batter
    static int outs = 0;                //Active outs per team per inning
    static int homeScore = 0;           //Home Team score
    static int awayScore = 0;           //Away Team score
    static int team_at_bat = 1;         //Team batting 1 = Away team 2 = Home team
    private static int nextAwayAtBat;   //Holding the next at bat Away team between innings
    private static int nextHomeAtBat;   //Holding the next at bat Home team between innings
    static int runs = 0;                //Runs in the inning
    int nextInning = 0;
    boolean gameOver = false;
    boolean extraInnings = false;       //Flag for tied game to add extra innings
    private static boolean thirdBase;   //Tracking base runners
    private static boolean firstBase;
    private static boolean secondBase;


    //set teams and stat fields
    String homeTeam = "Home Depot";
    String awayTeam = "Family    ";
    String[] homePlayers = {"Kimberly", "David", "John", "Debbie", "Nina", "Pam", "Matt", "Mark", "Colby"};
    String[] awayPlayers = {"Sandy", "Scott", "Tracy", "Mary", "Jaylan", "Bill", "Angie", "Danielle", "Leigh"};
    //Scoreboard fields
    static public List<String> teamScores = new ArrayList<String>();
    static String innScore = "";
    static String scoreAway ="";
    static String scoreHome ="";
    //Team Stats fields
    static public List<String> rowInning = new ArrayList<String>();
    static String batResults = "";

    public void startGame() {

        do {
            //run first 9 Innings extraInning set to false, runs extra Inning if set to true
            runInning(extraInnings);
            checkScore();

        } while(!gameOver);

            teamScores.add(innScore);
            teamScores.add("------------|---------------------------------");
            teamScores.add(scoreAway);
            teamScores.add(scoreHome);
            printService("FINAL SCORE");
            printService("---------------------------");
            printService("VISITOR: " + awayTeam + "  " + awayScore);
            printService("   HOME: " + homeTeam + "  " + homeScore);
            printService("---------------------------\n");
            printService(teamScores);
            printService("\nTEAM ROSTER");
            printService("\n" + awayTeam);
            printService("--------------");
            printService(Arrays.asList(awayPlayers));
            printService("\n" + homeTeam);
            printService("--------------");
            printService(Arrays.asList(homePlayers));
            printService("\nGAME STATS");
            printService(rowInning);

        }

    //check the score and set extraInnings to true if tied and set gameOver to true if not tied
    public void checkScore(){

            if (homeScore == awayScore) {
                extraInnings = true;
            } else {
                gameOver = true;
                if (homeScore > awayScore) {
                    printService("\n" + homeTeam + " wins the game!\n");

                } else {
                    printService("\n" + awayTeam + " wins the game!\n");

                }
            }

        }

    //Method to Run Innings for regular 9 inning and run extra innings if game is tied after the 9th and above innings

    public void runInning(boolean extraInnings) {
        if (extraInnings){
            //add to scoreboard and Stats for extra innings
            innScore += (Integer.toString(inning + 1) + "\t");
            rowInning.add("\nInning: " + (inning + 1));
            rowInning.add("----------------------------------------------");
            rowInning.add(awayTeam);
            team_at_bat = 1;
            halfInning(nextAwayAtBat);
            team_at_bat = 2;
            rowInning.add(homeTeam);
            halfInning(nextHomeAtBat);
        }
        else {
            innScore = "Innings\t\t|";
            scoreAway = (awayTeam + "\t|");
            scoreHome = (homeTeam + "\t|");
            for (inning = nextInning; inning < innings; inning++) {
                //System.out.print("Inning: " + (inning + 1));
                //System.out.println("*******************************");
                if (team_at_bat == 2) {
                    //homeTeam batting
                    rowInning.add(homeTeam);
                    halfInning(nextHomeAtBat);
                } else {
                    //away team batting
                    innScore += (Integer.toString(inning + 1) + "\t");
                    rowInning.add("\nInning: " + (inning + 1));
                    rowInning.add("----------------------------------------------");
                    rowInning.add(awayTeam);
                    //System.out.println("Team up: " + awayTeam);
                    halfInning(nextAwayAtBat);
                    //System.out.println("****** "+awayTeam + " Score " + awayScore + " ******");
                }

            }
        }
    }

    // Running through batting lineup for each team
    public void halfInning(int nextAtBat){
        try {
            this.batter = nextAtBat;
            while (outs < Outs) {
                if(team_at_bat == 1) {
                    batResults += awayPlayers[batter] + " - ";
                }else
                    batResults += homePlayers[batter] + " - ";
                //System.out.print("Batter " +batter+ " results ");
                runBases(batterResults());
                if (batter == 8) {
                    batter = 0;
                } else {
                    batter++;
                }
            } // ends while loop
            rowInning.add(batResults);
            if (team_at_bat == 1) {
                scoreAway += (Integer.toString(runs)+"\t");
                awayScore += runs;
                nextAwayAtBat = batter;
                //System.out.println(awayTeam + " runs: " + runs);
                inning--;
                team_at_bat = 2;
            } else {
                scoreHome += (Integer.toString(runs)+"\t");
                homeScore += runs;
                nextHomeAtBat = batter;
                //System.out.println(homeTeam + " runs: " + runs);
                team_at_bat = 1;
            }
            //reset fields to start next inning
            resetFields();

        }catch (ArrayIndexOutOfBoundsException | NullPointerException e){
            System.out.println("Exception : " +e.getMessage()+e.getCause()+e.getClass());
        }

    }

    //Method to advance runners on base and calculate runs when crossing home plate with hit being passed

    public static void runBases(int hit) {

        switch (hit) {
            case 0:
                outs++;
                batResults += "OUT; ";
                //System.out.println("out; ");
                break;
            case 1:
                if (thirdBase){
                    runs++;
                    thirdBase = false;
                }if (secondBase){
                    thirdBase = true;
                    secondBase = false;
                }if (firstBase){
                    secondBase = true;
                }
                firstBase = true;
                batResults += "SINGLE; ";
               // System.out.println("single; ");
                break;
            case 2:
                if (thirdBase) {
                    runs++;
                    thirdBase = false;
                }
                if (secondBase) {
                    runs++;
                    secondBase = false;
                }
                if (firstBase) {
                    thirdBase = true;
                    firstBase = false;
                }
                secondBase = true;
                batResults += "DOUBLE; ";
               // System.out.println("Double; ");
                break;
            case 3:
                if (thirdBase) {
                    runs++;
                    thirdBase = false;
                }
                if (secondBase) {
                    runs++;
                    secondBase = false;
                }
                if (firstBase) {
                    runs++;
                    firstBase = false;
                }
                batResults += "TRIPLE; ";
                //System.out.println("triple; ");
                thirdBase = true;
                break;
            case 4:
                if (thirdBase & secondBase & firstBase) {
                    batResults += "GRAND SLAM; ";
                    //System.out.println("grand slam; ");
                    runs += 3;
                }
                else {
                    //System.out.println("Home Run; ");
                    batResults += "HOME RUN; ";
                    if (thirdBase)
                        runs++;
                    if (secondBase)
                        runs++;
                    if (firstBase)
                        runs++;
                }
                thirdBase = false;
                secondBase = false;
                firstBase = false;
                runs++;  // run for the batter
                break;
            default:

        }
    }

    // Method to take randomNumber and assign the at bat results returning the value of the hit

    public static int batterResults() {

        int a = randomNumber();

        if (a > UpperOut) {
            if (a <= UpperSingle && a >= LowerSingle) {
                //System.out.println("Batter hit a single");
                return 1;
            } else if (a <= UpperDouble && a >= LowerDouble) {
                //System.out.println("Batter hit a double");
                return 2;
            } else if (a <= UpperTriple && a >= LowerTriple) {
                //System.out.println("Batter hit a triple");
                return 3;
            } else {
                //System.out.println("Batter Hit a Home Run");
                return 4;
            }
        } else {
            //System.out.println("Batter Is out");
            return 0;
        }
    }

    public void resetFields (){

        firstBase = false;
        secondBase = false;
        thirdBase = false;
        outs = 0;
        runs = 0;
        batResults ="";

    }
}
