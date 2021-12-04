package models;

public class Board {

    private TileType[][] tiles;
    public Board(){
        tiles = new TileType[7][7];
        for (int i = 0; i <  7 ; i++) for (int j = 0; j < 7  ; j++) tiles[i][j] = TileType.E;
    }

    public TileType[][] getTiles() {
        return tiles;
    }

    public void setTiles(TileType[][] tiles) {
        this.tiles = tiles;
    }
}
