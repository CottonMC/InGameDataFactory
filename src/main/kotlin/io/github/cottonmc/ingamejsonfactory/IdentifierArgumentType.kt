/* This file is a part of the In-Game JSON Factory project
 * by the Cotton Project, licensed under the MIT license.
 * Full code and license: https://github.com/CottonMC/ingame-json-factory
 */
package io.github.cottonmc.ingamejsonfactory

import com.mojang.brigadier.LiteralMessage
import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType
import io.github.cottonmc.jsonfactory.data.Identifier

object IdentifierArgumentType : ArgumentType<Identifier> {
    private val MISSING_PART = DynamicCommandExceptionType {
        LiteralMessage("Missing $it")
    }

    override fun <S> parse(reader: StringReader): Identifier {
        val namespace = reader.readUnquotedString()
        reader.expect(':')
        val path = reader.readUnquotedString()

        if (namespace.isEmpty())
            throw MISSING_PART.create("namespace")
        else if (path.isEmpty())
            throw MISSING_PART.create("path")

        return Identifier(namespace, path)
    }
}
