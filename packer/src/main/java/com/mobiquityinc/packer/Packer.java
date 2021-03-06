package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * See PackerTest.java
 */
public class Packer {

	private static final Logger logger = LoggerFactory.getLogger(Packer.class);

	/*
	 * threshold for decimal comparison
	 */
	static final double WEIGHT_PRECISION = .001;
	static final String WEIGHT_FORMAT = "%.2f";

	private final List<Parameters> input;

	/**
	 * Determines which things to put into a package so that the total weight is less than or equal to the package limit
	 * and the total cost is as large as possible.
	 *
	 * @param testFilePath path to a test file (can be absolute and relative)
	 * @return the solution
	 */
	@SuppressWarnings("WeakerAccess")
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
			logger.error("path = " + path, e);
			throw new APIException(e);
		}
	}

	private String resolve() {
		return input.stream()
				.map(parameters -> new PackageChallenge(parameters).solve())
				.collect(Collectors.joining("\n")) + "\n";
	}
}
