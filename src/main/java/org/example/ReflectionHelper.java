package org.example;


import org.example.annotations.SerializableField;

import javax.lang.model.element.Element;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Допоміжні методи для роботи з рефлексією.
 */
public class ReflectionHelper {

    /**
     * Отримує мапу класів та їх серіалізованих полів.
     *
     * @param annotatedClasses множина класів, що позначені анотацією {@link Serialized}
     * @return мапа класів та списків їх полів
     */
    public static Map<Element, List<Element>> mapSerializableFields(Set<? extends Element> annotatedClasses) {
        Map<Element, List<Element>> classFieldsMap = new HashMap<>();

        for (Element cls : annotatedClasses) {
            List<Element> fields = cls.getEnclosedElements().stream()
                    .filter(e -> e.getKind().isField())
                    .filter(e -> e.getAnnotation(SerializableField.class) != null)
                    .collect(Collectors.toList());
            classFieldsMap.put(cls, fields);
        }

        return classFieldsMap;
    }

    /**
     * Формує ім'я геттера для поля.
     *
     * @param field поле класу
     * @return ім'я геттера
     */
    public static String generateGetterName(Element field) {
        String fieldName = field.getSimpleName().toString();
        return "get" + capitalize(fieldName);
    }

    /**
     * Отримує ім'я властивості для серіалізації.
     *
     * @param field поле класу
     * @return ім'я властивості
     */
    public static String getSerializedName(Element field) {
        SerializableField annotation = field.getAnnotation(SerializableField.class);
        return annotation.name().isEmpty() ? field.getSimpleName().toString() : annotation.name();
    }

    /**
     * Отримує повне ім'я класу.
     *
     * @param cls елемент класу
     * @return повне ім'я класу
     */
    public static String getFullClassName(Element cls) {
        return cls.asType().toString();
    }

    /**
     * Допоміжний метод для приведення першої літери до великої.
     *
     * @param str вхідний рядок
     * @return рядок з першою великою літерою
     */
    private static String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}