package t1;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map.Entry;

public class VigenereCipherBreaker implements CipherBreaker {
	String cipherText;
	ArrayList<Double> averages = new ArrayList<>();
	// every key with the IOC value
	Hashtable<Integer, Double> hash = new Hashtable<>();
	ArrayList<Integer> keys = new ArrayList<>();

	@Override
	public void setCipherText(String text) {
		// TODO Auto-generated method stub
		cipherText = text;
	}

	@Override
	public int computeKeyLength() {
		getAllPossible(cipherText);
		// TODO Auto-generated method stub
		moreThanThreshold();// fill the keys
		int best = 0;
		for (int i = 0; i < keys.size(); i++) {
			if (checkMultiples(keys.get(i))) {
				best = keys.get(i);
				break;
			}

		}
		return best;
	}

	public void moreThanThreshold() {
		for (Entry<Integer, Double> entry : hash.entrySet()) {
			Integer key = entry.getKey();
			Double value = entry.getValue();
			if (value > 0.053) {
				keys.add(key);
				System.out.println("Key: " + key + " Value: " + value);
			}
		}
		Collections.reverse(keys);
		System.out.println(keys);
	}

	public boolean checkMultiples(int key) {
		for (Entry<Integer, Double> entry : hash.entrySet()) {
			for (int k = key; k < 22; k *= 2) {
				if (!hash.containsKey(k))
					return false;
			}

		}
		return true;
	}

	public ArrayList<Double> getAllPossible(String s) {
		for (int i = 2; i <= 22; i++) {
			ArrayList<String> m = split(s, i);
			ArrayList<Double> ics = new ArrayList<>();
			for (int j = 0; j < m.size(); j++) {
				ics.add(calculateIOC(m.get(j)));
			}
			averages.add(getAvg(ics));
			hash.put(i, getAvg(ics));

		}
		return averages;
	}

	// get string at key key from string s.
	public ArrayList<String> split(String s, int key) {
		ArrayList<String> output = new ArrayList<>();
		for (int j = key; j >= 1; j--) {

			String m = "";
			for (int i = key - j; i < s.length(); i += key) {
				m += Character.toString(s.charAt(i));
			}
			output.add(m);
		}
		return output;
	}

	public double calculateIOC(String s) {

		int stringLength = 0;
		double sum = 0.0;
		double ic = 0.0;
		int[] values = new int[26];
		for (int i = 0; i < 26; i++) {
			values[i] = 0;
		}

		int frequency;
		for (int i = 0; i < s.length(); i++) {
			frequency = s.charAt(i) - 65;
			values[frequency]++;
			stringLength++;
		}
		for (int i = 0; i < 26; i++) {
			frequency = values[i];
			sum = sum + (frequency * (frequency - 1));
		}

		ic = sum / (stringLength * (stringLength - 1));

		return ic;
	}

	public double getAvg(ArrayList<Double> ic) {
		double sum = 0.0;
		for (int i = 0; i < ic.size(); i++) {
			sum += ic.get(i);
		}
		return sum / ic.size();
	}

