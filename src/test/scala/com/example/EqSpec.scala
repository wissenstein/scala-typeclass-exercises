package com.example

import com.example.laws.discipline.EqTests
import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks.forAll
import org.scalatest.prop.Tables.Table

class EqSpec extends MySpec, Matchers:
  "Function 'eq'" - {
    "returns true when both integers are the same" in {
      Eq[Int].eq(17, 17) shouldBe true
    }

    "returns false when integers are different" in {
      Eq[Int].eq(17, 18) shouldBe false
    }

    "returns true when both strings are the same" in {
      Eq[String].eq("Hänsel", "Hänsel") shouldBe true
    }

    "returns false when strings are different" in {
      Eq[String].eq("Hänsel", "Gretel") shouldBe false
    }

    "returns true when persons have same names and same ids" in {
      val haensel1 = Person("Hänsel", 17)
      val haensel2 = Person("Hänsel", 17)

      Eq[Person].eq(haensel1, haensel1) shouldBe true
    }

    "returns false when persons have at least one property different" in {
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

    "can compare also Options" in {
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
  }

  "Function 'eqTo'" - {
    "can be used as syntax extension to compare optional strings" in {
      import com.example.Eq.Syntax.EqOps

      val options = Table(
        ("option 1", "option 2", "result"),
        (Some("mumin"), Some("mumin"), true),
        (Some("mumin"), Some("trollet"), false),
        (Some("snork"), None, false),
        (None, Some("snusmumriken"), false),
      )
      forAll(options) { (option1, option2, result) =>
        option1 eqTo option2 shouldBe result
      }
    }
  }

  // #16: Write tests for every Eq instance (Int, String and Person)
  //           using Discipline and the 'checkAll' method
  "Laws are fulfilled for" - {
    checkAll("stringEq", EqTests[String].eq)
    checkAll("intEq", EqTests[Int].eq)
    checkAll("personEq", EqTests[Person].eq)
    checkAll("optionalStringEq", EqTests[Option[String]].eq)
    checkAll("optionalIntEq", EqTests[Option[Int]].eq)
    checkAll("optionalPersonEq", EqTests[Option[Person]].eq)
  }
