package robotrain;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import robocode.BattleResults;

/**
 * Generic fitness calculated for ACO
 * @author Max
 *
 */
public class ACOFitness implements GenericFitness<int[]> {

	BattleRunner br;
	String enemy, acoFile, bot;
	int rounds;

	public ACOFitness(String enemy, String bot, String acoFile, int rounds) {
		br = new BattleRunner();
		this.enemy = enemy;
		this.acoFile = acoFile;
		this.bot = bot;
		this.rounds = rounds;
	}

	@Override
	public double calculateFitness(int[] antSolution) {
		//write the ant walk to file
		try {
			PrintStream s = new PrintStream(new File(BattleRunner.RobocodePath
					+ "/robots/maxsbots/" + acoFile));
			for (int i = 0; i < antSolution.length; ++i)
				s.println(antSolution[i]);
			s.flush();
			s.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		//run the battle
		BattleResults[] res = br.runBattle(true, enemy + "," + bot, rounds);
		//return the percentage score
		return res[1].getScore()
				/ ((double) res[0].getScore() + res[1].getScore());
	}

}
