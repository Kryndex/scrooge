package com.twitter.scrooge.backend

import com.twitter.scrooge.mustache.Dictionary
import com.twitter.scrooge.mustache.Dictionary._
import com.twitter.scrooge.ast.{Enum, Identifier}

trait EnumTemplate { self: TemplateGenerator =>
  def enumDict(
    namespace: Identifier,
    enum: Enum
  ): Dictionary =
    Dictionary(
      "package" -> genID(namespace),
      "EnumName" -> genID(enum.sid.toTitleCase),
      "docstring" -> v(enum.docstring.getOrElse("")),
      "values" -> v(enum.values.map { value =>
        Dictionary(
          "valuedocstring" -> v(value.docstring.getOrElse("")),
          "name" -> genID(value.sid),
          "originalName" -> v(value.sid.originalName),
          "unquotedNameLowerCase" -> v(value.sid.fullName.toLowerCase),
          "value" -> v(value.value.toString),
          "annotations" -> TemplateGenerator.renderPairs(value.annotations)
        )
      })
    )
}
