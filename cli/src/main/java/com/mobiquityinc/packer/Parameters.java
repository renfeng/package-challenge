package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Parameters {

	private final int weightLimit;
	private final Map<Integer, Item> items;

	public static Parameters parse(String parameters) {
		/*
		 * https://regex101.com/r/kXDloC/1
		 */
		Pattern pattern = Pattern.compile("(?<weightLimit>\\d+) :(?<items>(?: [(][^)]*[)])+)");

		Matcher matcher = pattern.matcher(parameters);
		if (matcher.matches()) {
			int weightLimit = Integer.parseInt(matcher.group("weightLimit"));
			String items = matcher.group("items");
			return new Parameters(weightLimit, Item.parseList(items));
		}
		throw new APIException("invalid parameters format: " + parameters);
	}

	public Parameters(int weightLimit, List<Item> items) {
		assert weightLimit <= 100 : "Max weight that a package can take is ≤ 100";
		assert items.size() <= 15 : "Up to 15 items";

		this.weightLimit = weightLimit;
		this.items = items.stream().collect(Collectors.toMap(Item::getIndex,
				Function.identity()));
	}

	public int getWeightLimit() {
		return weightLimit;
	}

	public Item getItem(int index) {
		return items.get(index);
	}

	public int getNumberOfItems() {
		return items.size();
	}

	public Collection<Item> getItems() {
		return items.values();
	}
}
