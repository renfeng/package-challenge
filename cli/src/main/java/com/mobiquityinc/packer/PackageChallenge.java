package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class PackageChallenge {

	private static final Logger logger = LoggerFactory.getLogger(PackageChallenge.class);

	private final Parameters parameters;

	private Package candidate;
	private Package ambiguousCandidate;

	PackageChallenge(Parameters parameters) {
		this.parameters = parameters;
	}

	String solve() {
		findAllPackagesUnderWeightLimit();
		if (candidate == null) {
			return "-";
		}

		if (ambiguousCandidate != null) {
			String message = "the behaviour is undefined when two packages have both the same cost and weight\n" +
					ambiguousCandidate + "\n" + candidate;
			logger.error(message);

			/*
			 * throws exception (complaining about the input) to avoid ambiguity of correct answers
			 */
			throw new APIException(message);
		}

		/*
		 * it is implied by the test data that items are sorted by index
		 */
		return candidate.getItems().stream()
				.sorted(Comparator.comparingInt(Item::getIndex))
				.map(item -> item.getIndex() + "")
				.collect(Collectors.joining(","));
	}

	private void findAllPackagesUnderWeightLimit() {
		/*
		 * since it is preferred to have packages of less weight if they have the same price (a.k.a. cost),
		 * sorts items by weight to optimize item combination traverse
		 */
		findNextPackageUnderWeightLimit(parameters.getItems().stream()
				.sorted(Comparator.comparingDouble(Item::getWeight))
				.collect(Collectors.toList()), new ArrayList<>());
	}

	private void findNextPackageUnderWeightLimit(List<Item> remainingItems, List<Item> itemCombination) {

		for (int i = 0; i < remainingItems.size(); i++) {
			List<Item> nextRemaining = new ArrayList<>(remainingItems.subList(i + 1, remainingItems.size()));

			List<Item> nextCombination = new ArrayList<>(itemCombination);
			nextCombination.add(remainingItems.get(i));

			Package aPackage = new Package(nextCombination);
			if (aPackage.getWeight() > parameters.getWeightLimit()) {
				/*
				 * since items are sorted by weight, item weight will be even higher
				 */
				break;
			}

			evaluate(aPackage);

			findNextPackageUnderWeightLimit(nextRemaining, nextCombination);
		}
	}

	private void evaluate(Package aPackage) {

		if (candidate == null || candidate.getCost() < aPackage.getCost()) {
			candidate = aPackage;
			ambiguousCandidate = null;
		} else if (ambiguousCandidate == null && candidate.getCost() == aPackage.getCost() &&
				aPackage.getWeight() < candidate.getWeight() + Packer.WEIGHT_PRECISION) {
			/*
			 * since items are sorted by weight, it is always true that aPackage.getWeight() >= candidate.getWeight()
			 */
			ambiguousCandidate = aPackage;
		}
	}
}
