package data;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    public Polinom reprezentare(String t) {

        String polinomFormat = "([+-]?[0-9]*[Xx]?\\^?[0-9]*)";
        String monomFormat = "([+-]?[0-9]*)([Xx]?)\\^?([0-9]*)";
        Pattern patern = Pattern.compile(polinomFormat);
        Matcher mat1 = patern.matcher(t);
        Polinom p = new Polinom();
        while (!mat1.hitEnd()) {
            int coeficient = 0;
            int exponent = 0;
            mat1.find();
            Pattern patern2 = Pattern.compile(monomFormat);
            Matcher mat2 = patern2.matcher(mat1.group());
            if (mat2.find()) {
                coeficient = Integer.valueOf(mat2.group(1));
                exponent = Integer.valueOf(mat2.group(3));
            }
            Monom m = new Monom(coeficient, exponent);
            p.add(m);
        }
        return p;

    }
}

