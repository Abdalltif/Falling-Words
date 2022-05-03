# Falling Words Game

## Overview
MVVM design architecture, Clean architecture, Dagger hilt, MockK and Coroutine.
The app works on both landscape and portrait mode also in dark theme without losing states.

## Time Invested
It toke me almost 6 hours to complete the project.
- 1 hour planning the architecture, game concept and design.
- 1 hour setup the project architecture and data layer with dagger hilt.
- 1 hour implementing layouts and UI elements.
- 2.5 hours implementing game logic.
- 30 minutes writing unit-tests.

## decisions made to solve certain aspects of the game

- MVVM design architecture with clean architecture so the project be testable and scalable for new features.
- Dagger Hilt to reduce code boilerplate and make it more readable. 
- UI state enum and game state object to update the UI for less boilerplate and readability.


## decisions made because of restricted time

- Single screen with dialogs and simple UI.
- Not storing game data in persistence storage for pausing the game.
- Focus on business logic.


## Improvements

- Improve the UI/UX and adding graphics, sounds and animations.
- Gameplay mechanics improvement like game difficulty mode.
- Adding more features like leaderboard.
- Implement network calls to fetch words on the cloud.
- Increase unit tests coverage.  
- Implement UI tests.