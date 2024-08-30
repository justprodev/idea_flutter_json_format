package com.justprodev.dart_json_generator.generator

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.justprodev.dart_json_generator.utils.toCamelCase
import com.justprodev.dart_json_generator.utils.toUpperCaseFirstOne

/**
 * Class to represent the structure of the JSON object
 */
abstract class Entity(
    open val name: String,
    open val children: List<Entity>? = null
) {
    abstract val type: String
}

/**
 * Empty entity class - Dynamic type
 */
private class EmptyEntity(
    override val name: String,
) : Entity(name) {
    override val type = "dynamic"
}

/**
 * Primitive entity class, for long, double, boolean, and string types
 */
private class PrimitiveEntity(
    override val type: String,
    override val name: String,
) : Entity(name)

/**
 * Object entity class
 */
private class ObjectEntity(
    override val name: String,
    override val children: List<Entity>?
) : Entity(name, children) {
    override val type = name.toUpperCaseFirstOne().toCamelCase()
}

/**
 * List entity class
 */
private class ListEntity(
    override val name: String,
    child: Entity?
) : Entity(name) {
    override val type = "List<${child?.type ?: "dynamic"}>"
}


/**
 * Create an Entity object from a JSON object
 *
 * Also, if we found [ObjectEntity], we add it to the [allEntities] list
 * In other words, we are accumulating all the entities in the [allEntities] list
 *
 * @param allEntities List of all entities
 * @param name Name of the entity
 * @param value JSON value of the entity
 *
 * @return Entity object
 */
fun createEntity(allEntities: MutableList<Entity>, name: String, value: JsonElement): Entity {
    // Handle null values
    if (value.isJsonNull) return EmptyEntity(name)

    // Handle objects
    if (value is JsonObject) {
        val newEntity = ObjectEntity(
            name,
            value.entrySet().map { createEntity(allEntities, it.key.toString(), it.value) }
        )
        // Add the new entity to the list
        allEntities.add(newEntity)
        return newEntity
    }

    // Handle arrays
    if (value is JsonArray) {
        return if (value.size() == 0) {
            ListEntity(name, null)
        } else {
            val temp = createEntity(allEntities, name, value[0])
            ListEntity(name, temp)
        }
    }

    // Handle basic types
    if (value is JsonPrimitive) {
        if (value.isBoolean)
            return PrimitiveEntity("bool", name)

        if (value.isNumber) {
            if (value.isLong())
                return PrimitiveEntity("int", name)

            if (value.isDouble())
                return PrimitiveEntity("double", name)
        }
    }

    // If none of the above matches, it defaults to the String type
    return PrimitiveEntity("String", name)
}

private fun JsonElement.isLong() = toString().toLongOrNull() != null
private fun JsonElement.isDouble() = toString().toDoubleOrNull() != null