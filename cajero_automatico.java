import java.io.*;
import java.util.Scanner;

public class CajeroAutomatico {
    private static final String SALDO_ARCHIVO = "saldo.dat";

    public static void main(String[] args) {
        inicializarSaldo();
        try (Scanner scanner = new Scanner(System.in)) {
            int opcion;
            
            do {
                clearScreen();
                mostrarMenu();
                System.out.print("Seleccione una opción: ");
                opcion = scanner.nextInt();
                
                switch (opcion) {
                    case 1:
                        clearScreen();
                        consultarSaldo();
                        break;
                    case 2:
                        clearScreen();
                        System.out.print("Ingrese la cantidad a retirar: ");
                        double monto = scanner.nextDouble();
                        retirarDinero(monto);
                        break;
                    case 3:
                        System.out.println("¡Gracias por usar el cajero! Hasta luego.");
                        break;
                    default:
                        System.out.println("Opción no válida. Intente nuevamente.");
                }
                presionarEnterParaContinuar(scanner);
            } while (opcion != 3);
        }
    }

    private static void inicializarSaldo() {
        File archivo = new File(SALDO_ARCHIVO);
        if (!archivo.exists()) {
            try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(SALDO_ARCHIVO))) {
                dos.writeDouble(1000.0);  // Saldo inicial de $1000.00
            } catch (IOException e) {
                System.out.println("Error al inicializar el saldo: " + e.getMessage());
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Menú del Cajero Automático ---");
        System.out.println("1. Consultar saldo");
        System.out.println("2. Retirar dinero");
        System.out.println("3. Salir");
    }

    private static void consultarSaldo() {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(SALDO_ARCHIVO))) {
            double saldo = dis.readDouble();
            System.out.println("Su saldo actual es: $" + saldo);
        } catch (IOException e) {
            System.out.println("Error al leer el saldo: " + e.getMessage());
        }
    }

    private static void retirarDinero(double monto) {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(SALDO_ARCHIVO))) {
            double saldo = dis.readDouble();

            if (monto > saldo) {
                System.out.println("Saldo insuficiente.");
            } else {
                saldo -= monto;
                try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(SALDO_ARCHIVO))) {
                    dos.writeDouble(saldo);
                    System.out.println("Retiro exitoso. Su nuevo saldo es: $" + saldo);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al procesar el retiro: " + e.getMessage());
        }
    }

    private static void presionarEnterParaContinuar(Scanner scanner) {
        System.out.println("Presione Enter para continuar...");
        scanner.nextLine(); // Limpia buffer
        scanner.nextLine(); // Espera Enter
    }

    public static void clearScreen()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public CajeroAutomatico() {
    }
}
