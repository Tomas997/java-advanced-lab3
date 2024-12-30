package org.example.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Позначає поле, яке повинно бути серіалізовано.
 * Дозволяє вказати альтернативне ім'я для поля під час серіалізації.
 * Якщо ім'я не вказане, використовується стандартне ім'я поля.
 *
 * <p>Приклад використання:</p>
 * <pre>
 * public class Book {
 *     {@code @SerializableField("title")}
 *     private String title;
 * }
 * </pre>
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface SerializableField {
    /**
     * Альтернативне ім'я поля для серіалізації.
     * Якщо залишити порожнім, буде використано ім'я поля за замовчуванням.
     *
     * @return альтернативне ім'я поля
     */
    String name() default "";
}