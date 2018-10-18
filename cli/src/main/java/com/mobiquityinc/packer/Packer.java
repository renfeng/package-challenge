package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * See PackerTest.java
 */
@SuppressWarnings("WeakerAccess")
public class Packer {

	/*
	 * threshold for decimal comparison
	 */
	static final int DIGITS_AFTER_DECIMAL_POINT = 2;
	static final double WEIGHT_PRECISION = Math.pow(.1, DIGITS_AFTER_DECIMAL_POINT);

	private final List<Parameters> input;

	/**
	 * Determines which things to put into a package so that the total weight is less than or equal to the package limit
	 * and the total cost is as large as possible.
	 *
	 * @param testFilePath path to a test file (can be absolute and relative)
	 * @return the solution
	 */
	public static String pack(String testFilePath) {
		return new Packer(testFilePath).resolve();
	}

	private Packer(String testFilePath) {
		input = parseTestFile(testFilePath);
	}

	private List<Parameters> parseTestFile(String path) {
		try (Stream<String> lines = Files.lines(Paths.get(path))) {
			return lines.map(Parameters::parse).collect(Collectors.toList());
		} catch (APIException e) {
			throw e;
		} catch (Exception e) {
			throw new APIException(e);
		}
	}

	private String resolve() {
		return input.stream()
				.map(parameters -> new PackageChallenge(parameters).solve())
				.collect(Collectors.joining("\n")) + "\n";
	}
}
