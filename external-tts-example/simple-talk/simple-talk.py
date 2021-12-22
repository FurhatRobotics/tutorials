import flask
from flask import make_response, request
import urllib
import subprocess
import uuid
import os
import platform
import wave
import audioop
import argparse
import io
import time

parser = argparse.ArgumentParser('simple-talk (simple text-to-speech server)')
args = parser.parse_args()

dir = os.path.dirname(os.path.realpath(__file__))

# The 'engines' dict contains the TTS engines available and their basic 
# invocation command. This will be different on different platforms.

engines = {}
if platform.system() == 'Linux':
    engines['dectalk'] = ['wine',os.path.join(dir,'bin','dectalk','say.exe')]
    engines['mimic']   = [os.path.join(dir,'bin','mimic','linux','mimic')]

elif platform.system() == 'Darwin':
    engines['mimic']   = [os.path.join(dir,'bin','mimic','macos','mimic')]

elif platform.system() == 'Windows':
    engines['dectalk'] = [os.path.join(dir,'bin','dectalk','say.exe')]
    engines['mimic']   = [os.path.join(dir,'bin','mimic','windows','mimic.exe')]

# the 'voices' dict list the available voices for each engine
# for mimic, you can run 'mimic -lv' to list available voices

voices = {}
voices['dectalk'] = ['paul','betty','harry','frank','kit','rita','ursula','dennis','wendy','val']
voices['mimic'] = ['ap', 'slt', 'slt_hts', 'kal', 'awb', 'kal16', 'rms', 'awb_time']

# getwavdata16khz(wavfile)
# input:        wavfile
# returns:      returns 16khz wav-encoded byte string
# side effects: wavfile is removed

def getwavdata16khz(wavfile):
    buf = None # Needs to be initialized
    with wave.open(wavfile,'rb') as wf1:
        p1 = wf1.getparams()
        data1 = wf1.readframes(p1.nframes)
        if p1.framerate != 16000:
            data2,state = audioop.ratecv(data1,p1.sampwidth,p1.nchannels,p1.framerate,16000,None)
        else:
            data2 = data1
        buf = io.BytesIO()
        with wave.open(buf,'wb') as wf2:
            wf2.setsampwidth(p1.sampwidth)
            wf2.setnchannels(p1.nchannels)
            wf2.setframerate(16000)
            wf2.writeframes(data2)
            wf2.close()
    os.remove(wavfile)
    return buf.getvalue()

# saycmd(text,engine,voice)
# input:   text to be synthesized, requested engine and voice
# returns: speech audio data

def saycmd(text,engine,voice):
    print('requested engine "{}" and voice "{}"'.format(engine,voice))
    # check if requested engine is available, if not use first available one
    if engine in engines:
        cmd = engines[engine]
    else:
        engine,cmd = list(engines.items())[0]
    print('using engine', engine)

    # check if requested voice exists for engine, if not use first available one
    if not voice in voices[engine]:
        voice = voices[engine][0]
        
    print('using voice', voice)
    print('cmd = ',cmd)

    if engine == 'dectalk':
        fn = str(uuid.uuid1()) + '.wav'
        wavpath = os.path.join(dir,'bin','dectalk',fn)
        # to set voice for dectalk, prepend text with
        # [:nX] where X is the first char of the voice name
        voicecode = '[:n{}]'.format(voice[0])
        cargs = ['-w', fn, voicecode + text]
        os.environ['DISPLAY']='localhost:10.0' # for remote linux
        # dectalk say.exe needs to be invoked from within the 'dectalk' folder
        subprocess.call(cmd + cargs,stderr=subprocess.STDOUT,
            cwd=os.path.join(dir,'bin','dectalk'))

    elif engine == 'mimic':
        fn = str(uuid.uuid1()) + '.wav'
        wavpath = os.path.join(dir,fn)
        cargs = ['-t', text, '-o', wavpath, '-voice', voice]
        subprocess.call(cmd + cargs, stderr=subprocess.STDOUT)
    else:
        print('bad engine',engine)

    return getwavdata16khz(wavpath)

# Create a web app instance and 
# define the entry points for the api: 
# '/' that prints some html help string...
app = flask.Flask(__name__)
app.config['DEBUG'] = False

@app.route('/')
def home():
    return '<h1>TTS API</h1><p>example command line usage:</p><pre>curl \"{}/say?text=hi+my+name+is+steven&engine=dectalk&voice=paul.wav\" --output out.wav</pre>'.format(request.host)

# ... and '/say' for doing text-to-speech
@app.route('/say')
def say():
    requrl = request.full_path
    # parse the url. first remove '.wav' extension and then look for 
    # text, engine and voice arguments
    # note: here we could also look for requests with a '.pho' extension
    # - furhat will send these to look for lipsync information

    if requrl.endswith('.wav'):
        req = requrl[:-4]
        res = urllib.parse.urlparse(req)
        pq = urllib.parse.parse_qs(res.query)
        text = pq['text'][0]
        try:
            voice = pq['voice'][0]
        except:
            voice = ''
        try:
            engine = pq['engine'][0]
        except:
            engine = ''

    else:
        return make_response('bad request',404)

    wavdata  = saycmd(text,engine,voice)
    response = make_response(wavdata)
    response.headers.set('Content-Type', 'audio/wav')
    return response

# start the server
app.run(host='0.0.0.0')
