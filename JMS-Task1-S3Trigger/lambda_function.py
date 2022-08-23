import json
import urllib.parse
import boto3

print('Loading function')

s3 = boto3.client('s3')
sqs = boto3.resource('sqs')
log_queue= "log-changes-queue"

def lambda_handler(event, context):
    bucket = event['Records'][0]['s3']['bucket']['name']
    key = urllib.parse.unquote_plus(event['Records'][0]['s3']['object']['key'], encoding='utf-8')
    send_messages(log_queue,"{} Log file in {} was changed".format("processed-orders.log","orders-bucket-sqs-processed"))
    
        
def send_messages(queue, message):
   queue = sqs.get_queue_by_name(QueueName=queue)
   queue.send_message(MessageBody=message)
