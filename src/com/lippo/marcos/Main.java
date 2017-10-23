package com.lippo.marcos;

import com.lippo.marcos.connection.Consumidor;
import com.lippo.marcos.connection.Produtor;
import com.lippo.marcos.util.util;
/**RABBITMQ
 *
 Channel
 Connection
 ConnectionFactory - *
 Consumer
 * */

public class Main {

    public static void main(String[] args) {
	// write your code here

        //System.out.println("PATH = "+new util().get_cwd());

        new Consumidor();

    }
}
