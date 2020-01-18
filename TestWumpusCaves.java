import java.util.Random;
import java.util.Scanner;

public class TestWumpusCaves {

	public static void main(String[] args) {

		testMove();
		testBatMovedYou();
		testChooseGameMode();
	}

	private static void testChooseGameMode() {
		boolean error = false;
		// test 1
		{
			Scanner scan = new Scanner("      RESCUE");
			WumpusCaves.GameMode theTest = WumpusCaves.chooseGameMode(scan);
			if (!(theTest == WumpusCaves.GameMode.RESCUE)) {
				System.out.println("testChooseGameMode: expected RESCUE; actual " + theTest);
				error = true;

			}
		}
		// test 2
		{
			Scanner scan = new Scanner("h U n  t");
			WumpusCaves.GameMode theTest = WumpusCaves.chooseGameMode(scan);
			if (!(theTest == WumpusCaves.GameMode.HUNT)) {
				System.out.println("testChooseGameMode: expected RESCUE; actual " + theTest);
				error = true;

			}
		}
		// test 3
		{
			Scanner scan = new Scanner("the H");
			WumpusCaves.GameMode theTest = WumpusCaves.chooseGameMode(scan);
			if (!(theTest == WumpusCaves.GameMode.HUNT)) {
				System.out.println("testChooseGameMode: expected RESCUE; actual " + theTest);
				error = true;

			}
		}
		{
			// test 4 directs to hunt because that's the default value and because
			// the character at 0 is "t" and not "r"
			Scanner scan = new Scanner("the R");
			WumpusCaves.GameMode theTest = WumpusCaves.chooseGameMode(scan);
			if (!(theTest == WumpusCaves.GameMode.HUNT)) {
				System.out.println("testChooseGameMode: expected HUNT; actual " + theTest);
				error = true;

			}
		}

		if (error) {
			System.out.println("Error in testChooseGameMode");
		} else {
			System.out.println("All tests in testChooseGameMode passed");
		}
	}

	/*
	 * Testing the batMovedYou() here
	 * 
	 */

	private static void testBatMovedYou() {
		Random rand = new Random();
		boolean error = false;
		// test 1
		{
			int[] location = { 1, 1 };
			char[][] cave = { { 'b', 'b', 'b' }, { 'b', 'b', ' ' } };
			WumpusCaves.batMovedYou(rand, cave, location);
			if (!(location[0] == 1 && location[1] == 2)) {
				System.out.println("testBatMovedYou: location expected 1,2; actual " + location[0] + "," + location[1]);
				error = true;
			}

		}
		// test 2
		{
			int[] location = { 1, 1 };
			char[][] cave = { { 'b', ' ', 'b', 'w' }, { 'w', 'b', 'w', 'b' } };
			WumpusCaves.batMovedYou(rand, cave, location);
			if (!(location[0] == 0 && location[1] == 1)) {
				System.out.println("testBatMovedYou: location expected 0,1; actual " + location[0] + "," + location[1]);
				error = true;
			}

		}
		// test 3 jagged
		{
			int[] location = { 1, 1 };
			char[][] cave = { { 'b', 'p', 'p', 'p' }, { 'w', ' ', 'w' } };
			WumpusCaves.batMovedYou(rand, cave, location);
			if (!(location[0] == 1 && location[1] == 1)) {
				System.out.println("testBatMovedYou: location expected 1,1; actual " + location[0] + "," + location[1]);
				error = true;
			}

		}
		if (error) {
			System.out.println("Error in testBatMovedYou");
		} else {
			System.out.println("All tests in testBatMovedYou passed");
		}

	}

	private static void testMove() {
		boolean error = false;

		{ // test 1
			int[] location = { 1, 1 };
			char[][] cave = { { ' ', ' ', ' ' }, { ' ', ' ', ' ' }, { ' ', ' ', ' ' } };

			WumpusCaves.move(cave, location, "n");
			if (!(location[0] == 0 && location[1] == 1)) {
				System.out.println("testMove 1: location expected 0,1; actual " + location[0] + "," + location[1]);
				error = true;
			}
		}

		{ // test 2
			int[] location = { 1, 1 };
			char[][] cave = { { ' ', ' ', ' ' }, { ' ', ' ', ' ' }, { ' ', ' ', ' ' } };

			WumpusCaves.move(cave, location, "w");
			if (!(location[0] == 1 && location[1] == 0)) {
				System.out.println("testMove 2: location expected 1,0; actual " + location[0] + "," + location[1]);
				error = true;
			}

		}

		{ // test 3 //wrap
			int[] location = { 0, 0 };
			char[][] cave = { { ' ', ' ' }, { ' ', ' ' } };

			WumpusCaves.move(cave, location, "n");
			if (!(location[0] == 1 && location[1] == 0)) {
				System.out.println("testMove 3: location expected 4,0; actual " + location[0] + "," + location[1]);
				error = true;
			}
		}

		{ // test 4 //wrap 2.0
			int[] location = { 0, 0 };
			char[][] cave = { { ' ', ' ', ' ' }, { ' ', ' ', ' ' } };

			WumpusCaves.move(cave, location, "w");
			if (!(location[0] == 0 && location[1] == 2)) {
				System.out.println("testMove 4: location expected 4,0; actual " + location[0] + "," + location[1]);
				error = true;
			}
		}

		if (error) {
			System.out.println("Error in testMove.");
		} else {
			System.out.println("All tests in testMove passed.");
		}
	}
}

