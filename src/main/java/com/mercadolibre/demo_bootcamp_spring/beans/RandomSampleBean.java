package com.mercadolibre.demo_bootcamp_spring.beans;

import com.mercadolibre.demo_bootcamp_spring.dtos.SampleDTO;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class RandomSampleBean {
	private Random random = new Random();
	public SampleDTO random() {
		return new SampleDTO(random.nextInt(Integer.MAX_VALUE));
	}
}