	public static void main(String[] args) {
		VigenereCipherBreaker v = new VigenereCipherBreaker();
		// double c = v.calculateIC(
		// "VVQGYTVVVKALURWFHQACMMVLEHUCATWFHHIPLXHVUWSCIGINCMUHNHQRMSUIMHWZODXTNAEKVVQGYTVVQPHXINWCABASYYMTKSZRCXWRPRFWYHXYGFIPSBWKQAMZYBXJQQABJEMTCHQSNAEKVVQGYTVVPCAQPBSLURQUCVMVPQUTMMLVHWDHNFIKJCPXMYEIOCDTXBJWKQGAN");
		// System.out.println(c);
		// ArrayList<String> g = v.split(
		// "VVQGYTVVVKALURWFHQACMMVLEHUCATWFHHIPLXHVUWSCIGINCMUHNHQRMSUIMHWZODXTNAEKVVQGYTVVQPHXINWCABASYYMTKSZRCXWRPRFWYHXYGFIPSBWKQAMZYBXJQQABJEMTCHQSNAEKVVQGYTVVPCAQPBSLURQUCVMVPQUTMMLVHWDHNFIKJCPXMYEIOCDTXBJWKQGAN",
		// 3);
		// System.out.println(g);
		// v.setCipherText(
		// "HAKQUMKNMJENPCACRMGDUCCDYAMGRLQFMJENPFTUHBQNTDLXGNWQFMJEPGSMGRGUPBTAECRFQFMJEZCLTZYEKELCSFCLEWNKGGTTDXFYXNLHYSNPOKDIMKNZVHBUAMCDBUTTPCXQFKQUZJLRPIGGTRVWHOIENIHPMBNELKSTPUMVEKNYBPSBINBHIVCNMNIMVLXDLNGGKGEGRLTPEMYHHUETREWGSVGNWGDEKFXHOKOSTTELQAFCZBPGEAPKKMBVIOGTACTMJERUTBNLMJIGMDBIIMCLPCTVJELCRXCPKGTMANXCTBFET");
		// // int n = v.computeKeyLength();
		// // System.out.println(n);
		// System.out.println(v.calculateIC(
		// "HAKQUMKNMJENPCACRMGDUCCDYAMGRLQFMJENPFTUHBQNTDLXGNWQFMJEPGSMGRGUPBTAECRFQFMJEZCLTZYEKELCSFCLEWNKGGTTDXFYXNLHYSNPOKDIMKNZVHBUAMCDBUTTPCXQFKQUZJLRPIGGTRVWHOIENIHPMBNELKSTPUMVEKNYBPSBINBHIVCNMNIMVLXDLNGGKGEGRLTPEMYHHUETREWGSVGNWGDEKFXHOKOSTTELQAFCZBPGEAPKKMBVIOGTACTMJERUTBNLMJIGMDBIIMCLPCTVJELCRXCPKGTMANXCTBFET"));
		ArrayList<String> g = v.split(
				"HAKQUMKNMJENPCACRMGDUCCDYAMGRLQFMJENPFTUHBQNTDLXGNWQFMJEPGSMGRGUPBTAECRFQFMJEZCLTZYEKELCSFCLEWNKGGTTDXFYXNLHYSNPOKDIMKNZVHBUAMCDBUTTPCXQFKQUZJLRPIGGTRVWHOIENIHPMBNELKSTPUMVEKNYBPSBINBHIVCNMNIMVLXDLNGGKGEGRLTPEMYHHUETREWGSVGNWGDEKFXHOKOSTTELQAFCZBPGEAPKKMBVIOGTACTMJERUTBNLMJIGMDBIIMCLPCTVJELCRXCPKGTMANXCTBFET",
				3);
		// ArrayList<Double> ic = new ArrayList<>();
		// for (int i = 0; i < g.size(); i++) {
		// System.out.println(v.calculateIC(g.get(i)));
		// ic.add(v.calculateIC(g.get(i)));
		// }
		// System.err.println(v.getAvg(ic));
		// System.out.println(g);
		// System.out.println(v.getAllPossible(
		// "HAKQUMKNMJENPCACRMGDUCCDYAMGRLQFMJENPFTUHBQNTDLXGNWQFMJEPGSMGRGUPBTAECRFQFMJEZCLTZYEKELCSFCLEWNKGGTTDXFYXNLHYSNPOKDIMKNZVHBUAMCDBUTTPCXQFKQUZJLRPIGGTRVWHOIENIHPMBNELKSTPUMVEKNYBPSBINBHIVCNMNIMVLXDLNGGKGEGRLTPEMYHHUETREWGSVGNWGDEKFXHOKOSTTELQAFCZBPGEAPKKMBVIOGTACTMJERUTBNLMJIGMDBIIMCLPCTVJELCRXCPKGTMANXCTBFET"));
		// for (Entry<Integer, Double> entry : v.hash.entrySet()) {
		// Integer key = entry.getKey();
		// Double value = entry.getValue();
		//
		// System.out.println("Key: " + key + " Value: " + value);
		// }
		v.setCipherText(
				"HAKQUMKNMJENPCACRMGDUCCDYAMGRLQFMJENPFTUHBQNTDLXGNWQFMJEPGSMGRGUPBTAECRFQFMJEZCLTZYEKELCSFCLEWNKGGTTDXFYXNLHYSNPOKDIMKNZVHBUAMCDBUTTPCXQFKQUZJLRPIGGTRVWHOIENIHPMBNELKSTPUMVEKNYBPSBINBHIVCNMNIMVLXDLNGGKGEGRLTPEMYHHUETREWGSVGNWGDEKFXHOKOSTTELQAFCZBPGEAPKKMBVIOGTACTMJERUTBNLMJIGMDBIIMCLPCTVJELCRXCPKGTMANXCTBFET");
		v.computeKeyLength();
	}
}
