# Offline v6-v10

## v6 Animated sprites
Asset-backed 64x56 animation renderer with fallback graphics.

## v7 Pet systems
Timed stat decay, care actions, EXP and persistent local state are supported by the architecture.

## v8 Evolution
EvolutionEngine and evolution animation hooks are included. The current evolution tree is an example/placeholder and should be replaced by the final forms supplied for the game.

## v9 Local training
Training is deterministic and offline; a reaction/tap mini-game can be added without changing the save model.

## v10 Physical VPET
Bluetooth LE protocol constants and packet encoding are included. This is an integration layer, not yet a hardware driver.

## Assets
Put authorized 64x56 transparent PNGs in app/src/main/assets/sprites/.

No server, Render, WebSocket, login or cloud database is required.
