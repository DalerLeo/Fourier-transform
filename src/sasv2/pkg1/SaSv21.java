/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sasv2.pkg1;

import java.sql.SQLException;
import java.util.List;
import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 *
 * @author dalerleo
 */
public class SaSv21 {

    /**
     * @param args the command line arguments
     */
        public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        short choice = 1;
        int songId = 0;
        String path = "";
        String name = "";
        System.out.println("\t*** WELCOME TO SLOWER VERSION OF SHAZAMMM ***\n\n");
        final short NUM_EL = 400;
        Scanner sc = new Scanner(System.in);
        while (choice != 0) {
            System.out.println("Choose option: \n 1. Store music. \n 2. Search for music \n 0. EXIT");
            choice = sc.nextShort();
            if (choice == 1) {
                System.out.println("Enter path for music");
                path = sc.next();
                System.out.println("Enter name of music");
                name = sc.next();
                songId = insertSong(path, name);
                if (songId == 0) {
                    continue;
                }
            }
            if (choice == 2) {
                System.out.println("Enter path for music");
                path = sc.next();
            }
            if (choice != 0) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                File fileIn = new File(path);
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(fileIn);
                AudioFormat format = audioInputStream.getFormat();
                int bytesPerFrame = audioInputStream.getFormat().getFrameSize();
                long frames = audioInputStream.getFrameLength();
                double durationInSeconds = (frames + 0.0) / format.getFrameRate();
                
                short NUM_OF_CHUNKS = (short) (durationInSeconds / 0.05);
                byte[][] data_chunks = new byte[NUM_OF_CHUNKS][NUM_EL];
                if (bytesPerFrame == AudioSystem.NOT_SPECIFIED) {
                    bytesPerFrame = 1;
                }
                int numBytes = 1024 * bytesPerFrame;
                byte[] audioBytes = new byte[numBytes];
                int numBytesRead = 0;

                while ((numBytesRead = audioInputStream.read(audioBytes, 0, audioBytes.length)) != -1) {
                    outputStream.write(audioBytes, 0, numBytesRead);
                }
                byte[] bytesOut = outputStream.toByteArray();
                int ind = 0;

                for (int i = 0; i < NUM_OF_CHUNKS; i++) {
                    for (int j = 0; j < NUM_EL; j++) {
                        data_chunks[i][j] = bytesOut[ind++];
                    }
                    List<Long> peaks = makeDft(data_chunks[i]);
                    if (choice == 1) {
                        insertHashes(peaks, songId, i);
                    }
                    if (choice == 2) {
                        if (searchSong(peaks)) {
                            System.out.println("find");
                            break;
                        }
                    }

                }
            }
        }
    }

    private static String concat(List<Long> arr) {
        StringBuilder concatenated = new StringBuilder();
        for (Long anArr : arr) {
            concatenated.append(String.valueOf(anArr));
        }
        return concatenated.toString();
    }

    private static boolean searchSong(List<Long> peaks) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        boolean found = false;
        int index = 0;
        try {
            DataBase db = new DataBase();

            ArrayList<String> ids = new ArrayList<>();
            ArrayList<String> collectedIds = new ArrayList<>();

            for (int i = 0; i < peaks.size(); i += 10) {
                String query = "Select * from hashtable WHERE value LIKE '%";
                index = 10 + i;
                if (peaks.size() > index) {
                    String hash = concat(peaks.subList(i, index));
                    query += hash + "%';";
                    ids = db.get_data(query, "trackid", "value");
                } else {
                    String hash = concat(peaks.subList(i, peaks.size()));
                    query += hash + "%';";
                    ids = db.get_data(query, "trackid", "value");
                }
                collectedIds.addAll(ids);
            }
            if (collectedIds.size() == 0) {
                return false;
            }

            StringBuilder getM = new StringBuilder("Select * from track where ");
            for (short k = 0; k < collectedIds.size(); k++) {
                if (k != collectedIds.size() - 1) getM.append("id=").append(collectedIds.get(k)).append(" or ");
                else getM.append("id=").append(collectedIds.get(k)).append(";");

            }
            if (!ids.isEmpty()) {
            ids = db.get_music(getM.toString());
                found = true;
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(ids.get(0)));
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
            }


        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return found;
    }

    private static List<Long> makeDft(byte[] bytesOut) {
        int N = bytesOut.length;
        long magnitudeArr[] = new long[N];
        double real[] = new double[N];
        double imag[] = new double[N];

        double omega = (2 * Math.PI) / N;
        for (int k = 0; k < N; k++) {
            for (int n = 0; n < N; n++) {
                real[k] += bytesOut[n] * Math.cos(omega * n * k);
                imag[k] += -(bytesOut[n] * Math.sin(omega * n * k));
            }

            long magnitude = Math.round(Math.sqrt(Math.pow(real[k], 2) + Math.pow(imag[k], 2)));
            magnitudeArr[k] = magnitude;
        }
        double avarage = 0.0;
        for (long aMagnitudeArr : magnitudeArr) {
            avarage = avarage + aMagnitudeArr;
        }

        avarage = avarage / magnitudeArr.length;
        ArrayList<Long> peaks = new ArrayList<Long>();
        for (long aMagnitudeArr : magnitudeArr) {
            if (aMagnitudeArr >= avarage) {
                peaks.add(aMagnitudeArr);
            }
        }
        return peaks;
    }

    private static void insertHashes(List<Long> peaks, int id, int position) {
        int index = 0;
        StringBuilder hashes = new StringBuilder("insert into hashtable(trackid, value, time_position) values");
        for (int i = 0; i < peaks.size(); i += 100) {
            index = 100 + i;
            if (peaks.size() > index) {
                String hash = concat(peaks.subList(i, index));
                hashes.append("(").append(id).append(",'").append(hash).append("',").append(position).append("),");
            } else {
                String hash = concat(peaks.subList(i, peaks.size()));
                hashes.append("(").append(id).append(", '").append(hash).append("',").append(position).append(");");
            }
        }
        try {
            DataBase db = new DataBase();
            db.execute(hashes.toString(), false);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static int insertSong(String path, String name) {
        String songQuery = String.format("insert into track(name, location) values('%s', '%s')", name, path);
        try {
            DataBase db = new DataBase();
            return db.execute(songQuery, true);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
}
