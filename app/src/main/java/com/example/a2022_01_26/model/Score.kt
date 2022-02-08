package com.example.a2022_01_26.model

class Score {

    private var score: Int = 0
    fun inc(): Int{
        score += 10
        return score
    }

    fun dec(): Int {
        score -= 5
        return score
    }
}