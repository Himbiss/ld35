load("nashorn:mozilla_compat.js");

importClass(java.lang.System);
importClass(java.lang.Thread);

var speed = 1000;
while(true) {
    me.setDeltas(10, 0);
    Thread.sleep(speed);
    me.setDeltas(0, 10);
    Thread.sleep(speed);
    me.setDeltas(-10, 0);
    Thread.sleep(speed);
    me.setDeltas(0, -10);
    Thread.sleep(speed);
}