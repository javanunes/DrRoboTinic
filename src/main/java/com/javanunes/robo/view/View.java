package com.javanunes.robo.view;
import com.javanunes.robo.controllers.Arquivos;
import com.javanunes.robo.controllers.Mensagens;
import com.javanunes.robo.controllers.SegundoPlano;
import java.util.Scanner;



public class View  extends Thread{
    
    public static void exibirMenuAjuda(){
        System.out.println("\n\n----- AJUDA DO DRROBOTINIC ------");
        System.out.println("help   : trás esse menu");
        System.out.println("cd     : lhe pergunta a sala que você deseja entrar no formato numerico, ex: -192838337");
        System.out.println("exit   : finaliza esse programa, termina a saida de texto para uma sala");
        System.out.println("chat   : manda a saida de texto direto para a sala escolhida");
        System.out.println("");     
    }
    
    
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
          
// ########################################### O MAIN COMEÇA AQUI ##############################################################################################        
          while(ms.isSaudavel()){
             System.out.println(ms.getPrompt()); 
             comando = teclado.nextLine();
             
             switch (comando) {
                  case "help":
                    exibirMenuAjuda();
                    break;
                  case "cd":
                    System.out.println("Codigo da sala a mandar: ");
                    comando = teclado.nextLine();
                    ms.setSalaID(comando);
                    ms.setPrompt("C:\\"+comando+"\\>");
                    comando ="";
                    break;
                  case "chat":
                      while(true){
                        ms.setPrompt("C:\\sendsala\\>");
                        System.out.println(ms.getPrompt());
                        comando = teclado.nextLine();
                        if(comando.equals("exit") || comando.equals("sair") || comando.equals("quit")){
                            ms.setPrompt("C:\\Windows\\>");
                            break;
                        }
                        ms.mandarMensagemPraSalaToscamente(comando);
                     }
                     break;
                  case "exit":
                    System.exit(0);
                    break;
                  default:

             }// fim switch
          } // fim while 
        }
        catch(Exception e){
            System.out.println("O DrRobotinic encontrou uma doença e precisa parar :"+e); 
        }
    } // fim main
    
}// fim View
