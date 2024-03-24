package engine.core;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;

import javax.sound.sampled.*;
import javax.sound.sampled.LineEvent.Type;

public class Soundboard {

    /**
     * An enumeration of all sounds this application may use.
     * An application should ideally prime this enum using
     * {@code Sound.values()} during startup.
     *
     * todo: a better implementation of this, because consumers have no way to
     * control the sound
     */
    public enum Sound {
        ;

        private String resourceId;
        private AudioFormat audioFormat;
        private byte[] resourceBytes;

        Sound(String resourceId) {
            this.resourceId = resourceId;

            // We make an attempt to preload the sound data during startup. This has a huge
            // impact on overall playback latency.
            try {
                // We have to read into the byte[] anyway, so we should do that immediately.
                // Also this allows us to create a ByteArray InputStream for the mark/seek
                // as required by AudioInputStream.
                URL resourceURL = getClass().getClassLoader().getResource(resourceId);
                byte[] resourceBytes = Files.readAllBytes(new File(resourceURL.toURI()).toPath());
                ByteArrayInputStream audioBytesIn = new ByteArrayInputStream(resourceBytes);

                AudioInputStream audioIn = AudioSystem.getAudioInputStream(audioBytesIn);
                audioFormat = audioIn.getFormat();

                int bufferSize = Math.toIntExact(audioIn.getFrameLength() * audioFormat.getFrameSize());

                System.err.println("Loading sound " + resourceId);
                System.err.println("  - Format: " + audioFormat);
                System.err.println("  - Buffer size: " + bufferSize);

                resourceBytes = new byte[bufferSize];
                audioIn.read(resourceBytes);
            } catch (UnsupportedAudioFileException | IOException | URISyntaxException e) {
                resourceBytes = null;
                e.printStackTrace();
            }
        }

        protected String getResourceId() {
            return resourceId;
        }

        protected AudioInputStream getAudioStream() throws UnsupportedAudioFileException, IOException {
            ByteArrayInputStream dataStream = new ByteArrayInputStream(resourceBytes);
            return new AudioInputStream(dataStream, audioFormat, resourceBytes.length);
        }
    }

    /**
     * Play the specified sound asynchronously.
     *
     * @param key the sound to play
     */
    public static void playSound(Sound key) {
        try {
            // obtain a clip on top of the sound
            Clip clip = AudioSystem.getClip();
            clip.open(key.getAudioStream());

            // rewind then hit play
            clip.setFramePosition(0);
            clip.start();

            // clean up the clip when it finishes
            clip.addLineListener(new LineListener() {
                public void update(LineEvent event) {
                    if (event.getType() == Type.STOP) {
                        clip.close();
                    }
                }
            });
        } catch (UnsupportedAudioFileException uafe) {
            System.err.println(key.getResourceId() + " is not a supported audio file.");
            uafe.printStackTrace();
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }
}
