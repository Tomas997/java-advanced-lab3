package org.example.serializer;

/**
 * Відповідальний за створення екземплярів згенерованих серіалізаторів.
 */
public class SerializerFactory {

    /**
     * Створює екземпляр серіалізатора для вказаного класу.
     *
     * @param serializerInterface інтерфейс серіалізатора
     * @param <T> тип серіалізатора
     * @return екземпляр згенерованого серіалізатора
     * @throws RuntimeException якщо не вдалося створити екземпляр
     */
    public static <T> T createInstance(Class<T> serializerInterface) {
        try {
            String implClassName = serializerInterface.getName() + "Impl";
            return serializerInterface.cast(Class.forName(implClassName).getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            throw new RuntimeException("Не вдалося знайти або створити реалізацію для: " + serializerInterface.getName(), e);
        }
    }
}