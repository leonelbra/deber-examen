
import java.util.Random;
import java.util.Scanner;

class Casilla {
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

class Tablero {
    private final int FILAS = 10;
    private final int COLUMNAS = 10;
    private final int MINAS = 10;
    private Casilla[][] casillas;

    public Tablero() {
        casillas = new Casilla[FILAS][COLUMNAS];
        inicializarCasillas();
        colocarMinas();
        contarMinasAlrededor();
    }

    private void inicializarCasillas() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                casillas[i][j] = new Casilla();
            }
        }
    }

    private void colocarMinas() {
        Random rand = new Random();
        int minasColocadas = 0;
        while (minasColocadas < MINAS) {
            int fila = rand.nextInt(FILAS);
            int col = rand.nextInt(COLUMNAS);
            if (!casillas[fila][col].tieneMina()) {
                casillas[fila][col].colocarMina();
                minasColocadas++;
            }
        }
    }

    private void contarMinasAlrededor() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                if (!casillas[i][j].tieneMina()) {
                    for (int[] dir : direcciones()) {
                        int ni = i + dir[0], nj = j + dir[1];
                        if (esValido(ni, nj) && casillas[ni][nj].tieneMina()) {
                            casillas[i][j].incrementarMinasAlrededor();
                        }
                    }
                }
            }
        }
    }

    private int[][] direcciones() {
        return new int[][]{
            {-1, -1}, {-1, 0}, {-1, 1},
            { 0, -1},          { 0, 1},
            { 1, -1}, { 1, 0}, { 1, 1}
        };
    }

    private boolean esValido(int fila, int col) {
        return fila >= 0 && fila < FILAS && col >= 0 && col < COLUMNAS;
    }

    public boolean descubrir(int fila, int col) {
        if (!esValido(fila, col) || casillas[fila][col].estaDescubierta()) {
            return true;
        }

        casillas[fila][col].descubrir();

        if (casillas[fila][col].tieneMina()) {
            return false; // Pierde el jugador
        }

        if (casillas[fila][col].getMinasAlrededor() == 0) {
            for (int[] dir : direcciones()) {
                int ni = fila + dir[0], nj = col + dir[1];
                if (esValido(ni, nj)) {
                    descubrir(ni, nj);
                }
            }
        }

        return true;
    }

    public void marcar(int fila, int col) {
        if (esValido(fila, col)) {
            casillas[fila][col].marcar();
        }
    }

    public void mostrarTablero() {
        System.out.print("  ");
        for (int j = 0; j < COLUMNAS; j++) {
            System.out.print(j + " ");
        }
        System.out.println();
        for (int i = 0; i < FILAS; i++) {
            System.out.print((char) ('A' + i) + " ");
            for (int j = 0; j < COLUMNAS; j++) {
                System.out.print(casillas[i][j].mostrar() + " ");
            }
            System.out.println();
        }
    }

    public boolean juegoGanado() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                if (!casillas[i][j].tieneMina() && !casillas[i][j].estaDescubierta()) {
                    return false;
                }
            }
        }
        return true;
    }
}

public class JuegoBuscaMinasCa{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Tablero tablero = new Tablero();
        boolean jugando = true;

        while (jugando) {
            tablero.mostrarTablero();
            System.out.println("Ingresa acción (descubrir o marcar) y coordenadas (ej: descubrir A5):");
            String accion = sc.next();
            String coordenada = sc.next();

            int fila = coordenada.toUpperCase().charAt(0) - 'A';
            int columna = Integer.parseInt(coordenada.substring(1));

            if (accion.equalsIgnoreCase("descubrir")) {
                boolean continuar = tablero.descubrir(fila, columna);
                if (!continuar) {
                    System.out.println("¡BOOM! Has perdido.");
                    tablero.mostrarTablero();
                    break;
                }
                if (tablero.juegoGanado()) {
                    System.out.println("¡Felicidades! Has ganado.");
                    tablero.mostrarTablero();
                    break;
                }
            } else if (accion.equalsIgnoreCase("marcar")) {
                tablero.marcar(fila, columna);
            } else {
                System.out.println("Acción no válida.");
            }
        }

        sc.close();
    }
}
