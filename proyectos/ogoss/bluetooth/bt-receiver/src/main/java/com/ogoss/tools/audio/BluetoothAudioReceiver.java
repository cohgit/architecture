

import java.util.UUID;

import javax.bluetooth.*;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class BluetoothAudioReceiver {

    private static boolean running = true;

    public static void main(String[] args) {
        try {
            // Create a Bluetooth server to accept incoming connections
            LocalDevice localDevice = LocalDevice.getLocalDevice();
            localDevice.setDiscoverable(DiscoveryAgent.GIAC);

            String url = "btspp://localhost:" + new UUID(0x1101).toString() + ";name=BluetoothAudioReceiver";
            StreamConnectionNotifier notifier = (StreamConnectionNotifier) Connector.open(url);

            System.out.println("Waiting for connection...");
            StreamConnection connection = notifier.acceptAndOpen();

            // Prepare audio output
            AudioFormat format = new AudioFormat(44100, 16, 2, true, false);
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            System.out.println("Connected, streaming audio...");

            // Start streaming audio
            byte[] buffer = new byte[1024];
            int bytesRead;
            while (running) {
                bytesRead = connection.openInputStream().read(buffer);
                if (bytesRead > 0) {
                    line.write(buffer, 0, bytesRead);
                }
            }

            // Close resources
            line.drain();
            line.close();
            connection.close();
            notifier.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
