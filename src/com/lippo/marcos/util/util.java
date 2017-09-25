package com.lippo.marcos.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class util {

    public util(){}

    public void print(String message){
        System.out.println(message);
    }

    public void executadoScript(){


        print("executando Script");


        //String script = "python3.5 /home/mrv/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/PDI/main.py";
        String script = "python3.5 /home/mrv/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/PDI/mv_pdi.py";
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


        String script = "python3.5 /home/mrv/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/PDI/outsu.py";

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

    public void executadoScript_kmeans(){


        print("executando Script kmeans");

        String script = "python3.5 /home/mrv/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/PDI/kmeans.py";

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

        print("Script kmeans.py Executado com Sucesso!");

    }



    public void executadoScript_grafico(){


        print("executando Script kmeans");

        String script = "python3.5 /home/mrv/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/connection/gerar_grafico.py";

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
