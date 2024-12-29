package com.example

import com.example.Eq.Syntax.EqOps

case class Person(name: String, id: Int)

object Person:
  object Instances: 
    // #9: Define an Eq instance for Person comparing them by name
    //          Extra points: receive an implicit instance for String and use it
    val nameEq: Eq[Person] = (person1, person2) => person1.name eqTo person2.name

    // #10: Define an Eq instance for Person comparing them by id
    //           Extra points: receive an implicit instance for Int and use it
    val idEq: Eq[Person] = (person1, person2) => person1.id eqTo person2.id