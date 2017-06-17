package com.lippo.marcos.connection;

/**
 * Created by mrv on 24/04/17.
 */

import com.rabbitmq.client.*;
import com.lippo.marcos.util.*;



import java.io.IOException;

public class Consumidor extends util{

    private final static String QUEUE_NAME = "phone_server";
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

                            executadoScript_kmeans();

                            executadoScript_otsu();

                            //compactar

                            String[] imagens = new String[4];
                            imagens[0] = "/home/mrv/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/extract/segmentadas/kmeans.png";
                            imagens[1] = "/home/mrv/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/extract/segmentadas/otsu.png";

                            imagens[2] = "/home/mrv/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/extract/segmentadas/kmeans.zip";
                            imagens[3] = "/home/mrv/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/extract/segmentadas/otsu.zip";

                            //zipar
                            zip.compactar("Kmeans.png",imagens[2], arquivo.converte_bytes(arquivo.ler_arquivo(imagens[0])));
                            zip.compactar("otsu.png",imagens[3], arquivo.converte_bytes(arquivo.ler_arquivo(imagens[1])));


                            //converter em binario e enviar

                            byte[] b_kmeans = arquivo.converte_bytes(arquivo.ler_arquivo(imagens[2]));
                            byte[] b_otsu = arquivo.converte_bytes(arquivo.ler_arquivo(imagens[3]));

                            //enviar duas imagens como zip - otsu / kmeans
                            new Produtor().send_file(b_kmeans);
                            new Produtor().send_file(b_otsu);
                            print("Arquivos enviados com sucesso");


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

    public void _Consumidor(String ttt){

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
