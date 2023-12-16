package SnakeLadder;

import java.util.Deque;
import java.util.LinkedList;

public class Game {

    // initially board and dice object are null and player is empty
    Board board;
    Dice dice;

    // LinkedList will also work in place of Deque
    Deque<Player> playersList = new LinkedList<>();
    Player winner;

    public Game(){

        initializeGame();
    }

    private void initializeGame() {

        board = new Board(10, 5,4);
        dice = new Dice(1);
        winner = null;
        addPlayers();
    }

    private void addPlayers() {
        Player player1 = new Player("p1", 0);
        Player player2 = new Player("p2", 0);
        playersList.add(player1);
        playersList.add(player2);
    }

    public void startGame(){

        // Once we got the winner then game will be stopped
        while(winner == null) {

            //check whose turn now
            Player playerTurn = findPlayerTurn();
            System.out.println("player turn is:" + playerTurn.id + " current position is: " + playerTurn.currentPosition);

            //roll the dice
            int diceNumbers = dice.rollDice();

            //get the new position
            int playerNewPosition = playerTurn.currentPosition + diceNumbers;
            // jumpCheck => do we have snake or ladder whatever is there we are checking if there exists jump or not.
            playerNewPosition = jumpCheck(playerNewPosition);

            // if jumpcheck is correct then we updated jump for next time.
            playerTurn.currentPosition = playerNewPosition;

            System.out.println("player turn is:" + playerTurn.id + " new Position is: " + playerNewPosition);
            //check for winning condition
            if(playerNewPosition >= board.cells.length * board.cells.length - 1){

                winner = playerTurn;
            }

        }

        System.out.println("WINNER IS:" + winner.id);
    }


    private Player findPlayerTurn() {

        Player playerTurns = playersList.removeFirst();
        playersList.addLast(playerTurns);
        return playerTurns;
    }

    private int jumpCheck (int playerNewPosition) {

        if(playerNewPosition > board.cells.length * board.cells.length - 1){
            return playerNewPosition;
        }

        Cell cell = board.getCell(playerNewPosition);
        if(cell.jump != null && cell.jump.start == playerNewPosition) {
            String jumpBy = (cell.jump.start < cell.jump.end)? "ladder" : "snake";
            System.out.println("jump done by: " + jumpBy);
            return cell.jump.end;
        }
        return playerNewPosition;
    }
}

