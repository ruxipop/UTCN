package model;
import data.Monom;
import data.Polinom;
import java.util.ArrayList;
import java.util.List;



public class Model {


	/*--------------------------adunare--------------------*/
	public static Polinom adunare(Polinom a, Polinom b) {
		Monom rezm;
		Polinom rezp = new Polinom();
		Polinom rezp1 = new Polinom();
		int da = a.getPolinom().size();
		int db = b.getPolinom().size();
		Monom auxa;
		Monom auxb;
		int i = 0, j = 0;
		while (i < da && j < db) {
			auxa = a.getPolinom().get(i);
			auxb = b.getPolinom().get(j);
			if (auxa.getExponent() == auxb.getExponent()) {
				rezm = new Monom(auxa.getCoeficient() + auxb.getCoeficient(), auxa.getExponent());
				rezp.add(rezm);
				i++;
				j++;
			} else {
				if (auxa.getExponent() > auxb.getExponent()) {
					rezp.add(auxa);
					i++;
				} else {
					rezp.add(auxb);
					j++;
				}
			}
		}
		while (i < da) {
			auxa = a.getPolinom().get(i);
			rezp.add(auxa);
			i++;
		}
		while (j < db) {
			auxb = b.getPolinom().get(j);
			rezp.add(auxb);
			j++;
		}
		rezp1 = verificare(rezp);
		return rezp1;
	}


	/*--------------------------scadere--------------------*/
	public static Polinom scadere(Polinom a, Polinom b) {
		Monom rezm;
		Polinom rezp = new Polinom();
		Polinom rez1 = new Polinom();
		Polinom p1 = new Polinom();
		int da = a.getPolinom().size();
		int db = b.getPolinom().size();
		Monom auxa;
		Monom auxb;
		int i = 0, j = 0;
		while (i < da && j < db) {
			auxa = a.getPolinom().get(i);
			auxb = b.getPolinom().get(j);
			if (auxa.getExponent() == auxb.getExponent()) {
				rezm = new Monom(auxa.getCoeficient() - auxb.getCoeficient(), auxa.getExponent());
				rezp.add(rezm);
				i++;
				j++;
			} else {
				if (auxa.getExponent() > auxb.getExponent()) {
					rezp.add(auxa);
					i++;
				} else {
					auxb.setCoeficient(-auxb.getCoeficient());
					rezp.add(auxb);
					j++;
				}
			}
		}
		while (i < da) {
			auxa = a.getPolinom().get(i);
			rezp.add(auxa);
			i++;
		}
		while (j < db) {
			auxb = b.getPolinom().get(j);
			auxb.setCoeficient(-auxb.getCoeficient());
			rezp.add(auxb);
			j++;
		}
		rez1 = verificare(rezp);
		return rez1;
	}



	/*--------------------------inmultire--------------------*/

	public static Polinom inmultire(Polinom a, Polinom b) {

		Monom rezm;
		Polinom rezp = new Polinom();
		for (Monom m1 : a.getPolinom()) {
			Polinom p1 = new Polinom();
			for (Monom m2 : b.getPolinom()) {
				rezm = new Monom(m1.getCoeficient() * m2.getCoeficient(), m1.getExponent() + m2.getExponent());
				p1.add(rezm);
			}
			rezp = adunare(rezp, p1);
		}
		return rezp;
	}





	/*--------------------------impartire--------------------*/

	public static List<Polinom> impartire(Polinom a, Polinom b) {


		Polinom rezp = new Polinom();
		List<Polinom> polinom = new ArrayList<Polinom>();


		while (!a.getPolinom().isEmpty() && a.getPolinom().get(0).getExponent() >= b.getPolinom().get(0).getExponent()) {
			Monom m1 = new Monom(b.getPolinom().get(0).getCoeficient(), b.getPolinom().get(0).getExponent());
			Monom m2 = a.getPolinom().get(0);
			double coeficientM = m2.getCoeficient() / m1.getCoeficient();
			int exponentM = m2.getExponent() - m1.getExponent();

			Monom m3 = new Monom(coeficientM, exponentM);
			Polinom p = new Polinom();
			p.add(m3);

			rezp.add(m3);

			Polinom p1 = new Polinom();
			p1 = inmultire(b, p);
			a = scadere(a, p1);

			a.elimMonom(0);

		}


		polinom.add(a);
		polinom.add(rezp);


		return polinom;

	}






	/*--------------------------derivare--------------------*/


	public static Polinom derivare(Polinom a) {
		Polinom rez1 = new Polinom();
		Polinom rezp = new Polinom();
		for (Monom m1 : a.getPolinom()) {
			m1.setCoeficient(m1.getCoeficient() * m1.getExponent());
			m1.setExponent(m1.getExponent() - 1);
			rezp.add(m1);
		}
		rez1 = verificare(rezp);
		return rez1;

	}


	/*--------------------------integrare--------------------*/

	public static Polinom integrare(Polinom a) {
		Polinom rez1 = new Polinom();
		Polinom rezp = new Polinom();
		for (Monom m1 : a.getPolinom()) {
			m1.setCoeficient(m1.getCoeficient() / (m1.getExponent() + 1));
			m1.setExponent(m1.getExponent() + 1);
			rezp.add(m1);
		}
		rez1 = verificare(rezp);
		return rez1;

	}

	public static Polinom verificare(Polinom rezp) {
		Polinom rezp1 = new Polinom();
		int m = 0;
		int n = 0;
		while (m < rezp.getPolinom().size()) {
			double coeficient = rezp.getPolinom().get(m).getCoeficient();
			Monom m1 = rezp.getPolinom().get(m);
			for (n = m + 1; n < rezp.getPolinom().size(); n++) {
				if (m1.getExponent() == rezp.getPolinom().get(n).getExponent()) {
					coeficient = coeficient + rezp.getPolinom().get(n).getCoeficient();
					rezp.elimMonom(n);
					n--;
				}


			}
			rezp1.add(new Monom(coeficient, m1.getExponent()));
			m++;

		}
		selectionSort(rezp1);
		return rezp1;

	}

	public static void selectionSort(Polinom rezp1) {
		for (int i = 0; i < rezp1.getPolinom().size() - 1; i++) {
			for (int j = 1 + i; j < rezp1.getPolinom().size(); j++) {
				if (rezp1.getPolinom().get(i).getExponent() < rezp1.getPolinom().get(j).getExponent()) {
					int m1 = rezp1.getPolinom().get(i).getExponent();
					double m2 = rezp1.getPolinom().get(i).getCoeficient();
					rezp1.getPolinom().get(i).setCoeficient(rezp1.getPolinom().get(j).getCoeficient());
					rezp1.getPolinom().get(i).setExponent(rezp1.getPolinom().get(j).getExponent());
					rezp1.getPolinom().get(j).setExponent(m1);
					rezp1.getPolinom().get(j).setCoeficient(m2);
				}
			}
		}

	}

}



