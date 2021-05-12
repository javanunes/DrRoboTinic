/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javanunes.robo.controllers;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ricardo
 */
public class SegundoPlano extends Thread{
    Mensagens ms = new Mensagens();
    Respostas rp = new Respostas();
    private int segundos = 1;
    private String lixo = "";
    
    @Override
    public void run() {
     try{
        lixo = ms.chupaRetorna(ms.getURLTelegramUpdates());
        Mensagens.numeroUltimaMensagemNova = ms.getLinhasJsonTelegram(lixo);
        String usuario = "";
        String mensagem = "";
        String sala= "";
        int linhas = 0;
        
        while(ms.isSaudavel()){
                if(segundos > 60)
                   segundos = 0;
                
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SegundoPlano.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                lixo = ms.chupaRetorna(ms.getURLTelegramUpdates());
                
                if(ms.getLinhasJsonTelegram(lixo) > Mensagens.numeroUltimaMensagemNova){
                    linhas = ms.getLinhasJsonTelegram(lixo);
                    usuario = ms.getUsuario(ms.getBloco(lixo, linhas));
                    sala = ms.getSala(ms.getBloco(lixo, linhas));
                    mensagem = ms.getTexto(ms.getBloco(lixo, linhas));
                    Mensagens.mensagemRecebida = "@" + usuario + " disse: " + mensagem;
                    Mensagens.mensagemRecebidaPuraBuffer = mensagem;
                    System.out.println(ms.getPrompt() + Mensagens.mensagemRecebida);
                    Mensagens.numeroUltimaMensagemNova = linhas;
                    rp.responder(Mensagens.mensagemRecebidaPuraBuffer);
                    mensagem="";
                }
                
                if(!ms.isSilencioso())
                 System.out.println(segundos);
                segundos++;   
                
        }
            
      }
      catch(NullPointerException e){
          System.out.println("A conexao com o servidor TELEGRAM não foi bem sucedida! Token, URL da API ou senhas erradas!");
      }
            
    }
       
}
