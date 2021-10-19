# camerafeed-demo

Demo that shows an external server listening to the camerafeed, doing object detection and returning the objects back to the skill.

 1. Install Python3 and pip3, and make sure they are accessible from the command line.

 2. Install the python requirements (preferably in a virtual env.)

 	`pip3 install -r requirements.txt`


 3. Download the Yolov3 weights and copy inside object-detection-server folder (This will take a while)

	```
	cd object-detection-server
	wget https://pjreddie.com/media/files/yolov3.weights
	```

	Or download it manually through your browser, and place in the root of object-detection-server/

 4. Alter the launch.json 

 	```
 		"Furhat_IP": "<local ip of the robot>",
    	"Dev_IP": "<local ip of your computer>", //You might be able to use ifconfig -a or ipconfig /all
	```
 
 5. In the skill (main.kt) change the objserv to the local ip of your computer
 	
 	```
 	val objserv = "tcp://<local ip of your computer>:9999" //The TCP socket of the object server
 	``` 

 6. Build the skill.

 	```
 	cd ObjectTalk
 	gradlew shadowJar
 	./gradlew shadowJar
 	```

 7. Upload the .skill file to the robot (can be found under build/libs)

 8. Start the skill

 9. Start the object-detection-server

    ```
    python3 objserv.py
    ``` 
 10. Enjoy :D