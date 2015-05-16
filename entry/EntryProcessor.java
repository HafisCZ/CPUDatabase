package entry;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class EntryProcessor {

	private File readable;

	public int blocks = 6;

	private String rawText = "";
	private Object[][] entries;

	private String divider = "^";
	private String entryDivider = "|";

	public EntryProcessor(String path) throws IOException {
		this.readable = new File(path);
		this.readable.createNewFile();
	}

	public EntryProcessor readEntries() throws IOException {
		if (this.readable.isFile()) {
			String uncleared = new String(Files.readAllBytes(Paths.get(this.readable.getCanonicalPath())));
			if (uncleared.length() <= 0) {
				this.entries = clean();
				return this;
			}
			rawText = clearRaw(uncleared);
			String[] temp2 = rawText.split("\\|");
			for (int i = 0; i >= temp2.length; i++) {
				temp2[i] = temp2[i].substring(0, 1).toUpperCase() + temp2[i].substring(1);
			}
			Arrays.sort(temp2);
			entries = new Object[temp2.length][blocks];
			for (int i = 0; i < temp2.length; i++) {
				String[] temp3 = temp2[i].split("\\^");
				for (int y = 0; y < blocks; y++) {
					this.entries[i][y] = temp3[y];
				}
			}
		}
		return this;
	}

	public EntryProcessor saveFile() throws IOException {
		if (this.readable.isFile()) {
			BufferedWriter w = new BufferedWriter(new FileWriter(readable));
			w.write(rawText);
			w.close();
		}
		return this;
	}

	public String getRaw() {
		return this.rawText;
	}

	public void writeEntry(String value) {
		this.rawText += value;
	}

	public Object[][] customFilter(String manufacturer, String socket) {
		Object[][] prefixed = this.entries;
		prefixed = manufacturerFilter(manufacturer, prefixed);
		return socketFilter(socket, prefixed);
	}

	public Object[][] manufacturerFilter(String prefix, Object[][] filterArray) {
		if (prefix == null || filterArray.length == 0) return filterArray;
		ArrayList<Object[]> prefixList = new ArrayList<Object[]>();
		for (Object[] o : filterArray) {
			if (o[0].equals(prefix)) prefixList.add(o);
		}
		Object[][] prefixed = new Object[prefixList.size()][blocks];
		prefixed = prefixList.toArray(prefixed);
		return prefixed;
	}

	public Object[][] socketFilter(String prefix, Object[][] filterArray) {
		if (prefix == null || filterArray.length == 0) return filterArray;
		ArrayList<Object[]> prefixList = new ArrayList<Object[]>();
		for (Object[] o : filterArray) {
			if (o[1].equals(prefix)) prefixList.add(o);
		}
		Object[][] prefixed = new Object[prefixList.size()][blocks];
		prefixed = prefixList.toArray(prefixed);
		return prefixed;
	}

	public void modifyEntry(String value, boolean doAll, String newValue) {
		if (doAll) {
			this.rawText = this.rawText.replaceAll(value, newValue);
		} else {
			this.rawText = this.rawText.replace(value, newValue);
		}
	}

	public void removeEntry(String value, boolean doAll) {
		if (doAll) {
			this.rawText = this.rawText.replaceAll(value, "");
		} else {
			this.rawText = this.rawText.replace(value, "");
		}
	}

	public void setDivider(String div) {
		this.divider = div;
	}

	public String getDivider() {
		return this.divider;
	}

	public void setEntryDivider(String div) {
		this.entryDivider = div;
	}

	public String getEntryDivider() {
		return this.entryDivider;
	}

	public Object[][] getEntries() {
		if (this.entries.length > 0) {
			return this.entries;
		} else {
			return new Object[][] {};
		}
	}

	private String clearRaw(String rawDirty) {
		String doubleD = this.entryDivider + this.entryDivider;
		if (rawDirty.contains(doubleD)) rawDirty = rawDirty.replace(doubleD, this.entryDivider);
		if (rawDirty.charAt(0) == this.entryDivider.toCharArray()[0]) rawDirty = rawDirty.substring(1);
		return rawDirty;
	}

	public Object[][] clean() {
		return new Object[][] {};
	}

}