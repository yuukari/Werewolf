package no.arcticdrakefox.wolfbot.model;

import java.util.Collection;
import java.util.Timer;

import com.google.common.collect.Lists;

import no.arcticdrakefox.wolfbot.WolfBot;
import no.arcticdrakefox.wolfbot.management.PlayerList;
import no.arcticdrakefox.wolfbot.management.commands.Command;

public class WolfBotModel {
	
	// New commands should have entries added here:
	private void initCommands ()
	{
		commands = Lists.newArrayList();
		// For easy commands, just implement anon. inner class Command - for other things, may wish to create a new class...
		commands.add(new Command ("!join",
				Lists.newArrayList(State.None, State.Starting),
				Lists.newArrayList(MessageType.CHANNEL, MessageType.PRIVATE))
		{			
			@Override
			public void runCommand(String[] args, String sender, MessageType type)
			{
				if (getPlayers().addPlayer(sender))
				{
					wolfBot.sendIrcMessage(channel, sender + " has joined the game!");
				}
				else
				{
					wolfBot.sendIrcMessage(channel, sender + " has already entered.");
				}
			}
			
			@Override
			public void runInvalidCommand(String[] args, String sender, MessageType type)
			{
				wolfBot.sendIrcMessage(channel, sender + " cannot join now, a game is in progress.");				
			}
		});
	}
	
	
	private String channel;
	private String password;
	private PlayerList players;
	private State state;
	private Timer startGameTimer;
	private boolean enableNotices;
	private boolean silentMode = false;
	
	private Collection<Command> commands;
	
	public Collection<Command> getCommands() {
		return commands;
	}

	public void setSilentMode (boolean silentMode) {
		this.silentMode = silentMode;
	}
	
	public boolean getSilentMode() {
		return silentMode;
	}
	
	private static WolfBotModel instance;
	public static WolfBotModel getInstance ()
	{
		return instance;
	}
	
	private WolfBot wolfBot;
	
	public WolfBotModel(PlayerList players, State state, Timer startGameTimer,
			boolean enableNotices, WolfBot wolfBot) {
		if (instance != null)
		{
			throw new RuntimeException ("May not instantiate model twice");
		}
		else
		{
			instance = this;
		}
		this.players = players;
		this.state = state;
		this.startGameTimer = startGameTimer;
		this.enableNotices = enableNotices;
		this.wolfBot = wolfBot;
		initCommands ();
	}
	

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public PlayerList getPlayers() {
		return players;
	}

	public void setPlayers(PlayerList players) {
		this.players = players;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Timer getStartGameTimer() {
		return startGameTimer;
	}

	public void setStartGameTimer(Timer startGameTimer) {
		this.startGameTimer = startGameTimer;
	}

	public boolean isEnableNotices() {
		return enableNotices;
	}

	public void setEnableNotices(boolean enableNotices) {
		this.enableNotices = enableNotices;
	}
}