package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PackageChallenge {

	private static final Logger logger = LoggerFactory.getLogger(PackageChallenge.class);

	private final Parameters parameters;

	private Package candidate;

	public PackageChallenge(Parameters parameters) {
		this.parameters = parameters;
	}

	public String solve() {
		findAllPackagesUnderWeightLimit();
		if (candidate == null) {
			return "-";
		} else {
			return candidate.getItems().stream()
					.map(item -> item.getIndex() + "")
					.collect(Collectors.joining(","));
		}
	}

	private void findAllPackagesUnderWeightLimit() {
		findNextPackageUnderWeightLimit(new ArrayList<>(parameters.getItems()), new ArrayList<>());
	}

	void findNextPackageUnderWeightLimit(List<Item> remainingItems, List<Item> itemCombination) {
		if (!itemCombination.isEmpty()) {
			double weight = itemCombination.stream().mapToDouble(Item::getWeight).sum();
			if (weight > parameters.getWeightLimit()) {
				return;
			}
			evaluate(itemCombination);
		}
		for (int i = 0; i < remainingItems.size(); i++) {
			List<Item> nextRemaining = new ArrayList<>(remainingItems.subList(i + 1, remainingItems.size()));
			List<Item> nextCombination = new ArrayList<>(itemCombination);
			nextCombination.add(remainingItems.get(i));
			findNextPackageUnderWeightLimit(nextRemaining, nextCombination);
		}
	}

	private void evaluate(List<Item> itemCombination) {
		Package aPackage = new Package(itemCombination);
		if (candidate == null || candidate.getCost() < aPackage.getCost()) {
			candidate = aPackage;
		} else if (candidate.getCost() == aPackage.getCost()) {
			if (aPackage.getWeight() < candidate.getWeight() - Packer.WEIGHT_PRECISION) {
				candidate = aPackage;
			} else if (aPackage.getWeight() < candidate.getWeight() + Packer.WEIGHT_PRECISION) {
				String message = "the behaviour is undefined when two packages have both the same cost and weight\n" +
						printPackage(aPackage) + "\n" + printPackage(candidate);
				logger.error(message);

				/*
				 * throws exception (complaining about the input) to avoid ambiguity of correct answers
				 */
				throw new APIException(message);
			}
		}
	}

	private String printPackage(Package aPackage) {
		return aPackage.getItems().size() + "," +
				String.format("%.2f", aPackage.getWeight()) + "," +
				aPackage.getCost() + " : " +
				aPackage.getItems().stream()
						.map(Item::toString)
						.collect(Collectors.joining(" "));
	}
}
