# Lecturer_Fight
TDT4240 project group 16 2023

Structure:



Most of the code for the game is located in the "core" package. The main class, "Lecturer_fight" inherits from the Game class. The rest of the code is organized in four different folders: Controller, Model, View and Utils. We used the MVC architecture for this game, which made it easier to split the code into different folders.
We also implemented an API interface in this package, which is used in the android package. This was necessary because the Firebase library can only be used in Android devices.

![image](https://user-images.githubusercontent.com/125463501/234295021-8345c698-110d-4ac2-8b56-4582b35e1924.png)


Backend:

For the backend, we decided to use Firebase Realtime Database to reduce the amount of work required. The database is structured like this:
    
![db](https://user-images.githubusercontent.com/125463501/234284743-97a9d177-3600-4e17-b9b5-0b40fe2238bd.png)

The "scores" node is updated every time a player finishes a match. In the "players" node, a child with a random name is created, but players are able to change their names. If a player is playing a solo match, a child is created in the "players" node with only the "score" field updated. The other fields, "busy" and "ready", are only used in multiplayer mode. When a player wants to play a multiplayer match, a child is created with the value "ready = true" and "busy = false". When another child is created with the same states, both players will go into the "busy = true" state.

Here are the steps to run the project:

1. Install Git and LibGDX.
2. Clone the project: 

'git clone https://github.com/astorvold/Lecturer_Fight.git'

3. use your favorite IDE with an android simulator included or compile and copy the game in your android device. 

All other third-party packets are included in the "build.gradle" file.
