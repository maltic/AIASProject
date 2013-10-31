package strategies.scanning;

import strategies.RobotData;
import strategies.Strategy;

class Point {
	double x, y;

	Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
}

public class SlowPredictiveScan implements Strategy {

	boolean isLeft(Point a, Point b, Point c) {
		return ((b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x)) > 0;
	}

	boolean left = true;

	@Override
	public double[] getNextMove(RobotData data) {
		if (data.scannedAge < 5) {
			double predictedX = Math.cos(data.scannedDirection)
					* data.scannedVelocity * data.scannedAge;
			double predictedY = Math.sin(data.scannedDirection)
					* data.scannedVelocity * data.scannedAge;
			Point predicted = new Point(predictedX, predictedY);
			Point lineStart = new Point(data.x, data.y);
			Point lineEnd = new Point(Math.cos(data.scannerHeading) * 5,
					Math.sin(data.scannerHeading) * 5);
			left = isLeft(lineStart, lineEnd, predicted);
			if (left)
				return new double[] { 3.0 };
			else
				return new double[] { -3.0 };
		} else {
			if (left)
				return new double[] { -5.0 };
			else
				return new double[] { 5.0 };
		}
	}
}