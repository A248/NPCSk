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

### NPC Auto Hide Distance

The distance outside of which NPCs are automatically hidden.
Outside this distance, the NPC is always hidden.
Inside this distance, the NPC will be shown to the player only if
the visibility of the NPC for the player is set to true.

Syntax:

`[npcsk] [npc] auto hide (distance|radius)`

Example:
```
set npc auto hide distance to 30
broadcast "NPCs will be automatically hidden when players are 30 blocks away!"
```

### Event-NPC

The NPC in a NPC interact event.

Syntax:

`[npcsk] npc-event-npc`

Example:
```
on npc interact:
  broadcast "NPC ID: %npc-event-npc%. (NPC IDs are ugly; they aren't display names)"
```

### Event-Player

The player in a NPC interact event.

Syntax:

`[npcsk] npc-event-player`

Example:
```
on npc interact:
  # {spawn} variable must be a location
  teleport npc-event-player to {spawn}
  send "teleported to spawn" to npc-event-player
```

### Last Created NPC

Gets the ID of the last NPC created with the *create* effect.

Syntax:

`[npcsk] last created npc`

Example:
```
# In this example there is only 1 npc stored in the {npc} variable
# in reality you can create unlimited NPCs
on load:
  # {spawn} variable must be a location
  npcsk create npc with name "Hub NPC", skin 100680423, and location {spawn}
  set {npc} to last created npc

on join:
  # show the npc on join
  set npc visibility of {npc} for player to true
```

### NPC Location

Gets the location of a NPC. This cannot be changed. To "move" a NPC, you'll have to delete and recreate it.

Syntax:

`[npcsk] npc location of [id] %string%`

Example:
```
on npc interact:
  # teleport all players to the npc
  loop all players:
    teleport loop-player to npc location of npc-event-npc
```

### NPC Item Slot

The item a NPC is holding in a slot. Available slots: "mainhand", "offhand", "helmet", "chestplate", "leggings", "boots".

Syntax:

`[npcsk] [item in] npc slot %string% (of|for) [npc] %string%`

Example:
```
# Midas touch
on npc interact:
  set item in npc slot "helmet" for npc-event-npc to gold helmet
  set item in npc slot "chestplate" for npc-event-npc to gold chestplate
  set item in npc slot "leggings" for npc-event-npc to gold leggings
  set item in npc slot "boots" for npc-event-npc to gold boots
```

### NPC State

Whether a NPC has a specific state. Available states: "crouched", "invisible", "on_fire".
The invisible state just means the npc has the invisibility potion effect. The NPC's tool is still visible,
and players can still interact with it. To make a NPC truly invisible, use the *visibility* expression.

Syntax:

`[npcsk] npc state %string% of [npc] %string%`

Example:
```
on npt interact:
  # This example will make the NPC crouch if it is not, and uncrouch if it is
  if npc state "crouched" of npc-event-npc = false:
    set npc state "crouched" of npc-event-npc to true
  else:
    set npc state "crouched" of npc-event-npc to false
```

### NPC Visibility

Whether the NPC is visible to a player. This expression controls the visibility.
The player will see the NPC if:
1. The player is close enough to the NPC (see *auto hide distance* expression)
2. The visibility of the NPC for the player is true

Syntax:

`[npcsk] npc visibility of %string% for %player%`

Example:
```
on npc interact:
  # hide the npc when the player clicks on it
  set npc visibility of npc-event-npc for npc-event-player to false
```

## Conditions

### NPC Click Type

Whether the click was a rightclick or leftclick in the npc interact event.

Syntax:

`[npcsk] [npc] [interaction] clicktype is (1¦leftclick|2¦rightclick)`

Example:
```
on npc interact:
  if clicktype is leftclick:
    send "click is a leftclick" to npc-event-player
  else:
    send "click is a rightclick" to npc-event-player
```

### NPC Exists

Used to check whether a NPC exists. When scripting, you should always store your NPC ids in variables,
then, you can use those variables to manipulate NPCs by their IDs.

Syntax:

`[npcsk] npc %string% exists`

Example:
```
on npc interact:
  if npc npc-event-npc exists:
    send "You will always see this message" to npc-event-player
  else:
    send "This is impossible! How did you click a nonexistent NPC?" to npc-event-player
```
