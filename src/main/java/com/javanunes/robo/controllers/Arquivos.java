/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javanunes.robo.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Properties;

/**
 *
 * @author ricardo
 */
public class Arquivos {
    private final String CONFIGURACOES_BASICAS_INICIALIZACAO = "/tmp/config.sys";
    private final String CONFIGURACOES_SALAS_LOGS = "/tmp/lastsalas.log";
    private final String CONFIGURACOES_BATEPAPO_LOGS = "/tmp/chats.log";
    
    public boolean existe(String arquivo){
        try{
           File fs = new File(arquivo);
           if(fs.exists()){
              return true; 
           }
           else{
              fs.createNewFile();
              return false;
           }
        }
        catch(Exception e){
            return false;
        }
    }
    
    public void salvarUltimaSala(String ultimaSala){
        if(!ultimaSala.isEmpty()){
            try{
               FileWriter arq = new FileWriter(CONFIGURACOES_SALAS_LOGS,true);
               arq.write(ultimaSala+"\n");
               arq.close();
            }
            catch(Exception e){
              System.out.println("Impossivel escrever em log de ultimas da sala: "+e);
            }
        }
    }
    
    public void listarConteudoDe(String arquivo){
            if(!arquivo.isEmpty()){
            try{
               BufferedReader br = new BufferedReader(new FileReader(arquivo));
               while(br.ready()){
                  System.out.println("C:\\>"+br.readLine());
               }
               br.close();
            }
            catch(Exception e){
               System.out.println("Nao deu!");
            }
        }
    }
    
    public void listarConteudoDeLogUtilmasSalas(){
          listarConteudoDe(CONFIGURACOES_SALAS_LOGS);
    }
    
    
    public void salvarValoresCampos(Mensagens ms){
        try{        
            if(!existe(CONFIGURACOES_BASICAS_INICIALIZACAO)){
                System.out.println(CONFIGURACOES_BASICAS_INICIALIZACAO + " não existe, criando...");
            }
            if(!ms.isSilencioso())
             System.out.println("Salvando");
            
            FileInputStream cfgr = new FileInputStream(CONFIGURACOES_BASICAS_INICIALIZACAO);
            FileOutputStream cfgw = new FileOutputStream(CONFIGURACOES_BASICAS_INICIALIZACAO);
            Properties parametros = new Properties();
            if(ms.getToken() == null){
               System.out.println("Token está vazio ou nulo para salvar");
               ms.setSaudavel(false);
            }    
            parametros.setProperty("token", ms.getToken());
            parametros.setProperty("salaID", ms.getSalaID());
            parametros.setProperty("urlAPI", ms.getUrlAPI());
            parametros.store(cfgw, CONFIGURACOES_BASICAS_INICIALIZACAO);
            cfgw.close();
        }
        catch(Exception e){
            System.out.println("ERRO FS EM "+CONFIGURACOES_BASICAS_INICIALIZACAO+" "+e);
        }
    }
    
    public String getValorCampo(String campo){
        try{
            if(!existe(CONFIGURACOES_BASICAS_INICIALIZACAO)){
               System.out.println(CONFIGURACOES_BASICAS_INICIALIZACAO + " não existe, criando...");
            }
            FileInputStream cfgr = new FileInputStream(CONFIGURACOES_BASICAS_INICIALIZACAO);
            Properties parametros = new Properties();
            parametros.load(cfgr);
            cfgr.close();
            return parametros.getProperty(campo);
        }
        catch(Exception e){
            System.out.println("Erro ao obter campos do arquivo "+ CONFIGURACOES_BASICAS_INICIALIZACAO + " pois "+e);
            return "";
        }
    }
    
    
    
    
}
