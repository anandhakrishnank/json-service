package org.smartwork.json.model;

import java.util.Arrays;

public class JsonResponse {
	
	private String key;
	
	private int[] numbers;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int[] getNumbers() {
		return numbers;
	}

	public void setNumbers(int[] numbers) {
		this.numbers = numbers;
	}

	@Override
	public String toString() {
		return "SampleResponse [key=" + key + ", numbers=" + Arrays.toString(numbers) + "]";
	}
	
	
	
	

}
