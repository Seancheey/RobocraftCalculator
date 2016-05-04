package com.seancheey;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class LanguageConverter {
	private ArrayList<LanguageMap> languages;
	private static LanguageConverter defaultConverter;
	private LanguageMap selectedLanguage;

	private LanguageConverter(ArrayList<LanguageMap> languages) {
		this.languages = languages;
		selectedLanguage = languages.get(0);
	}

	private static ArrayList<String> getElements(String string) {
		ArrayList<String> ss = new ArrayList<>();
		for (String s : string.split("\t"))
			if (s.length() != 0) {
				ss.add(s);
			}
		return ss;
	}

	private static LanguageConverter readConverterConfig(File file) throws IOException {
		FileReader freader = new FileReader(file);
		BufferedReader reader = new BufferedReader(freader);

		ArrayList<String> headers = getElements(reader.readLine());
		ArrayList<LanguageMap> lan = new ArrayList<>();
		{
			for (int i = 0; i < headers.size(); i++) {
				lan.add(new LanguageMap(headers.get(i)));
			}
		}
		while (true) {
			String line = reader.readLine();
			if (line == null)
				break;
			ArrayList<String> row = getElements(line);
			if (row.size() >= headers.size()) {
				for (int i = 0; i < row.size(); i++) {
					lan.get(i).put(row.get(0), row.get(i));
				}
			}
		}
		reader.close();
		freader.close();
		LanguageConverter c = new LanguageConverter(lan);
		return c;
	}

	public static final LanguageConverter defaultCvt() {
		if (defaultConverter == null)
			try {
				defaultConverter = readConverterConfig(new File("res/Languages"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		return defaultConverter;
	}

	public String convertString(String s) {
		for (String eng : selectedLanguage.keySet()) {
			if (eng.equals(s)) {
				return selectedLanguage.get(s);
			}
		}
		return s;
	}

	public void setLanguage(String lan) {
		for (LanguageMap l : languages) {
			if (l.getLanguage().equals(lan)) {
				selectedLanguage = l;
				break;
			}
		}
	}

	@Override
	public String toString() {
		return "LanguageConverter [languages=" + languages + ", selectedLanguage=" + selectedLanguage + "]";
	}

}

class LanguageMap extends HashMap<String, String> {

	private static final long serialVersionUID = 4005738807835257519L;
	private final String language;

	public LanguageMap(String language) {
		this.language = language;
	}

	public String getLanguage() {
		return language;
	}

	@Override
	public String toString() {
		return "LanguageMap [language=" + language + "]";
	}

}