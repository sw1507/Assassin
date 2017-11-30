# Assassin

Program Details

“Assassin” is a game often played on college campuses. Each person playing has a particular target that
he/she is trying to “assassinate.” Generally “assassinating” a person means finding them on campus in
public and acting on them in some way (e.g. saying “You’re dead,” squirting them with a water gun, or
touching them). One of the things that makes the game more interesting to play in real life is that initially
each person knows only who they are assassinating; they don’t know who is trying to assassinate them,
nor do they know whom the other people are trying to assassinate.
Assassin Rules

• You start out with a group of people who want to play the game
• A circular chain of assassination targets (called the “kill ring” in this program) is established.
• When someone is assassinated, the links need to be changed to “skip” that person

In this assignment, you will write a class AssassinManager that keeps track of who is stalking whom and
the history of who killed whom. You will maintain two linked lists:

• a list of people currently alive (the “kill ring”) and
• a list of those who are dead (the “graveyard”).

As people are killed, you will move them from the kill ring to the graveyard by rearranging links between
nodes. The game ends when only one node remains in the kill ring, representing the winner.

A client program called AssassinMain has been written for you. It reads a file of names, shuffles the
names, and constructs an object of your class AssassinManager. This main program then asks the user
for the names of each victim to kill until there is just one player left alive (at which point the game is over
and the last remaining player wins). AssassinMain calls methods of the AssassinManager class to carry
out the tasks involved in administering the game.
