import pika
import cv2
import numpy as np

from com.lippo.marcos.PDI.outsu import main_otsu


def callback(ch, method, properties, body):
    print(" [x] Recebido dados ")

    #use numpy to construct an array from the bytes
    x = np.fromstring(body, dtype='uint8')

    #decode the array into an image
    img = cv2.imdecode(x, cv2.IMREAD_UNCHANGED)


    main_otsu(img)



def init_server():
    connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost'))
    channel  = connection.channel()

    channel.queue_declare(queue='hello')

    channel.basic_consume(callback, queue='phone_server', no_ack=True)

    print(' [*] Waiting for messages. To exit press CTRL+C')
    channel.start_consuming()

if __name__ == '__main__':
    init_server()