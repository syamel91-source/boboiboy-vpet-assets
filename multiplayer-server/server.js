const http = require("http");
const WebSocket = require("ws");

const PORT = Number(process.env.PORT || 8080);
const server = http.createServer((req, res) => {
  if (req.url === "/health") {
    res.writeHead(200, {"Content-Type": "application/json"});
    res.end(JSON.stringify({ok:true, service:"boboiboy-vpet-multiplayer", version:"0.6.0"}));
    return;
  }
  res.writeHead(404);
  res.end("Not Found");
});

const wss = new WebSocket.Server({server});
const queue = [];
const rooms = new Map();
let nextRoom = 1;

function send(ws, msg) {
  if (ws.readyState === WebSocket.OPEN) ws.send(JSON.stringify(msg));
}
function other(side) { return side === "A" ? "B" : "A"; }
function state(room) {
  return {
    type:"state",
    roomId:room.id,
    players:{A:room.players.A.hp, B:room.players.B.hp},
    turn:room.turn,
    winner:room.winner,
    log:room.log.slice(-12)
  };
}
function broadcast(room) {
  const s = state(room);
  send(room.players.A.ws, s);
  send(room.players.B.ws, s);
}
function makeRoom(a,b) {
  const id = "BB-" + String(nextRoom++).padStart(4,"0");
  const room = {
    id,
    players:{A:{ws:a,hp:100}, B:{ws:b,hp:100}},
    turn:"A",
    winner:null,
    log:[]
  };
  rooms.set(id,room);
  a.room=id; a.side="A";
  b.room=id; b.side="B";
  broadcast(room);
}
function action(room,side,name) {
  if (room.winner || room.turn !== side) return;
  const me=room.players[side], foe=room.players[other(side)];
  if(name==="attack") {
    foe.hp=Math.max(0,foe.hp-12);
    room.log.push(side+" attacked for 12");
  } else if(name==="special") {
    foe.hp=Math.max(0,foe.hp-22);
    room.log.push(side+" used SPECIAL for 22");
  } else if(name==="heal") {
    const before=me.hp;
    me.hp=Math.min(100,me.hp+10);
    room.log.push(side+" healed "+(me.hp-before));
  } else return;
  if(foe.hp<=0) room.winner=side;
  else room.turn=other(side);
  broadcast(room);
}

wss.on("connection",ws=>{
  ws.on("message",raw=>{
    let msg;
    try { msg=JSON.parse(raw.toString()); } catch {
      return send(ws,{type:"error",message:"Invalid JSON"});
    }
    if(msg.type==="matchmake") {
      if(queue.length) makeRoom(queue.shift(),ws);
      else { queue.push(ws); send(ws,{type:"queued"}); }
    } else if(msg.type==="action" && ws.room) {
      const room=rooms.get(ws.room);
      if(room) action(room,ws.side,msg.action);
    } else if(msg.type==="ping") send(ws,{type:"pong"});
  });
  ws.on("close",()=>{
    const i=queue.indexOf(ws);
    if(i>=0) queue.splice(i,1);
    if(ws.room) {
      const room=rooms.get(ws.room);
      if(room) {
        const otherPlayer=room.players[other(ws.side)];
        if(!room.winner) send(otherPlayer.ws,{type:"disconnected"});
        rooms.delete(room.id);
      }
    }
  });
});

server.listen(PORT,"0.0.0.0",()=>console.log("BoBoiBoy VPET server listening on "+PORT));
