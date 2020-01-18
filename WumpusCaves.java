import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * This project is an adventure game inspired by both the classic Hunt the
 * Wumpus game and the Tham Luang cave rescue.
 * https://en.wikipedia.org/wiki/Hunt_the_Wumpus
 * https://en.wikipedia.org/wiki/Tham_Luang_cave_rescue
 * 
 * @author Jim Williams
 * @author TODO Andrew Rodriguez-Solis
 */
public class WumpusCaves {

	/**
	 * Whether the game is search and rescue or hunt the wumpus.
	 */
	enum GameMode {
		HUNT, RESCUE
	};

	/**
	 * Update the location parameter based on the direction. The cave is in the
	 * shape of a torus meaning it wraps all directions. Movement in any direction
	 * (n,s,e,w) is handled by this method.
	 * 
	 * @param caveExplored The cave being explored.
	 * @param locChange    The current row and column that are changed, based on the
	 *                     direction, to the new row and column.
	 * 
	 * @param direction    Either "n","s","e" or "w" Config.ROW is 0 Config.COLUMN
	 *                     is 1
	 * 
	 */
	public static void move(char[][] caveExplored, int[] locChange, String direction) {

		switch (direction) {
		case "n":
			locChange[Config.ROW]--;
			if (locChange[Config.ROW] < 0)
				locChange[Config.ROW] = caveExplored.length - 1;
			break;
		case "s":
			locChange[Config.ROW]++;
			if (locChange[Config.ROW] >= caveExplored.length)
				locChange[Config.ROW] = 0;
			break;
		case "e":
			locChange[Config.COLUMN] = ++locChange[1] % caveExplored[0].length;
			break;
		case "w":
			locChange[Config.COLUMN]--;
			if (locChange[Config.COLUMN] < 0)
				locChange[Config.COLUMN] = caveExplored[0].length - 1;
			break;
		}
	}

	/**
	 * Algorithm: based on the users position and cave we are given its perceptions.
	 * If the user is close to a bat we "feel a rustling" If the user is close to a
	 * pit we "feel a draft" If we are in rescue mode we "hear a child snoring" but
	 * if we are in hunt mode we smell "an awful smell"
	 * 
	 * The switch statement is used to verify the if we are facing a bat, pit,
	 * wumpus/kid
	 * 
	 * @param exploringCave the cave being explored by the user
	 * @param locationUser  the location of the user
	 * @param mode          the mode the user is in
	 */
	public static void usersPerceptions(char[][] exploringCave, int[] locationUser, GameMode mode) {
		boolean[] perceptions = new boolean[Config.numPerceptions];
		String[] direction = { "n", "s", "e", "w" };

		for (int i = 0; i < direction.length; i++) {
			int[] tempArr = Arrays.copyOf(locationUser, locationUser.length);
			move(exploringCave, tempArr, direction[i]);

			switch (exploringCave[tempArr[0]][tempArr[1]]) {
			case 'p':
				perceptions[Config.PerceivePIT] = true;
				break;
			case 'b':
				perceptions[Config.PerceiveBAT] = true;
				break;
			case 'w':
				perceptions[Config.PerceiveWUMPUS] = true;
				break;
			}
		}

		if (perceptions[Config.PerceiveBAT]) {
			System.out.println("you hear a rustling");
		}
		if (perceptions[Config.PerceivePIT]) {
			System.out.println("you feel a draft");
		}
		if (perceptions[Config.PerceiveWUMPUS]) {
			if (mode == GameMode.RESCUE) {
				System.out.println("you hear a child snoring");
			} else {
				System.out.println("there's an awful smell");
			}
		}

	}

	;

	/**
	 * Algorithm: When the user lands on a bat, the user is moved to a random
	 * direction within the cave. The while loop is executed when the user is not in
	 * an empty space.
	 * 
	 * @param rand          randomizes the location of the user when we land on a
	 *                      bat
	 * @param caveExploring the cave being explored by the user
	 * @param theLoc        the location of the user in the cave
	 * 
	 * 
	 */
	public static void batMovedYou(Random rand, char[][] caveExploring, int[] theLoc) {

		while (caveExploring[theLoc[Config.ROW]][theLoc[Config.COLUMN]] != ' ') {
			theLoc[Config.ROW] = rand.nextInt(caveExploring.length);
			theLoc[Config.COLUMN] = rand.nextInt(caveExploring[0].length);
		}

		System.out.println("A huge bat picked you up and dropped you in another room...");
	}

	/**
	 * Prints out the result of the action of moving to the current location.
	 * 
	 * Algorithm: This method is used to identify the status of the position the
	 * user is currently in. The bat, pit and wumpus conditions all have different
	 * outcomes
	 * 
	 * @param randNum        A random number generator used to randomize position
	 *                       when we land on a bat
	 * @param caveToExplore  The cave being explored.
	 * @param playerLocation The current location of the player
	 * @param mode           Whether rescuing a child or hunting the wumpus
	 * @return true if alive, otherwise false.
	 */
	public static boolean status(Random randNum, char[][] caveToExplore, int[] playerLocation, GameMode mode) {
		if (caveToExplore[playerLocation[Config.ROW]][playerLocation[Config.COLUMN]] == Config.BAT) {
			batMovedYou(randNum, caveToExplore, playerLocation);
		}

		else if (caveToExplore[playerLocation[Config.ROW]][playerLocation[Config.COLUMN]] == Config.PIT) {
			System.out.println("You fell into a pit.");
			return false;
		}

		System.out.println("room " + playerLocation[Config.ROW] + "" + playerLocation[Config.COLUMN]);

		// if the player lands on wumpus or kid
		if (caveToExplore[playerLocation[Config.ROW]][playerLocation[Config.COLUMN]] == Config.WUMPUS) {
			if (mode == GameMode.RESCUE) {
				System.out.println("You've found the child safe and happy to see you!");
			} else {
				System.out.println("You've been eaten by the Wumpus.");
			}
			return false;
		} else {
			// Calling usersPerceptions here
			usersPerceptions(caveToExplore, playerLocation, mode);
		}
		return true;
	}

