package players;

import java.io.FileInputStream;

import java.io.IOException;
import java.nio.ByteBuffer;

public class Account {

	public static Player loadNecessaryForLogin(String username) throws IOException {
	    FileInputStream fin = new FileInputStream("./data/players/"+username.toLowerCase()+".pl");
	    byte[] data = new byte[fin.available()];
	    fin.read(data);
	    ByteBuffer playerData = ByteBuffer.wrap(data);
	    long password = playerData.getLong();
	    int rights = playerData.get();
	    boolean members = playerData.get() == 1 ? true : false;
	    int xCoordinate = playerData.getShort();
	    int yCoordinate = playerData.getShort();
	    int zCoordinate = playerData.getShort();
	    return new Player(null, null);
	}

	/**
	 * This method is used when a new player joins (one that does not currently have a save file). We use this method so that we can set all of the necessary settings that would already exist in a pre-existing player. 
	 * @param username The username of the new player.
	 * @param password The password of the new player.
	 * @return The new player.
	 */
	public static Player newPlayer(String username, String password) {
		Player player = new Player(username, password);
		player.setPublicChat((byte)0);
		player.setPrivateChat((byte) 0);
		player.setTrade((byte) 0);
		player.setClanSetting((byte) 0);
		player.setAssist((byte) 0);
		return player;
	}
	
}