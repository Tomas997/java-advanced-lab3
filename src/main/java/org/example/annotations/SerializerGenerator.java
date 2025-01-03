package org.example.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Позначає клас, для якого необхідно згенерувати серіалізатор.
 * Використовується разом з анотаціями {@link SerializableField} та {@link Serialized}.
 */
@Target(TYPE)
@Retention(SOURCE)
public @interface SerializerGenerator {
}