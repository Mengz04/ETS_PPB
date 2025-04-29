package com.example.booksport

object CourtRepository {
    private val courts = listOf(
        Court(1, "Sony Badminton", "Badminton", "Mulyosari, Surabaya",
            2, "Mon-Sun (07.00 - 21.00)", 4.7f, R.drawable.sony_badminton, 50000),
        Court(2, "Bugar Badminton", "Badminton", "Merr, Surabaya",
            3, "Mon-Sun (06.00 - 22.00)", 4.8f, R.drawable.bugar_badminton, 40000),
        Court(3, "Jono Futsal", "Futsal", "Ketintang, Surabaya",
            4, "Mon-Sun (06.00 - 24.00)", 4.5f, R.drawable.jono_futsal, 45000),
        Court(4, "Speed Futsal", "Futsal", "Gayungsari, Surabaya",
            5, "Mon-Sun (06.00 - 24.00)", 4.5f, R.drawable.speed_futsal, 55000),
        Court(5, "Slow Basket", "Basket", "Citraland, Surabaya",
            4, "Mon-Sun (06.00 - 24.00)", 3.0f, R.drawable.slow_basket, 60000),
        Court(6, "Champion Tennis", "Tennis", "Ketintang, Surabaya",
            4, "Mon-Sun (06.00 - 24.00)", 4.0f, R.drawable.champion_tenis, 70000)
    )

    fun getCourtById(id: Int): Court? {
        return courts.find { it.id == id }
    }

    fun getAllCourts(): List<Court> {
        return courts
    }
}
