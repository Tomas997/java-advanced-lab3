
# Лабораторна робота №3

## Автор
IO-25 Плаксенков Андрій

## Опис проєкту
Цей проєкт демонструє використання анотацій для автоматичної генерації серіалізаторів об'єктів у JSON та XML формати. Система включає:
- Анотації для позначення класів та полів, які повинні бути серіалізовані.
- Генератор серіалізаторів, що створює реалізацію серіалізаторів на основі позначених класів.
- Інтерфейс `Serializer`, який визначає методи для серіалізації об'єктів.

Проєкт підтримує генерацію серіалізаторів для класів із заданими анотаціями:
- `@SerializableField`: Позначає поле, яке підлягає серіалізації.
- `@Serialized`: Позначає клас, який потрібно серіалізувати.
- `@SerializerGenerator`: Вказує, що для класу необхідно згенерувати серіалізатор.

## Структура проєкту
- **Пакет `org.example.annotations`**: Містить визначення анотацій.
- **Пакет `org.example.entity`**: Містить приклади класів, які потрібно серіалізувати (наприклад, `Book`, `Movie`, `Product`).
- **Пакет `org.example.serializer`**: Містить інтерфейс `Serializer` та фабрику `SerializerFactory`.
- **`SerializerAnnotationProcessor`**: Анотаційний процесор для генерації серіалізаторів.

## Інструкції зі збірки
1. Переконайтеся, що у вас встановлено **JDK 17** або новішу версію.
2. Завантажте проєкт із репозиторію або розпакуйте архів із кодом.
3. Відкрийте проєкт у вашій IDE (наприклад, IntelliJ IDEA або Eclipse).

## Інструкції із запуску
1. Запустіть процес збірки проєкту. Для цього можна скористатися наступними командами:
   ```bash
   ./gradlew build  # Для Gradle
   mvn clean install  # Для Maven
   ```
2. Переконайтеся, що згенеровані серіалізатори доступні у вихідному коді.
3. Запустіть тестові класи для перевірки роботи серіалізаторів.

