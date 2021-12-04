package resLoader.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.Player;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class PlayerJson {
    public static void jsonTofilePlayer(Player player) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter fileWriter = new FileWriter("./src/main/resources/jsonFiles/" + player.getName() + ".json");
        gson.toJson(player, fileWriter);
        fileWriter.close();
    }

    public static File getPlayerFiles(String name) {
        File directory = new File("./src/main/resources/jsonFiles");
        File[] listOfPlayersFiles = directory.listFiles();
        for (File file : listOfPlayersFiles) {
            if ((name + ".json").equals(file.getName())) {
                return file;
            }
        }
        return null;
    }

    public static ArrayList<Player> getAllPlayers(){
        ArrayList<Player> result = new ArrayList<>();
        File directory = new File("./src/main/resources/jsonFiles");
        File[] listOfPlayersFiles = directory.listFiles();
        for (File file : listOfPlayersFiles) {
            try {
                result.add(jsonFileReader(file.getName().substring(0,file.getName().length()-5)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(result);
        return result;
    }

    public static Player jsonFileReader(String name) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileReader fileReader = new FileReader("./src/main/resources/jsonFiles/" + name + ".json");
        Player player = gson.fromJson(fileReader, Player.class);
        fileReader.close();
        return player;
    }
}
