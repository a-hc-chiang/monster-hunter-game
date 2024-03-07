# Angela Chiang's Personal Project


# Project idea 
Monster killing game

I want to create a game that involves a player fighting monsters.
- single level
- player uses ranged attacks

The player will have x (suggested value will be 20) turns to kill as many monsters as possible, the game will be over when the player runs out of turns.

**Monsters:** basic monster with 3 health points

# User Stories: 

**Phase 0 + 1:**

As a user, I want new monsters be summoned every few turns 

As a user, I want to choose how many turns I can have per game 

As a user, I want the game to keep track of scores, so I can show to my friends 

As a user, I want at least 2 minutes of gameplay per game 

**Phase 2:**

As a user, I want to be able to save my current game before quitting the app 

As a user, I want to be able to load sessions of my games when I relaunch the game 

**Phase 3:**

# Instructions for Grader: 

how to move player icon: up, left, down, right buttons

how to attack: attack button to indicate attack, then left, down, right buttons to indicate attack direction 

how to save current game: Save game button

how to load previous game: Load game button

how to quit game: Quit game button 

other counters such as remaining turns and score will be on the top part of the window

the counters will consistently update throughout the game

As a user, I want to be able to spawn more monsters to the board (Monster class and GameHandler Class) 
such monsters will be added onto the displayed game board

As a user, I want to be able to load and save the state of the application"

**Phase 4:**

# Example of event log:
Wed Nov 29 20:21:03 PST 2023
User moved up!

Wed Nov 29 20:21:03 PST 2023
Added  5 Monsters to GameHandler monsters!

Wed Nov 29 20:21:03 PST 2023
Turns active! Adding new monsters onto the grid.

Wed Nov 29 20:21:04 PST 2023
User moved up!

Wed Nov 29 20:21:04 PST 2023
User moved right!

Wed Nov 29 20:21:04 PST 2023
Added  5 Monsters to GameHandler monsters!

Wed Nov 29 20:21:04 PST 2023
Turns active! Adding new monsters onto the grid.

Wed Nov 29 20:21:06 PST 2023
User moved up!

Wed Nov 29 20:21:07 PST 2023
Removed  0 Monsters from GameHandler monsters!

Wed Nov 29 20:21:08 PST 2023
Removed  0 Monsters from GameHandler monsters!

Wed Nov 29 20:21:10 PST 2023
Removed  2 Monsters from GameHandler monsters!

# Phase 4: Task 3

Designing the application, to reduce the coupling of the game, I would implement an observer pattern. The ConsoleHandler and ConsoleHandlerGui 

will be the observers while a whole separate class will appear with functionality to be the observable

I find that the two observer classes have very similar if not identical behaviour. An extra class will be created, but coupling will decrease. 


