load("nashorn:mozilla_compat.js");

importClass(java.lang.System);
importClass(java.lang.Thread);

var speed = 1000;

var running = true;
while(running) {
    wait(speed);
    me.setDeltas(10, 0);
    wait(speed);
    me.setDeltas(0, 10);
    wait(speed);
    me.setDeltas(-10, 0);
    wait(speed);
    me.setDeltas(0, -10);
}

function wait (sleep) {
    Thread.sleep(speed);
    if (java.lang.Thread.interrupted()) running = false;
}