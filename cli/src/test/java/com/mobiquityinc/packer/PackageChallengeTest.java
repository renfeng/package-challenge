package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import org.junit.Test;

public class PackageChallengeTest {

	@Test(expected = APIException.class)
	public void givenAmbiguity_shouldThrow() {
		Parameters parameters = Parameters.parse("30 : (1,15.3,€34) (2,15.3,€34)");
		new PackageChallenge(parameters).solve();
	}
}