package com.example

trait Eq[A]:
  // #1: Define an 'eq' method that takes two A values as parameters, and returns a Boolean
  def eq(a: A, b: A): Boolean

object Eq:
  // #2: Define the method 'apply' so we can summon instances from implicit scope
  def apply[A](using ev: Eq[A]): Eq[A] = ev

  // #3: Define the method 'instance' so we can build instances of the Eq typeclass more easily.
  //          It should take as the only parameter a function of type (A, A) => Boolean
  def instance[A](f: (A, A) => Boolean): Eq[A] = (a, b) => f(a, b)

  // #4: Define an Eq instance for String
  given stringEq: Eq[String] = (a, b) => a == b

  // #5: Define an Eq instance for Int
  given intEq: Eq[Int] = (a, b) => a == b

  // #6: Define an Eq instance for Person. Two persons are equal if both their names and ids are equal.
  //          Extra points: receive implicit instances for String and Int and use them
  given personEq: Eq[Person] = (a, b) => Eq[String].eq(a.name, b.name) && Eq[Int].eq(a.id, b.id)

  // #7: Provide a way to automatically derive instances for Eq[Option[A]] given that we have an implicit
  //          instance for Eq[A]
  given optionEq[A](using eq: Eq[A]): Eq[Option[A]] = (a: Option[A], b: Option[A]) => a match
    case None => b.isEmpty
    case Some(v) => b match
      case None => false
      case Some(w) => eq.eq(v, w)

  object Syntax:
    // #8: Define a class 'EqOps' with a method 'eqTo' that enables the following syntax:
    //          "hello".eqTo("world")
    implicit class EqOps[A](a: A):
      infix def eqTo(b: A)(using eq: Eq[A]): Boolean = eq.eq(a, b)
