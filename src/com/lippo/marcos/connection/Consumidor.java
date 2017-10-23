package com.lippo.marcos.connection;


import com.rabbitmq.client.*;
import com.lippo.marcos.util.*;

import java.io.IOException;
import java.util.ArrayList;

public class Consumidor extends util{

    private final static String QUEUE_NAME = "phone_server";
    private final static String PATH = "/home/pavic/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/";
    private String name_image = "data/consumer.png";
    private String file_zip_receiver = PATH+"data/data.zip";
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

                            executadoScript_grafico();

                            //compactar

                            String[] imagens = new String[5];

                            imagens[0] = PATH+"data/extract/segmentadas/kmeans.png";
                            imagens[1] = PATH+"data/extract/segmentadas/otsu.png";
                            imagens[2] = PATH+"data/extract/sobrepostas/kmeans_sobreposta.png";
                            imagens[3] = PATH+"data/extract/sobrepostas/otsu_sobreposta.png";
                            imagens[4] = PATH+"data/grafico.png";

                            String[] name_imagens = new String[5];

                            name_imagens[0] = "kmeans.png";
                            name_imagens[1] = "otsu.png";
                            name_imagens[2] = "sb_kmeans.png";
                            name_imagens[3] = "sb_otsu.png";
                            name_imagens[4] = "grafico.png";

                            //converter um bytes e joagar em yum arraylist

                            ArrayList<byte[]> mylist = new ArrayList<>();


                            byte[] b_kmeans = arquivo.converte_bytes(arquivo.ler_arquivo(imagens[0]));
                            byte[] b_otsu = arquivo.converte_bytes(arquivo.ler_arquivo(imagens[1]));
                            byte[] sb_kmeans = arquivo.converte_bytes(arquivo.ler_arquivo(imagens[2]));
                            byte[] sb_otsu = arquivo.converte_bytes(arquivo.ler_arquivo(imagens[3]));
                            byte[] grafico = arquivo.converte_bytes(arquivo.ler_arquivo(imagens[4]));

                            mylist.add(b_kmeans);
                            mylist.add(b_otsu);
                            mylist.add(sb_kmeans);
                            mylist.add(sb_otsu);
                            mylist.add(grafico);


                            String path_send_zip = PATH+"data/extract/segmentadas/data_zip/send_zip.zip";
                            //zipar

                            zip.compactar_files(name_imagens, path_send_zip, mylist);

                            //zip.compactar("Kmeans.png",imagens[2], arquivo.converte_bytes(arquivo.ler_arquivo(imagens[0])));
                            //zip.compactar("otsu.png",imagens[3], arquivo.converte_bytes(arquivo.ler_arquivo(imagens[1])));


                            //converter em binario e enviar

                            byte[] body_send = arquivo.converte_bytes(arquivo.ler_arquivo(path_send_zip));

                            //enviar duas imagens como zip - otsu / kmeans
                            new Produtor().send_file(body_send);
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
