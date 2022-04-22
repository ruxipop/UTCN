package controller;

import model.Model;
import view.View;
import data.Regex;
import data.Polinom;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class Controller {
	private Model model;
	private View view;

	public Controller(View view, Model model) {
		this.model = model;
		this.view = view;

		view.adunareLinstener(new AdunareListener());
		view.scadereLinstener(new ScadereListener());
		view.inmultireLinstener(new InmultireListener());
		view.impartireLinstener(new ImpartireListener());
		view.derivareLinstener(new DerivareListener());
		view.integrareLinstener(new IntegrareListener());
		view.deleteLinstener(new DeleteListener());
		view.instructiuniLinstener(new InstructiuniListener());
		view.unarButtonListener(new UnarListener());
		view.binarButtonListener(new BinarListener());
	}


	//----------------metode-----------//


	//---------------1.adunare-----------//

	class AdunareListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			boolean ok = true;
			view.setInvizibile();

			Regex ba = new Regex();
			Polinom a = new Polinom();
			Polinom b = new Polinom();
			try {
				a = ba.reprezentare(view.getTextPol1());
				b = ba.reprezentare(view.getTextPol2());
			} catch (Exception ex) {
				view.showError("Verificati instructiunile!");
				ok = false;
			}

			if (ok) {
				view.setTextOutput(model.adunare(a, b).toString());
			}

		}
	}


	//----------------2.scadere-----------//


	class ScadereListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			boolean ok = true;
			view.setInvizibile();

			Regex r = new Regex();
			Polinom a = new Polinom();
			Polinom b = new Polinom();
			try {
				a = r.reprezentare(view.getTextPol1());
				b = r.reprezentare(view.getTextPol2());
			} catch (Exception ex) {
				view.showError("Verificati instructiunile!");
				ok = false;

			}

			if (ok) {
				view.setTextOutput(model.scadere(a, b).toString());
			}
		}
	}


	//----------------3.inmultire-----------//

	class InmultireListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			boolean ok = true;

			view.setInvizibile();
			Regex ba = new Regex();
			Polinom a = new Polinom();
			Polinom b = new Polinom();
			try {
				a = ba.reprezentare(view.getTextPol1());
				b = ba.reprezentare(view.getTextPol2());
			} catch (Exception ex) {
				view.showError("Verificati instructiunile!");
				ok = false;
			}

			if (ok) {
				view.setTextOutput(model.inmultire(a, b).toString());
			}

		}
	}


	//----------------4.impartire-----------//

	class ImpartireListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			boolean ok = true;

			List<Polinom> polinom = new ArrayList<Polinom>();
			Regex ba = new Regex();
			Polinom a = new Polinom();
			Polinom b = new Polinom();
			try {
				a = ba.reprezentare(view.getTextPol1());
				b = ba.reprezentare(view.getTextPol2());
				view.setIntrariDiv();
			} catch (Exception ex) {
				view.showError("Verificati instructiunile!");
				ok = false;
			}
			if (b.getPolinom().size()> 0) {
				if (b.getPolinom().get(0).getCoeficient() == 0) {
					ok = false;

					try {
						throw new Exception();
					} catch (Exception exception) {
						view.showError("Verificati instructiunile!");
					}
				}

			}
			if (ok) {
				if (a.getPolinom().get(0).getExponent() < b.getPolinom().get(0).getExponent()) {

					view.setTextOutput("0");
					view.setTextRest(a.toString());

				} else {
					polinom = model.impartire(a, b);
					if (polinom.get(0).isEmpty()) {
						view.setTextRest("0");

					} else {

						view.setTextRest(polinom.get(0).toString());
					}

					view.setTextOutput(polinom.get(1).toString());
				}
			}

		}
	}


	//----------------5.derivare-----------//
	class DerivareListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			boolean ok = true;
			view.setInvizibile();

			Regex ba = new Regex();
			Polinom a = new Polinom();
			try {
				a = ba.reprezentare(view.getTextPol1());
			} catch (Exception ex) {
				view.showError("Verificati instructiunile!");
				ok = false;
			}
			if (ok) {
				view.setTextOutput(model.derivare(a).toString());
			}
		}
	}

	//----------------6.integrare-----------//
	class IntegrareListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			boolean ok = true;
			view.setInvizibile();
			Regex ba = new Regex();
			Polinom a = new Polinom();

			try {
				a = ba.reprezentare(view.getTextPol1());
			} catch (Exception ex) {
				view.showError("Verificati instructiunile!");
				ok = false;
			}
			if (ok) {
				view.setTextOutput(model.integrare(a).toString());
			}


		}
	}

	//----------------7.stergere-----------//

	class DeleteListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			view.setInvizibile();
			view.resetPol1("");
			view.resetPol2("");
			view.setTextOutput("");
			;
		}
	}


	//----------------8.instructiuni-----------//

	class InstructiuniListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			view.setInvizibile();
			view.showMessageDialog();

		}
	}


	class UnarListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			view.setInvizibile();
			view.setIntrariUnar();
		}
	}


	class BinarListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			view.setInvizibile();
			view.setIntratiBinar();

		}
	}

}





