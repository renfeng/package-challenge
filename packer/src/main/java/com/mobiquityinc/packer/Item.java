package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Item {

	private static final Logger logger = LoggerFactory.getLogger(Item.class);

	/*
	 * https://regex101.com/r/nV9QJb/1
	 */
	private static final Pattern ITEM_PATTERN = Pattern.compile(" [(](?<index>\\d+),(?<weight>\\d+(?:[.]\\d+)?),€(?<cost>\\d+)[)]");

	private final int index;
	private final double weight;
	private final int cost;

	static List<Item> parseList(String items) {
		ArrayList<Item> list = new ArrayList<>();

		Matcher matcher = ITEM_PATTERN.matcher(items);
		int expectedPosition = 0;
		while (matcher.find() && matcher.start() == expectedPosition) {
			Item item = new Item(Integer.parseInt(matcher.group("index")),
					Double.parseDouble(matcher.group("weight")),
					Integer.parseInt(matcher.group("cost")));
			list.add(item);
			expectedPosition = matcher.end();
		}
		if (expectedPosition < items.length()) {
			String message = "invalid item format: " + items.substring(expectedPosition);
			logger.error(message);
			throw new APIException(message);
		}
		return list;
	}

	Item(int index, double weight, int cost) {
		assert weight <= 100 : "Max weight of an item is ≤ 100";
		assert cost <= 100 : "Cost of an item is ≤ 100";

		this.index = index;
		this.weight = weight;
		this.cost = cost;
	}

	int getIndex() {
		return index;
	}

	double getWeight() {
		return weight;
	}

	int getCost() {
		return cost;
	}

	@Override
	public String toString() {
		return "(" + getIndex() + "," + getWeight() + "," + getCost() + ")";
	}
}
