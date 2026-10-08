# Offline v4 — Evolution System

The VPET now has an offline evolution framework.

## Forms

- Basic
- Thunder
- Wind
- Earth

Each form can have:
- required level
- required battle wins
- required happiness
- evolution animation
- dedicated sprite sequence

## Evolution flow

Pet state
→ check requirements
→ play EVOLUTION animation
→ change form
→ save locally
→ continue gameplay

## Save slots

Three independent local slots are supported by SaveSlotManager.

No cloud account is required.

## Next

Add the actual BoBoiBoy forms and replace the example requirements with the project's final evolution tree.
