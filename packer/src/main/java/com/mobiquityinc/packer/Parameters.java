package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class Parameters {

	private static final Logger logger = LoggerFactory.getLogger(Parameters.class);

	/*
	 * https://regex101.com/r/kXDloC/1
	 */
	private static final Pattern LINE_PATTERN = Pattern.compile("(?<weightLimit>\\d+) :(?<items>(?: [(][^)]*[)])+)");

	private final int weightLimit;
	private final Map<Integer, Item> items;

	static Parameters parse(String parameters) {
		Matcher matcher = LINE_PATTERN.matcher(parameters);
		if (matcher.matches()) {
			int weightLimit = Integer.parseInt(matcher.group("weightLimit"));
			String items = matcher.group("items");
			return new Parameters(weightLimit, Item.parseList(items));
		}

		String message = "invalid parameters format: " + parameters;
		logger.error(message);
		throw new APIException(message);
	}

	private Parameters(int weightLimit, List<Item> items) {
		assert weightLimit <= 100 : "Max weight that a package can take is ≤ 100";
		assert items.size() <= 15 : "Up to 15 items";

		this.weightLimit = weightLimit;
		this.items = items.stream().collect(Collectors.toMap(Item::getIndex,
				Function.identity()));
	}

	int getWeightLimit() {
		return weightLimit;
	}

	Item getItem(int index) {
		return items.get(index);
	}

	int getNumberOfItems() {
		return items.size();
	}

	Collection<Item> getItems() {
		return items.values();
	}
}
