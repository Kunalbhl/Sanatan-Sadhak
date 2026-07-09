import re
with open("app/src/main/java/com/example/ui/components/AudioEngine.kt", "r") as f:
    content = f.read()

bad = """            audioTrack = AudioTrack(
                AudioManager.STREAM_MUSIC,
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                minSize,
                AudioTrack.MODE_STREAM
            )"""

good = """            audioTrack = AudioTrack.Builder()
                .setAudioAttributes(
                    android.media.AudioAttributes.Builder()
                        .setUsage(android.media.AudioAttributes.USAGE_MEDIA)
                        .setContentType(android.media.AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                .setAudioFormat(
                    android.media.AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(sampleRate)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(minSize)
                .setTransferMode(AudioTrack.MODE_STREAM)
                .build()"""

if bad in content:
    content = content.replace(bad, good)
    with open("app/src/main/java/com/example/ui/components/AudioEngine.kt", "w") as f:
        f.write(content)
    print("Fixed AudioTrack Builder")
else:
    print("Not found")
