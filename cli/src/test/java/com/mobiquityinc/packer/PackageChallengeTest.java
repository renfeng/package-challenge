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
}