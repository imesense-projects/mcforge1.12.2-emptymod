# Commit style

English | [Русский](./commit-style.rus.md)

This document defines the standards for writing commit messages to maintain a clear, consistent and meaningful project history.

## Purpose

- Provide readable and searchable project history.
- Enable effective collaboration among team members.
- Support automated changelog generation.
- Make it easier to track changes and debug issues.

## Commit Message Structure

### 1. Header (Required)
  
- __Format__: `<Verb> <subject>`
- __Capitalize__ the first letter.
- __Max length__: 72 characters.
- __Imperative mood__ (e.g., `Fix`/`Add`/`Remove` and not `Fixed`/`Added`).
- __No prefixes__ like `[Fix]` or `[Feature]`.

Good examples:

- `Fix memory leak in data parser`
- `Add user profile validation`

Bad examples:

- `Fixed bug` - too vague
- `[Feature] New login screen` - prefix not allowed

### 2. Body (Optional, for complex changes)

- Explain __what changed__ and __why__.
- Wrap text at __72 characters__ per line.
- Use bullet points for multiple changes (if needed).

Good example:

```text
Optimize database query performance

- Replace nested queries with JOINs
- Add indexes on frequently filtered columns
- Reduces response time by ~40% in benchmarks
```

### 3. Footer (Optional, for metadata)

- Reference issues (e.g., `Closes #123`).
- Co-authors (e.g., `Co-authored-by: Name <email>`).

## Common Mistakes

- Using tags or metadata:
  - Bad: `[BUGFIX] Fix null pointer exception`
  - Good: `Fix null pointer exception in parser`

- Incorrect verb form:
  - Bad: `Fixed broken tests`
  - Good: `Fix broken unit tests`

- Non-imperative verbs:
  - Bad: `Fixes rendering bug`
  - Good: `Fix rendering bug`

- Vague messages:
  - Bad: `Make it better`
  - Good: `Improve image loading performance`

- Poor noun placement:
  - Bad: `Add support compression`
  - Good: `Add compression support`

## Commit Message Examples

### Single-line Messages

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

### Multi-line Messages

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
