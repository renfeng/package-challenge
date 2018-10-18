package com.mobiquityinc.packer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Package {

	private double weight;
	private int cost;
	private Set<Item> items;

	Package(Collection<Item> items) {
		setItems(new HashSet<>(items));
	}

	double getWeight() {
		return weight;
	}

	int getCost() {
		return cost;
	}

	Set<Item> getItems() {
		return items;
	}

	private void setItems(Set<Item> items) {
		weight = items.stream().mapToDouble(Item::getWeight).sum();
		cost = items.stream().mapToInt(Item::getCost).sum();
		this.items = items;
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
