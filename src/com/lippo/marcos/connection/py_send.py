import pika
import logging

f = open(file='image.jpg', mode='rb')
i = f.read()

logging.basicConfig()
connection = pika.BlockingConnection(pika.ConnectionParameters(host='localhost'))
channel = connection.channel()

channel.queue_declare(queue='enviar')

channel.basic_publish(exchange='',routing_key='hello',body=i)
print('[x] Sent ‘Hello World!')
connection.close()