/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javanunes.robo.controllers;

/**
 *
 * @author ricardo
 */
public class Respostas {
    Mensagens ms = new Mensagens();
    
    public void responder(String pergunta){
              
          if(pergunta.contains("Wie gehts")){
              ms.mandarMensagemPraSalaToscamente("Mir geht es gut");
              limpa();
          }
        
    }
    
    public void limpa(){
        Mensagens.mensagemRecebidaPuraBuffer="";
    }
    
}
