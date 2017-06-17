package com.lippo.marcos;

import com.lippo.marcos.connection.Consumidor;
import com.lippo.marcos.connection.Produtor;

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

        new Consumidor();
        //new Produtor("HEHEHEHE");

    }
}
