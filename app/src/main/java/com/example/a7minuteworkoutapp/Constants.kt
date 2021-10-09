package com.example.a7minuteworkoutapp

class Constants {
    companion object{
        fun defaultExerciseList():ArrayList<ExerciseModel>{
             val exerciseList = ArrayList<ExerciseModel>()
             val jumpingJacks = ExerciseModel(1, "Jumping Jacks",
                 R.drawable.jumping_jacks,false, false)
                exerciseList.add(jumpingJacks)
             val lunges = ExerciseModel(2, "Lunges",
                R.drawable.lunge,false, false)
            exerciseList.add(lunges)
            val planks = ExerciseModel(3, "Planks",
                R.drawable.plank,false, false)
            exerciseList.add(planks)
            val highKnees = ExerciseModel(4, "High Knees Raise",
                R.drawable.high_knees,false, false)
            exerciseList.add(highKnees)
            val squats = ExerciseModel(5, "Squats",
                R.drawable.squat,false, false)
            exerciseList.add(squats)
            val crunches = ExerciseModel(6, "Abdominal Crunches",
                R.drawable.ab_crunch,false, false)
            exerciseList.add(crunches)
            val stepUps = ExerciseModel(7, "Step Ups",
                R.drawable.stepup,false, false)
            exerciseList.add(stepUps)
            val pushUpRotation = ExerciseModel(8, "PushUp and Rotation",
                R.drawable.pushup_and_rotation,false, false)
            exerciseList.add(pushUpRotation)
            val sidePlanks = ExerciseModel(9, "Side Planks",
                R.drawable.side_plank,false, false)
            exerciseList.add(sidePlanks)
            val wallSits = ExerciseModel(10, "Wall Sits",
                R.drawable.wall_sit,false, false)
            exerciseList.add(wallSits)
            val dips = ExerciseModel(11, "Triceps Dips",
                R.drawable.triceps_dip,false, false)
            exerciseList.add(dips)
            val pushUps = ExerciseModel(12, "Regular PushUps",
                R.drawable.pushups,false, false)
            exerciseList.add(pushUps)





            return exerciseList
        }
    }
}