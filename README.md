# Chord Demo Project for CSCI 6421 GWU

## How To Run

```
./gradlew chord-node:buildApp

# To start a bootstrapping node
java -jar ./chord-node/build/libs/chord-node-1.0.0.RELEASE.jar \
    --spring.profiles.active=default \
    --chord.node-name="${node-name}" \
    --chord.node-port="${port}" \
    --chord.finger-ring-size-bits="${size}" \
    --chord.bootstrapping-node="true"

# To start a normal node
java -jar ./chord-node/build/libs/chord-node-1.0.0.RELEASE.jar \
    --spring.profiles.active=default \
    --chord.node-name="${node-name}" \
    --chord.node-port="${port}" \
    --chord.finger-ring-size-bits="${size}" \
    --chord.bootstrapping-node="false"
    --chord.joining-to-port="${known-node-port}"
```

## Reference

[Chord: A Scalable Peer-to-peer Lookup Protocol
 for Internet Applications](https://pdos.csail.mit.edu/papers/ton:chord/paper-ton.pdf)

[Chord Implementation](http://web.mit.edu/6.033/2001/wwwdocs/handouts/dp2-chord.html) 

[Chord Wikipedia](https://en.wikipedia.org/wiki/Chord_(peer-to-peer))

[Chord: Building a DHT in Golang](https://medium.com/techlog/chord-building-a-dht-distributed-hash-table-in-golang-67c3ce17417b)

[Chord DHT](https://www2.cs.duke.edu/courses/fall18/compsci514/slides/21DHT.pdf)

[Chord Finger Table](http://cseweb.ucsd.edu/~gmporter/classes/fa17/cse124/post/chord-finger-tables/)

[Chord Youtube](https://www.youtube.com/watch?v=q29szpcnorA)

[Donut](http://alevy.github.io/donut/chord_implementation.html)

[wang502/chord](https://github.com/wang502/chord)
