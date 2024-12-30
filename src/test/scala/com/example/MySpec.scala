package com.example

import com.example.laws.discipline.FreeSpecDiscipline
import org.scalacheck.{Arbitrary, Gen}
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.prop.Configuration

trait MySpec extends AnyFreeSpec, FreeSpecDiscipline, Configuration:
  given arbitraryPerson: Arbitrary[Person] = Arbitrary:
    for 
      name <- Gen.alphaNumStr
      id <- Gen.chooseNum(1, 100)
    yield
      Person(name, id)  
