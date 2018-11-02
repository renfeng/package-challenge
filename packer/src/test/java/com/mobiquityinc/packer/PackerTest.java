package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

public class PackerTest {

	@BeforeClass
	public static void init() throws IOException {
		InputStream stream = PackerTest.class.getResourceAsStream("/logging.properties");
		LogManager.getLogManager().readConfiguration(stream);
	}

	@Test
	public void giveInput_shouldMatchOutput() throws IOException {
		String solution = Packer.pack("src/test/resources/input");
		String expected = IOUtils.toString(new FileReader("src/test/resources/output"));
		Assert.assertEquals(expected, solution);
	}

	@Test(expected = APIException.class)
	public void giveInvalidTestFile_shouldThrow() {
		Packer.pack(null);
	}

	@Test(expected = APIException.class)
	public void givenInvalidParameterFormat_shouldThrow() {
		Packer.pack("src/test/resources/invalid");
	}
}