package org.example.serializer;

/**
 * Інтерфейс, що визначає методи для серіалізації об'єктів у JSON та XML формати.
 */
public interface Serializer {

    /**
     * Перетворює об'єкт у JSON рядок.
     *
     * @param obj об'єкт для серіалізації
     * @return JSON представлення об'єкта
     */
    String toJson(Object obj);

    /**
     * Перетворює об'єкт у XML рядок.
     *
     * @param obj об'єкт для серіалізації
     * @return XML представлення об'єкта
     */
    String toXml(Object obj);
}