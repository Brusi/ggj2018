package com.brusi.ggj2018.assets;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.brusi.ggj2018.game.Utils;
import com.brusi.ggj2018.ggj2018;
import com.brusi.ggj2018.utils.SoundEvictingQueue;

public class SoundAssets {
    public final float MUSIC_VOLUME = 0.6f;

	public Music music1A;
	public Music music1B;
	public Music music2A;
	public Music music2B;

	public Sound playerDash;

	private SoundEvictingQueue currentlyPlaying = new SoundEvictingQueue(100);

	private final boolean ENABLE_OVERALL_SOUNDS = true;

	private float pitch = 1f;
	// This should be a reference to the asset manager owned by Assets.get().
	private AssetManager assetManager;

	public void init() {
		music1A = newMusic("teleporting_1_a.mp3");
		music1B = newMusic("teleporting_1_b.mp3");
		music2A = newMusic("teleporting_2_a.mp3");
		music2B = newMusic("teleporting_2_b.mp3");
	}

	public static SoundAssets get() {
		return ((ggj2018) Gdx.app.getApplicationListener()).soundAssets;
	}


    public long playSoundFixedPitch(Sound sound) {
        return playSoundWithPitch(sound, 1, 1);
    }

    public long playSound(Sound sound, float volume) {
        float pitch = Utils.randomRange(0.95f, 1.05f);
        return playSoundWithPitch(sound, volume, pitch);
    }

    public long playSoundWithPitch (Sound sound, float volume, float pitch) {
		if (!ENABLE_OVERALL_SOUNDS) return 0;

		long id = sound.play(volume);
		sound.setPitch(id, pitch);
		currentlyPlaying.add(sound, id, pitch);
		return id;
	}

	public long playSoundAtIndex(Sound[] sounds, int index) {
		if (!ENABLE_OVERALL_SOUNDS) return 0;
		return playSound(sounds[index]);
	}

	public long playSound(Sound sound) {
		return playSound(sound, 1);
	}

	public void playRandomSound(Sound[] sounds) {
		playRandomSound(sounds, 1);
	}

	public void playRandomSound(Sound[] sounds, float volume) {
		if (!ENABLE_OVERALL_SOUNDS) return;
		int index = Utils.randomInt(sounds.length);
		playSound(sounds[index], volume);
	}

	private Sound newSound(String filename) {
		String path = "sounds/" + filename;
		return Gdx.audio.newSound(Gdx.files.internal(path));
	}

    private Music newMusic(String filename) {
        String path = "sounds/" + filename;
	    return Gdx.audio.newMusic(Gdx.files.internal(path));
    }

	public void stopSound(Sound sound) {
		if (!ENABLE_OVERALL_SOUNDS) return;
		sound.stop();
	}

	public void stopSoundArray(Sound[] sounds) {
		if (!ENABLE_OVERALL_SOUNDS) return;
		for (Sound s : sounds) {
			SoundAssets.get().stopSound(s);
		}
	}

	public void stopAllSounds() {
		if (!ENABLE_OVERALL_SOUNDS) return;
		currentlyPlaying.forEach(new SoundEvictingQueue.Consumer() {
			@Override
			public void accept(Sound s, long id, float pitch) {
				s.stop();
			}
		});
		currentlyPlaying.clear();
//        pauseMusic();
	}

	private  void stopAllMusic() {
		music1A.stop();
        music1B.stop();
		music2A.stop();
		music2B.stop();
	}

	public  void setPitch(float newPitch) {
		if (newPitch == pitch) {
			return;
		}
		pitch = newPitch;
		currentlyPlaying.forEach(new SoundEvictingQueue.Consumer() {
			@Override
			public void accept(Sound s, long id, float base_pitch) {
				s.setPitch(id, pitch * base_pitch);
			}
		});
	}

	public void restart() {
		stopAllSounds();
		pitch = 1;
	}

	public void pauseAllSounds() {
		currentlyPlaying.forEach(new SoundEvictingQueue.Consumer() {
			@Override
			public void accept(Sound s, long id, float pitch) {
				s.pause();
			}
		});
//        pauseMusic();
	}

	public void resumeAllSounds() {
		currentlyPlaying.forEach(new SoundEvictingQueue.Consumer() {
			@Override
			public void accept(Sound s, long id, float pitch) {
				s.resume();
			}
		});
	}

	public void startGameMusic() {
//        startMusic(gameMusic);
	}

	public void playGameMusics() {
		music1A.play();
		music1B.play();
		music2A.play();
		music2A.setVolume(0);
		music2B.play();
		music2B.setVolume(0);
	}

//	public void pauseMusic() {
//		if (!ENABLE_OVERALL_SOUNDS) return;
//        if (currentlyPlayedMusic != null) {
//            currentlyPlayedMusic.pause();
//        }
//    }
//
//	private Music getGameMusic(float gameTime) {
//        return (gameTime < LOOPING_GAMEPLAY_MUSIC_START_TIME)
//                ? gameMusic
//                : loopingGameMusic;
//    }
//    private float getMusicPosition(float gameTime) {
//        if (gameTime < LOOPING_GAMEPLAY_MUSIC_START_TIME) {
//            return gameTime;
//        }
//        return (gameTime - LOOPING_GAMEPLAY_MUSIC_START_TIME) % LOOPING_GAMEPLAY_MUSIC_DURATION;
//    }
//
//    public void resumeMusic() {
//        if (!ENABLE_OVERALL_SOUNDS) return;
//
//		if (!Settings.get().soundEnabled.on()) {
//			return;
//		}
//        if (!Settings.get().musicEnabled.on()) {
//            return;
//        }
//		if (currentlyPlayedMusic != null) {
//            currentlyPlayedMusic.play();
//        }
//	}
//
//	public void startShopMusic() {
//		if (!ENABLE_OVERALL_SOUNDS) return;
//		if (!Settings.get().soundEnabled.on()) {
//			return;
//		}
//        if (!Settings.get().musicEnabled.on()) {
//            return;
//        }
//		stopAllMusic();
//
//		shopMusic.play();
//	}
//
//	public void startMenuMusic() {
//        Music music = menuMusic;
//        startMusic(music);
//	}
//
//    private void startMusic(Music music) {
//        if (!ENABLE_OVERALL_SOUNDS) return;
//        stopAllMusic();
//        if (!Settings.get().soundEnabled.on()) {
//            return;
//        }
//        if (!Settings.get().musicEnabled.on()) {
//            return;
//        }
//
//        music.play();
//        currentlyPlayedMusic = music;
//    }
//
//    public void shortVibrate() {
//		if (!Settings.get().vibrationEnabled.on()) {
//			return;
//		}
//		Gdx.input.vibrate(20);
//	}
//
//	public void longVibrate() {
//		if (!Settings.get().vibrationEnabled.on()) {
//			return;
//		}
//		Gdx.input.vibrate(500);
//	}
//
//    public void resumeMenuMusic() {
//        if (currentlyPlayedMusic == menuMusic) {
//            resumeMusic();
//            return;
//        }
//        startMenuMusic();
//    }
//
//    public void maybeStartLoopingMusic(float gameTime) {
//        if (gameTime > LOOPING_GAMEPLAY_MUSIC_START_TIME
//                && currentlyPlayedMusic != loopingGameMusic) {
//        	currentlyPlayedMusic.stop();
//            currentlyPlayedMusic = loopingGameMusic;
//            resumeMusic();
//        }
//    }
//
//    public void stopMusic() {
//        currentlyPlayedMusic.stop();
//    }
}