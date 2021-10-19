import zmq
import numpy as np
import cv2
import json
import sys
import argparse

parser = argparse.ArgumentParser(description='Display furhat camerafeed with overlayed annotations (face bounding boxes, user id:s,emotion estimates). Make sure external camera feed is enabled on the robot')
parser.add_argument('addr',default='127.0.0.1',help='IP address to furhat robot, excluding port nr')
# parser.add_argument('-ov','--output_video',help='if specified, raw video will be saved to this file')
args=parser.parse_args()

# annotate image with some of the meta data attributes
def annotate(img,annot):

    # loop over all detected faces
    for u in annot['users']:
        # get bounding box
        x = u['bbox']['x']
        y = u['bbox']['y']
        w = u['bbox']['w']
        h = u['bbox']['h']
        
        # draw bounding box
        cv2.rectangle(img,(x,y),(x+w,y+h),(255,255,255),1)

        # landmarks are given in a list of the form x1,y1,x2,y2...
        for lx,ly in zip(*[iter(u['landmarks'])]*2):
            # landmark coordinates are normalized [0-1] relative to the bounding box
            # so they have to be un-normalized for plotting
            cv2.circle(img,(int(x+lx*w),int(y+ly*h)),4,(0,255,255),1)
        
        font = cv2.FONT_HERSHEY_PLAIN
        
        # show user ID at the top
        cv2.putText(img,'id:' + str(u['id']),(x,y),font,1,(128,255,128),1)
        y+=10
        
        # show emotion estimates
        for emo,val in u['emotion'].items():
            cv2.putText(img,emo + ':' + str(val),(x,y+30),font,1,(255,255,0),1)
            y+=10
url = 'tcp://{}:3000'.format(args.addr)
            
# Setup the sockets
context = zmq.Context()

# Input camera feed from furhat using a SUB socket
insocket = context.socket(zmq.SUB)
insocket.setsockopt_string(zmq.SUBSCRIBE, '')
insocket.connect(url)
insocket.setsockopt(zmq.RCVHWM, 1)
insocket.setsockopt(zmq.CONFLATE, 1)  # Only read the last message to avoid lagging behind the stream.

print('listening to {}, entering loop'.format(url))

img = None

if args.help:
    print('furhattube - display furhat camerafeed with overlayed annotations')
    print('             (face bounding boxes, user id:s, emotion estimates)')
    print('   usage: 1) make sure camera feed is')

#if args.output_video:
#    ov = cv2.VideoWriter(args.output_video,cv2.VideoWriter_fourcc(*'MJPG'),10,(480, 640))

while True:
    
    string = insocket.recv()
    magicnumber = string[0:3]
    print(magicnumber)
    # check if we have a JPEG image (starts with ffd8ff
    if magicnumber == b'\xff\xd8\xff':
        buf = np.frombuffer(string,dtype=np.uint8)
        img = cv2.imdecode(buf,flags=1)
        #if 'ov' in globals():
        #    ov.write(img)
    # if not JPEG, let's assume JSON
    else:         
        annot = json.loads(string.decode())
        print(annot)
        if isinstance(img,np.ndarray):
            annotate(img,annot)
            cv2.imshow('FurhatTube',img)
            k = cv2.waitKey(1)

            if k%256 == 27: # When pressing esc the program stops.
            # ESC pressed
                print("Escape hit, closing...")
                break

#if 'ov' in globals():
#    print('releaseing video') 
#    ov.release()
