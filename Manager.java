
import java.util.Arrays;

public class Manager {

	// ATRIBUTES

	private int numPlayers;
	private int numDays;
	private Card[] availibleDeck;

	// CONSTRUCTORS

	public Manager() {}

	public Manager(int numPlayers, int numDays, Card[] availibleDeck) {

		this.numPlayers = numPlayers;
		this.numDays = numDays;
		this.availibleDeck = availibleDeck;

	}

	// SETTERS
	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}

	public void setNumDays(int numDays) {
		this.numDays = numDays;
	}

	public void setAvailibleDeck(Card[] availibleDeck) {

		this.availibleDeck = availibleDeck;

	}

	// GETTERS

	public int getNumPlayers() {
		return numPlayers;
	}

	public int getNumDays() {
		return numDays;
	}

	public Card[] getAvailibleDeck() {
		return this.availibleDeck;
	}

	// METHODS

	/*
	 * startGame: DONE
	 * takes in a number of players, their chosen colors, num of dollars/credits
	 * a starting rank, the trailer, and turn number, then initilizes the players
	 * in the game
	 * RETURNS: array of initialized players
	 */

	public Player[] startGame(int numPlayers, String[] colors, int dollars, int credits, int rank, Room trailer) {

		Player[] players = new Player[numPlayers];

		for (int i = 0; i < numPlayers; i++) {
			players[i] = new Player(null, rank, credits, dollars, false, trailer, 0,null,null,null);
		}

		return players;
	}

	/*
	 * 
	 * endGame():
	 * ends the current game tells view to print rankings and
	 * goes back to main menu
	 */

	public void endGame(Player[] players) {

		// Tell the view to print out player stats

		// check if final day is over

		// if true

		// calculate each player's points, store in array

		// sort array

		// choose the player with the most points as the winner

	}

	/*
	 * calcUserPoints(): DONE
	 * user's points will be calculated by
	 * 1 point for every dollar + 1 point for every credit + (5 * userRank)
	 * This will return an int representing the amount of points a player has
	 */

	public int calcUserPoints(Player player) {

		int cPoints = player.getCredits();
		int dPoints = player.getCredits();
		int rPoints = player.getRank();
		
		return cPoints + dPoints + (5 * rPoints);
	}

	/*
	 * chooseTurnsRoll(): DONE
	 * This function will roll a 1000 sided die that will ensure a player
	 * will not get the same number when deciding on player order
	 */

	public int chooseTurnsRoll() {

		int turns = (int) Math.floor((Math.random() * 1000) + 1);
		return turns;

	}

}
