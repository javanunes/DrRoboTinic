/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javanunes.robo.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

/**
 *
 * @author ricardo
 */
public class Arquivos {
    private final String CONFIGURACOES_BASICAS_INICIALIZACAO = "/tmp/config.sys";
    
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
