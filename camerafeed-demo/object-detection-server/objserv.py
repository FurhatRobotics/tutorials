import zmq
import numpy as np
import cv2
import time
import json

def detect(net, img, confidence_threshold):
    #detecting objects
    blob = cv2.dnn.blobFromImage(img,0.00392,(416,416),(0,0,0),True,crop=False)
        
    net.setInput(blob)
    outs = net.forward(outputlayers)

    # get confidence score of algorithm in detecting an object in blob
    class_ids=[]
    confidences=[]
    boxes=[]
    for out in outs:
        for detection in out:
            scores = detection[5:]
            class_id = np.argmax(scores)
            confidence = scores[class_id]
            if confidence > confidence_threshold:
            #onject detected
                center_x= int(detection[0]*width)
                center_y= int(detection[1]*height)
                w = int(detection[2]*width)
                h = int(detection[3]*height)        
                x=int(center_x - w/2)
                y=int(center_y - h/2)
                boxes.append([x,y,w,h]) #put all rectangle areas
                confidences.append(float(confidence)) #how confidence was that object detected and show that percentage
                class_ids.append(class_id) #name of the object tha was detected

    indexes = cv2.dnn.NMSBoxes(boxes,confidences,0.4,0.6)
    return {'indexes':indexes,'boxes':boxes, 'class_ids': class_ids}


def draw(img,res):
    boxes = res['boxes']
    indexes = res['indexes']
    class_ids = res['class_ids']
    font = cv2.FONT_HERSHEY_PLAIN
    for i in range(len(boxes)):
        if i in indexes:
            x,y,w,h = boxes[i]
            label = str(classes[class_ids[i]])
            color = colors[i]
            cv2.rectangle(img,(x,y),(x+w,y+h),color,2)
            cv2.putText(img,label,(x,y+30),font,3,(255,255,0),2)

    return img
 

def formatresult(res,width,height):
    boxes = res['boxes']
    indexes = res['indexes']
    class_ids = res['class_ids']
    output = []
    for i in range(len(boxes)):
        if i in indexes:
            x,y,w,h = boxes[i]
            label = str(classes[class_ids[i]])
            x /= width
            w /= width
            y /= height
            h /= height
            output.append({'item':label,'bbox':[x,y,w,h]})
    return output

def objectList(res):
    boxes = res['boxes']
    indexes = res['indexes']
    class_ids = res['class_ids']
    output = []
    for i in range(len(boxes)):
        if i in indexes:
            x,y,w,h = boxes[i]
            label = str(classes[class_ids[i]])
            x /= width
            w /= width
            y /= height
            h /= height
            output.append(label)
    return output

def getObjectSet(list):
    set = {}
    for item in list:
        set[item] = True
    return set

def compareSets(set1, set2):
    out = []
    for item in set2.keys():
        if not item in set1:
            out.append('enter_' + item)
    for item in set1.keys():
        if not item in set2:
            out.append('leave_' + item)
    return out

# Load configuration
with open('launch.json') as f:
  config = json.load(f)
print(config)


#Load YOLO
net = cv2.dnn.readNet("yolov3.weights","yolov3.cfg")
classes = []
with open("coco.names","r") as f:
    classes = [line.strip() for line in f.readlines()]

layer_names = net.getLayerNames()
outputlayers = [layer_names[i[0] - 1] for i in net.getUnconnectedOutLayers()]

colors= np.random.uniform(0,255,size=(len(classes),3))


# Setup the sockets
context = zmq.Context()

# Input camera feed from furhat using a SUB socket
insocket = context.socket(zmq.SUB)
insocket.setsockopt_string(zmq.SUBSCRIBE, '')
insocket.connect('tcp://' + config["Furhat_IP"] + ':3000')
insocket.setsockopt(zmq.RCVHWM, 1)
insocket.setsockopt(zmq.CONFLATE, 1)  # Only read the last message to avoid lagging behind the stream.

# Output results using a PUB socket
context2 = zmq.Context()
outsocket = context2.socket(zmq.PUB)
outsocket.bind("tcp://" + config["Dev_IP"] + ":" + config["detection_exposure_port"])

print('connected, entering loop')
prevset = {}
iterations = 0
detection_period = config["detection_period"] # Detecting objects is resource intensive, so we try to avoid detecting objects in every frame
detection_threshold = config["detection_confidence_threshold"] # Detection threshold takes a double between 0.0 and 1.0
x = True
while x:

    string = insocket.recv()
    magicnumber = string[0:3]
    print(magicnumber)
    # check if we have a JPEG image (starts with ffd8ff)
    if magicnumber == b'\xff\xd8\xff':
        buf = np.frombuffer(string,dtype=np.uint8)
        img = cv2.imdecode(buf,flags=1)

        if (iterations % detection_period == 0):
            print("Detecting objects!")
            buf = np.frombuffer(string,dtype=np.uint8)
            img = cv2.imdecode(buf,flags=1)
            height,width,channels = img.shape
            res = detect(net,img, detection_threshold)
            img2=draw(img,res)

            currset = getObjectSet(objectList(res))
            objdiff = compareSets(prevset,currset)
            if len(objdiff):
                msg = ' '.join(objdiff)
                outsocket.send_string(msg)

            prevset = currset
            cv2.imshow("yolov3", img2)

        iterations = iterations + 1
    
    k = cv2.waitKey(1)
    if k%256 == 27: # When pressing esc the program stops.
        # ESC pressed
        print("Escape hit, closing...")
        break
