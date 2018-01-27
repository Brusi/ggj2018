package com.brusi.ggj2018.utils;

import com.badlogic.gdx.audio.Sound;

public class SoundEvictingQueue {

	private class Entry {
		Sound sound;
		long id;
		float pitch;
	}
	
	public interface Consumer {
		void accept(Sound s, long id, float pitch);
	}

	private Entry[] entries;
	int index = 0;
	int maxSize = 0;
	

	public SoundEvictingQueue(int size) {
		entries = new Entry[size];
	}
	
	public void add(Sound sound, long id, float pitch) {
		if (entries[index] == null) {
			entries[index] = new Entry();
		}
		entries[index].sound = sound;
		entries[index].id = id;
		entries[index].pitch = pitch;

		index = (index + 1) % entries.length;
		maxSize = Math.min(entries.length, maxSize+1);
	}
	
	public void clear() {
		index = maxSize = 0;
	}
	
	public void forEach(Consumer c) {
		for (int i=0; i < maxSize; i++) {
			Entry entry = entries[i];
			c.accept(entry.sound, entry.id, entry.pitch);
		}
	}

}
