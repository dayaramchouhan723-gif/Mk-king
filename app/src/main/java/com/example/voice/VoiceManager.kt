package com.example.voice

import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.math.sin

class VoiceManager(private val context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var isTtsReady = false

    private var speechRecognizer: SpeechRecognizer? = null
    private var isListening = false

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    private val _isRecognizing = MutableStateFlow(false)
    val isRecognizing: StateFlow<Boolean> = _isRecognizing.asStateFlow()

    private val _recognizedText = MutableStateFlow("")
    val recognizedText: StateFlow<String> = _recognizedText.asStateFlow()

    // Real-time audio RMS level (0.0f to 1.0f) for pulsing orb and waveform
    private val _rmsLevel = MutableStateFlow(0f)
    val rmsLevel: StateFlow<Float> = _rmsLevel.asStateFlow()

    private val _wakeWordTriggered = MutableStateFlow<String?>(null)
    val wakeWordTriggered: StateFlow<String?> = _wakeWordTriggered.asStateFlow()

    private val scope = CoroutineScope(Dispatchers.Default)
    private var synthJob: Job? = null
    private var speakingRmsJob: Job? = null

    init {
        try {
            tts = TextToSpeech(context.applicationContext, this)
        } catch (_: Exception) { }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            isTtsReady = true
            // Attempt setting Hindi or fallback to English / Default
            val hindiResult = tts?.setLanguage(Locale("hi", "IN"))
            if (hindiResult == TextToSpeech.LANG_MISSING_DATA || hindiResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                tts?.setLanguage(Locale.US)
            }
            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    _isSpeaking.value = true
                    startSimulatedSpeakingWaveform()
                }

                override fun onDone(utteranceId: String?) {
                    _isSpeaking.value = false
                    stopSimulatedSpeakingWaveform()
                }

                @Deprecated("Deprecated in Java")
                override fun onError(utteranceId: String?) {
                    _isSpeaking.value = false
                    stopSimulatedSpeakingWaveform()
                }
            })
        }
    }

    private fun startSimulatedSpeakingWaveform() {
        speakingRmsJob?.cancel()
        speakingRmsJob = scope.launch {
            var step = 0
            while (isActive && _isSpeaking.value) {
                step++
                val simulatedRms = (0.3f + 0.65f * kotlin.math.abs(sin(step * 0.35f))).toFloat()
                _rmsLevel.value = simulatedRms
                delay(60)
            }
            _rmsLevel.value = 0f
        }
    }

    private fun stopSimulatedSpeakingWaveform() {
        speakingRmsJob?.cancel()
        _rmsLevel.value = 0f
    }

    fun speak(text: String, pitch: Float = 1.0f, speed: Float = 1.0f, onDone: (() -> Unit)? = null) {
        if (!isTtsReady || tts == null) {
            onDone?.invoke()
            return
        }
        tts?.setPitch(pitch)
        tts?.setSpeechRate(speed)
        val utteranceId = "utterance_${System.currentTimeMillis()}"
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
    }

    fun stopSpeaking() {
        tts?.stop()
        _isSpeaking.value = false
        stopSimulatedSpeakingWaveform()
    }

    fun startListening(onResult: (String) -> Unit) {
        if (speechRecognizer == null) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context.applicationContext)
        }

        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en-US")
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        }

        speechRecognizer?.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                _isRecognizing.value = true
            }

            override fun onBeginningOfSpeech() {}

            override fun onRmsChanged(rmsdB: Float) {
                // rmsdB typically ranges from -2 to 10
                val normalized = ((rmsdB + 2f) / 12f).coerceIn(0.05f, 1.0f)
                _rmsLevel.value = normalized
            }

            override fun onBufferReceived(buffer: ByteArray?) {}

            override fun onEndOfSpeech() {
                _isRecognizing.value = false
                _rmsLevel.value = 0.05f
            }

            override fun onError(error: Int) {
                _isRecognizing.value = false
                _rmsLevel.value = 0f
            }

            override fun onResults(results: Bundle?) {
                _isRecognizing.value = false
                _rmsLevel.value = 0f
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                val spoken = matches?.firstOrNull() ?: ""
                _recognizedText.value = spoken
                checkForWakeWords(spoken)
                onResult(spoken)
            }

            override fun onPartialResults(partialResults: Bundle?) {
                val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                val partial = matches?.firstOrNull() ?: ""
                _recognizedText.value = partial
                checkForWakeWords(partial)
            }

            override fun onEvent(eventType: Int, params: Bundle?) {}
        })

        try {
            speechRecognizer?.startListening(intent)
            _isRecognizing.value = true
        } catch (_: Exception) {
            _isRecognizing.value = false
        }
    }

    fun stopListening() {
        try {
            speechRecognizer?.stopListening()
        } catch (_: Exception) {}
        _isRecognizing.value = false
        _rmsLevel.value = 0f
    }

    private fun checkForWakeWords(text: String) {
        val lower = text.lowercase()
        when {
            lower.contains("hail myra") -> _wakeWordTriggered.value = "Hail Myra"
            lower.contains("hail kyra") -> _wakeWordTriggered.value = "Hail Kyra"
            lower.contains("hail sona") -> _wakeWordTriggered.value = "Hail Sona"
        }
    }

    fun clearWakeWord() {
        _wakeWordTriggered.value = null
    }

    // Volume controls
    fun adjustVolume(percentChange: Int) {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as? AudioManager ?: return
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        val step = (maxVolume * (percentChange / 100f)).toInt().coerceAtLeast(1)
        val target = if (percentChange > 0) {
            (currentVolume + step).coerceAtMost(maxVolume)
        } else {
            (currentVolume - step).coerceAtLeast(0)
        }
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, target, AudioManager.FLAG_SHOW_UI)
    }

    fun muteVolume() {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as? AudioManager ?: return
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.FLAG_SHOW_UI)
    }

    fun maxVolume() {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as? AudioManager ?: return
        val max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, max, AudioManager.FLAG_SHOW_UI)
    }

    // Analog Synth Audio Generation
    fun playSynthTone(frequency: Float = 440f, durationMs: Long = 800) {
        synthJob?.cancel()
        synthJob = scope.launch(Dispatchers.IO) {
            val sampleRate = 22050
            val numSamples = (sampleRate * (durationMs / 1000.0)).toInt()
            val sample = ShortArray(numSamples)

            for (i in 0 until numSamples) {
                val t = i.toDouble() / sampleRate
                val angle = 2.0 * Math.PI * frequency * t
                // Warm hybrid waveform
                val wave = (0.7 * sin(angle) + 0.3 * sin(2.0 * angle))
                sample[i] = (wave * Short.MAX_VALUE * 0.7).toInt().toShort()
            }

            val bufferSize = AudioTrack.getMinBufferSize(
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT
            )

            val audioTrack = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(sampleRate)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(bufferSize.coerceAtLeast(numSamples * 2))
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()

            try {
                audioTrack.write(sample, 0, numSamples)
                audioTrack.play()
                delay(durationMs)
                audioTrack.stop()
                audioTrack.release()
            } catch (_: Exception) {}
        }
    }

    fun destroy() {
        tts?.shutdown()
        speechRecognizer?.destroy()
        synthJob?.cancel()
        speakingRmsJob?.cancel()
    }
}
