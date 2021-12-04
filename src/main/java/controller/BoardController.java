package controller;

import models.Board;
import models.Player;
import models.TileType;

public class BoardController {

    private Board board;
    public BoardController(Board board){
        this.board = board;
    }

   public boolean isWinner(Board board){
        this.board = board;
       return checkHorizontally() || checkVertically() || checkNegativeDiagonal() || checkPositiveDiagonal();
   }

    private boolean checkHorizontally(){
        for(int i = 0; i < 7; i++){
            for(int j = 0; j <= 7 - 4; j++){
                boolean flag = true;
                for(int k = j; k < j + 4; k++){
                    if (board.getTiles()[i][k] != board.getTiles()[i][j]) {
                        flag = false;
                        break;
                    }
                }

                if(flag && board.getTiles()[i][j]!=TileType.E) return true;
            }
        }
        return false;
    }

    private boolean checkVertically(){
        for(int i = 0; i <= 7 - 4; i++){
            for(int j = 0; j < 7; j++){
                boolean flag = true;
                for(int k = i; k < i + 4; k++){
                    if (board.getTiles()[k][j] != board.getTiles()[i][j]) {
                        flag = false;
                        break;
                    }
                }

                if(flag && board.getTiles()[i][j]!=TileType.E)
                    return true;
            }
        }
        return false;
    }

    private boolean checkNegativeDiagonal(){
        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 7; j++){
                int currentI = i, currentJ = j;
                boolean flag = true;

                for(int k = 0; k < 4; k++){
                    if(!isInBoard(currentI, currentJ))
                        flag = false;
                    else if(board.getTiles()[currentI][currentJ] != board.getTiles()[i][j])
                        flag = false;
                    currentI++;
                    currentJ++;
                }
                if(flag&& board.getTiles()[i][j]!=TileType.E)
                    return true;
            }
        }
        return false;
    }

    private boolean checkPositiveDiagonal(){
        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 7; j++){
                int currentI = i, currentJ = j;
                boolean flag = true;
                for(int k = 0; k < 4; k++){
                    if(!isInBoard(currentI, currentJ))
                        flag = false;
                    else if(board.getTiles()[currentI][currentJ] != board.getTiles()[i][j])
                        flag = false;
                    currentI++;
                    currentJ--;
                }
                if(flag&& board.getTiles()[i][j]!=TileType.E)
                    return true;
            }
        }
        return false;
    }

    private boolean isInBoard(int i, int j) {
        return i>=0 && i<7 && j>=0 && j< 7 ;
    }

    public boolean tied(Board board){
        this.board = board;
        for (int i = 0; i < 7; i++) for (int j = 0; j < 7; j++) if(board.getTiles()[i][j]==TileType.E)return false;
        return true;
    }



}
