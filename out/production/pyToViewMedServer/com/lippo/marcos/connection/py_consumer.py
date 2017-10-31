import pika
import cv2
import numpy as np

from com.lippo.marcos.PDI.outsu import main_otsu
from com.lippo.marcos.connection.py_send import send_data

def callback(ch, method, properties, body):

    print(" [x] Recebido dados ")

    #use numpy to construct an array from the bytes
    x = np.fromstring(body, dtype='uint8')

    #decode the array into an image
    img = cv2.imdecode(x, cv2.IMREAD_UNCHANGED)

    body_send = main_otsu(img)

    print(' convertida em blocos ')

    # enviar as imagens ao phone
    send_data(body_send)



def init_server():
    connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost'))
    channel  = connection.channel()

    channel.queue_declare(queue='hello')

    channel.basic_consume(callback, queue='phone_server', no_ack=True)

    print(' [*] Waiting for messages. To exit press CTRL+C')
    channel.start_consuming()


if __name__ == '__main__':
    init_server()