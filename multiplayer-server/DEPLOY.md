# Deploy BoBoiBoy VPET Multiplayer

## Render

1. Open the Render dashboard.
2. Create a new Web Service from the GitHub repository:
   syamel91-source/boboiboy-vpet-assets
3. Select the `main` branch.
4. Set Root Directory to:
   `multiplayer-server`
5. Build Command:
   `npm install`
6. Start Command:
   `npm start`
7. Health Check Path:
   `/health`
8. Deploy.

Render will provide an HTTPS public hostname.

The WebSocket endpoint uses the same hostname with `wss://`.

Example:
`wss://boboiboy-vpet-multiplayer.onrender.com`

## Test

Open:
`https://YOUR-HOST/health`

Expected JSON contains:
`"ok": true`

Then configure Android:
`PRODUCTION_WS_URL = "wss://YOUR-HOST"`

## Security before public launch

Add authentication, rate limiting, persistent player identity, and stricter action validation before production release.
