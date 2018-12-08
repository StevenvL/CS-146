package Project1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Steven Luu
 * songShuffler is a class that will shuffle a playlist. It does this by taking in a file, reading it, and shuffling it
 * using the Fisher-Yates algorithm.
 * 
 * It will then return an output file with the shuffled songs.
 */
public class songShuffler {
	private static final String INPUT_FILE = "src/Project1/Playlist.txt"; 						//Input file given by teacher
	private static final String OUTPUT_FILE = "src/Project1/LuuStevenPlaylist.txt";			//Output file created by program
	public String[] playlist; //This will be the playlist that will contain the shuffled songs

	/**
	 * The constructor will do 3 things/methods. It will convert the .txt file into an arraylist and then into an array.
	 * Shuffle the array and eventually print the array into an output .txt file.
	 * @throws IOException
	 */
	public songShuffler() throws IOException {
		inputFileToArray();
		shuffleArray();
		printArray();
	}

	/**
	 * Reads the input .txt file and transfers the data into an arraylist. Eventually converts that arraylist to an array.
	 * @throws IOException
	 */
	private void inputFileToArray() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE));
		ArrayList<String> tempPlaylist = new ArrayList<String>();
		String readline;

		while ((readline = br.readLine()) != null) {
			readline = readline.trim();
			tempPlaylist.add(readline);
		}

		br.close();
		playlist = tempPlaylist.toArray(new String[tempPlaylist.size()]);
	}

	/**
	 * Shuffles the arraylist using the Fisher-Yates algorithm and a seed of "20".
	 */
	private void shuffleArray() {
		Random randomGenerator = new Random();
		randomGenerator.setSeed(20);

		for (int i = playlist.length - 1; i > 0; i--) {
			int swapWith = randomGenerator.nextInt(i);

			String temp = playlist[swapWith];
			playlist[swapWith] = playlist[i];
			playlist[i] = temp;
		}
	}

	/**
	 * Prints the shuffled array into the output .txt file.
	 * @throws IOException
	 */
	private void printArray() throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE));
		for (int i = 0; i < playlist.length; i++) {
			writer.write(playlist[i]);
			writer.newLine();
		}
		writer.close();
	}
}
