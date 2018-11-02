package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import org.junit.Assert;
import org.junit.Test;

public class PackageChallengeTest {

	@Test(expected = APIException.class)
	public void givenAmbiguity_shouldThrow() {
		Parameters parameters = Parameters.parse("30 : (1,15.3,€34) (2,15.3,€34)");
		new PackageChallenge(parameters).solve();
	}

	@Test
	public void givenSamePackagesOfLessCost_shouldNotThrow() {
		Parameters parameters = Parameters.parse("30 : (1,15.3,€34) (2,15.3,€34) (3,15.3,€35)");
		String solution = new PackageChallenge(parameters).solve();
		Assert.assertEquals("3", solution);
	}

	@Test
	public void givenTwoHalfWeight_shouldFit() {
		Parameters parameters = Parameters.parse("31 : (1,15.5,€34) (2,15.5,€34)");
		String solution = new PackageChallenge(parameters).solve();
		Assert.assertEquals("1,2", solution);
	}

	@Test
	public void givenSameCost_shouldPreferLessWeight() {
		Parameters parameters = Parameters.parse("30 : (1,15,€34) (2,15.5,€34)");
		String solution = new PackageChallenge(parameters).solve();
		Assert.assertEquals("1", solution);
	}

	@Test
	public void givenFifteenItems_shouldWork() {
		Parameters parameters = Parameters.parse("15 :" +
				" (1,1,€1) (2,1,€1) (3,1,€1) (4,1,€1) (5,1,€1)" +
				" (6,1,€1) (7,1,€1) (8,1,€1) (9,1,€1) (10,1,€1)" +
				" (11,1,€1) (12,1,€1) (13,1,€1) (14,1,€1) (15,1,€1)");
		String solution = new PackageChallenge(parameters).solve();
		Assert.assertEquals("1,2,3,4,5,6,7,8,9,10,11,12,13,14,15", solution);
	}
}