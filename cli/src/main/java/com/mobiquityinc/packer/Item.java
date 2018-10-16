package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Item implements Comparable<Item> {

	private final int index;
	private final double weight;
	private final int cost;

	public static List<Item> parseList(String items) {
		ArrayList<Item> list = new ArrayList<>();

		/*
		 * https://regex101.com/r/nV9QJb/1
		 */
		Pattern pattern = Pattern.compile(" [(](?<index>\\d+),(?<weight>\\d+(?:[.]\\d+)?),€(?<cost>\\d+)[)]");

		Matcher matcher = pattern.matcher(items);
		int expectedPosition = 0;
		while (matcher.find()) {
			if (matcher.start() == expectedPosition) {
				Item item = new Item(Integer.parseInt(matcher.group("index")),
						Double.parseDouble(matcher.group("weight")),
						Integer.parseInt(matcher.group("cost")));
				list.add(item);
				expectedPosition = matcher.end();
			} else {
				break;
			}
		}
		if (expectedPosition < items.length()) {
			throw new APIException("invalid item format: " + items.substring(expectedPosition));
		}
		return list;
	}

	public Item(int index, double weight, int cost) {
		assert weight <= 100 : "Max weight of an item is ≤ 100";
		assert cost <= 100 : "Cost of an item is ≤ 100";

		this.index = index;
		this.weight = weight;
		this.cost = cost;
	}

	public int getIndex() {
		return index;
	}

	public double getWeight() {
		return weight;
	}

	public int getCost() {
		return cost;
	}

	@Override
	public int compareTo(Item o) {
		return Integer.compare(getIndex(), o.getIndex());
	}

	@Override
	public String toString() {
		return "(" + getIndex() + "," + getWeight() + "," + getCost() + ")";
	}
}
