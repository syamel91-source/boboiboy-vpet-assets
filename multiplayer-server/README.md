# BoBoiBoy VPET Multiplayer Server

Online multiplayer server package for the BoBoiBoy VPET project.

## Architecture

Android VPET <-> WSS <-> Node.js WebSocket server <-> WSS <-> Android VPET

Physical VPET hardware connects to Android through BLE.

## Deployment

The server requires Node.js 20+.

```bash
npm install
npm start
```

The server listens on `PORT` or `8080`.

Health endpoint:

`/health`

Production clients should use `wss://`.

## Battle

The server is authoritative for:

- matchmaking
- rooms
- turns
- HP
- Attack
- Special
- Heal
- winner
- disconnect handling

See `server.js` for the protocol implementation.
