package com.mobiquityinc.packer;

import org.junit.Assert;
import org.junit.Test;

public class ParametersTest {

	@Test
	public void shouldParseLine1() {
		String line = "81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)";
		Parameters parameters = Parameters.parse(line);
		Assert.assertEquals(81, parameters.getWeightLimit());
		Assert.assertEquals(6, parameters.getNumberOfItems());
		assertEquals(new Item(1, 53.38, 45), parameters.getItem(1));
		assertEquals(new Item(2, 88.62, 98), parameters.getItem(2));
		assertEquals(new Item(3, 78.48, 3), parameters.getItem(3));
		assertEquals(new Item(4, 72.30, 76), parameters.getItem(4));
		assertEquals(new Item(5, 30.18, 9), parameters.getItem(5));
		assertEquals(new Item(6, 46.34, 48), parameters.getItem(6));
	}

	@Test
	public void shouldParseLine2() {
		String line = "8 : (1,15.3,€34)";
		Parameters parameters = Parameters.parse(line);
		Assert.assertEquals(8, parameters.getWeightLimit());
		Assert.assertEquals(1, parameters.getNumberOfItems());
		assertEquals(new Item(1, 15.3, 34), parameters.getItem(1));
	}

	/*
	 * TODO negative tests
	 */

	private void assertEquals(Item expected, Item actual) {
		Assert.assertEquals(expected.getIndex(), actual.getIndex());
		Assert.assertEquals(expected.getWeight(), actual.getWeight(), .001);
		Assert.assertEquals(expected.getCost(), actual.getCost());
	}
}