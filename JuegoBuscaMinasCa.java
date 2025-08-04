import java.util.Random;
import java.util.Scanner;

public class BuscaminasSimple {
    private static final int FILAS = 8;
    private static final int COLUMNAS = 8;
    private static final int MINAS = 10;
    
    private char[][] tableroVisible;
    private boolean[][] minas;
    private boolean[][] descubierto;
    private boolean[][] marcado;
    
    public BuscaminasSimple() {
        tableroVisible = new char[FILAS][COLUMNAS];
        minas = new boolean[FILAS][COLUMNAS];
        descubierto = new boolean[FILAS][COLUMNAS];
        marcado = new boolean[FILAS][COLUMNAS];
        inicializarJuego();
    }
    
    private void inicializarJuego() {
        // Inicializar tablero
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                tableroVisible[i][j] = '#';
            }
        }
        
        // Colocar minas aleatoriamente
        Random rand = new Random();
        int minasColocadas = 0;
        while (minasColocadas < MINAS) {
            int fila = rand.nextInt(FILAS);
            int col = rand.nextInt(COLUMNAS);
            if (!minas[fila][col]) {
                minas[fila][col] = true;
                minasColocadas++;
            }
        }
    }
    
    private void mostrarTablero() {
        System.out.print("  ");
        for (int j = 0; j < COLUMNAS; j++) {
            System.out.print(j + " ");
        }
        System.out.println();
        
        for (int i = 0; i < FILAS; i++) {
            System.out.print((char)('A' + i) + " ");
            for (int j = 0; j < COLUMNAS; j++) {
                System.out.print(tableroVisible[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    private int contarMinasAlrededor(int fila, int col) {
        int count = 0;
        for (int i = Math.max(0, fila-1); i <= Math.min(FILAS-1, fila+1); i++) {
            for (int j = Math.max(0, col-1); j <= Math.min(COLUMNAS-1, col+1); j++) {
                if (minas[i][j] && !(i == fila && j == col)) {
                    count++;
                }
            }
        }
        return count;
    }
    
    private void descubrirCasilla(int fila, int col) {
        if (fila < 0 || fila >= FILAS || col < 0 || col >= COLUMNAS || descubierto[fila][col]) {
            return;
        }
        
        descubierto[fila][col] = true;
        
        if (minas[fila][col]) {
            return;
        }
        
        int minasCercanas = contarMinasAlrededor(fila, col);
        tableroVisible[fila][col] = minasCercanas == 0 ? ' ' : (char)(minasCercanas + '0');
        
        if (minasCercanas == 0) {
            for (int i = fila-1; i <= fila+1; i++) {
                for (int j = col-1; j <= col+1; j++) {
                    descubrirCasilla(i, j);
                }
            }
        }
    }
    
    private boolean jugar() {
        Scanner sc = new Scanner(System.in);
        
        while (true) {
            mostrarTablero();
            System.out.println("Ingresa acción (D para descubrir, M para marcar) y coordenada (ej: D A5):");
            String accion = sc.next().toUpperCase();
            String coord = sc.next().toUpperCase();
            
            int fila = coord.charAt(0) - 'A';
            int col = Integer.parseInt(coord.substring(1));
            
            if (fila < 0 || fila >= FILAS || col < 0 || col >= COLUMNAS) {
                System.out.println("Coordenada inválida. Intenta nuevamente.");
                continue;
            }
            
            if (accion.equals("D")) {
                if (minas[fila][col]) {
                    System.out.println("¡BOOM! Has perdido.");
                    revelarMinas();
                    mostrarTablero();
                    return false;
                }
                
                descubrirCasilla(fila, col);
                
                if (verificarVictoria()) {
                    System.out.println("¡Felicidades! Has ganado.");
                    mostrarTablero();
                    return true;
                }
            } else if (accion.equals("M")) {
                marcado[fila][col] = !marcado[fila][col];
                tableroVisible[fila][col] = marcado[fila][col] ? 'F' : '#';
            } else {
                System.out.println("Acción no válida. Usa D o M.");
            }
        }
    }
    
    private boolean verificarVictoria() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                if (!minas[i][j] && !descubierto[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private void revelarMinas() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                if (minas[i][j]) {
                    tableroVisible[i][j] = 'X';
                }
            }
        }
    }
    
    public static void main(String[] args) {
        BuscaminasSimple juego = new BuscaminasSimple();
        juego.jugar();
    }
}
