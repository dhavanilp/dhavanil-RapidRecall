# CMPUT 301: Assignment 1

## Student Details
- **Full Name:** `Dhavanil Patel`
- **CCID:** `1801889`

## References and Resources
`Google Gemini. (2026, Sept. 8th).  Gemini version 3.1 Name.`

prompt: how do I enumerate through different stages of a game in Kotlin, can I use enum similar to python.

response: Kotlin uses the enum class keyword to define enumerations, which works very similarly to Python Enum but offers stronger typing and built-in object-oriented features.
Here is how to define, iterate through, and manage game stages using Kotlin enums.
1. Defining an Enum
At its simplest, you declare an enum class and list your stages. You can also attach properties and functions directly to the enum constants, which is highly useful for game development.
2. Enumerating (Iterating) Through Stages
To loop through all stages, use the .entries property (introduced in modern Kotlin as a performance improvement over the older .values() array).
3. Managing State with when
Kotlin enums pair perfectly with the when expression (Kotlin's version of Python match/case or C's switch). The compiler will enforce that you handle every possible game stage, preventing unhandled state bugs.
Key insight: Because updateGame checks every enum value, if you add a new stage like CUTSCENE to the enum class later, the Kotlin compiler will throw an error at the when block until you add logic to handle it.


prompt 2: what are coroutines in Kotlin and how can I use them to cycle through a list of numbers

respose: Think of coroutines as "lightweight threads." They allow you to run long background tasks—like downloading a file or loading a heavy game asset—without freezing your application or consuming massive amounts of system memory. You can run 100,000 coroutines concurrently without crashing, which would be impossible with traditional threads.

Instead of messy callbacks, coroutines let you write asynchronous code that looks and behaves like simple, sequential, top-to-bottom code.



