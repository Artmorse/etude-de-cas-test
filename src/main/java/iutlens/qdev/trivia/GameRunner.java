
package iutlens.qdev.trivia;
import java.util.Random;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameRunner {

	private static boolean notAWinner;

	private static final Logger LOGGER = LogManager.getLogger(GameRunner.class.getSimpleName());

	public static void main(String[] args) {

		LOGGER.info("The game has started.");

		Game aGame = new Game();
		
		aGame.add("Chet");
		aGame.add("Pat");
		aGame.add("Sue");
		
		Random rand = new Random();
	
		do {
			aGame.roll(rand.nextInt(5) + 1);
			
			if (rand.nextInt(9) == 7) {
				notAWinner = aGame.wrongAnswer();
			} else {
				notAWinner = aGame.wasCorrectlyAnswered();
			}
		} while (notAWinner);

		LOGGER.info("The game is finished.");
	}
}
