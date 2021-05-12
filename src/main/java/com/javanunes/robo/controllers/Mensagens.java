/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javanunes.robo.controllers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author ricardo
 */

// Em projetos futuros, Mensagens pode ser apenas uma entidade(struct em c++ kkkkkkkk) e não mais um controller

public class Mensagens {
    // obtenha seu token no telegram do seu bot e coloque em token por exemplo: 2424242424:AHFJZEKTHHFF00X0
    private String token="";
    // URL de comunicao principal com o telegram e suas APIs
    private String urlAPI="";
    private String salaID="";
    private String nick="";
    private String URLAPIMetodoTelegramSufixo="/sendMessage"; 
    private String prompt="C:\\Windows\\>";
    // Para esconder as mensagens de debug
    private boolean silencioso = true;
    private boolean saudavel = true;
    public static String mensagemRecebida=""; 
    public static String mensagemRecebidaPuraBuffer=""; 
    public static Integer numeroUltimaMensagemNova=0;
    
    public Mensagens(){
        if(!isSilencioso())
            System.out.println("Mensagens() chamada ");
        carregaCamposIniciais();
        seCamposVaziosPreenchelosManualmente();
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
   
    public String getURLTelegramUpdates(){
        return this.urlAPI + this.token + "/" + "getUpdates";
    }
    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUrlAPI() {
        return urlAPI;
    }

    public void setUrlAPI(String urlAPI) {
        this.urlAPI = urlAPI;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getSalaID() {
        return salaID;
    }

    public void setSalaID(String salaID) {
        this.salaID = salaID;
    }



    public boolean isSilencioso() {
        return silencioso;
    }

    public void setSilencioso(boolean silencioso) {
        this.silencioso = silencioso;
    }

    public boolean isSaudavel() {
        return saudavel;
    }

    public void setSaudavel(boolean saudavel) {
        this.saudavel = saudavel;
    }

    public String getURLAPIMetodoTelegramSufixo() {
        return URLAPIMetodoTelegramSufixo;
    }

    public void setURLAPIMetodoTelegramSufixo(String URLAPIMetodoTelegramSufixo) {
        this.URLAPIMetodoTelegramSufixo = URLAPIMetodoTelegramSufixo;
    }
   
    
    
    public String chupaRetorna(String siteChupado){
        try{
           if(!silencioso){ 
             System.out.println("Chamando por -> "+siteChupado);
           }
           URL url = new URL(siteChupado);
           URLConnection socket = url.openConnection();
           BufferedReader conseguido = new BufferedReader(new InputStreamReader(socket.getInputStream()));
           String conteudo = "";
           while (conseguido.ready()) {
              conteudo += conseguido.readLine();
           }   
           conseguido.close();
           if(conteudo.isBlank() || conteudo.isEmpty() ){
               System.out.println("A Url "+siteChupado +" nao retornou nada!!!");
           }
           if(conteudo.contains("{\"ok\":true,\"result\":")){
               if(!silencioso)
                System.out.println("Conteúdo esperado do site OK!");
           }
           if(conteudo.contains("{\"ok\":false,\"error_code\":401,\"description\":\"Unauthorized\"}")){
               System.out.println("ERRO FATAL TOKEN DO ROBOT: o seu token está errado ou não existe, crie um correto! Saindo...");
               System.exit(1);
           }
           return conteudo;
        }
        catch(Exception e){
            System.out.println("ERRO FATAL TOKEN DO ROBOT: token errado, de um robot errado ou URL podre, crie um token correto, saindo...");
            System.out.println("o formato geralmente é assim: https://api.telegram.org/bot[token]/[metodoAPITelegram]");
            setSaudavel(false);
            return null;
        }
    }
    
    public String getBloco(String lixo, int linha){
        try{
           JSONObject jsonPreMapeado = new JSONObject(lixo);
           JSONArray pedacoJsonEscolhido = jsonPreMapeado.getJSONArray("result");
           JSONObject blocoLinha = pedacoJsonEscolhido.getJSONObject(linha);
           return blocoLinha.toString();
        }
        catch(Exception e){
            System.out.println("Erro ao obter bloco json para ser quebrado e extraido os campos pois -> "+e);
            return null;
        }
    }
       
    public int getUpdateId(String textoJson, int linha){
        try{
           JSONObject jsonPreMapeado = new JSONObject(textoJson);
           JSONArray pedacoJsonEscolhido = jsonPreMapeado.getJSONArray("result");
           JSONObject blocoLinha = pedacoJsonEscolhido.getJSONObject(linha);
           return blocoLinha.getInt("update_id");
        }
        catch(Exception e){
            System.out.println("Erro ao obter update_id por linha pois -> "+e);
            return 0;
        }
    }
    
    
    public Integer getLinhasJsonTelegram(String textoJsonAbsurdo){
         if(textoJsonAbsurdo.isEmpty() || textoJsonAbsurdo == null){
            System.out.println("Erro fatal de tratamento de lixo: não chegou nenhum lixo aqui ver o que tem dentro dele, saindo...");
            setSaudavel(false);
            System.exit(1);
         }
         try{
           JSONObject jsonPreMapeado = new JSONObject(textoJsonAbsurdo);
           JSONArray blocosRetornados = jsonPreMapeado.getJSONArray("result");
           Integer linhasBlocos = blocosRetornados.length(); 
           blocosRetornados.clear();
           if(!silencioso)
            System.out.println("linhas num: "+linhasBlocos);
           return linhasBlocos - 1;
         }
         catch(Exception e){
            System.out.println("ERRO JSON: erro ao extrair o numero de blocos de jsons enormes a para se examinar->"+e);
            System.out.println("Chegou essa bosta aqui:\n"+textoJsonAbsurdo);
            return null;
         }
    }
    
    public String getUsuario(String lixo){
        int apartirDe =0;
        int comecarProcurarApartirDoPonto=0;
        int tamanhoDessaString = 12;
        final int TAMANHO_DELIMITADOR = 2;    
        String valor = null;
        comecarProcurarApartirDoPonto = lixo.indexOf("\"from\":{");
        apartirDe = lixo.indexOf("\"username\"",comecarProcurarApartirDoPonto)-2;
        valor = lixo.substring(apartirDe + tamanhoDessaString + TAMANHO_DELIMITADOR, lixo.indexOf("\"},", apartirDe + tamanhoDessaString + TAMANHO_DELIMITADOR) );
        return valor;
    }
    
    public String getTexto(String lixo){
        int apartirDe =0;
        int comecarProcurarApartirDoPonto=0;
        int tamanhoDessaString = 7;
        final int TAMANHO_DELIMITADOR = 1;    
        String valor = null;
        comecarProcurarApartirDoPonto = lixo.indexOf("\"from\":{");
        apartirDe = lixo.indexOf("\"text\":",comecarProcurarApartirDoPonto);
        valor = lixo.substring(apartirDe + tamanhoDessaString + TAMANHO_DELIMITADOR, lixo.indexOf("\"}}", apartirDe + tamanhoDessaString + TAMANHO_DELIMITADOR) );
        return valor;
    }
    
    public String getSala(String lixo){
        int apartirDe =0;
        int comecarProcurarApartirDoPonto=0;
        int tamanhoDessaString = 8;
        final int TAMANHO_DELIMITADOR = 1;    
        String valor = null;
        comecarProcurarApartirDoPonto = lixo.indexOf("\"chat\":{");
        apartirDe = lixo.indexOf("\"title\":",comecarProcurarApartirDoPonto);
        valor = lixo.substring(apartirDe + tamanhoDessaString + TAMANHO_DELIMITADOR, lixo.indexOf("\",\"type", apartirDe + tamanhoDessaString + TAMANHO_DELIMITADOR) );
        return valor;
    }
    
    public void mandaProServidorTelegram(String menssagemViaURL){
        try{
            org.apache.http.client.HttpClient httpclient = HttpClients.createDefault();
            HttpPost httppost = new HttpPost( getUrlAPI()  + getToken() + getURLAPIMetodoTelegramSufixo());
            
            // Request parameters and other properties.
            List<NameValuePair> variaveisURL = new ArrayList<NameValuePair>(2);
            variaveisURL.add(new BasicNameValuePair("chat_id", salaID));
            variaveisURL.add(new BasicNameValuePair("text", menssagemViaURL));
            httppost.setEntity(new UrlEncodedFormEntity(variaveisURL, "UTF-8"));

            //Execute and get the response.
            org.apache.http.HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                try (InputStream instream = entity.getContent()) {
                    // do something useful
                }
            }
        }
        catch(IOException e){
            System.out.println("ERRO HTTP AO MANDAR MENSAGENS: não foi possivel se comunicar com o servidor via http em "+getUrlAPI()  + getToken() + getURLAPIMetodoTelegramSufixo()+" pois ->"+e);
            setSaudavel(false);
        }
        
    }
    
    public void mandarMensagemPraSalaToscamente(String mensagem){
         //String comando = "curl -s -X POST " + URLFinal + " -d chat_id=" + salaID + " -d text=" + "caguei\rnacasa";
         //System.out.print(comando);
         try{
            if(!mensagem.equals("exit") && !mensagem.equals("sair") && !mensagem.equals("quit")){
               mandaProServidorTelegram(mensagem); 
            } 
         }
         catch(Exception e){
            System.out.println("ERRO HTTP POST: não foi possivel mandar mensagem via http pro Telegram pois ->"+e);
         }
         //bash(comando);
    }
    
    public void carregaCamposIniciais(){
         Arquivos av = new Arquivos();
         setToken(av.getValorCampo("token"));
         setUrlAPI(av.getValorCampo("urlAPI"));
         setSalaID(av.getValorCampo("salaID"));
    }
 
    public void seCamposVaziosPreenchelosManualmente(){
        try{
            Arquivos av = new Arquivos();
            Scanner teclado = new Scanner(System.in);
            
            if(!isSilencioso()){
                System.out.println("ENTRANDO EM MODO DE EXIGENCIA DE DADOS MANUAIS (é o primeiro uso) :::" );
                System.out.println(System.getProperties().getProperty("os.name"));
            }    
                
            if(getToken() == null){
                System.out.println("C:\\> Token vazio, digite no formato seguinte: 12345:ABCDABCD ");
                setToken(teclado.nextLine());
            }
            if(getSalaID() == null){
               System.out.println("C:\\> Sala id vazia, digite no formato seguinte: -999999292929 ");
               setSalaID(teclado.nextLine());
            }
            if(getUrlAPI()== null){
               System.out.println("C:\\> URL vazia, digite no formato seguinte: https://api.telegram.org/bot  "); 
               setUrlAPI(teclado.nextLine());
            } 
            av.salvarValoresCampos(this);
        }
        catch(Exception e){
            System.out.println("ERRO AO OBTER ENTRADA MANUAL DO USUARIO: "+e);
        }
    }
 
}
