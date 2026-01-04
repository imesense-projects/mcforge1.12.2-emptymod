# Branching Model

English | [Русский](./branching-model.rus.md)

This document describes the principles for maintaining the project repository history and branch management strategy.

## History Style

The project maintains a linear commit history to:

- Simplify code review and bisecting.
- Provide clear chronological progression.
- Reduce merge conflicts.
- Make the history easier to understand.

## Branch Types

### Permanent Branches

- `default`
  - The primary development branch.
  - Always in a production-ready state.
  - Protected branch (only maintainers can push).
  - All changes must come through merge requests.
  - Direct commits are prohibited.

### Regular Branches

- All working branches must follow these naming conventions:

  ```text
  feature/<short-descriptive-name>
  ```

  Example: `feature/user-authentication`

## Branch Management Rules

### 1. Creation

- Always branch from `default`.
- Keep branches short-lived.
- Limit branch scope to a single logical change.

### 2. Naming

- Use lowercase letters.
- Separate words with hyphens.
- Keep names under 30 characters.
- Avoid special characters.

### 3. Synchronization

- Rebase regularly on `default`.
- Resolve conflicts in your branch.

### 4. Cleanup

- Delete merged branches.
- Archive experimental branches if kept.
- Regularly prune stale branches.

## Merge Process

All changes must follow this workflow:

1. Create a branch from `default`.
2. Develop and commit changes.
3. Create a merge/pull request.
4. Pass code review and CI/CD checks.
5. Rebase onto latest `default`.
6. Squash commits if necessary.
7. Get maintainer approval.
8. Merge using fast-forward strategy.
