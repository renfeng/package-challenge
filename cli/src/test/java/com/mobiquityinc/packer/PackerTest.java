package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;

public class PackerTest {

	@Test
	public void giveInput_shouldMatchOutput() throws IOException {
		String solution = Packer.pack("src/test/resources/input");
		String expected = IOUtils.toString(new FileReader("src/test/resources/output"));
		Assert.assertEquals(expected, solution);
	}

	@Test(expected = APIException.class)
	public void giveIncorrectParameters_shouldThrow() {
		Packer.pack(null);
	}
}