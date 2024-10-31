package org.example

class Gamer (val name:String) {
    var points:Int = 0

    fun addPoint(){
        points++
    }

    fun removePoint(){
        points--
    }
}