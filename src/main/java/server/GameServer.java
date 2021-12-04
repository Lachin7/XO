package server;

import controller.BoardController;
import models.Board;
import models.Player;
import models.TileType;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class GameServer {

    private GamePlayer currentPlayer;
    private Board board;
    private BoardController boardController;

    GameServer(){
        board = new Board();
        boardController = new BoardController(board);
    }

    public synchronized void makeMove(int row, int column, GamePlayer player) {
        if (player != currentPlayer) {
            throw new IllegalStateException("Not your turn");
        } else if (player.opponent == null) {
            throw new IllegalStateException("You don't have an opponent yet");
        } else if (board.getTiles()[row][column] != TileType.E) {
            throw new IllegalStateException("Cell already occupied");
        }
        board.getTiles()[row][column] = currentPlayer.getTileType();
        currentPlayer = currentPlayer.opponent;
    }

    public class GamePlayer extends Player implements Runnable{
        private TileType tileType;
        private GamePlayer opponent;
        private Socket socket;
        private Scanner input;
        private PrintWriter output;

        public GamePlayer(Socket socket, TileType tileType) {
            this.socket = socket;
            this.tileType = tileType;
        }

        @Override
        public void run() {
            try {
                setup();
                processCommands();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (opponent != null && opponent.output != null) {
                    opponent.output.println("OTHER_PLAYER_LEFT");
                }
                try {
                    socket.close();
                } catch (IOException e) {}
            }
        }

        private void setup() throws IOException {
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);
            output.println("WELCOME " + this.getTileType());
            if (tileType == TileType.X) {
                currentPlayer = this;
                output.println("MESSAGE Waiting for opponent to connect");
            } else {
                opponent = currentPlayer;
                opponent.opponent = this;
                opponent.output.println("GET_ENEMY_NAME");
                output.println("GET_ENEMY_NAME");
                opponent.output.println("MESSAGE Your move");
            }
        }

        private void processCommands() {
            while (input.hasNextLine()) {
                var command = input.nextLine();
                if (command.startsWith("QUIT")) {
                    return;
                } else if (command.startsWith("MOVE")) {
                    processMoveCommand(Integer.parseInt(command.substring(5,6)),Integer.parseInt(command.substring(6,7)));
                }
                else if (command.startsWith("ENEMY_NAME")) opponent.output.println(command);
            }
        }

        private void processMoveCommand(int row , int column) {
            try {
                makeMove(row,column, this);
                output.println("VALID_MOVE");
                opponent.output.println("OPPONENT_MOVED " + row +","+ column);
                if (boardController.isWinner(board)) {
                    output.println("VICTORY");
                    opponent.output.println("DEFEAT");
                } else if (boardController.tied(board)) {
                    output.println("TIE");
                    opponent.output.println("TIE");
                }
            } catch (IllegalStateException e) {
                output.println("MESSAGE " + e.getMessage());
            }
        }

        public TileType getTileType() {
            return tileType;
        }

        public void setTileType(TileType tileType) {
            this.tileType = tileType;
        }
    }
}
