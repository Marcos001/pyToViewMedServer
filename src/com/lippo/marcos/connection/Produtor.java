package com.lippo.marcos.connection;

import com.lippo.marcos.util.util;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


public class Produtor extends util {


    public Produtor(){}

    public void send_file(byte[] conteudo){

        final  String QUEUE_NAME = "server_phone"; //"hello"
        final int PORT = Integer.getInteger("amqp.port", 5672);

        final String HOST = System.getProperty("amqp.host", "localhost");
        final String EXCHANGE = System.getProperty("amqp.exchange", "systemExchange");
        final String ENCODING = "UTF-8";
        Connection connection;

        try{

            print(" configuration connection");
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(HOST);
            factory.setPort(PORT);
            factory.setUsername("nig");
            factory.setPassword("nig");
            factory.setVirtualHost("/");
            connection = factory.newConnection();
            Channel channel = connection.createChannel();

            print(" sending message > ");
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, conteudo);
            System.out.println(" [x] arquivo enviado ");

            print("closing connection > ");
            channel.close();
            connection.close();


        }catch (Exception erro){
            System.out.println("Erro ao instanciar publisher enviando ao cliente >"+HOST+" \n"+erro);
        }


    }




    public Produtor(String message){

       Connection connection;
       //String QUEUE_NAME = "hello";

      int PORT = Integer.getInteger("amqp.port", 5672);
      final String EXCHANGE = System.getProperty("amqp.exchange", "systemExchange");
      final String ENCODING = "UTF-8";
      final String QUEUE_NAME = "A";
      final String HOST = System.getProperty("amqp.host", "localhost"); //192.168.0.111

        try{

            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(HOST);
            factory.setPort(PORT);
            factory.setUsername("nig");
            factory.setPassword("nig");
            factory.setVirtualHost("/");

            connection = factory.newConnection();
            Channel channel = connection.createChannel();



            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");

            channel.close();
            connection.close();


        }catch (Exception erro){
            System.out.println("Erro ao instanciar publisher enviando ao cliente >"+HOST+" \n"+erro);
        }


    }


    public void _Produtor(String ip, String message){

        Connection connection;
        final String QUEUE_NAME = "other";
        final String EXCHANGE = System.getProperty("amqp.exchange", "systemExchange");
        final String ENCODING = "UTF-8";
        int PORT = Integer.getInteger("amqp.port", 5672);

        System.out.println("Querendo Publicar com IP = "+ip);

       String HOST = System.getProperty("amqp.host", ip);

        try{

            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(HOST);
            factory.setPort(PORT);
            factory.setUsername("nig");
            factory.setPassword("nig");
            factory.setVirtualHost("/");
            connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message+ "'");

            channel.close();
            connection.close();


        }catch (Exception erro){
            System.out.println("Erro ao instanciar publisher enviando ao cliente >"+HOST+" \n"+erro);
        }


    }


}
