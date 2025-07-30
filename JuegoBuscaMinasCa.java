package juego;

import java.util.Scanner;

public class JuegoBuscaMinasCa {
    public static void main(String[] args) {
        try (Scanner entrada = new Scanner(System.in)) {
			Tablero tablero = new Tablero();
			boolean jugando = true;

			while (jugando) {
			    tablero.mostrarTablero();
			    System.out.println("\nIngresa una acción:" +
			    					"\n- Descubrir (d)" +
			    					"\n- Marcar (m)" +
			    					"\nSeguido de las coordenadas" +
			    					"\n(ejemplo: d A5):");
			    String accion = entrada.next();
			    String coordenada = entrada.next();

			    int fila = coordenada.toUpperCase().charAt(0) - 'A';
			    int columna = Integer.parseInt(coordenada.substring(1));

			    if (accion.equalsIgnoreCase("d")) {
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
			    } else if (accion.equalsIgnoreCase("m")) {
			        tablero.marcar(fila, columna);
			    } else {
			        System.out.println("Acción no válida.");
			    }
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
