package com.mobiquityinc.packer;

import java.util.Collection;
import java.util.stream.Collectors;

public class Package {

	private final double weight;
	private final int cost;
	private final Collection<Item> items;

	Package(Collection<Item> items) {
		weight = items.stream().mapToDouble(Item::getWeight).sum();
		cost = items.stream().mapToInt(Item::getCost).sum();
		this.items = items;
	}

	double getWeight() {
		return weight;
	}

	int getCost() {
		return cost;
	}

	Collection<Item> getItems() {
		return items;
	}

	@Override
	public String toString() {
		return getItems().size() + "," +
				String.format(Packer.WEIGHT_FORMAT, getWeight()) + "," +
				getCost() + " : " +
				getItems().stream()
						.map(Item::toString)
						.collect(Collectors.joining(" "));
	}
}
