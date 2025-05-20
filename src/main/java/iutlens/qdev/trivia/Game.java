package iutlens.qdev.trivia;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The type Game.
 */
public class Game {


  private static final Logger LOGGER = LogManager.getLogger(Game.class.getPackage().getName());

  /**
   * The Players.
   */
  List<String> players = new ArrayList<>();
  /**
   * The Places.
   */
  int[] places = new int[6];
  /**
   * The Purses.
   */
  int[] purses = new int[6];
  /**
   * The In penalty box.
   */
  boolean[] inPenaltyBox = new boolean[6];

  private static final String SPORTS = "Sports";
  private static final String POP = "Pop";
  private static final String SCIENCE = "Science";
  private static final String ROCK = "Rock";

  /**
   * The Pop questions.
   */
  List<String> popQuestions = new LinkedList<>();
  /**
   * The Science questions.
   */
  List<String> scienceQuestions = new LinkedList<>();
  /**
   * The Sports questions.
   */
  List<String> sportsQuestions = new LinkedList<>();
  /**
   * The Rock questions.
   */
  List<String> rockQuestions = new LinkedList<>();

  /**
   * The Current player.
   */
  int currentPlayer = 0;
  /**
   * The Is getting out of penalty box.
   */
  boolean isGettingOutOfPenaltyBox;

  /**
   * Instantiates a new Game.
   */
  public Game() {
    for (int i = 0; i < 50; i++) {
      popQuestions.addLast(createQuestion(i, POP));
      scienceQuestions.addLast(createQuestion(i, SCIENCE));
      sportsQuestions.addLast(createQuestion(i, SPORTS));
      rockQuestions.addLast(createQuestion(i, ROCK));
    }
  }

  /**
   * Create question string.
   *
   * @param index the index
   * @param type  the type
   * @return the string
   */
  public String createQuestion(int index, String type) {
    return type + " Question " + index;
  }

  /**
   * Is playable boolean.
   *
   * @return the boolean
   */
  public boolean isPlayable() {
    return (howManyPlayers() >= 2);
  }

  /**
   * Add boolean.
   *
   * @param playerName the player name
   * @return the boolean
   */
  public boolean add(String playerName) {

    players.add(playerName);
    places[howManyPlayers()] = 0;
    purses[howManyPlayers()] = 0;
    inPenaltyBox[howManyPlayers()] = false;

    LOGGER.info("{} was added", playerName);
    LOGGER.info("They are player number {}", players.size());
    return true;
  }

  /**
   * How many players int.
   *
   * @return the int
   */
  public int howManyPlayers() {
    return players.size();
  }

  /**
   *
   * @param roll
   */
  public void roll(int roll) {
    LOGGER.info("{} is the current player", players.get(currentPlayer));
    LOGGER.info("They have rolled a {}", roll);

    if (inPenaltyBox[currentPlayer]) {
      if (roll % 2 != 0) {
        isGettingOutOfPenaltyBox = true;

        LOGGER.info("{} is getting out of the penalty box", players.get(currentPlayer));
        places[currentPlayer] = places[currentPlayer] + roll;
        if (places[currentPlayer] > 11) {
          places[currentPlayer] = places[currentPlayer] - 12;
        }

        LOGGER.info("{}'s new location is {}", players.get(currentPlayer), places[currentPlayer]);
        final String currentCategory = currentCategory(places[currentPlayer]);
        LOGGER.info("The category is {}", currentCategory);
        askQuestion();
      } else {
        final String chevre = players.get(currentPlayer);
        LOGGER.info("{} is not getting out of the penalty box", chevre);
        isGettingOutOfPenaltyBox = false;
      }

    } else {

      places[currentPlayer] = places[currentPlayer] + roll;
      if (places[currentPlayer] > 11) {
        places[currentPlayer] = places[currentPlayer] - 12;
      }

      LOGGER.info("{}'s new location is {}", players.get(currentPlayer), places[currentPlayer]);
      LOGGER.info("The category is {}", currentCategory(places[currentPlayer]));
      askQuestion();
    }

  }

  /**
   * Ask a question to the user.
   */
  private void askQuestion() {
		if (currentCategory(places[currentPlayer]).equals(POP)) {
			LOGGER.info(popQuestions.removeFirst());
		}
		if (currentCategory(places[currentPlayer]).equals(SCIENCE)) {
			LOGGER.info(scienceQuestions.removeFirst());
		}
		if (currentCategory(places[currentPlayer]).equals(SPORTS)) {
			LOGGER.info(sportsQuestions.removeFirst());
		}
		if (currentCategory(places[currentPlayer]).equals(ROCK)) {
			LOGGER.info(rockQuestions.removeFirst());
		}
  }


  private String currentCategory(int place) {
    return switch (place) {
      case 0, 4, 8 -> POP;
      case 1, 5, 9 -> SCIENCE;
      case 2, 6, 10 -> SPORTS;
      default -> ROCK;
    };
  }

  /**
   * Was correctly answered boolean.
   *
   * @return the boolean
   */
  public boolean wasCorrectlyAnswered() {
    if (inPenaltyBox[currentPlayer]) {
      if (isGettingOutOfPenaltyBox) {
        LOGGER.info("Answer was correct!!!!");
        purses[currentPlayer]++;
        LOGGER.info(players.get(currentPlayer)
            + " now has "
            + purses[currentPlayer]
            + " Gold Coins.");

        boolean winner = didPlayerWin();
        currentPlayer++;
				if (currentPlayer == players.size()) {
					currentPlayer = 0;
				}

        return winner;
      } else {
        currentPlayer++;
				if (currentPlayer == players.size()) {
					currentPlayer = 0;
				}
        return true;
      }


    } else {

      LOGGER.info("Answer was corrent!!!!");
      purses[currentPlayer]++;
      LOGGER.info(players.get(currentPlayer)
          + " now has "
          + purses[currentPlayer]
          + " Gold Coins.");

      boolean winner = didPlayerWin();
      currentPlayer++;
			if (currentPlayer == players.size()) {
				currentPlayer = 0;
			}

      return winner;
    }
  }

  /**
   * Wrong answer boolean.
   *
   * @return the boolean
   */
  public boolean wrongAnswer() {
    LOGGER.info("Question was incorrectly answered");
    LOGGER.info(players.get(currentPlayer) + " was sent to the penalty box");
    inPenaltyBox[currentPlayer] = true;

    currentPlayer++;
		if (currentPlayer == players.size()) {
			currentPlayer = 0;
		}
    return true;
  }


  private boolean didPlayerWin() {
    return purses[currentPlayer] != 6;
  }
}
