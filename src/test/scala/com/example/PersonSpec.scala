package com.example

import com.example.Person.Instances.{idEq, nameEq}
import com.example.laws.discipline.EqTests
import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks.forAll
import org.scalatest.prop.Tables.Table

class PersonSpec extends MySpec, Matchers:
  "nameEq compares persons by name" in {
    val persons = Table(
      ("person1", "person2", "result"),
      (Person("Hänsel", 327), Person("Hänsel", 273), true),
      (Person("Gretel", 327), Person("Hänsel", 273), false),
      (Person("Gretel", 327), Person("Hänsel", 327), false),
    )
    forAll(persons) { (person1, person2, result) =>
      nameEq.eq(person1, person2) shouldBe result
    }
  }

  "idEq compares persons by id" in {
    val persons = Table(
      ("person1", "person2", "result"),
      (Person("Hänsel", 327), Person("Hänsel", 273), false),
      (Person("Gretel", 327), Person("Hänsel", 273), false),
      (Person("Gretel", 327), Person("Hänsel", 327), true),
    )
    forAll(persons) { (person1, person2, result) =>
      idEq.eq(person1, person2) shouldBe result
    }
  }

  // #17: Write tests for additional Eq instances defined in Person using
  //           Discipline and the 'checkAll' method
  "Laws are fulfilled for" - {
    checkAll("nameEq", EqTests(using nameEq).eq)
    checkAll("idEq", EqTests(using idEq).eq)
  }
