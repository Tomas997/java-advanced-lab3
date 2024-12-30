package org.example.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Позначає клас для серіалізації з можливістю налаштування імені моделі.
 * Дозволяє вказати, як називатиметься модель у серіалізованому форматі.
 *
 * <p>Приклад використання:</p>
 * <pre>
 * {@code
 * @Serialized("book")
 * public class Book {
 *     private String title;
 *     private String author;
 * }
 * }
 * </pre>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Serialized {

    /**
     * Ім'я моделі у серіалізованому форматі.
     * Якщо не вказано, використовується ім'я класу з маленької літери.
     *
     * @return ім'я моделі
     */
    String name() default "";
}