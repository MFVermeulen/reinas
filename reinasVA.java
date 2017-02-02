import java.util.*;

/**
 * Programa principal para encontrar soluciones al problema aplicando el algoritmo de vuelta atrás.
 * 
 * @author Melvin Vermeulen
 * @version 11.01.2017
 */


public class ReinasVA
{
    int N;
    int pos;
    boolean traza = false;
    boolean grafico = false;
    boolean completable = true;
    
    private String trazaTexto = "";
    private String solucionGrafica = "";
    
    int k;
    
    private ArrayList<Integer> soluciones;
    private ArrayList<ArrayList<Integer>> solucionFinal;
    String solucionesTexto = "\nSoluciones del problema\n\n";
    
    
    /**
     * Constructor de la clase ReinasVA
     */
    public ReinasVA(int N, boolean traza, boolean grafico)
    {
        this.N = N;
        this.traza = traza;
        this.grafico = grafico;
        soluciones = new ArrayList<Integer>(N);
        solucionFinal = new ArrayList<ArrayList<Integer>>();
        pos = 0;
        k = 0;
        
        for(int i = 0; i<N; i++){
            soluciones.add(k, 0);
        }
        
        calculaSolucionesVA(soluciones, N, k);

    }
    
    public void calculaSolucionesVA(ArrayList<Integer> soluciones, int N, int k){
        
        if(traza){
            trazaTexto += "k: " + k + "\n";
        }
        
        soluciones.set(k, 0);
   
        while(soluciones.get(k) < N){
            
            soluciones.set(k, soluciones.get(k)+1);
            
            if(traza){
                trazaTexto += "soluciones[" + k + "]: " + soluciones.get(k) + "\n";
            }
            
            completable = completable(soluciones, k);
            
            if(traza){
                trazaTexto += "Completable: " + completable + "\n";
            }
            
            if(completable){      
                if(k == N-1){
                    solucionFinal.add(new ArrayList<Integer>(N));
                    for(int l = 0; l<N; l++){
                        (solucionFinal.get(pos)).add(soluciones.get(l));
						
                    }
					solucionesTexto += pos + 1 + ": ";
					for(int i = 0; i < N; i++){
						if(i>25){
							solucionesTexto += (char)((i/25)+97) + "" + (char)((i%25)+97) + "" + soluciones.get(i) + "";
						}else{
							solucionesTexto += (char)(i+97) + "" + soluciones.get(i) + " ";
						}
					}
					solucionesTexto += "\n";

                    pos++;
                    if(traza){
                        trazaTexto += "--------------------Solucion: ";
                        for(int j = 0; j<N; j++){
                            trazaTexto += j + "-" + soluciones.get(j) + "; ";
                        }
                        trazaTexto += "\n";
                    }
                } else{
                    calculaSolucionesVA(soluciones, N, k+1);
                }
            } else{
                if(traza){
                    trazaTexto += "No completable: ";

                    for(int i = 0; i<=k; i++){
                        trazaTexto += i + "-" + soluciones.get(i) + "; ";
                    }
                    trazaTexto += "\n";
                }
            }

        }
        
    }
    
    public boolean completable(ArrayList<Integer> solucionParcial, int k){
        
        boolean completable = true;
        
        for(int i = 0; i <= k-1; i++){ 
            if(solucionParcial.get(i) == solucionParcial.get(k) || ((Math.abs((solucionParcial.get(i))-(solucionParcial.get(k)))) == Math.abs(i-k))){
                completable = false;
            }
        }

        return completable;
    }
    
    public String getSolucionGrafica(){
        
        int numeroDeFila = 0;
        
        for(int pos = 0; pos < solucionFinal.size(); pos++){
            solucionGrafica += "   -";
            for(int i = 0; i < N; i++){
                solucionGrafica += "----";
            }
            solucionGrafica += "\n";
            for(int j = 0; j < N; j++){
                numeroDeFila = N-j;
                if(numeroDeFila < 10){
                    solucionGrafica += " ";
                }
                solucionGrafica += numeroDeFila + " |";
                for(int i = 0; i < N; i++){
                    if((solucionFinal.get(pos)).get(i) == numeroDeFila){
                        solucionGrafica += " R |";
                    } else {
                        if((i%2)==(j%2)){
                            solucionGrafica += "   |";
                        } else {
                            solucionGrafica += " * |";
                        }
                    }
                }
                solucionGrafica += "\n   -";
                for(int q = 0; q<N; q++){
                    solucionGrafica += "----";
                }
                solucionGrafica += "\n";
            }
            solucionGrafica += "     ";
            for(int l = 0; l<N; l++){
                if(l>25){
                    solucionGrafica += (char)((l/25)+97) + (char)((l%25)+97) + "  ";
                } else {
                    solucionGrafica += (char)(l+97) + "   ";
                }
            }
                
            solucionGrafica += "\n\n";
        }
        return solucionGrafica;
    }
    
    public String getTraza(){
        return trazaTexto;
    }
    
    public String getSoluciones()
    {
        return solucionesTexto;
    }
}
