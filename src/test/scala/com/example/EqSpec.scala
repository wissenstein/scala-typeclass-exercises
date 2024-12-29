package com.example

import com.example.laws.discipline.EqTests
import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks.forAll
import org.scalatest.prop.Tables.Table

class EqSpec extends MySpec, Matchers {
  test("function 'eq' returns true for integers 17 and 17") {
    Eq[Int].eq(17, 17) shouldBe true
  }

  test("function 'eq' returns false for integers 17 and 18") {
    Eq[Int].eq(17, 18) shouldBe false
  }

  test("function 'eq' returns true for strings 'Hänsel' and 'Hänsel'") {
    Eq[String].eq("Hänsel", "Hänsel") shouldBe true
  }

  test("function 'eq' returns false for strings 'Hänsel' and 'Gretel'") {
    Eq[String].eq("Hänsel", "Gretel") shouldBe false
  }

  test("function 'eq' returns true for persons with same names and same ids") {
    val haensel1 = Person("Hänsel", 17)
    val haensel2 = Person("Hänsel", 17)

    Eq[Person].eq(haensel1, haensel1) shouldBe true
  }

  test("function 'eq' returns false for persons if at least one property of other person is different") {
    val persons = Table(
      ("person 1", "person 2"),
      (Person("Hänsel", 42), Person("Hänsel", 43)),
      (Person("Hänsel", 42), Person("Gretel", 42)),
      (Person("Gretel", 17), Person("Hänsel", 71)),
    )
    forAll(persons) { (person1, person2) =>
      Eq[Person].eq(person1, person2) shouldBe false
    }
  }

  test("function 'eq' can compare also Options") {
    val options = Table(
      ("option 1", "option 2", "result"),
      (Some("hello"), Some("hello"), true),
      (Some("hello"), Some("good-bye"), false),
      (Some("hello"), None, false),
      (None, Some("hello"), false),
    )
    forAll(options) { (option1, option2, result) =>
      Eq[Option[String]].eq(option1, option2) shouldBe result
    }
  }

  test("function 'eqTo' can be used as syntax extension to compare optional strings") {
    import com.example.Eq.Syntax.EqOps

    val options = Table(
      ("option 1", "option 2", "result"),
      (Some("hello"), Some("hello"), true),
      (Some("hello"), Some("good-bye"), false),
      (Some("hello"), None, false),
      (None, Some("hello"), false),
    )
    forAll(options) { (option1, option2, result) =>
      option1 eqTo option2 shouldBe result
    }
  }

  // #16: Write tests for every Eq instance (Int, String and Person)
  //           using Discipline and the 'checkAll' method
  checkAll("stringEq", EqTests[String].eq)
  checkAll("intEq", EqTests[Int].eq)
  checkAll("personEq", EqTests[Person].eq)
  checkAll("optionalStringEq", EqTests[Option[String]].eq)
  checkAll("optionalIntEq", EqTests[Option[Int]].eq)
  checkAll("optionalPersonEq", EqTests[Option[Person]].eq)
}