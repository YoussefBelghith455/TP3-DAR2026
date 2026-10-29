
package activite3_2;

import java.io.Serializable;

public class Operation implements Serializable {

    private static final long serialVersionUID = 1L;

    private double nombre1;
    private String operateur;
    private double nombre2;

    public Operation(double nombre1, String operateur, double nombre2) {
        this.nombre1 = nombre1;
        this.operateur = operateur;
        this.nombre2 = nombre2;
    }

    public double getNombre1() {
        return nombre1;
    }

    public String getOperateur() {
        return operateur;
    }

    public double getNombre2() {
        return nombre2;
    }
}
