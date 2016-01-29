package com.caseStudy;

import java.util.StringTokenizer;

/**
 * Created by root on 16/1/16.
 */
public class Calculation {

	public static int findMax(int arr[]) {
		int max = arr[0];
		for (int i = 0; i < arr.length; i++) {
			if (max < arr[i])
				max = arr[i];
		}
		return max;
	}

	public static int cube(int n) {
		return n * n * n;
	}

	public static String reverseWords(String str) {
		StringBuilder result = new StringBuilder();
		StringTokenizer tokenizer = new StringTokenizer(str, "");

		while (tokenizer.hasMoreElements()) {
			StringBuilder sb = new StringBuilder();
			sb.append(tokenizer.nextToken());
			sb.reverse();

			result.append(sb);
			result.append("");
		}
		return result.toString();
	}
}
