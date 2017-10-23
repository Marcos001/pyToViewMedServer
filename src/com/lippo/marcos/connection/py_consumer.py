import pika, io, os
from PIL import Image





def callback(ch, method, properties, body):
    print(" [x] Recebido dados ")
    image = Image.open(io.BytesIO(body))
    image.show()


def init_server():
    connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost'))
    channel  = connection.channel()

    channel.queue_declare(queue='hello')

    channel.basic_consume(callback, queue='phone_server', no_ack=True)

    print(' [*] Waiting for messages. To exit press CTRL+C')
    channel.start_consuming()

if __name__ == '__main__':
    init_server()