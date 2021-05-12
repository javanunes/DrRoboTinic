package com.javanunes.robo.view;
import com.javanunes.robo.controllers.Arquivos;
import com.javanunes.robo.controllers.Mensagens;
import com.javanunes.robo.controllers.SegundoPlano;
import java.util.Scanner;



public class View  extends Thread{
    
    public static void main(String[] args) {
        try{
          String bannerApresentacao="\n\nMicrocroft Windows Telegram Robotinic [Version 10.0.242469]\n(c) 2015 Microsoft Corporation. All rights reserved.\n";  
          Mensagens ms = new Mensagens();
          Arquivos av = new Arquivos();
          SegundoPlano sp = new SegundoPlano();
          Scanner teclado = null;
          teclado = new Scanner(System.in);
          String comando = null;
          
          System.out.println(bannerApresentacao);
            
          if (ms.getToken().isEmpty() || ms.getUrlAPI().isEmpty() || ms.getSalaID().isEmpty() ){
              ms.setSaudavel(false);
          }
          
       
          
          if(ms.isSaudavel()){
            if(!ms.isSilencioso())  
            System.out.println("Subdindo segundo plano");
            
            Thread segundoPlano =  new Thread(sp);
            segundoPlano.start();
          }
          
        
          while(ms.isSaudavel()){
             System.out.println(ms.getPrompt()); 
             comando = teclado.nextLine();
             
             switch (comando) {
                  case "cd":
                    
                    break;
                  case "chat":
                      while(true){
                        ms.setPrompt("C:\\sala\\>");
                        System.out.println(ms.getPrompt());
                        comando = teclado.nextLine();
                        if(comando.equals("exit") || comando.equals("sair") || comando.equals("quit")){
                            ms.setPrompt(ms.getPrompt());
                            break;
                        }
                        ms.mandarMensagemPraSalaToscamente(comando);
                     }
                     break;
                  case "exit":
                    System.exit(0);
                    break;
                  default:

             }
          } 
        }
        catch(Exception e){
            System.out.println("ERRO COMPLEXO TREE:"+e);    
        }
        
        
    } // fim main
    
}// fim View
