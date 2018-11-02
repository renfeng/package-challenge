package com.mobiquityinc.packer;

import org.openjdk.jmh.annotations.Benchmark;

public class SubsetRecursionBenchmark {

	@Benchmark
	public void emptyPayload() {
	}

	@Benchmark
	public void solveChallenge() {
		Parameters parameters = Parameters.parse("15 :" +
				" (1,1,€1) (2,1,€1) (3,1,€1) (4,1,€1) (5,1,€1)" +
				" (6,1,€1) (7,1,€1) (8,1,€1) (9,1,€1) (10,1,€1)" +
				" (11,1,€1) (12,1,€1) (13,1,€1) (14,1,€1) (15,1,€1)");
		new PackageChallenge(parameters).solve();
	}

}
