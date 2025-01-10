import pyaudio

def is_microphone_active():
    p = pyaudio.PyAudio()
    active = any([p.get_device_info_by_index(i)['maxInputChannels'] > 0 for i in range(p.get_device_count())])
    p.terminate()
    return active

while True:
    if is_microphone_active():
        print("Micrófono activo")
    else:
        print("Micrófono inactivo")
