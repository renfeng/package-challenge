package com.mobiquityinc.packer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PackageChallenge {

	private final Parameters parameters;
	private final Collection<Package> candidates;

	public PackageChallenge(Parameters parameters) {
		this.parameters = parameters;
		candidates = new ArrayList<>();
	}

	public String solve() {
		findAllPackagesByWeightLimit(new ArrayList<>(parameters.getItems()), parameters.getWeightLimit(), new ArrayList<>());
		if (candidates.isEmpty()) {
			return "-";
		} else {
			List<Package> packagesSortedByCost = candidates.stream()
					.sorted()
					.collect(Collectors.toList());
			return packagesSortedByCost.get(0).getItems().stream()
					.map(item -> item.getIndex() + "")
					.collect(Collectors.joining(","));
		}
	}

	void findAllPackagesByWeightLimit(List<Item> items, double weightLimit, List<Item> partial) {
		double weight = 0;
		for (Item x : partial) {
			weight += x.getWeight();
		}
		if (weight >= weightLimit) {
			return;
		}
		if (!partial.isEmpty()) {
			candidates.add(new Package(partial));
		}

		for (int i = 0; i < items.size(); i++) {
			List<Item> remaining = new ArrayList<>();
			Item n = items.get(i);
			for (int j = i + 1; j < items.size(); j++) {
				remaining.add(items.get(j));
			}
			List<Item> partial_rec = new ArrayList<>(partial);
			partial_rec.add(n);
			findAllPackagesByWeightLimit(remaining, weightLimit, partial_rec);
		}
	}
}
