package com.lippo.marcos.util;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;


public class util {

    public util(){}

    public String get_cwd(){
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current relative path is: " + s);
        return s;
    }

    public void print(String message){
        System.out.println(message);
    }

    public void executadoScript(){


        print("executando Script");


        //String script = "python3.5 /home/mrv/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/PDI/main.py";
        String script = "python3.5 /home/pavic/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/PDI/mv_pdi.py";
        //String script = "python3.5 /home/mrv/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/PDI/outsu.py";

        try
        {
            Runtime rt2 = Runtime.getRuntime();
            Process pr2 = rt2.exec(script);
            pr2.waitFor();
            BufferedReader br = new BufferedReader(new InputStreamReader(pr2.getInputStream()));
            String line = "";
            while ((line = br.readLine()) != null)
            {
                System.out.println(line);
            }
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            e.printStackTrace();
        }

        print("Script .py Executado com Sucesso!");

    }

    public void executadoScript_otsu(){


        print("executando Script");


        String script = "python3.5 /home/pavic/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/PDI/outsu.py";

        try
        {
            Runtime rt2 = Runtime.getRuntime();
            Process pr2 = rt2.exec(script);
            pr2.waitFor();
            BufferedReader br = new BufferedReader(new InputStreamReader(pr2.getInputStream()));
            String line = "";
            while ((line = br.readLine()) != null)
            {
                System.out.println(line);
            }
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            e.printStackTrace();
        }

        print("Script otsu.py Executado com Sucesso!");

    }

    public void executadoScript_otsu_param(Image img){


        print("executando Script");


        String script = "python3.5 PDI/outsu.py";

        try
        {
            print("Java chamando interpretador python");
            Runtime rt2 = Runtime.getRuntime();
            Process pr2 = rt2.exec(script);
            pr2.waitFor();
            BufferedReader br = new BufferedReader(new InputStreamReader(pr2.getInputStream()));
            String line = "";
            while ((line = br.readLine()) != null)
            {
                System.out.println(line);
            }
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            e.printStackTrace();
        }

        print("Script otsu.py Executado com Sucesso!");

    }

    public void executadoScript_kmeans(){


        print("executando Script kmeans");

        String script = "python3.5 /home/pavic/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/PDI/kmeans.py";

        try
        {
            Runtime rt2 = Runtime.getRuntime();
            Process pr2 = rt2.exec(script);
            pr2.waitFor();
            BufferedReader br = new BufferedReader(new InputStreamReader(pr2.getInputStream()));
            String line = "";
            while ((line = br.readLine()) != null)
            {
                System.out.println(line);
            }
            print("Script kmeans.py Executado com Sucesso!");
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            e.printStackTrace();
            print("Script kmeans.py não executado!");
        }



    }



    public void executadoScript_grafico(){


        print("executando Script kmeans");

        String script = "python3.5 /home/pavic/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/connection/gerar_grafico.py";

        try
        {
            Runtime rt2 = Runtime.getRuntime();
            Process pr2 = rt2.exec(script);
            pr2.waitFor();
            BufferedReader br = new BufferedReader(new InputStreamReader(pr2.getInputStream()));
            String line = "";
            while ((line = br.readLine()) != null)
            {
                System.out.println(line);
            }
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
            e.printStackTrace();
        }

        print("Script grfico.py Executado com Sucesso!");

    }



}
