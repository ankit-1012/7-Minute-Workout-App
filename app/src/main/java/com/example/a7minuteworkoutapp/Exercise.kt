package com.example.a7minuteworkoutapp

import android.app.Dialog
import android.content.Intent
import android.content.IntentSender
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.dailog_custom_back_confirmation.*
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log


class Exercise : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseList: ArrayList<ExerciseModel>?= null
    private var currentExercisePosition =  -1
    private var tts:TextToSpeech?= null
    private var player: MediaPlayer?=null
    private var exerciseAdapter: ExerciseStatusAdapter?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)
        setSupportActionBar(toolbar_exercise)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar_exercise.setNavigationOnClickListener {
            customDialogForBackButton()
        }
        tts = TextToSpeech(this,this)
        exerciseList= Constants.defaultExerciseList()
        setUpRestView()
        setUpExerciseStatusRecyclerView()


    }

    override fun onDestroy() {
        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        if(tts!=null){
            tts!!.stop()
            tts!!.shutdown()
        }
        if(player!=null){
            player!!.stop()
        }

        super.onDestroy()
    }

    private fun setRestProgressbar() {
        progressBar.progress = restProgress
        restTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                progressBar.progress = 10 - restProgress
                tvTimer.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                exerciseList!![currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()


                setUpExerciseView()
            }

        }.start()

    }



    private fun setExerciseProgressbar() {


        progressBarExercise.progress = exerciseProgress
        exerciseTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                progressBarExercise.progress = 30 - exerciseProgress
                tvExerciseTimer.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {
                if(currentExercisePosition<11){
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsComplete(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setUpRestView()
                }
                else{
                 finish()
                    val intent= Intent(this@Exercise, FinishActivity::class.java)
                    startActivity(intent)
                }
            }

        }.start()

    }
    private fun setUpExerciseView() {

        llRestView.visibility= View.GONE
        llExerciseView.visibility= View.VISIBLE
        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        speakOut(exerciseList!![currentExercisePosition].getName())
        setExerciseProgressbar()
        ivImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        tvExerciseName.text= exerciseList!![currentExercisePosition].getName()

    }
    private fun setUpRestView() {
        try{
            player = MediaPlayer.create(applicationContext, R.raw.press_start)
            player!!.isLooping=false
            player!!.start()
        }catch (e:Exception){
            e.printStackTrace()
        }


        llRestView.visibility= View.VISIBLE
        llExerciseView.visibility= View.GONE
        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        tvUpcomingExercise.text= exerciseList!![currentExercisePosition+1].getName()
        setRestProgressbar()

    }

    override fun onInit(status: Int) {
        if(status ==TextToSpeech.SUCCESS){
            var result= tts!!.setLanguage(Locale.US)
            if(result==TextToSpeech.LANG_MISSING_DATA||result==TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS", "the language is not supported")
            }
        }else{
            Log.e("TTS", "Initialization failed")
        }

    }
    private fun speakOut(text:String){
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null, "")
    }
    private fun setUpExerciseStatusRecyclerView(){
        rvExerciseStatus.layoutManager= LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL
        ,false)
        exerciseAdapter= ExerciseStatusAdapter(exerciseList!!,this)
        rvExerciseStatus.adapter=exerciseAdapter
    }
    private fun customDialogForBackButton(){
        val customDialog =Dialog(this)
        customDialog.setContentView(R.layout.dailog_custom_back_confirmation)
        customDialog.tvYes.setOnClickListener {
            finish()
            customDialog.dismiss()
        }
        customDialog.tvNo.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }
}