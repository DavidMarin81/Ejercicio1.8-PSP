import java.io.*;
import java.util.Scanner;

/* Usando ProcessBuilder.Redirect, modifica el Ejemplo5.java para que la salida del proceso
se muestre en la consola, la entrada la toma desde un fichero de texto, y la salida la lleve
a un fichero de texto. Realiza los ejercicios 7, 8 y 9.
 */

public class Ejercicio1_8 {
    public static void main(String[] args) throws IOException {

        Scanner sc = new Scanner(System.in);
        String cadena;
        File fOut = new File("salida.txt");
        File fErr = new File("error.txt");
        System.out.print("Introduzca la cadena: ");
        cadena = sc.nextLine();

        File directorio = new File(".\\out\\production\\Ejercicio1_8-Redireccionamiento");

        ProcessBuilder pb = new ProcessBuilder("java", "Ejercicio1_8-Redireccionamiento", cadena);

        // se establece el directorio donde se encuentra el ejecutable
        pb.directory(directorio);

        // se ejecuta el proceso
        Process p = pb.start();
        try {
            InputStream er = p.getErrorStream();
            BufferedReader brer = new BufferedReader(new InputStreamReader(er));
            String liner = null;
            while ((liner = brer.readLine()) != null)
                System.out.println("ERROR >" + liner);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        // COMPROBACION DE la salida del programa
        int exitVal;
        try {
            exitVal = p.waitFor();
            System.out.println("Valor de Salida: " + exitVal);
            switch (exitVal) {
                case (0):
                    System.out.println("FINAL CORRECTO...");
                    pb.redirectOutput(fOut);
                    pb.start();
                    break;
                case (1):
                    System.out.println("FINAL INCORRECTO...");
                    pb.redirectError(fErr);
                    pb.start();
                    break;
            }

        } catch (InterruptedException e){
            e.printStackTrace();
        }

        try {
            InputStream is = p.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String linea;
            while ((linea = br.readLine()) != null)
            {
                System.out.println(linea);
            }
            br.close();
        } catch (Exception ee) 	{
            ee.printStackTrace();
        }

    }
}
