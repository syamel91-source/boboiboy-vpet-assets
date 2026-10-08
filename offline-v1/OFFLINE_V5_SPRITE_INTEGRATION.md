# Offline v5 — 64x56 Sprite Integration

The app now has a production asset interface for the BoBoiBoy VPET sprite system.

## Required asset format

- 64x56 pixels
- PNG
- Transparent background
- Consistent character anchor
- One frame per file

## Directory

Place files in:

`app/src/main/assets/sprites/`

## Naming

`idle_01.png`
`idle_02.png`
`attack_01.png`
`special_01.png`
`hurt_01.png`
`happy_01.png`
`sleep_01.png`
`eat_01.png`
`evolution_01.png`

The repository also supports:
- walk
- sad
- angry
- excited
- victory
- defeat

## Gameplay mapping

Feed → EAT
Train → EXCITED
Sleep → SLEEP
Clean → HAPPY
Attack → ATTACK
Special → SPECIAL
Damage taken → HURT
Victory → VICTORY
Defeat → DEFEAT
Evolution → EVOLUTION

Until actual PNGs are installed, the application can continue using its fallback display.
