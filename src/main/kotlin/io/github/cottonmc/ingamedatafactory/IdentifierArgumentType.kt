/* This file is a part of the In-Game Data Factory project
 * by the Cotton Project, licensed under the MIT license.
 * Full code and license: https://github.com/CottonMC/ingame-data-factory
 */
package io.github.cottonmc.ingamedatafactory

import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType
import io.github.cottonmc.jsonfactory.data.Identifier
import net.minecraft.text.TranslatableTextComponent

object IdentifierArgumentType : ArgumentType<Identifier> {
    private val MISSING_PART = DynamicCommandExceptionType {
        TranslatableTextComponent("argument.igdf.identifier_part_missing", it)
    }

    override fun <S> parse(reader: StringReader): Identifier {
        val namespace = reader.readUnquotedString()
        reader.expect(':')
        val path = reader.readUnquotedString()

        if (namespace.isEmpty())
            throw MISSING_PART.createWithContext(reader, "namespace")
        else if (path.isEmpty())
            throw MISSING_PART.createWithContext(reader, "path")

        return Identifier(namespace, path)
    }
}
