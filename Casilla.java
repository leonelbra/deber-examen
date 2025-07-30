package juego;

public class Casilla {
    private boolean tieneMina;
    private boolean descubierta;
    private boolean marcada;
    private int minasAlrededor;

    public Casilla() {
        this.tieneMina = false;
        this.descubierta = false;
        this.marcada = false;
        this.minasAlrededor = 0;
    }

    public boolean tieneMina() {
        return tieneMina;
    }

    public void colocarMina() {
        this.tieneMina = true;
    }

    public boolean estaDescubierta() {
        return descubierta;
    }

    public void descubrir() {
        this.descubierta = true;
    }

    public boolean estaMarcada() {
        return marcada;
    }

    public void marcar() {
        this.marcada = !marcada;
    }

    public int getMinasAlrededor() {
        return minasAlrededor;
    }

    public void incrementarMinasAlrededor() {
        this.minasAlrededor++;
    }

    public String mostrar() {
        if (marcada) return "F";
        if (!descubierta) return "#";
        if (tieneMina) return "X";
        if (minasAlrededor == 0) return " ";
        return String.valueOf(minasAlrededor);
    }
}
