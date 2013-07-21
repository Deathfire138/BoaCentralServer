package players;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player {
	
	private Map<String, Byte> friends = new HashMap<String, Byte>();
	private ArrayList<String> ignores = new ArrayList<String>();
	String username, password, currentClan;
	byte publicChat, privateChat, trade, clan, assist;
	
	public Player() {
		
	}
	
	public Player(String username, String password) {
		this.username = username;
		this.password = password;
		//stuff.
	}
	
	public Map<String, Byte> getFriends() {
		return friends;
	}
	
	public void setFriends(Map<String, Byte> friends) {
		this.friends = friends;
	}
	
	public ArrayList<String> getIgnores() {
		return ignores;
	}
	
	public void setIgnores(ArrayList<String> ignores) {
		this.ignores = ignores;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void addFriend(String string, byte clanRank) {
		friends.put(string, clanRank);
	}
	
	public void removeFriend(String string) {
		friends.remove(string);
	}
	
	public void addIgnore(String string) {
		ignores.add(string);
	}
	
	public void removeIgnore(String string) {
		ignores.remove(string);
	}
	
	public byte getRights() {
		return 2;
	}
	
	public void setPublicChat(byte pubchat) {
		publicChat = pubchat;
	}
	
	public void setPrivateChat(byte privchat) {
		privateChat = privchat;
	}
	
	public void setTrade(byte tr) {
		trade = tr;
	}
	
	public void setClanSetting(byte cs) {
		clan = cs;
	}
	
	public void setAssist(byte as) {
		assist = as;
	}

	public byte getPrivateChat() {
		return privateChat;
	}
	
	public void setCurrentClan(String string) {
		currentClan = string;
	}
	
	public String getCurrentClan() {
		return currentClan;
	}
	
}