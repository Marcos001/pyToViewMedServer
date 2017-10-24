import pika
import logging

def send_data(data):
    logging.basicConfig()
    connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost'))
    channel = connection.channel()

    channel.queue_declare(queue='enviar')

    channel.basic_publish(exchange='',routing_key='server_phone', body=data)
    print('[x] Sent ‘IMAGEM')
    connection.close()