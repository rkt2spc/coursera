# Web tracking

Suppose that you are tracking n web sites and m users and you want to support the following API:

- User visits a website.
- How many times has a given user visited a given site?

## Answer

- Nested SymbolTable(s). Increase counter by `users[user][website]++`
