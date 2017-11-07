package com.lippo.marcos.teste;

import com.lippo.marcos.connection.Produtor;
import com.lippo.marcos.util.Arquivo;
import com.lippo.marcos.util.DCompactar;
import com.lippo.marcos.util.util;
import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class Testes {

    private final static String QUEUE_NAME = "phone_server";
    private final static String PATH = "/home/pavic/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/";
    private String name_image = "data/consumer.png";
    private String file_zip_receiver = PATH+"data/data.zip";
    private int cont_connections = 0;


    Arquivo arquivo;
    DCompactar zip;
    util ut;
    ConnectionFactory factory;
    Connection connection;
    Channel channel;

    @Test
    public  void test_ve(){

        String path = "/home/pavic/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/connection/imagem.png";
        assertTrue(new File(path).exists());

    }


    @Test
    public void test_Consumidor(){

        arquivo = new Arquivo();
        zip = new DCompactar();
        ut = new util();

        String QUEUE_NAME = "phone_server";
        String PATH = "/home/pavic/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/";
        int cont_connections = 0;

        // @Test - verificando se as intancias nao sao nulas
        assertNotNull(arquivo);
        assertNotNull(zip);
        assertNotNull(ut);

        //entrada
        System.out.println("Entrada");

        // arquivos que ja devem existir

        String path_send_zip = PATH+"data/extract/segmentadas/data_zip/send_zip.zip";
        String path_imagem_recebida = "/home/pavic/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/extract/pim_imagem.png";

        //compactar

        // arquivos craidos ao decorrer do PDI - verificar soemnte apos gerar

        String[] imagens = new String[5];

        imagens[0] = PATH+"data/extract/segmentadas/kmeans.png";
        imagens[1] = PATH+"data/extract/segmentadas/otsu.png";
        imagens[2] = PATH+"data/extract/sobrepostas/kmeans_sobreposta.png";
        imagens[3] = PATH+"data/extract/sobrepostas/otsu_sobreposta.png";
        imagens[4] = PATH+"data/grafico.png";


        // 2 ETAPA - PROCESSAMENTO
        System.out.println("Processamento");

        cont_connections++;
        System.out.println("CONEXÃO ["+cont_connections+"]");

       new Thread(new Runnable() {
            @Override
            public void run() {

                try{

                    factory = new ConnectionFactory();
                    factory.setHost("localhost");
                    connection = factory.newConnection();
                    channel = connection.createChannel();

                    // @Test - testando instancias nulas
                    assertNotNull(connection);
                    assertNotNull(connection);
                    assertNotNull(channel);


                    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                    System.out.println(" [*] Aguardando mensagens. To exit press CTRL+C");

                    Consumer consumer = new DefaultConsumer(channel) {
                        @Override
                        public void handleDelivery(String consumerTag,
                                                   Envelope envelope,
                                                   AMQP.BasicProperties properties,
                                                   byte[] body) throws IOException {


                            // criando imagem com o fluxo de bytes recebido pela rede
                            arquivo.criar_arquivo(path_imagem_recebida, body);



                            // @Test - verificando se os arquivos existem
                            assertTrue(new File(path_imagem_recebida).exists());



                            // scripts de PDI para realizar as operacoes em imagens e contagem de
                            // tempo gerando grafico para mensuracao
                            ut.executadoScript_kmeans();

                            ut.executadoScript_otsu();

                            ut.executadoScript_grafico();


                            // @TEST - verificando se os arquivos foram gerados
                            assertTrue(new File(imagens[0]).exists());
                            assertTrue(new File(imagens[1]).exists());
                            assertTrue(new File(imagens[2]).exists());
                            assertTrue(new File(imagens[3]).exists());
                            assertTrue(new File(imagens[4]).exists());


                            // nome dos arquivos a serem criados
                            String[] name_imagens = new String[5];

                            name_imagens[0] = "kmeans.png";
                            name_imagens[1] = "otsu.png";
                            name_imagens[2] = "sb_kmeans.png";
                            name_imagens[3] = "sb_otsu.png";
                            name_imagens[4] = "grafico.png";


                            //converter um bytes e joagar em yum arraylist
                            ArrayList<byte[]> mylist = new ArrayList<>();

                            // convertendo os arquivos em bytes
                            byte[] b_kmeans = arquivo.converte_bytes(arquivo.ler_arquivo(imagens[0]));
                            byte[] b_otsu = arquivo.converte_bytes(arquivo.ler_arquivo(imagens[1]));
                            byte[] sb_kmeans = arquivo.converte_bytes(arquivo.ler_arquivo(imagens[2]));
                            byte[] sb_otsu = arquivo.converte_bytes(arquivo.ler_arquivo(imagens[3]));
                            byte[] grafico = arquivo.converte_bytes(arquivo.ler_arquivo(imagens[4]));


                            // @Test -> testando se os arquivos nao estao nulos
                            assertNotNull(b_kmeans);
                            assertNotNull(b_otsu);
                            assertNotNull(sb_kmeans);
                            assertNotNull(sb_otsu);
                            assertNotNull(grafico);

                            // add on byte array for zip all content bytes
                            mylist.add(b_kmeans);
                            mylist.add(b_otsu);
                            mylist.add(sb_kmeans);
                            mylist.add(sb_otsu);
                            mylist.add(grafico);

                            // @Test - testando se os arquivos foram todos acionados
                            assertEquals(5, mylist.size());


                            zip.compactar_files(name_imagens, path_send_zip, mylist);

                            //testar se os arquivos foram criados com sucesso


                            byte[] body_send = arquivo.converte_bytes(arquivo.ler_arquivo(path_send_zip));

                            // @Test - verificando se os arquivos existem
                            assertTrue(new File(path_send_zip).exists());


                            // @Test - testar se o corpo do arquivo convertido e diferente de nulo
                            assertNotNull(body_send);

                            // SAIDA
                            System.out.println("saida do teste do servidor que recebe e processa as imagens");
                            //enviar duas imagens como zip - otsu / kmeans
                            assertTrue(new Produtor().send_file(body_send));


                        }
                    };
                    channel.basicConsume(QUEUE_NAME, true, consumer);
                }catch (Exception erro){
                    System.out.println("Erro na conexão!");
                }

            }
        }).start();

        System.out.println("FIM");
    }


    @Test
    public void teste_Produtor(){

        String path_file = "/home/pavic/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/extract/segmentadas/data_zip/send_zip.zip";

        assertTrue(new File(path_file).exists());

        byte[] conteudo;

        conteudo = arquivo.converte_bytes(arquivo.ler_arquivo(path_file));

        //@ Test -
        assertNotNull(conteudo);

        final  String QUEUE_NAME = "server_phone"; //"hello"
        final int PORT = Integer.getInteger("amqp.port", 5672);


        final String HOST = System.getProperty("amqp.host", "localhost");
        final String EXCHANGE = System.getProperty("amqp.exchange", "systemExchange");
        final String ENCODING = "UTF-8";

        Connection connection;
        boolean create_connection = false;

        try{

            System.out.println(" configurações da conexão ");
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(HOST);
            factory.setPort(PORT);
            factory.setUsername("nig");
            factory.setPassword("nig");
            factory.setVirtualHost("/");

            // @Test -
            assertNotNull(factory);

            connection = factory.newConnection();

            // @Test -
            assertNotNull(connection);

            Channel channel = connection.createChannel();

            // @Test -
            assertNotNull(channel);

            System.out.println(" enviando mensagem > ");
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, conteudo);
            System.out.println(" [x] arquivo enviado ");
            create_connection = true;

            System.out.println("fechando conexão");
            channel.close();
            connection.close();


        }catch (Exception erro){
            System.out.println("Erro ao instanciar publisher enviando ao cliente >"+HOST+" \n"+erro);
        }

        assertTrue(create_connection);

    }



}
