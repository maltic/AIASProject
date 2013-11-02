package robotrain;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import neuralnet.NeuralNetwork;

/**
 * A special fitness calculated for training using data collecred from DataBot
 * 
 * @author Max
 * 
 */
public class DataNNFitness implements GenericFitness<NeuralNetwork> {
	/**
	 * A little data class to store normalized databot collected data
	 * 
	 * @author Max
	 * 
	 */
	private class BData {
		double x, y, heading, dist, velocity, eheading;

		public BData(Scanner sc) {
			// read a single entry and normalize all the data
			this.x = sc.nextDouble() / 800.0;
			this.y = sc.nextDouble() / 600.0;
			this.heading = sc.nextDouble() / 360.0;
			double power = sc.nextDouble() / 3.0;
			this.dist = sc.nextDouble() / 1000.0;
			double ee = sc.nextDouble() / 100.0;
			double em = sc.nextDouble() / 100.0;
			this.velocity = (sc.nextDouble() + 8.0) / 16.0;
			this.eheading = sc.nextDouble() / 360.0;
		}
	}

	private ArrayList<BData> hits;
	private ArrayList<BData> misses;

	public DataNNFitness() {
		hits = new ArrayList<BData>();
		misses = new ArrayList<BData>();
		try {
			FileReader fr = new FileReader(BattleRunner.RobocodePath
					+ "/robots/maxsbots/DataBot.data/data");
			Scanner sc = new Scanner(fr);
			// loop through all battle entries
			while (sc.hasNextInt()) {
				// read all hits
				int numHits = sc.nextInt();
				for (int i = 0; i < numHits; ++i) {
					hits.add(new BData(sc));
				}
				// read all the misses
				int numMisses = sc.nextInt();
				for (int i = 0; i < numMisses; ++i) {
					misses.add(new BData(sc));
				}
			}
			// close resources
			sc.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public double calculateFitness(NeuralNetwork nn) {
		double err = 0.0;
		// number of mispredicted hits
		for (BData bd : hits) {
			double out = nn.feedForward(new double[] { bd.x, bd.y, bd.heading,
					bd.velocity, bd.eheading, bd.dist })[0];
			err += Math.abs(1 - out);
		}
		// number of misprediced misses
		for (BData bd : misses) {
			double out = nn.feedForward(new double[] { bd.x, bd.y, bd.heading,
					bd.velocity, bd.eheading, bd.dist })[0];
			err += Math.abs(0 - out);
		}
		return -err;
	}

}