# BoBoiBoy VPET Sprite Asset Guide — v3

The offline app is prepared for production 64x56 VPET frames.

## Frame
- Width: 64 px
- Height: 56 px
- PNG
- Transparent background
- Consistent character anchor/position
- Full-color pixel-art style

## Animation groups

| Group | Suggested frames |
|---|---:|
| Idle | 4 |
| Walk | 6 |
| Eat | 6 |
| Sleep | 4 |
| Happy | 4 |
| Sad | 4 |
| Angry | 4 |
| Hurt | 3 |
| Special | 8 |
| Evolution | 12 |
| Excited | 6 |
| Victory | 6 |
| Defeat | 4 |

## Naming

Use:
`animation_01.png`

Example:
`special_01.png`
`special_02.png`

Place the PNG files in the Android assets sprite directory.

The SpriteEngine maps animation states to these sequences.
