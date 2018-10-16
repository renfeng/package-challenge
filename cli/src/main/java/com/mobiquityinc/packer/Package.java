package com.mobiquityinc.packer;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Package {

	private double weight;
	private int cost;
	private Set<Item> items;

	public Package(Collection<Item> items) {
		setItems(new HashSet<>(items));
	}

	public double getWeight() {
		return weight;
	}

	public int getCost() {
		return cost;
	}

	public Set<Item> getItems() {
		return items;
	}

	public void setItems(Set<Item> items) {
		weight = items.stream().mapToDouble(Item::getWeight).sum();
		cost = items.stream().mapToInt(Item::getCost).sum();
		this.items = new TreeSet<>(items);
	}
}
