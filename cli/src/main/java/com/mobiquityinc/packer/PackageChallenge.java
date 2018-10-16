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
			if (logger.isDebugEnabled()) {
				logger.debug("n/a");
			}
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

	void findNextPackageUnderWeightLimit(List<Item> remaining, List<Item> combination) {
		if (!combination.isEmpty()) {
			double weight = combination.stream().mapToDouble(Item::getWeight).sum();
			if (weight >= parameters.getWeightLimit()) {
				return;
			}
			Package aPackage = new Package(combination);
			if (candidate == null || candidate.getCost() < aPackage.getCost()) {
				candidate = aPackage;
			} else if (candidate.getCost() == aPackage.getCost()) {
				if (aPackage.getWeight() < candidate.getWeight() - Packer.WEIGHT_PRECISION) {
					candidate = aPackage;
				} else if (aPackage.getWeight() < candidate.getWeight() + Packer.WEIGHT_PRECISION) {
					String message = "the behaviour is undefined when two packages have both the same cost and weight";
					logger.error(message);
					/*
					 * throws exception (complaining about the input) to avoid ambiguity of correct answers
					 */
					throw new APIException(message);
				}
			}

		}
		for (int i = 0; i < remaining.size(); i++) {
			List<Item> nextRemaining = new ArrayList<>(remaining.subList(i + 1, remaining.size()));
			List<Item> nextCombination = new ArrayList<>(combination);
			nextCombination.add(remaining.get(i));
			findNextPackageUnderWeightLimit(nextRemaining, nextCombination);
		}
	}
}
