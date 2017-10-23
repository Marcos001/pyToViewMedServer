package com.lippo.marcos.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;



public class DCompactar extends util{

    private final static String PATH = "/home/pavic/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/";

    public DCompactar(){

    }



    public void compactar_files(String[] path_arquivo, String nome_arquivo_zip,  ArrayList<byte[]> mylist ){

        try{

            FileOutputStream file_output = new FileOutputStream(nome_arquivo_zip);

            ZipOutputStream inst_zip = new ZipOutputStream(file_output);

            for(int i=0;i<path_arquivo.length;i++){
                inst_zip.putNextEntry(new ZipEntry(path_arquivo[i]));
                inst_zip.write(mylist.get(i));
            }


            inst_zip.closeEntry();
            inst_zip.close();

            print("zip criado com sucesso");

        }catch (Exception erro){
            print("erro ao compactar o arquivo");
        }

    }



    public void compactar(String path_arquivo, String nome_arquivo_zip, byte[] conteudo_file){

        try{

            FileOutputStream file_output = new FileOutputStream(nome_arquivo_zip);

            ZipOutputStream inst_zip = new ZipOutputStream(file_output);

            inst_zip.putNextEntry(new ZipEntry(path_arquivo));

            inst_zip.write(conteudo_file);

            inst_zip.closeEntry();
            inst_zip.close();

            print("zip criado com sucesso");

        }catch (Exception erro){
            print("erro ao compactar o arquivo");
        }

    }


    public void compactar_mais(String[] arquivos,   String arquivoCompactado ){


        byte[] buffer = new byte[1024];

        try {

                // cria o arquivo zip
            ZipOutputStream saidaDeStream = new ZipOutputStream(new FileOutputStream(arquivoCompactado));

                // marca o modo de compreensão do arquivo
            saidaDeStream.setLevel(Deflater.BEST_COMPRESSION);

                // laço para pegar todos os arquivos que serao zipados
            for (int i = 0; i < arquivos.length; i++)
            {
                // carrega o arquivo em um stream
                FileInputStream entradaDeStream = new FileInputStream(arquivos[i]);

                // cria uma entrada no zip para o arquivo
                saidaDeStream.putNextEntry(new ZipEntry(arquivos[i]));

                // transfere os dados do arquivo para o zip
                int tamanhoArquivo;
                while ((tamanhoArquivo = entradaDeStream.read(buffer)) < 0)
                {
                    saidaDeStream.write(buffer, 0, tamanhoArquivo);
                }

                // fecha a entrada do arquivo no zip
                saidaDeStream.closeEntry();

                    // fecha o arquivo
                entradaDeStream.close();
            }

                // fecha o arquivo zip
            saidaDeStream.close();
            print("Arquivos compactados com sucesso!");

        } catch (Exception e) {
            print("Erro ao compactar os arquivos");
        }

    }

    public void descompactar(String path_file_zip){

        byte[] buffer = new byte[1024];

        try{

            //cria o input
            ZipInputStream zinstream = new ZipInputStream(new FileInputStream(path_file_zip));

            //pega a proxima entrada do arquivo
            ZipEntry zentry = zinstream.getNextEntry();

            while (zentry != null) {
            // Pega o nome da entrada
                String entryName = zentry.getName();

            // Cria o output do arquivo , Sera extraido onde esta rodando a classe
                String saida = PATH+"data/extract/";

                print("salvando em : "+ entryName);

                FileOutputStream outstream = new FileOutputStream(saida+entryName);

                int n;

                 // Escreve no arquivo
                 while ((n = zinstream.read(buffer)) > -1) {
               	 outstream.write(buffer, 0, n);
                 }

                // Fecha arquivo
               	outstream.close();

               // Fecha entrada e tenta pegar a proxima
               zinstream.closeEntry();
               zentry = zinstream.getNextEntry();

            }


            }catch (Exception erro){
            print("Erro ao extrair os arquivos");
            }



    }

}
