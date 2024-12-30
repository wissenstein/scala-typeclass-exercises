package com.example.laws.discipline

import com.example.Eq
import com.example.laws.EqLaws
import org.scalacheck.Arbitrary
import org.scalacheck.Prop.forAll
import org.typelevel.discipline.Laws

trait EqTests[A] extends Laws:
  def laws: EqLaws[A]

  // #14: Define a RuleSet containing the laws in EqLaws
  def eq(using arb: Arbitrary[A]): RuleSet = new DefaultRuleSet(
    name = "Eq",
    parent = None,
    "reflexivity" -> forAll(laws.reflexivity),
    "symmetry" -> forAll(laws.symmetry),
    "transitivity" -> forAll(laws.transitivity)
  )

// #15: Define a companion object with an 'apply' method so that we can
//           easily instantiate tests with e.g. EqTests[Int]
object EqTests:
  def apply[A](using eqA: Eq[A]): EqTests[A] = new EqTests[A]:
    override def laws: EqLaws[A] = new EqLaws[A]:
      override def eq: Eq[A] = eqA
