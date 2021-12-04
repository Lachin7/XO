package server;

import models.TileType;
import resLoader.ConfigLoader;

import java.net.ServerSocket;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.concurrent.Executors;

public class Server {

   private static ConfigLoader configLoader = new ConfigLoader("serverConfig");
   private final SecureRandom secureRandom = new SecureRandom();
   private final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    public static void main(String[] args) throws Exception {
        try (var listener = new ServerSocket(configLoader.checkIfIsEmpty("port") ? 8000 : configLoader.readInteger("port"))) {
            System.out.println("XO Server is Running...");
            var pool = Executors.newFixedThreadPool(200);
            while (true) {
                GameServer game = new GameServer();
                pool.execute(game.new GamePlayer(listener.accept(), TileType.X));
                pool.execute(game.new GamePlayer(listener.accept(), TileType.O));
            }
        }
    }

    public String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }


//    private ServerSocket serverSocket;
//    private boolean isRunning;
//    private DataInputStream dataInputStream;
//    private DataOutputStream dataOutputStream;

//    public Server(){
//        try {
//            serverSocket = new ServerSocket();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    @Override
//    public void run() {
//        isRunning = true;
//        while (isRunning){
//            try {
//                Socket socket = serverSocket.accept();
//                dataOutputStream = new DataOutputStream(socket.getOutputStream());
//                dataInputStream = new DataInputStream(socket.getInputStream());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
