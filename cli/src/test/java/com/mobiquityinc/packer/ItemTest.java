package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import org.junit.Test;

public class ItemTest {

	@Test(expected = APIException.class)
	public void givenInvalidItemFromBeginning_shouldThrow() {
		Item.parseList("invalid from the beginning");
	}

	@Test(expected = APIException.class)
	public void givenInvalidItemFromMiddle_shouldThrow() {
		Item.parseList(" (1,15.3,€34)invalid from somewhere in the middle");
	}
}