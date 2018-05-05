/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javatest1;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.Math;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioSystem;

import javax.sound.sampled.Clip;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.nio.charset.StandardCharsets;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import sun.audio.AudioData;
import sun.audio.AudioDataStream;
import sun.audio.AudioPlayer;

import org.tritonus.sampled.convert.PCM2PCMConversionProvider;

//import org.tritonus.sampled.convert.PCM2PCMConversionProvider;
/**
 *
 * @author dalerleo
 */
public class SasTransform {
    	static boolean running = false;
        long songID = 0;
    
    /**
     * @param args the command line arguments
     */
//   public List<String> viewTable(Connection con)
//    throws SQLException {
//    List<String> ne = new ArrayList<>();
//    String paths[];
//    Statement stmt = null;
//    String query =
//        "select name, person_id, tune from person";
//    try {
//        stmt = con.createStatement();
//        ResultSet rs = stmt.executeQuery(query);
//        while (rs.next()) {
//            String coffeeName = rs.getString("tune");
//            String supplierID = rs.getString("name");
//            ne.add(coffeeName);
//            System.out.println(coffeeName + "\t" + supplierID);
//        }
//    } catch (SQLException e ) {
//        e.printStackTrace();
//    } finally {
//        if (stmt != null) { stmt.close(); }
//    }
//    return ne;
//}
    public static void main(String args[])
    {
//

//    int N =4; 
//    double x[]={0.0, 1.00, 0.0, -1.0 }; 
//    double F[]=new double [8];
//    
//     
//     double omega = (2*Math.PI); 
//    for(int k=0; k<N; k++)
//    {    
//        
//        for (int n=0; n<N; n++)
//        {
//            //System.out.println("X[n]: " + x[n] + "F[k]: " + F[k]);
//             F[k]= F[k]+x[n]*(Math.cos((omega*k*n)/N)-Math.sin((omega*k*n)/N)); 
//
//        }
//              System.out.println("F{"+k+"]"+F[k]);
//    }
        try {

            
//            Class.forName("com.mysql.jdbc.Driver");
//            Connection conn = null;
//            conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/javaTest","root", "");
//            String filePath = "./0980.wav";
//            String sql = "INSERT INTO person (name, tune) values (?, ?)";
//            PreparedStatement statement = conn.prepareStatement(sql);
//            statement.setString(1, "Muxlisa");
//            statement.setString(2, filePath);
//            statement.executeUpdate();
//            System.out.print("Database is connected !");
//            List<String> da = viewTable(conn);
//            System.out.println(da);
//            
//            for (String t : da) {
//            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(t));
//            Clip clip = AudioSystem.getClip();
//            clip.open(ais);
//            clip.start();
//            long si = clip.getMicrosecondLength();
//            System.out.println(si);
//            for(int i = 0; i<30000; i++)
//            System.out.println("WASTING TIME");
//            }
//            conn.close();


//            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(filePath));
            //Clip clip = AudioSystem.getClip();
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            int read;
//            byte[] buff = new byte[2048];
//            while ((read = ais.read(buff)) > 0)
//            {
//                System.out.println(read + " " + buff);
//                out.write(buff, 0, read);
//            }

//            System.out.print(out);
      //      System.out.println(out.toString());

//            clip.open(ais);
//            clip.start();
//            out.close();


// HOW MUSIC TURNED INTO BYTES 

            String filePath = "./Pido_O.wav";
            File wavFile = new File(filePath);
            ByteArrayOutputStream out2 = new ByteArrayOutputStream();
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File("./welcome.wav"));
            AudioFormat format = ais.getFormat();    
            System.out.println(ais.getFormat());
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(wavFile));
            FileInputStream fileIn = new FileInputStream(wavFile);
            int read;
            byte[] buff = new byte[1024];
            while ((read = fileIn.read(buff)) != -1)
            {
                 out2.write(buff, 0, read);
            }
            fileIn.close();
            out2.close();
            //ais.read(data);
            int j = 0;
            byte[] audioBytes = out2.toByteArray();
            for (int i=0; i<audioBytes.length; i++) {
                j++;
            //    System.out.println(audioBytes[i]);
            }
            byte[] b = {1, 0, -1, 0};
            DFT transform = new DFT(b);
            System.out.println("ARRAY: " + Arrays.toString(transform.makeDft(b)));
            
//            AudioData audiodata = new AudioData(audioBytes);
            // Create an AudioDataStream to play back
//            AudioDataStream audioStream = new AudioDataStream(audiodata);
            // Play the sound
//            AudioPlayer.player.start(audioStream);
            
