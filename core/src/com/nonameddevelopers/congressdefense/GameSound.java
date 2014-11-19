package com.nonameddevelopers.congressdefense;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class GameSound {

	public static CongressDefense GAME;
	private Sound sound;
	private long lastId = 0;
	private long duration;
	private long playedTime;
	private long currentTime;
	
	public GameSound(String src, long duration) {
		this.sound = Gdx.audio.newSound(Gdx.files.internal(src));
		this.duration = duration;
		this.playedTime = 0;
	}
	
	public long play () {
		currentTime =  System.currentTimeMillis();
		if (GAME.isSoundOn
			&& playedTime+duration < currentTime) {
			playedTime = currentTime;
			lastId = sound.play();
		}
		return lastId;
	}

	public long play (float volume) {
		currentTime =  System.currentTimeMillis();
		if (GAME.isSoundOn
			&& playedTime+duration < currentTime) {
			playedTime = currentTime;
			lastId = sound.play(volume);
		}
		return lastId;
	}

	
	public long play (float volume, float pitch, float pan) {
		currentTime =  System.currentTimeMillis();
		if (GAME.isSoundOn
			&& playedTime+duration < currentTime) {
			playedTime = currentTime;
			lastId = sound.play(volume, pitch, pan);
		}
		return lastId;
	}


	public long loop () {
		return sound.loop();
	}

	public long loop (float volume) {
		return sound.loop(volume);
	}
	
	public long loop (float volume, float pitch, float pan) {
		return sound.loop(volume, pitch, pan);
	}
		
	public void stop () {
		sound.stop();
	}

	public void pause () {
		sound.pause();
	}

	public void resume () {
		sound.resume();
	}

	public void dispose () {
		sound.dispose();
	}

	public void stop (long soundId) {
		sound.stop(soundId);
	}

	public void pause (long soundId) {
		sound.pause(soundId);
	}

	public void resume (long soundId) {
		sound.resume(soundId);
	}

	public void setLooping (long soundId, boolean looping) {
		sound.setLooping(soundId, looping);
	}

	public void setPitch (long soundId, float pitch) {
		sound.setPitch(soundId, pitch);
	}

	public void setVolume (long soundId, float volume) {
		sound.setVolume(soundId, volume);
	}

	public void setPan (long soundId, float pan, float volume) {
		sound.setPan(soundId, pan, volume);
	}

	public void setPriority (long soundId, int priority) {
		sound.setPriority(soundId, priority);
	}
	
}
