package com.undabot.weatherapp.data.utils;

public class TextFormatUtils {

	public static String capitalizeFirstLetter(String original) {
		if (original.length() == 0) {
			return original;
		}
		return original.substring(0, 1).toUpperCase() + original.substring(1);
	}

	public static String capitalizeFirstLetterInEachWord(String original) {
		//First make trim to remove white spaces and check if input is empty
		original = original.trim();
		if (original.length() == 0) {
			return original;
		}
		//Remove unnecessary spaces
		original = original.replaceAll("\\s+", " ");
		//Split words
		String[] words = original.split(" ");
		//Format each word
		StringBuilder formatted = new StringBuilder();
		for (int i = 0; i < words.length; i++) {
			formatted.append(Character.toUpperCase(words[i].charAt(0)));
			formatted.append(words[i].substring(1));
			if (i < words.length - 1) {
				formatted.append(' ');
			}
		}
		return formatted.toString();
	}
}
