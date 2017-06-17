package com.lippo.marcos.connection;

/**
 * Created by mrv on 24/04/17.
 */

import com.rabbitmq.client.*;
import com.lippo.marcos.util.*;



import java.io.IOException;

public class Consumidor extends util{

    private final static String QUEUE_NAME = "enviar";
    private String name_image = "/home/mrv/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/consumer.png";
    private String file_zip_receiver = "/home/mrv/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/data.zip";
    private int cont_connections = 0;

    private void receber_zip(){

        Arquivo arquivo = new Arquivo();
        DCompactar zip = new DCompactar();

        cont_connections++;
        print("CONEXÃO ["+cont_connections+"]");

        new Thread(new Runnable() {
            @Override
            public void run() {


                try{
                    ConnectionFactory factory = new ConnectionFactory();
                    factory.setHost("localhost");
                    //factory.setUsername("nig");
                    //factory.setPassword("nig");
                    Connection connection = factory.newConnection();
                    Channel channel = connection.createChannel();

                    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                    System.out.println(" [*] Aguardando mensagens. To exit press CTRL+C");

                    Consumer consumer = new DefaultConsumer(channel) {
                        @Override
                        public void handleDelivery(String consumerTag,
                                                   Envelope envelope,
                                                   AMQP.BasicProperties properties,
                                                   byte[] body) throws IOException {

                            print("obtido fuxo de bytes");

                            //String message = new String(body, "UTF-8"); - teto somente

                            print("Escrevendo o arquivo");
                            arquivo.criar_arquivo(file_zip_receiver, body);

                            print("descompactando o arquivo > ");
                            zip.descompactar(file_zip_receiver);
                            print("arquivos descompactados");

                            //print("imagem recebida -> segmentado com Outsu > ");
                            print("run script");
                            executadoScript();

                            new Produtor("Apto a enviar imagens");


                        }
                    };
                    channel.basicConsume(QUEUE_NAME, true, consumer);
                }catch (Exception erro){
                    System.out.println("Erro na conexão!");
                }

            }
        }).start();


    }


    public Consumidor(){

        receber_zip();

    }

    public Consumidor(String ttt){

        new Thread(new Runnable() {
            @Override
            public void run() {


                try{
                    ConnectionFactory factory = new ConnectionFactory();
                    factory.setHost("localhost");
                    Connection connection = factory.newConnection();
                    Channel channel = connection.createChannel();

                    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                    System.out.println(" [*] Aguardando mensagens. To exit press CTRL+C");

                    Consumer consumer = new DefaultConsumer(channel) {
                        @Override
                        public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                                throws IOException {
                            String message = new String(body, "UTF-8");


                            System.out.println(" [x] Received '" + message + "'");


                        }
                    };
                    channel.basicConsume(QUEUE_NAME, true, consumer);
                }catch (Exception erro){
                    System.out.println("NAO FOI");
                }

            }
        }).start();

    }




}
