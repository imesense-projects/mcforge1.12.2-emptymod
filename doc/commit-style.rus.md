# Стиль фиксаций

[English](./commit-style.md) | Русский

Этот документ определяет стандарты для написания сообщений фиксации для ведения чёткой, последовательной и содержательной истории проекта.

## Назначение

- Обеспечить удобную для чтения и поиска историю проекта.
- Обеспечить эффективное сотрудничество между членами команды.
- Поддержать автоматическую генерацию списка изменений.
- Упростить отслеживание изменений и отладку проблем.

## Структура сообщения фиксации

### 1. Заголовок (обязательно)
  
- __Формат__: `<Глагол> <существительное>`
- __Заглавная__ первая буква.
- __Максимальная длина__: 72 символа.
- __Повелительное наклонение__ (например, `Fix`/`Add`/`Remove` и не `Fixed`/`Added`).
- __Без префиксов__ вида `[Fix]` или `[Feature]`.

Хорошие примеры:

- `Fix memory leak in data parser`
- `Add user profile validation`

Плохие примеры:

- `Fixed bug` - слишком расплывчато
- `[Feature] New login screen` - префикс не разрешён

### 2. Тело (опционально, для комплексных изменений)

- Объяснить __что изменилось__ и __почему__.
- Уместить текст в __72 символа__ на строку.
- Использовать маркированный список для нескольких изменений (если необходимо).

Хороший пример:

```text
Optimize database query performance

- Replace nested queries with JOINs
- Add indexes on frequently filtered columns
- Reduces response time by ~40% in benchmarks
```

### 3. Подвал (опционально, для метаданных)

- Ссылки на проблемы (например, `Closes #123`).
- Соавторы (например, `Co-authored-by: Name <email>`).

## Распространённые ошибки

- Использование тегов или метаданных:
  - Плохо: `[BUGFIX] Fix null pointer exception`
  - Хорошо: `Fix null pointer exception in parser`

- Неправильная форма глагола:
  - Плохо: `Fixed broken tests`
  - Хорошо: `Fix broken unit tests`

- Глаголы без повелительного наклонения:
  - Плохо: `Fixes rendering bug`
  - Хорошо: `Fix rendering bug`

- Неопределённые сообщения:
  - Плохо: `Make it better`
  - Хорошо: `Improve image loading performance`

- Неправильное положение существительных:
  - Плохо: `Add support compression`
  - Хорошо: `Add compression support`

## Примеры сообщений о фиксации

### Однострочные сообщения

```text
Fix race condition in thread manager
```

```text
Refactor configuration parser
```

```text
Update dependencies to latest versions
```

```text
Remove legacy compatibility layer
```

### Многострочные сообщения

```text
Improve error handling in network module

Added comprehensive error checking for socket operations and implemented
proper cleanup routines for failed connections. The changes prevent
memory leaks during network failures.
```

```text
Add performance benchmarks for core functions

Implemented a new benchmarking suite that measures execution time of
critical path operations. Includes tests for various input sizes and
edge cases.
```