           // listenSound(1, false, filePath);
      
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static AudioFormat getFormat() {
            float sampleRate = 44100;
            int sampleSizeInBits = 8;
            int channels = 1; // mono
            boolean signed = true;
            boolean bigEndian = true;
            return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed,
                            bigEndian);
    }
    
    private static void listenSound(long songId, boolean isMatching, String path)
			throws LineUnavailableException, IOException,
			UnsupportedAudioFileException {

		AudioFormat formatTmp = null;
		TargetDataLine lineTmp = null;
		String filePath = path;
		AudioInputStream din = null;
		AudioInputStream outDin = null;
		PCM2PCMConversionProvider conversionProvider = new PCM2PCMConversionProvider();
		boolean isMicrophone = false;

		if (filePath == null || filePath.equals("") || isMatching) {

                    System.err.println("Path is Empty");
		} else {
			AudioInputStream in;
			File file = new File(filePath);
                        in = AudioSystem.getAudioInputStream(file);

			AudioFormat baseFormat = in.getFormat();

			System.out.println(baseFormat.toString());

			AudioFormat decodedFormat = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED,
					baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
					baseFormat.getChannels() * 2, baseFormat.getSampleRate(),
					false);

			din = AudioSystem.getAudioInputStream(decodedFormat, in);

			if (!conversionProvider.isConversionSupported(getFormat(),
					decodedFormat)) {
				System.out.println("Conversion is not supported");
			}

			System.out.println("DECODE FORMAT: " + decodedFormat.toString());

			outDin = conversionProvider.getAudioInputStream(getFormat(), din);
			formatTmp = decodedFormat;

			DataLine.Info info = new DataLine.Info(TargetDataLine.class,
					formatTmp);
			lineTmp = (TargetDataLine) AudioSystem.getLine(info);
		}

		final AudioFormat format = formatTmp;
		final TargetDataLine line = lineTmp;
		final AudioInputStream outDinSound = outDin;

		final long sId = songId;
		final boolean isMatch = isMatching;

		Thread listeningThread = new Thread(new Runnable() {
			public void run() {
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				running = true;
				int n = 0;
				byte[] buffer = new byte[(int) 1024];

				try {
					while(running) {
						n++;
						if (n > 1000)
							break;

						int count = 0;
						count = outDinSound.read(buffer, 0, 1024);
						if (count > 0) {
							out.write(buffer, 0, count);
						}
					}
                                    System.out.println("RUNNING TIME" + n + " BYTES: #" + out.toByteArray().length);
					byte b[] = out.toByteArray();
					for (int i = 0; i < b.length; i++) {
						System.out.println(b[i]);
					}

					try {
//						makeSpectrum(out, sId, isMatch);

						FileWriter fstream = new FileWriter("out.txt");
						BufferedWriter outFile = new BufferedWriter(fstream);

						byte bytes[] = out.toByteArray();
						for (int i = 0; i < b.length; i++) {
							outFile.write("" + b[i] + ";");
						}
						outFile.close();

					} catch (Exception e) {
						System.err.println("Error: " + e.getMessage());
					}

					out.close();
					line.close();
				} catch (IOException e) {
					System.err.println("I/O problems: " + e);
					System.exit(-1);
				}

			}

		});

		listeningThread.start();
	}
    
}
