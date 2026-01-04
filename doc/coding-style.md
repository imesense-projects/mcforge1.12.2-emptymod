# Coding Style

English | [Русский](./coding-style.rus.md)

This document defines the coding standards for the project.
This standard is designed for the Java code style.

## File Conventions

- Default file extension for Java files is `java`.
- File name must exactly match the primary entity name.
- Files must be saved in __UTF-8__ encoding with __LF__ line endings and must end with one empty line.

## Naming Conventions

- Package names must be in the singular and __Lower Case__.
- Name of class or interface should be written in __Pascal Case__.
  - Interface must have `I` prefix.
  - Test class must have `Test` postfix.
- Names of the constants must be written in all caps __Snake Case__.
- Names of variables and methods must be in __Camel Case__.
  - Event methods must have `on` prefix, then event name and priority.

## Code Formatting

- Max 120 characters per line.
- Use 4 spaces instead of tabs.
- Use curly braces to indicate conditions and cycles.
- Curly braces should be on a new line.
- Use 1 space between the keyword and the condition.

## Best Practices

- Use grouping rules and separate imports by:
  - Standard Java (`java.*`, `javax.*`).
  - Dependencies (`org.springframework.*`).
  - Project-specific (`org.imesense.*`).
- Use one empty line between imports groups.
- Specify `final` in the final implementation of classes if there are no abstract and overridable methods.
- Classes with methods marked as `@SubscribeEvent` should have `@Mod.EventBusSubscriber` annotation.
- Use `@Unique` annotation for multiple usage of fields instead of duplicates.

## Examples

```java
if (condition)
{
    // ...
}
else
{
    // ...
}
```

```java
public class UserService
{
    public void updateUser(User user)
    {
        // ...
    }
}
```

```java
for (int i = 0; i < 10; i++)
{
    System.out.println(i);
}

switch (value)
{
    case 1:
        // ...
        break;
    default:
        // ...
}
```

```java
list.forEach(item ->
{
    System.out.println(item);
});
```

```java
int[] numbers =
{
    1, 2, 3
};
```

```java
package org.imesense.service;

import java.io.*;
import java.net.URL;

import javax.servlet.*;

public class UserService implements IUserService
{  
    private static final int MAX_ATTEMPTS = 3;

    private final UserRepository repository;

    public UserService(UserRepository repo)
    {
        this.repository = repo;
    }

    public User findById(long id)
    {
        // ...
    }
}
```
