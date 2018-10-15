package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Packer {

	public static String pack(String testFilePath) {
		return resolve(parseParameters(testFilePath));
	}

	private static List<Parameters> parseParameters(String testFilePath) {
		try (Stream<String> lines = Files.lines(Paths.get(testFilePath))) {
			return lines.map(Parameters::parse).collect(Collectors.toList());
		} catch (APIException e) {
			throw e;
		} catch (Exception e) {
			throw new APIException(e);
		}
	}

	private static String resolve(List<Parameters> input) {
		return input.stream()
				.map(parameters -> new PackageChallenge(parameters).solve())
				.collect(Collectors.joining("\n")) + "\n";
	}
}