	/**
	 * Algorithm: The user is prompted to choose a game mode. Based on the GameMode,
	 * we print different messages.
	 * 
	 * @param scan is used to pass the users input
	 * @return
	 */
	public static GameMode chooseGameMode(Scanner scan) {

		GameMode mode = GameMode.HUNT;

		if (scan.nextLine().trim().toLowerCase().charAt(0) == 'r') {
			mode = GameMode.RESCUE;
		}
		System.out.println("Thank you for coming to help us at Wumpus Caves.");
		if (mode == GameMode.RESCUE) {
			System.out.println("A child wandered into the cave and has not returned.");
			System.out.println("Please help us find our child!");
		} else {
			System.out.println("A Wumpus comes out of the cave at night and");
			System.out.println("attacks the villagers. Please hunt it down.");
		}
		System.out.println();
		return mode;
	}

	/**
	 * Summary: Users mode, cave, action and introduction
	 * 
	 * Algorithm: This method prompts the user to choose a GameMode which is why
	 * chooseGameMode() is called. The users is also prompted to enter the number of
	 * the cave, if the cave is not accessible, we prompt the user again. While the
	 * user is alive the switch statement gathers the choice the user wants to make
	 * and calls methods based on the input. When the while statement is false that
	 * means we are dead and we exit out of the game
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		Random someNUM = new Random(Config.SEED);

		// The mode the user decides to enter
		System.out.print("Would you like to go on a hunt or rescue a child (h/r): ");
		GameMode mode = chooseGameMode(scan);
		// The cavern chosen by the user at the start of the game
		char[][] cavernByUser = Config.CAVES[0];
		// the number of the cavern chosen
		int cavernNum = 0;
		// boolean is assigned to true when the size of the cave is valid
		boolean caveSize = false;
		while (!caveSize) {
			System.out.print("Please enter the number of the cave to enter: ");
			cavernNum = scan.nextInt();
			scan.nextLine();
			if (cavernNum >= 1 && cavernNum <= Config.CAVES.length) {
				caveSize = true;
			}
		}
		cavernByUser = Config.CAVES[cavernNum - 1];

		System.out.println();
		System.out.print("Use your senses to find your way in the cave. ");
		System.out.println("Beware of the huge bats");
		System.out.println("and the bottomless pits. Good Luck!\n");
		System.out.println("You enter the cave...");
		// the users location in the cavern
		int[] usersLocation = { 0, 0 };

		while (status(someNUM, cavernByUser, usersLocation, mode)) {

			System.out.print("action: ");
			// the direction the user wants to go to
			String userChoice = scan.nextLine().trim().toLowerCase();
			switch (userChoice) {
			case "n":
			case "s":
			case "e":
			case "w":
				move(cavernByUser, usersLocation, userChoice);
				break;
			case "g":

				// boolean stores the result of what happens when we use equipment
				boolean outcome = equipOutcome(scan, mode, usersLocation, cavernByUser);
				if (outcome) {
					return;
				}
				break;
			default:
				if (mode == GameMode.RESCUE) {
					System.out.println("Move (nsew) or get rope (g).");
				} else {
					System.out.println("Move (nsew) or get arrow (g).");
				}
				break;
			}
		}
		System.out.println("Thanks for playing Wumpus Caves!");
		scan.close();
	}

	/**
	 * Algorithm: This method returns the outcome of the equipment the user decides
	 * to use. Based on the GameMode the users actions have different effects when
	 * using equipments. If the GameMode is h and the user shoots Wumpus is killed,
	 * if the GamMode is r the user throws a rope and if the rope lands on the kid,
	 * then the game is won.
	 * 
	 * @param scan      scanner to take the users equipment of the direction
	 * @param mode      the mode of the game
	 * @param usersLoc  the users location in the cave
	 * @param cavByUser the cavern selected by the user
	 */

	public static boolean equipOutcome(Scanner scan, GameMode mode, int[] usersLoc, char[][] cavByUser) {
		if (mode == GameMode.RESCUE) {
			System.out.print("What direction to throw rope (nsew): ");
		} else {
			System.out.print("What direction to fire arrow (nsew): ");
		}

		String equipmentDirection = scan.nextLine().trim().toLowerCase();
		boolean success = false;
		if (mode == GameMode.RESCUE) {
			System.out.println("Rope flies " + equipmentDirection + "");
		} else {
			System.out.println("Arrow flies " + equipmentDirection + "");
		}

		int[] equipmentLocation = Arrays.copyOf(usersLoc, usersLoc.length);

		// the location the equipment goes to and the direction it was sent to
		move(cavByUser, equipmentLocation, equipmentDirection);

		if (cavByUser[equipmentLocation[Config.ROW]][equipmentLocation[Config.COLUMN]] == Config.WUMPUS) {
			if (mode == GameMode.RESCUE) {
				System.out.println(
						"Congratulations! The child grabbed the rope and " + "you brought safely out of the cave!");
			} else {
				System.out.println("Congratulations! You killed the Wumpus and saved the villagers"
						+ " from their nightly terror.");
			}
			success = true;
		}

		if (success) {
			System.out.println("Hopefully, you can now find your way out of the cave....");
			return success;
		}
		return false;
	}
}

