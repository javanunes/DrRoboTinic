/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javanunes.robo.controllers;

import java.util.Map;

/**
 *
 * @author ricardo
 */
public class Respostas {
    Mensagens ms = new Mensagens();
    Arquivos av = new Arquivos();
    String respostaPronta = "";
    Map<String,String> respostas,respostasSemNulos = null;
    
    
    public void respondeAutomatico(String pergunta){
         try{
          // Aqui se carrega respostas automaticas
          
          if(!Mensagens.respostaVeioDoArquivoMemoria)
             respostas = av.lerRespostasProntasArquivo();
          
          if(respostas != null){
              Mensagens.respostaVeioDoArquivoMemoria = true;
              respostasSemNulos = respostas;
          }    
          
          if(respostasSemNulos != null ){ 
              respostaPronta = respostasSemNulos.get(pergunta);      
              if(!respostaPronta.isBlank() || !respostaPronta.isEmpty()){
                  ms.mandarMensagemPraSalaToscamente(respostaPronta);
                  Mensagens.respostaVeioDoArquivoMemoria=true;
                  Mensagens.respostaManual = false;
                  limpa();
              }
           }
         }  
         catch(Exception e){
             if(!ms.isSilencioso()){
                System.out.println("Não tenho resposta automática para essa pergunta !");
             }
         }
    }
    
    
    public void responder(String pergunta){
         
        
        
          
          respondeAutomatico(pergunta);
          
          // Coloque AQUI as suas perguntas e respostas ################################################
          if(pergunta.contains("Wie gehts")){
              ms.mandarMensagemPraSalaToscamente("Mir geht es gut");
              Mensagens.respostaManual = false;
              limpa();
          }
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
        
    }
    
    public void limpa(){
        Mensagens.mensagemRecebidaPuraBuffer="";
    }
    
}
