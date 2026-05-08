# Cruise Ship Management System

this is an oop assignment i did in my first year. the goal was to model a cruise ship with rooms and passengers using proper object oriented principles like encapsulation, constructors and the equals method.

---

## classes

there are 4 classes in total:

- **Passenger** - stores passenger info (name, address, age, room number)
- **Room** - represents a single room, tracks if its occupied and who is in it
- **Cruise** - the main class, manages all rooms and passengers on the ship
- **Assignment2** - driver class to test everything

---

## how to run

```bash
javac *.java
java Assignment2
```

---

## what it does

- you can create a cruise ship with a set number of interior and windowed rooms
- passengers get automatically assigned to the first available room when added
- you can search for a passenger by name
- toString prints a summary of the ship's current state

---

## what i focused on

the main thing i had to be careful about was privacy leaks. for example in the copy constructor of Cruise, if you just copy the array reference both objects end up pointing to the same passengers in memory. so i made sure to loop through and copy each passenger individually using the copy constructor.

also the equals method in Passenger was a bit tricky because you have to check each field separately and handle the case where the object being compared is null or a different class entirely.

---

## built with

- java
- netbeans

---

## author

Melih Kaan Metek  
Computer Engineering — Ankara Medipol University  
github: [melihkaanmetek](https://github.com/melihkaanmetek)
