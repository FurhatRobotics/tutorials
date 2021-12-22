# External TTS Example

This example shows how you can go about to call a custom TTS engine from a furhat skill. The example has two parts: a skill (`ExTTS`) and a simple text-to-speech server (`simple-talk`).

## The `simple-talk` server 


The server is a single-file python script that wraps two text-to-speech engine executables:
* **[mimic](https://github.com/MycroftAI/mimic1)** (binaries for Linux and MacOS provided) 
* **[dectalk](https://github.com/connornishijima/80speak)** (binary for Windows provided, can be used on Linux using `wine`. Fun fact: **dectalk** was the voice used by *Stephen Hawking*)

The server python script requires python3. 

## Step 1 run the external TTS servers

### Instructions for *MacOS*:
1) Download and unzip the binaries:
   
    `cd simple-talk`

    `wget www.speech.kth.se/tts-demos/mimic-macos.zip && unzip mimic-macos.zip`

2) Install requirements:

    `pip install flask`

3) Verify that the mimic executable exists and can sythesize speech:

     `bin/mimic/macos/mimic -t "hello world" -o test.wav`

4) Start the server

    `python simple-talk.py`

5) Verify that it runs by pointing your browser to `localhost:5000` 

!Note that the dectalk TTS engine is not available for mac. 

### Instructions for *Linux* (Ubuntu):

1) Download and unzip the binaries:
   
    ```
    cd simple-talk
    wget www.speech.kth.se/tts-demos/mimic-linux.zip && unzip mimic-linux.zip
    wget www.speech.kth.se/tts-demos/dectalk-bin.zip && unzip dectalk-bin.zip
    ```

2) Install requirements:

    `pip install flask`

    `sudo apt install wine` (optional - needed for dectalk)

3) Verify that the executables exists and can sythesize speech:

    ```
    bin/mimic/linux/mimic -t "hello world" -o test.wav
    cd bin/dectalk
    wine say.exe -w test2.wav "hello world too"
    ```

4) Start the server

    `python simple-talk.py`

5) Verify that it runs by pointing your browser to `localhost:5000`

### Instructions for *Windows* 

1) Download and unzip the binaries:
   
    `cd simple-talk`

    Download  [mimic-windows.zip](www.speech.kth.se/tts-demos/mimic-windows.zip) and [dectalk-bin.zip](www.speech.kth.se/tts-demos/dectalk-bin.zip) and unzip them. You should end up with a folder structure that looks like
    ```
    simple-talk
      +-- bin
            +-- mimic
                  +-- windows
            +-- dectalk
    ```

2) Install requirements:

    `pip install flask`

3) Verify that the executables exists and can sythesize speech:

    `bin\mimic\windows\mimic.exe -t "hello world" -o test.wav`

    `cd bin\dectalk`

    `say.exe -w test2.wav "hello world too"`

4) Start the server

    `python simple-talk.py`

5) Verify that it runs by pointing your browser to `localhost:5000`


## Step 2 Run the `ExTTS` skill

1) Modify the `server` value in `src/main/kotlin/furhatos/app/extts/flow/interaction.kt` to reflect the address of the `simple-talk` server
2) Run the skill. Furhat should now speak with a custom TTS voice. 








