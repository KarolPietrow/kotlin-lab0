package org.example

fun main() {
    println("Podaj login: ")
    val login:String = readLine() ?:""
    val gamer = Gamer(login)
    println("Podaj liczbę rund: ")
    val rounds = readLine()?.toIntOrNull() ?: 1
    val game = Game(gamer)
    game.play(rounds)

}