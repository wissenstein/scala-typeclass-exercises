package com.example.laws.discipline

import org.scalactic.Prettifier
import org.scalactic.source.Position
import org.scalatest.freespec.AnyFreeSpecLike
import org.scalatest.prop.Configuration
import org.scalatest.prop.Configuration.PropertyCheckConfiguration
import org.scalatestplus.scalacheck.Checkers
import org.typelevel.discipline.Laws
import org.typelevel.discipline.scalatest.Discipline

trait FreeSpecDiscipline extends Discipline, AnyFreeSpecLike:
  self: Configuration =>

  final def checkAll(name: String, ruleSet: Laws#RuleSet)
                    (using config: PropertyCheckConfiguration, prettifier: Prettifier, pos: Position): Unit =
    name - {
      for ((id, prop) <- ruleSet.all.properties)
        id in {
          Checkers.check(prop)(using convertConfiguration(config), prettifier, pos)
        }
    }
