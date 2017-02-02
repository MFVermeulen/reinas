import java.io.*;
import java.util.*;


/**
 * Programa 'reinas' para resolver el problema de las 8 reinas.
 * 
 * La primera parte del programa se encargará de las operaciones de entrada y salida.
 * El cálculo de las soluciones se hará en ReinasVA.java
 * 
 * @author Melvin Vermeulen
 * @version 18.01.2017
 */

public class reinas
{        
    public static void main(String args[]){
        
        boolean traza = false;
        boolean grafico = false;
        boolean salidaFichero = false;
        
        int argIndex = 0;
        
        int N = 0;
        String fichero_salida = "";
        
        String trazaArchivo = "";
        String solucionGrafica = "";
        String cabeceraArchivo = "";
        String textoSalida = "";
    
        String soluciones = "";
        
        BufferedReader lector = null;
        
        if(args.length == 0){
            System.out.println("No se ha pasado ningún parámetro.");
            System.exit(1);
        } else if(args.length == 1 && args[0].equals("-h")){

            System.out.println("\nSINTAXIS: \n" +
                                "reinas [-t][-g][-h] N [fichero_salida] \n" +
                                "-t                     Traza \n" +
                                "-g                     Modo gráfico \n" +
                                "-h                     Muestra esta ayuda \n" +
                                "N                      Tamaño del tablero y número de reinas \n" +
                                "fichero_salida         Nombre del fichero de salida \n");
            System.exit(1);
            
        } else if(args.length == 1){
            try{
                N = Integer.parseInt(args[0]);
                argIndex = 1;
            } catch(NumberFormatException e){
                System.out.println("Valor de tamaño incorrecto.");
                System.exit(1);
            }
        } else if((args[0].equals("-t") || args[0].equals("-g")) && (args.length == 1)){
            System.out.println("Debes indicar un tamaño.");
            System.exit(1);
        } else if((args[0].equals("-t") && args[1].equals("-g")) && (args.length == 2)){
            System.out.println("Debes indicar un tamaño");
        } else if((args[0].equals("-t") && args[1].equals("-g")) && (args.length > 2)){
            traza = true;
            grafico = true;
            try{
                N = Integer.parseInt(args[2]);
                argIndex = 3;
            } catch(NumberFormatException e){
                System.out.println("Valor de tamaño incorrecto.");
                System.exit(1);
            }
        } else if(args[0].equals("-t") && (args.length > 1)){
            traza = true;
            try{
                N = Integer.parseInt(args[1]);
                argIndex = 2;
            } catch(NumberFormatException e){
                System.out.println("Valor de tamaño incorrecto.");
                System.exit(1);
            }
        } else if(args[0].equals("-g") && (args.length > 1)){
            grafico = true;
            try{
                N = Integer.parseInt(args[1]);
                argIndex = 2;
            } catch(NumberFormatException e){
                System.out.println("Valor de tamaño incorrecto.");
                System.exit(1);
            }
        } else if(args.length == 2){
            try{
                N = Integer.parseInt(args[0]);
                argIndex = 1;
            } catch(NumberFormatException e){
                System.out.println("Valor de tamaño incorrecto.");
                System.exit(1);
            }
        } else{
            System.out.println("Entrada errónea.");
            System.exit(1);
        }
                  
        if(argIndex+1 == args.length){
            fichero_salida = args[argIndex];
            salidaFichero = true;
        }
            
        /**Opciones correctas. Ejecutar programa para calcular las posiciones.*/
        
        if(N < 4){
            System.out.println("No existe solución para este tamaño. Indica un tamaño más grande.");
        } else {
            ReinasVA reinasVA = new ReinasVA(N, traza, grafico);
            soluciones = reinasVA.getSoluciones();
            if(traza){
                trazaArchivo = reinasVA.getTraza();
            }
            if(grafico){
                solucionGrafica = reinasVA.getSolucionGrafica();
            }
        }       
        
        try{
            
                        
            System.out.println("\n-----Problema de las " + N + " reinas.-----\n");                       

                
            if(salidaFichero){
                
                try{
                    
                    File ficheroSalida = new File(fichero_salida);
                                        
                    cabeceraArchivo = "-----Problema de las " + N + " reinas.-----\n";
                
                    if(traza && grafico){

                        textoSalida = cabeceraArchivo + trazaArchivo + solucionGrafica;
                        System.out.println("Traza , solución gráfica y resultado en fichero de salida: " + fichero_salida + ".");

                    } else if(traza && !grafico){
                        textoSalida = cabeceraArchivo + trazaArchivo;
                        System.out.println("Traza y resultado en fichero de salida: " + fichero_salida + ".");
                    } else if(grafico){
                        textoSalida = cabeceraArchivo + solucionGrafica;
                        System.out.println("Solución gráfica y resultado en fichero de salida: " + fichero_salida + ".");
                    } else {
                        System.out.println("Resultado en fichero de salida: " + fichero_salida + ".");
                    }
                    
                    textoSalida += soluciones;
                    
                    escribirArchivo(textoSalida, ficheroSalida);
                    
                } catch(FileNotFoundException e){
                    e.printStackTrace();
                }
                
            } else {
                
                if(traza){
                    System.out.println(trazaArchivo);
                }
                if(grafico){
                    System.out.println(solucionGrafica);
                }
                System.out.println(soluciones);
            }
                
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try {
                if(lector != null){
                    lector.close();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        
    }
    
    /**
     * Definimos un método para poder imprimir la traza y el resultado línea por línea en el fichero de salida.
     * 
     * @param texto el texto a escribir en el archivo
     * @param archivo el archivo al que se va a escribir el texto
     * 
     * @return - nada, ya que se escribe en el archivo
     *
     */
    
    private static void escribirArchivo(String texto, File archivo) throws IOException {
        try (
            BufferedReader lector2 = new BufferedReader(new StringReader(texto));
            PrintWriter escritor = new PrintWriter(new FileWriter(archivo));
            ) {
                lector2.lines().forEach(line -> escritor.println(line));
            }
    }
}
