import pika

#configurando o rabbit

connection = pika.BlockingConnection(pika.ConnectionParameters( host='localhost'))

channel = connection.channel()

channel.queue_declare(queue="enviar")

print('[*] Waiting for messages. To exit press CTRL+C')

def callback(ch, method, properties, body):
    f = open('/home/mrv/IdeaProjects/pyToViewMedServer/src/com/lippo/marcos/data/outputimage.jpg','wb')
    f.write(body)
    f.close()
channel.basic_consume(callback, queue="enviar", no_ack=True)


# main
channel.start_consuming()

''' restore
import pika

f=open(“outputimage.jpg”,”wb”)

connection = pika.BlockingConnection(pika.ConnectionParameters(
host=’localhost’))
channel = connection.channel()

channel.queue_declare(queue=’hello’)

print ‘ [*] Waiting for messages. To exit press CTRL+C’

def callback(ch, method, properties, body):
f.write(body)
f.close()
channel.basic_consume(callback,
queue=’hello’,
no_ack=True)

channel.start_consuming()
'''

