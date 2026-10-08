# BoBoiBoy VPET Offline

Offline-first Android VPET.

## Implemented
- Pet Home and persistent local save
- Feed / Train / Sleep / Clean
- Player vs CPU battle
- 64x56 transparent PNG animation pipeline
- Animation mapping for care and battle events
- Evolution engine
- Three-slot save framework
- Offline Bluetooth LE protocol layer
- Audio event hooks

## Current limitation
The sprite renderer and asset contract are included, but the actual character PNG artwork is not embedded automatically. Add your authorized 64x56 sprite files to app/src/main/assets/sprites/.

## No online dependency
No server, Render, WebSocket, account or cloud database is needed.

Open offline-v1 in Android Studio and build the app.
