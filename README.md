🐾 Biting On Fish
A Multiplayer Dungeon-Crawler About Family, Survival, and Darkness Beneath the Floorboards

Biting On Fish is a Java-based, networked dungeon-crawler where players take on the role of kittens searching for their missing mother. Traverse hand-crafted rooms, fight enemies, defeat bosses, and uncover the dark world hidden beneath their home.

The game features real-time multiplayer combat, procedurally generated dungeons, unique enemy AI, a tutorial system, and dramatic narrative cutscenes — all rendered on a custom Java game engine using Swing.

🌟 Features
🎮 Real-Time Multiplayer

Server–client architecture using Java sockets

GameServer handles logic; GameCanvas handles rendering

Each player’s movement, attacks, and interactions are synchronized across clients

🗺️ Dynamic Dungeon Generation

Graph-based room system

Backtracking support with BFS connectivity checks

Each floor contains seven room types, complete with unique environments and enemies

Locked doors, cleared-room detection, and seamless room transitions

🐢 Unique Boss Fights

Each level ends with a custom boss.
Example: Snapping Turtle Boss

Phase 1: Slow, tanky, lethal attacks

Phase 2: Invincible shell mode; emerges only near players

Custom spawn indicators and boss-room logic

🤖 Enemy AI

Enemies scan for the nearest player within a radius

Move directly toward targets

Collision, attack patterns, and HP handled server-side

More advanced behaviors planned

🧰 Items and Tooltips

Item drops with adjustable probabilities

Persistent tooltips tied to each item (client-side rendering + server-side state syncing)

Right-click interactions

Server-validated item pickups

📝 Built-In Tutorial System

A full guided onboarding implemented with:

Step-by-step overlays

Timed instructions

Door locking/unlocking logic

Detection of combat actions, movement, item clicks

Reliable progression handled server-side

🎬 Narrative & Cutscenes

Emotional storyline about kittens searching for their mother

Cutscenes before boss fights and at major story beats

Dark but heartfelt tone focused on survival and family bonds

🛠️ Tech Stack
Language & Core

Java 17

Swing for rendering (JFrame, JComponent, paintComponent)

Java Sockets for multiplayer

ExecutorService for threaded tasks (enemy spawning, tutorial timing, etc.)

Game Architecture

GameServer – game state logic

GameCanvas – rendering

GameFrame – main window & UI

GameStateManager – decoupled logic controller

Room, Door, Enemy, Boss, Item, SpawnIndicator classes

MobSpawner for scheduled spawning

DungeonMap with serialization/deserialization over network

🏗️ Project Structure
/src
  /client
    GameClient.java
    GameFrame.java
    GameCanvas.java
    TutorialManager.java
  /server
    GameServer.java
    ServerMaster.java
    ConnectedPlayer.java
    MobSpawner.java
  /game
    Room.java
    Door.java
    Enemy.java
    Boss.java
    Item.java
    ItemTooltip.java
