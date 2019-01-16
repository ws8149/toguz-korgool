# Team Lemur:

## Team members:

Frederico (Fred) Arnaud Lacs

Wei Chew

Bonian Hu

Sebastian (Sebastian Tranaeus) Tranaeus

Mufid Alkhaddour

# Toguz-Korgool:

A two-player game that is played on a board with two rows of nine holes.
There are two "kazans" between these rows, which are used to collect captured stones of each user, separately.
At the beginning there are nine stones in each hole, except the kazans, which are empty, so players need a total of 162 stones.
Players move alternately. A move consists of taking stones from a hole and distributing them to other holes.
On his/her turn, a player takes all the stones of one of his holes, which is not a tuz (see below), and distributes them anticlockwise, one by one, into the following holes.
The first stone must be dropped into the hole which was just emptied.
However, if the move began from a hole which contained only one stone, this stone is put into the next hole.
If the last stone falls into a hole on the opponent's side, and this hole then contains an even number of stones, these stones are captured and stored in the player's kazan.
If the last stone falls into a hole of the opponent, which then has three stones, the hole is marked as a "tuz". There are a few restrictions on creating a tuz:
A player may create only one tuz in each game.
The last hole of the opponent (his ninth or rightmost hole) cannot be turned into a tuz.
A tuz cannot be made if it is symmetrical to the opponent's one (for instance, if the opponent's third hole is a tuz, you cannot turn your third hole into one).
It is permitted to make such a move, but it wouldn't create a tuz.
The stones that fall into a tuz are captured by its owner. He may transfer its contents at any time to his kazan.
The game ends when a player can't move at his turn because all the holes on his side, which are not tuz, are empty.
When the game is over, the remaining stones which are not yet in a kazan or in a tuz are won by the player on whose side they are.
The winner is the player who, at the end of the game, has captured more stones in their tuz and their kazan.
When each player has 81 stones, the game is a draw.

## Classes:

MainMenuGUI:
The main page you start with, you are able to start a normal game, start a custom game or load the last game you were playing and have saved.

GameBoardApp:
GUI responsible for playing the game.

BoardCustomizationGUI:
GUI responsible for customizing a game before loading the values to a GameBoardApp constructor to create one.

GameBoard:
Class responsible for the logic of the whole game.

Hole:
A representation of hole could be a kazan or a normal hole.

Turn:
A turn whether it is the player's turn or the opponent's turn.

Player:
Represents the person playing the game or the opponent AI.

Opponent:
Inherits from player and represents the opponent AI.

Used JSON for the saving and loading functionalities. When save button is clicked the data of the game is written into a file
using JSON, and when the load button is clicked the file is read and passed to the custom game constructor also using JSON.

