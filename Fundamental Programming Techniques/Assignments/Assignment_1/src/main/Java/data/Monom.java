package data;

public class Monom {


	private int exponent;
	private double coeficient;

	public Monom(double coeficient, int exponent) {
		this.exponent = exponent;

		this.coeficient = coeficient;
	}

	public void setExponent(int exponent) {
		this.exponent = exponent;
	}

	public void setCoeficient(double coeficient) {
		this.coeficient = coeficient;
	}

	public double getCoeficient() {
		return coeficient;
	}

	public int getExponent() {
		return exponent;
	}

	public boolean isOne() {
		if (coeficient == 1 || coeficient == -1) {
			return true;
		}
		return false;
	}

	public boolean isIntreg() {
		if (coeficient == (int) (coeficient) || coeficient == -(int) (coeficient)) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		if (coeficient == 0) {
			return "";
		}

		if (coeficient > 0) {

			if (exponent == 1) {
				return "+" + (isOne() ? "" : (isIntreg() ? (int) (coeficient) : String.format("%.2f", coeficient))) + "x";
			}

			if (exponent == 0) {
				return "+" + (isOne() ? "1" : (isIntreg() ? (int) (coeficient) : String.format("%.2f", coeficient)));
			} else

				return "+" + (isOne() ? "" : (isIntreg() ? (int) (coeficient) : String.format("%.2f", coeficient))) + "x^" + (int) exponent + " ";
		} else {
			if (exponent == 1) {
				return (isOne() ? "-" : (isIntreg() ? (int) (coeficient) : String.format("%.2f", coeficient))) + "x";
			}
			if (exponent == 0) {
				return "" + (isOne() ? "-1" : (isIntreg() ? (int) (coeficient) : String.format("%.2f", coeficient)));
			}
			return (isOne() ? "-" : (isIntreg() ? (int) (coeficient) : String.format("%.2f", coeficient))) + "x^" + (int) exponent + " ";
		}

	}
}





