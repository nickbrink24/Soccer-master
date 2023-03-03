package cs301.Soccer;

import android.util.Log;

import cs301.Soccer.soccerPlayer.SoccerPlayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

/**
 * Soccer player database -- presently, all dummied up
 *
 * @author *** put your name here ***
 * @version *** put date of completion here ***
 */
public class SoccerDatabase implements SoccerDB {

    // dummied up variable; you will need to change this
    private Hashtable<String, SoccerPlayer> database = new Hashtable<>();

    /**
     * add a player
     *
     * @see SoccerDB#addPlayer(String, String, int, String)
     */
    @Override
    public boolean addPlayer(String firstName, String lastName,
                             int uniformNumber, String teamName) {

        String player = firstName + "#" + lastName;
        if (database.containsKey(player)) {
            return false;
        } else {
            SoccerPlayer newPlayer = new SoccerPlayer(firstName, lastName, uniformNumber, teamName);
            database.put(player, newPlayer);
            return true;
        }
    }

    /**
     * remove a player
     *
     * @see SoccerDB#removePlayer(String, String)
     */
    @Override
    public boolean removePlayer(String firstName, String lastName) {
        String player = firstName + "#" + lastName;
        if (database.containsKey(player)) {
            database.remove(player);
            return true;
        } else {
            return false;
        }
    }

    /**
     * look up a player
     *
     * @see SoccerDB#getPlayer(String, String)
     */
    @Override
    public SoccerPlayer getPlayer(String firstName, String lastName) {
        String player = firstName + "#" + lastName;
        if (database.containsKey(player)) {
            return database.get(player);
        } else {
            return null;
        }
    }

    /**
     * increment a player's goals
     *
     * @see SoccerDB#bumpGoals(String, String)
     */
    @Override
    public boolean bumpGoals(String firstName, String lastName) {
        String player = firstName + "#" + lastName;
        if (database.containsKey(player)) {
            SoccerPlayer player1 = database.get(player);
            player1.bumpGoals();
            return true;
        } else {
            return false;
        }
    }

    /**
     * increment a player's yellow cards
     *
     * @see SoccerDB#bumpYellowCards(String, String)
     */
    @Override
    public boolean bumpYellowCards(String firstName, String lastName) {
        String player = firstName + "#" + lastName;
        if (database.containsKey(player)) {
            SoccerPlayer player1 = database.get(player);
            player1.bumpYellowCards();
            return true;
        } else {
            return false;
        }
    }

    /**
     * increment a player's red cards
     *
     * @see SoccerDB#bumpRedCards(String, String)
     */
    @Override
    public boolean bumpRedCards(String firstName, String lastName) {
        String player = firstName + "#" + lastName;
        if (database.containsKey(player)) {
            SoccerPlayer player1 = database.get(player);
            player1.bumpRedCards();
            return true;
        } else {
            return false;
        }
    }

    /**
     * tells the number of players on a given team
     *
     * @see SoccerDB#numPlayers(String)
     */
    @Override
    // report number of players on a given team (or all players, if null)
    public int numPlayers(String teamName) {
        if (teamName == null) {
            return database.size();
        } else {
            int count = 0;
            for (SoccerPlayer player : database.values()) {
                if (player.getTeamName().equals(teamName)) {
                    count++;
                }
            }

            return count;
        }
    }

    /**
     * gives the nth player on a the given team
     *
     * @see SoccerDB#playerIndex(int, String)
     */
    // get the nTH player
    @Override
    public SoccerPlayer playerIndex(int idx, String teamName) {
        SoccerPlayer ret = null;

        if (idx > database.size()) {
            return null;
        }

        int count = 0;
        if (teamName == null) {
            for (SoccerPlayer player : database.values()) {
                if (count == idx) {
                    ret = player;
                }

                count++;
            }
        } else {
            for (SoccerPlayer player : database.values()) {
                if (player.getTeamName().equals(teamName)) {
                    if (count == idx) {
                        ret = player;
                    }

                    count++;
                }
            }
        }

        return ret;
    }

    /**
     * reads database data from a file
     *
     * @see SoccerDB#readData(java.io.File)
     */
    // read data from file
    @Override
    public boolean readData(File file) {
        Scanner scan = null;

        try {
            scan = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        while (scan.hasNextLine()) {
            String firstName = scan.nextLine();
            String lastName = scan.nextLine();
            String teamName = scan.nextLine();
            int jerseyNum = scan.nextInt();
            int goals = scan.nextInt();
            int redCards = scan.nextInt();
            int yellowCards = scan.nextInt();

            String player = firstName + "#" + lastName;
            if (database.containsKey(player)) {
                database.remove(player);
            }

            SoccerPlayer player1 = new SoccerPlayer(firstName, lastName, jerseyNum, teamName);

            for (int i = 0; i < goals; i++) {
                player1.bumpGoals();
            }

            for (int i = 0; i < redCards; i++) {
                player1.bumpRedCards();
            }

            for (int i = 0; i < yellowCards; i++) {
                player1.bumpYellowCards();
            }

            database.put(player, player1);
            String unneccessary = scan.nextLine();
        }

        scan.close();
        return true;
    }

    /**
     * write database data to a file
     *
     * @see SoccerDB#writeData(java.io.File)
     */
    // write data to file
    @Override
    public boolean writeData(File file) {
        PrintWriter pw = null;

        try {
            pw = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        for (SoccerPlayer player : database.values()) {
            pw.println(player.getFirstName());
            pw.println(player.getLastName());
            pw.println(player.getTeamName());
            pw.println(Integer.toString(player.getUniform()));
            pw.println(Integer.toString(player.getGoals()));
            pw.println(Integer.toString(player.getRedCards()));
            pw.println(Integer.toString(player.getYellowCards()));
        }

        pw.close();
        return true;
    }

    /**
     * helper method that logcat-logs a string, and then returns the string.
     *
     * @param s the string to log
     * @return the string s, unchanged
     */
    private String logString(String s) {
        //Log.i("write string", s);
        return s;
    }

    /**
     * returns the list of team names in the database
     *
     * @see cs301.Soccer.SoccerDB#getTeams()
     */
    // return list of teams
    @Override
    public HashSet<String> getTeams() {
        HashSet<String> ret = new HashSet<>();
        for (SoccerPlayer player : database.values()) {
            if (!ret.contains(player.getTeamName())) {
                ret.add(player.getTeamName());
            }
        }

        return ret;
    }

    /**
     * Helper method to empty the database and the list of teams in the spinner;
     * this is faster than restarting the app
     */
    public boolean clear() {
        if (database != null) {
            database.clear();
            return true;
        }
        return false;
    }
}
