# Documentation

## Introduction

NPCSk allows you to create NPCs. However, all NPCs are removed on server stop.

NPCSk and its supporting library, NPCLib, use special **IDs** to identify NPCs.
Once an NPC is created with the *create* effect, the NPC's ID can be found using the *last created* expression.

## Events

### NPC Interact Event

Fired when a player clicks on a NPC.

Syntax:

`[npcsk] npc interact [event]`

Example:
```
on npc interact:
  send "you clicked" to npc-event-player
```

## Effects

### Create NPC

Creates a NPC with given name, skin, and location.
The skin is a number. See https://mineskin.org/ for details on skins.

Syntax:

`[npcsk] create npc with name %strings%, skin %number%, and location %location%`

Example:
```
Command /createnpc <text>:
  Executable by: players
  Trigger:
    create npc with name arg 1, skin 100680423, and location player's location
    send "Created NPC at your location"
```

### Delete NPC

Deletes a NPC by ID.

Syntax:
`[npcsk] (delete|remove|clear) npc [(with|from) id] %string%`

Example:
```
Command /testnpc:
  Executable by: players
  Trigger:
    npcsk create npc with name "TestNPC", skin 100680423, and location player's location
    set {_id} to last created npc
    send "Created test NPC at your location. Deleting in 5 seconds..."
    wait 5 seconds
    delete npc from id {_id}
```

## Expressions

### All NPCs

Gets all NPC IDs.

Syntax:
`[npcsk] [all] (npcs|npc ids)`

Example:
```
Command /showall:
  Executable by: players
  Trigger:
    loop all players:
      loop all npcs:
        set visibility of loop-value-2 for loop-value-1 to true
    send "Now all players can see all NPCs"
```



## Conditions
