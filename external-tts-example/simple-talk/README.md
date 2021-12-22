# dectalk-server

## install requirements

`pip3 install -r requirements.txt`

`sudo apt install wine`

## start server

`python3 talk-server.py`

## call server

`curl "X.X.X.X:5000/say?text=hi+my+name+is+steven" --output out.wav`


# Windows
 - download and unzip dectalk files in the simple-talk directory
 - www.speech.kth.se/tts-demos/dectalk-bin.zip
 - You should now have the folder structure like this: `simple-talk/bin/dectalk`
 - Start the server like this: `python3 simple-talk.py --engine dectalk`