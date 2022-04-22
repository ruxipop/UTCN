import data.Monom;
import data.Polinom;
import model.Model;

import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TesteUnitare {
	@Test
	public void adunare() throws Exception {


		Polinom p1 = new Polinom(new ArrayList<Monom>(Arrays.asList(new Monom(4, 5), new Monom(-3, 4), new Monom(1, 2), new Monom(-8, 1), new Monom(1, 0))));
		Polinom p2 = new Polinom(new ArrayList<Monom>(Arrays.asList(new Monom(3, 4), new Monom(-1, 3), new Monom(1, 2), new Monom(2, 1), new Monom(-1, 0))));
		Polinom p3 = new Polinom(new ArrayList<Monom>(Arrays.asList(new Monom(4, 5), new Monom(-1, 3), new Monom(2, 2), new Monom(-6, 1))));

		assertEquals(p3.toString(), Model.adunare(p1, p2).toString());
	}

	@Test
	public void scadere() throws Exception {


		Polinom p1 = new Polinom(new ArrayList<Monom>(Arrays.asList(new Monom(4, 5), new Monom(-3, 4), new Monom(1, 2), new Monom(-8, 1), new Monom(1, 0))));
		Polinom p2 = new Polinom(new ArrayList<Monom>(Arrays.asList(new Monom(3, 4), new Monom(-1, 3), new Monom(1, 2), new Monom(2, 1), new Monom(-1, 0))));
		Polinom p3 = new Polinom(new ArrayList<Monom>(Arrays.asList(new Monom(4, 5), new Monom(-6, 4), new Monom(1, 3), new Monom(-10, 1), new Monom(2, 0))));
		assertEquals(p3.toString(),Model.scadere(p1, p2).toString());
	}

	@Test
	public void inmultire() throws Exception {

		Polinom p1 = new Polinom(new ArrayList<Monom>(Arrays.asList(new Monom(3, 2), new Monom(-1, 1), new Monom(1, 0))));
		Polinom p2 = new Polinom((new ArrayList<Monom>(Arrays.asList(new Monom(1, 1), new Monom(-2, 0)))));
		Polinom p3 = new Polinom(new ArrayList<Monom>(Arrays.asList(new Monom(3, 3), new Monom(-7, 2), new Monom(3, 1), new Monom(-2, 0))));


		assertEquals(p3.toString(),Model.inmultire(p1, p2).toString());

	}

	@Test
	public void impartire() throws Exception {


		Polinom p1 = new Polinom(new ArrayList<Monom>(Arrays.asList(new Monom(1, 3), new Monom(-2, 2), new Monom(6, 1), new Monom(-5, 0))));
		Polinom p2 = new Polinom(new ArrayList<Monom>(Arrays.asList(new Monom(1, 2), new Monom(-1, 0))));
		Polinom prest = new Polinom(new ArrayList<Monom>(Arrays.asList(new Monom(7, 1), new Monom(-7, 0))));
		Polinom pcat = new Polinom(new ArrayList<Monom>(Arrays.asList(new Monom(1, 1),new Monom(-2, 0))));


		assertEquals(prest.toString(),Model.impartire(p1, p2).get(0).toString());
		assertEquals(pcat.toString(),Model.impartire(p1, p2).get(1).toString());

	}

	@Test
	public void derivare() throws Exception {


		Polinom p1 = new Polinom(new ArrayList<Monom>(Arrays.asList(new Monom(1, 3), new Monom(-2, 2), new Monom(6, 1), new Monom(-5, 0))));
		Polinom p2 = new Polinom(new ArrayList<Monom>(Arrays.asList(new Monom(3, 2), new Monom(-4, 1), new Monom(6, 0))));

		assertEquals(p2.toString(),Model.derivare(p1).toString());
	}

	@Test
	public void integrare() throws Exception {
		Polinom p1 = new Polinom(new ArrayList<Monom>(Arrays.asList(new Monom(1, 3), new Monom(4, 2), new Monom(5, 0))));
		Polinom p2 = new Polinom(new ArrayList<Monom>(Arrays.asList(new Monom(0.25, 4), new Monom(1.33, 3), new Monom(5, 1))));

		assertEquals(p2.toString(),Model.integrare(p1).toString());

	}

}



