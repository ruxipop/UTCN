package data;

import java.util.ArrayList;
import java.util.List;

public class Polinom {
	private List<Monom> polinom = new ArrayList<>();

	public Polinom(ArrayList<Monom> polimon) {

		this.polinom = polimon;
	}

	public Polinom() {

		polinom = new ArrayList<Monom>();
	}


	public List<Monom> getPolinom() {
		return polinom;
	}

	public void add(Monom m) {
		polinom.add(m);
	}


	public void elimMonom(int i) {
		polinom.remove(i);


	}

	public boolean isEmpty() {
		if (polinom.isEmpty()) return true;
		else return false;

	}


	@Override

	public String toString() {
		String s = "";
		if (polinom.size() == 1) {
			if (polinom.get(0).getCoeficient() == 0)
				s += "0";

		} else {

			for (int i = 0; i < polinom.size() - 1; i++)
				s += polinom.get(i).toString();
		}
		s += polinom.get(polinom.size() - 1).toString();
		return s;
	}


}

