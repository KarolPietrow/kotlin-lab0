package org.example

import java.io.File
import java.io.IOException
import kotlin.random.Random

class Game (val gamer:Gamer) {
    private val filename = "data.txt"
    private var scores: MutableMap<String, Int> = mutableMapOf()

    init {
        scores = readScores() ?: mutableMapOf()
    }

    private fun readScores(): MutableMap<String, Int>? {
        try {
            val file = File(filename)
            if (!file.exists()) {
                file.createNewFile()
                return mutableMapOf()
            }
            val lines = file.readLines()
            val scoreMap = mutableMapOf<String, Int>()

            for (line in lines) {
                val data = line.split(",")
                if (data.size == 2) {
                    val name = data[0]
                    val score = data[1].toIntOrNull() ?: 0
                    scoreMap[name] = score
                }
            }
            return scoreMap
        } catch (e: IOException) {
            println("Błąd: ${e.message}")
            return null
        }
    }

    private fun saveScores(scores: Map<String, Int>) {
        try {
            val file = File(filename)
            file.writeText("")
            for ((name, score) in scores) {
                file.appendText("$name,$score\n")
            }
        } catch (e: IOException) {
            println("Error: ${e.message}")

        }
    }

    fun play(nRounds: Int) {
        println("Witaj ${gamer.name}! Wybrana liczba rund: $nRounds")
        println("Zaczynajmy!")
        // papier = 0
        // kamien = 1
        // nozyce = 2
        var number: Int
        var guess: Int
        for (i in 1..nRounds) {
            number = Random.nextInt(0, 3)
            println("Wybierz jedną z poniższych opcji:")
            println("Papier - 0; Kamień - 1; Nożyce - 2")
            guess = readlnOrNull()?.toIntOrNull() ?: 0
            while (guess > 2 || guess < 0) {
                println("Nieprawidłowa opcja! Wybierz ponownie.")
                guess = readlnOrNull()?.toIntOrNull() ?: 0
            }
            when (guess) {
                0 -> { // Gracz wybrał PAPIER
                    when (number) {
                        0 -> println("Remis! Komputer również wybrał papier.")
                        1 -> {
                            gamer.addPoint()
                            println("Wygrałeś! Wybrałeś papier, a komputer wybrał kamień.")
                        }
                        2 -> {
                            gamer.removePoint()
                            println("Przegrałeś! Wybrałeś papier, a komputer wybrał nożyce.")
                        }
                    }
                }
                1 -> { // Gracz wybrał KAMIEŃ
                    when (number) {
                        0 -> {
                            gamer.removePoint()
                            println("Przegrałeś! Wybrałeś kamień, a komputer wybrał papier.")
                        }
                        1 -> println("Remis! Komputer również wybrał kamień.")
                        2 -> {
                            gamer.addPoint()
                            println("Wygrałeś! Wybrałeś kamień, a komputer wybrał nożyce.")
                        }
                    }
                }
                2 -> { // Gracz wybrał NOŻYCE
                    when (number) {
                        0 -> {
                            gamer.addPoint()
                            println("Wygrałeś! Wybrałeś nożyce, a komputer wybrał papier.")
                        }
                        1 -> {
                            gamer.removePoint()
                            println("Przegrałeś! Wybrałeś nożyce, a komputer wybrał kamień.")
                        }
                        2 -> println("Remis! Komputer również wybrał nożyce.")
                    }
                }
            }
        }

        println("Wynik gracza ${gamer.name}: ${gamer.points}")

        val currentBestScore = scores[gamer.name] ?: 0
        if (gamer.points > currentBestScore) {
            scores[gamer.name] = gamer.points
            println("To nowy rekord dla gracza ${gamer.name}!")
        } else {
            println("Nie udało się pobić rekordu. Twój najlepszy wynik: $currentBestScore")
        }

        saveScores(scores)
        displayScoreTable()
    }

    private fun displayScoreTable() {
        println("\nTABELA WYNIKÓW:")
        println("Gracz\tWynik")
        for ((name, score) in scores) {
            println("$name\t$score")
        }
    }
}