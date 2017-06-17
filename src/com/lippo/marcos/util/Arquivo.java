package com.lippo.marcos.util;

import com.lippo.marcos.util.util;

import java.io.*;

/**
 * Created by nig on 19/05/17.
 */
public class Arquivo extends util{


    public Arquivo(){}

    public File ler_arquivo(String caminho){
        File _arquivo = new File(caminho);
        return _arquivo;
    }

    public byte[] converte_bytes(File arquivo){

        int tamanho = (int) arquivo.length();
        byte[] sendBuf = new byte[tamanho];
        FileInputStream inFile = null;

        try{
            inFile = new FileInputStream(arquivo);
            inFile.read(sendBuf, 0, tamanho);
            print("Arquivo convertido com sucesso");
        }catch (Exception erro){
            print("Erro ao conveter o arquvo em bytes : "+erro);
        }
        return sendBuf;
    }

    public void criar_arquivo(String path_name_file, byte[] conteudo_binario){

        try{

            FileOutputStream criar = new FileOutputStream(path_name_file);
            FileDescriptor _descricao_arquivo = criar.getFD();

            criar.write(conteudo_binario);

            criar.flush();
            _descricao_arquivo.sync();
            criar.close();

            print("Imagem escrita com sucesso");


        }catch (Exception erro){
            print("erro ao criar o arquivo : "+erro);
        }

    }


}
