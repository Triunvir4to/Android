package com.example.jetpackcompose.repository

import com.example.jetpackcompose.model.Person

class PersonRepository {
    fun getAllData(): List<Person> {
        return listOf(
            Person(id = 0, firstName = "John", lastName = "Doe", age = 28),
            Person(id = 1, firstName = "Jane", lastName = "Smith", age = 34),
            Person(id = 2, firstName = "Alice", lastName = "Johnson", age = 45),
            Person(id = 3, firstName = "Bob", lastName = "Brown", age = 22),
            Person(id = 4, firstName = "Charlie", lastName = "Davis", age = 30),
            Person(id = 5, firstName = "David", lastName = "Miller", age = 27),
            Person(id = 6, firstName = "Eve", lastName = "Wilson", age = 32),
            Person(id = 7, firstName = "Frank", lastName = "Moore", age = 40),
            Person(id = 8, firstName = "Grace", lastName = "Taylor", age = 25),
            Person(id = 9, firstName = "Hank", lastName = "Anderson", age = 35)
        )
    }
}
