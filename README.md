# Connect Four Game

this is a connect four game i made for my intro to programming course. its a 2 player game that runs in the console. you take turns dropping pieces and the first one to get 4 in a row wins.

---

## how to run

you need java installed on your computer.

```bash
javac ConnectFourGame.java
java ConnectFourGame
```

thats it.

---

## how to play

- player 1 is X, player 2 is O
- each turn you pick a column number between 1 and 7
- the piece drops to the lowest empty spot in that column
- get 4 in a row horizontally, vertically or diagonally to win
- if the board fills up with no winner its a draw
- after the game ends you can choose to play again

---

## what the board looks like

```
    1   2   3   4   5   6   7  
|   |   |   |   |   |   |   |
-----------------------------
|   |   |   |   |   |   |   |
-----------------------------
|   |   |   |   |   |   |   |
-----------------------------
|   |   |   |   |   |   |   |
-----------------------------
|   | o |   |   |   |   |   |
-----------------------------
|   | x | x |   |   |   |   |
-----------------------------
```

---

## things i worked on

the win check was the hardest part. at first i had 4 separate loops for each direction (horizontal, vertical, two diagonals) which was a lot of repeated code. i rewrote it so theres one method called checkLine that takes a direction as a parameter and handles all 4 cases. much cleaner.

i also added input validation because if you typed a letter instead of a number the whole program crashed with an exception. fixed that with a try-catch block.

---

## built with

- java
- netbeans

---

## author

Melih Kaan Metek  
Computer Engineering — Ankara Medipol University  
github: [melihkaanmetek](https://github.com/melihkaanmetek)
