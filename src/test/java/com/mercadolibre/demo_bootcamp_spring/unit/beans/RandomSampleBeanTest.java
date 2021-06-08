package com.mercadolibre.demo_bootcamp_spring.unit.beans;

import com.mercadolibre.demo_bootcamp_spring.dtos.SampleDTO;
import com.mercadolibre.demo_bootcamp_spring.beans.RandomSampleBean;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomSampleBeanTest {

	@Test
	public void randomPositiveTestOK() {
		RandomSampleBean randomSample = new RandomSampleBean();

		SampleDTO sample = randomSample.random();
		
		assertTrue(sample.getRandom() >= 0);
	}
}
