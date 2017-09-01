/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.travelmate.util;

/**
 *
 * @author Wolverine
 */
public class GerarDacNossoNumero {
    
    private String nossoNumero;
    private String carteira;
    private String agencia;
    private String conta;
    private String[] calculo;
    private String[] sequencia = new String[20]; 
    private String[] modulo10= new String[20];
    private String dac;

    public GerarDacNossoNumero(String nossoNumero, String carteira, String agencia, String conta) {
        this.nossoNumero = nossoNumero;
        this.carteira = carteira;
        this.agencia = agencia;
        this.conta = conta;
        calculo = new String[20];
        carregarDados();
        calcularModulo10();
        calcularDigito();
    }
    
    
    
    
    public void carregarDados() {
         
        sequencia[0]= agencia.substring(0,1);
        sequencia[1]= agencia.substring(1,2);
        sequencia[2]= agencia.substring(2,3);
        sequencia[3]= agencia.substring(3,4);
        sequencia[4]= conta.substring(0,1);
        sequencia[5]= conta.substring(1,2);
        sequencia[6]= conta.substring(2,3);
        sequencia[7]= conta.substring(3,4);
        sequencia[8]= conta.substring(4,5);
        sequencia[9]= carteira.substring(0,1);
        sequencia[10]= carteira.substring(1,2);    
        sequencia[11]= carteira.substring(2,3);
        sequencia[12]= nossoNumero.substring(0,1);
        sequencia[13]= nossoNumero.substring(1,2);
        sequencia[14]= nossoNumero.substring(2,3);
        sequencia[15]= nossoNumero.substring(3,4);
        sequencia[16]= nossoNumero.substring(4,5);
        sequencia[17]= nossoNumero.substring(5,6);
        sequencia[18]= nossoNumero.substring(6,7);
        sequencia[19]= nossoNumero.substring(7,8);
        
        
        byte num=1;
        for(int i=0;i<sequencia.length;i++){
            if (num==1){
                modulo10[i]="1";
                num=2;
            }else {
                modulo10[i]="2";
                num=1;
            }
        }
    }
    
    public void calcularModulo10(){
        int numeroSequencia;
        int numeroModulo;
        int soma;
        calculo = new String[20];
        for(int i=0;i<sequencia.length;i++){
            numeroSequencia = Integer.parseInt(sequencia[i]);
            numeroModulo = Integer.parseInt(modulo10[i]);
            soma = numeroSequencia * numeroModulo;
            calculo[i]= String.valueOf(soma);
        }  
    }
    
    public void calcularDigito(){
        int numero;
        int soma=0;
        for(int i=0;i<calculo.length;i++){
            numero = Integer.parseInt(calculo[i]);
            if (numero>9){
                String n = calculo[i];
                int n1 = Integer.parseInt(n.substring(0,1));
                int n2 = Integer.parseInt(n.substring(1,2));
                numero = n1 + n2;
            }
            soma= soma + numero;
        }
        int resto = soma % 10;
        int dac = 10 - resto;
        this.dac = String.valueOf(dac);
    }

    public String getDac() {
        return dac;
    }

    public void setDac(String dac) {
        this.dac = dac;
    }
    
    
}
